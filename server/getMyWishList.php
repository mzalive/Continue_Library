<?php
include ("conn.php");
include ("cachehandler.php");
include ("getWishHeat.php");
if(!is_null($_POST["user_id"]) && !is_null($_POST["start"]) && !is_null($_POST["count"]))
{
	$conn = mysql_open();
	$user_id = $_POST["user_id"];
	$start = $_POST["start"];
	$count = $_POST["count"];
	$cache = new cachehandler($_POST['action']);
	try{
		if($output = $cache -> get($user_id))
		{
			// echo 'has cache!';
		}else{
			$output = $cache -> save("foo", $user_id);
		}
		$output = json_decode($output,true);
		if($output['error_code'] == RESULT_OK){
			$wishs = $output["books"];
			$total = $output["wish_total"];

			$count = $_POST['count'];
			$output["wish_count"] = $count;
			if($total - $start < $count)
			{
				$count = $total-$start;
				$output["wish_count"] =$count;
			}
			$wishs = array_slice($wishs,$start,$count);

			$wishs = get_heat($wishs);

			$output["book_total"]=0;
			$output["book_count"]=0;
			$output["book_start"]=0;

			$output["books"] = $wishs;
		}
		echo json_encode($output,JSON_UNESCAPED_UNICODE);
	}catch(Exception $e){	//更新的时候要在代码里添加throw
		$response = array('error_code' => '999');
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	}
	
	mysql_close($conn);
}

function foo(){
	require_once("getBookInfo.php");
	require_once("buildBook.php");
	$response = array();

	$user_id = $_POST["user_id"];
	$start = $_POST["start"];
	$count = $_POST["count"];

	$sql = "select wish_id from user_wishlist where user_id = '$user_id'";
	$query = mysql_query($sql);
	if(!$query)
		return die_with_response(DATABASE_OPERATION_ERROR,$response);	

	$total_amount = mysql_num_rows($query);

	$response["error_code"] = $total_amount?RESULT_OK:NO_CONTENT;
	$response["wish_total"] = $total_amount;
	$response["wish_start"] = $start;
	$wishs = array();

	while($result = mysql_fetch_object($query)){
		$query_wish = get_wish_info($result -> wish_id);
		if($result_wish = mysql_fetch_object($query_wish)){
			$wish = build_wish($result_wish);
			if(!is_null($wish))
				array_push($wishs, $wish);
			else
				return die_with_response(DATABASE_OPERATION_ERROR,$response);	
		}else
			return die_with_response(DATABASE_OPERATION_ERROR,$response);	

	}
	$response["books"] = $wishs;
	$output = json_encode($response,JSON_UNESCAPED_UNICODE);
	return $output;
}
?>