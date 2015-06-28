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
		$wishs = $output["books"];
		$total = $output["total"];
		if($start+$count>$total)
			$count = $total-$start;
		$wishs = array_slice($wishs,$start,$count);
		$output["wishs"] = $wishs;
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

	$sql = "select wish_id from user_wishlist where user_id = '$user_id'";
	$query = mysql_query($sql);

	$total_amount = mysql_num_rows($query);

	$response["error_code"] = 1000;
	$response["total"] = $total_amount;
	$response["count"] = $count;
	if($total_amount - $start < $count)
		$response["count"] = $total_amount - $start;
	$response["start"] = $start;
	$wishs = array();

	while($result = mysql_fetch_object($query)){
		$bId = $result -> wish_id;

		$sql_author = "select author from wish_author where wish_Id = '$bId'";
		$query_author = mysql_query($sql_author);
		$author = array();

		$sql_want = "select uwish_Id from user_wishlist where user_Id = '$user_id' and wish_Id = '$bId'";
		$query_want = mysql_query($sql_want);
		$want = mysql_num_rows($query_want)==0?false:true;


		while($result_author = mysql_fetch_object($query_author)){
			array_push($author, $result_author -> author);
		}
		$query_wish = getWishInfo($bId);
		while($result_wish = mysql_fetch_object($query_wish)){
			$wish_title = $result_wish -> wish_Title;
			$wish_subtitle = $result_wish -> wish_Subtitle;
			$wish_isbn = $result_wish -> wish_Isbn;
			$wish_publisher = $result_wish -> wish_Publisher;
			$wish_imageurl = $result_wish -> wish_ImageUrl;
			$wish_summary = $result_wish -> wish_Summary;
			$wish_publishdate = $result_wish -> wish_PublishDate;

			$wish = array(
				'isbn' => $wish_isbn,
				'title' => $wish_title,
				'subtitle' => $wish_subtitle,
				'publisher' => $wish_publisher,
				'author' => $author,
				'summary' => $wish_summary,
				'image' => $wish_imageurl,
				'pubdate' => $wish_publishdate,
				'status' => array(
					'isInStock' => false,
					'amount_total' => 0,
					'isWanted' => $want
					)
				);
			array_push($wishs, $wish);
		}

	}
	$response["books"] = $wishs;
	$output = json_encode($response);
	return $output;
}
?>