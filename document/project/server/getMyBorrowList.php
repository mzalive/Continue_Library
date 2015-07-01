<?php
include ("conn.php");
include ("cachehandler.php");
if(!is_null($_GET["user_id"]) && !is_null($_GET["start"]) && !is_null($_GET["count"]))
{
	$conn = mysql_open();

	$user_id = $_GET["user_id"];
	$start = $_GET["start"];
	$count = $_GET["count"];

	$cache = new cachehandler($_GET['action']);

	try{
		if($output = $cache -> get($user_id))
		{
			// echo 'has cache!';
		}else{
			$output = $cache -> save("foo", $user_id);
		}
		$output = json_decode($output,true);
		if($output['error_code'] == RESULT_OK){
			$books = $output["books"];
			$total = $output["book_total"];

			$count = $_GET['count'];
			$output["book_count"] = $count;
			if($total - $start < $count)
			{
				$count = $total-$start;
				$output["book_count"] =$count;
			}
			$books = array_slice($books,$start,$count);

			$output["books"] = $books;
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

	$user_id = $_GET["user_id"];
	$start = $_GET["start"];
	$count = $_GET["count"];

	$sql = "select book_id from borrowlist where user_id = '$user_id'";
	$query = mysql_query($sql);

	$total_amount = mysql_num_rows($query);

	$response["error_code"] = $total_amount?RESULT_OK:NO_CONTENT;
	$response["book_total"] = $total_amount;
	$response["book_start"] = $start;
	$books = array();

	while($result = mysql_fetch_object($query)){
		$bId = $result -> book_id;
		$sql_author = "select author from book_author where book_Id = '$bId'";
		$query_author = mysql_query($sql_author);
		$author = array();
		while($result_author = mysql_fetch_object($query_author)){
			array_push($author, $result_author -> author);
		}
		$query_book = get_book_info($bId);
		while($result_book = mysql_fetch_object($query_book)){
			$book = buildBook($result_book);
			array_push($books, $book);
		}

	}
	$response["books"] = $books;
	$output = json_encode($response,JSON_UNESCAPED_UNICODE);
	return $output;
}
?>