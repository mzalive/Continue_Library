<?php
include ("conn.php");
include ('cachehandler.php');
include ("getWishHeat.php");
if(!is_null($_POST["start"]) && !is_null($_POST["count"]) && !is_null($_POST["user_id"])){
	$conn = mysql_open();

	$start = $_POST["start"];
	$count = $_POST["count"];
	$user_id = $_POST["user_id"];

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
	require_once("buildBook.php");
	$response = array();

	$start = $_POST["start"];
	$count = $_POST["count"];

	$sql_wish = "select * from wish";
	$query_wish =  mysql_query($sql_wish);

	$total_amount = mysql_num_rows($query_wish);

	$response["error_code"] = $total_amount?RESULT_OK:NO_CONTENT;
	$response["wish_total"] = $total_amount;
	$response["wish_start"] = $start;
	$wishs = array();

	while($result_wish = mysql_fetch_object($query_wish)){
		$wish = build_wish($result_wish);
		array_push($wishs, $wish);
	}
	$response["books"] = $wishs;
	$output = json_encode($response);
	return $output;
}
?>