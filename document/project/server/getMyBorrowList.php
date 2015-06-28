<?php
include ("conn.php");
if(!is_null($_GET["user_id"]) && !is_null($_GET["start"]) && !is_null($_GET["count"]))
{
	$user_id = $_GET["user_id"];
	$start = $_GET["start"];
	$count = $_GET["count"];

	try{
		$output = foo();
		$output = json_decode($output,true);
		$books = $output["books"];
		$total = $output["total"];
		if($start+$count>$total)
			$count = $total-$start;
		$books = array_slice($books,$start,$count);
		$output["books"] = $books;
		echo json_encode($output,JSON_UNESCAPED_UNICODE);
	}catch(Exception $e){	//更新的时候要在代码里添加throw
		$response = array('error_code' => '999');
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	}
}

function foo(){
	require_once("getBookInfo.php");
	$conn = mysql_open();
	$response = array();

	$user_id = $_GET["user_id"];
	$start = $_GET["start"];
	$count = $_GET["count"];

	$sql = "select book_id from borrowlist where user_id = '$user_id'";
	$query = mysql_query($sql);

	$total_amount = mysql_num_rows($query);

	$response["error_code"] = 1000;
	$response["total"] = $total_amount;
	$response["count"] = $count;
	if($total_amount - $start < $count)
		$response["count"] = $total_amount - $start;
	$response["start"] = $start;
	$books = array();

	while($result = mysql_fetch_object($query)){
		$bId = $result -> book_id;
		$sql_author = "select author from book_author where book_Id = '$bId'";
		$query_author = mysql_query($sql_author);
		$author = array();
		while($result_author = mysql_fetch_object($query_author)){
			array_push($author, $result_author -> author);
		}
		$query_book = getBookInfo($bId);
		while($result_book = mysql_fetch_object($query_book)){
			$book_title = $result_book -> book_Title;
			$book_subtitle = $result_book -> book_Subtitle;
			$book_isbn = $result_book -> book_Isbn;
			$book_publisher = $result_book -> book_Publisher;
			$book_imageurl = $result_book -> book_ImageUrl;
			$book_summary = $result_book -> book_Summary;
			// $book_amount_available = $result_book -> book_Amount_Available;
			$book_amout_total = $result_book -> book_Amount_Total;
			$book_publishdate = $result_book -> book_PublishDate;

			$book = array(
				'isbn' => $book_isbn,
				'title' => $book_title,
				'subtitle' => $book_subtitle,
				'publisher' => $book_publisher,
				'author' => $author,
				'summary' => $book_summary,
				'image' => $book_imageurl,
				'pubdate' => $book_publishdate,
				'status' => array(
					'isInStock' => false,
					'amount_total' => $book_amout_total,
					'isWanted' => false
					)
				);
			array_push($books, $book);
		}

	}
	$response["books"] = $books;
	$output = json_encode($response);
	return $output;
}
?>