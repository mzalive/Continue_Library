<?php
if(!is_null($_POST["start"]) && !is_null($_POST["count"])){
	include ('conn.php');
	include ('cachehandler.php');

	$conn = mysql_open();

	$start = $_POST["start"];
	$count = $_POST["count"];

	$cache = new cachehandler($_POST['action']);
	try{
		if($output = $cache -> get('1'))
		{
			// echo 'has cache!';
		}else{
			$output = $cache -> save("foo", '1');
		}
		$output = json_decode($output,true);
		if($output['error_code'] == RESULT_OK){
			$books = $output["books"];
			$total = $output["book_total"];

			$count = $_POST['count'];
			$output["book_count"] = $count;
			if($total - $start < $count)
			{
				$count = $total-$start;
				$output["book_count"] =$count;
			}
			$books = array_slice($books,$start,$count);
			
			$output["wish_start"] = 0;
			$output["wish_total"] = 0;
			$output["wish_count"] = 0;

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
	require_once("dieError.php");

	$response = array();
	$books = array();

	$start = $_POST["start"];
	$count = $_POST["count"];

	$sql_book = "select * from book";
	$query_book =  mysql_query($sql_book);
	if(!$query_book)
		return die_with_response(DATABASE_OPERATION_ERROR,$response);

	$total_amount = mysql_num_rows($query_book);
	$response["error_code"] = $total_amount?RESULT_OK:NO_CONTENT;
	$response["book_total"] = $total_amount;
	$response["book_start"] = $start;

	while($result_book = mysql_fetch_object($query_book)){
		$book = build_book($result_book);
		if(!is_null($book))
			array_push($books, $book);
		else
			return die_with_response(DATABASE_OPERATION_ERROR,$response);
	}
	$response["books"] = $books;
	$output = json_encode($response,JSON_UNESCAPED_UNICODE);
	return $output;
}
?>