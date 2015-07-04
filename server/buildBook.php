<?php
function build_book($result_book)
{
	$book_id = $result_book -> book_Id;
	$book_title = $result_book -> book_Title;
	$book_subtitle = $result_book -> book_Subtitle;
	$book_isbn = $result_book -> book_Isbn;
	$book_publisher = $result_book -> book_Publisher;
	$book_imageurl = $result_book -> book_ImageUrl;
	$book_summary = $result_book -> book_Summary;
	$book_amout_total = $result_book -> book_Amount_Total;
	$book_publishdate = $result_book -> book_PublishDate;

	$sql_author = "select author from book_author where book_Id = '$book_id'";
	$query_author = mysql_query($sql_author);
	$author = array();
	while($result_author = mysql_fetch_object($query_author)){
		array_push($author, $result_author -> author);
	}

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
			'is_in_stock' => false,
			'amount_total' => $book_amout_total,
			'heat' => 0,
			'is_wanted' => false
			)
		);
	return $book;
}

function build_wish($result_wish){

	$user_id = $_GET["user_id"];

	$wish_id = $result_wish -> wish_Id;
	$wish_title = $result_wish -> wish_Title;
	$wish_subtitle = $result_wish -> wish_Subtitle;
	$wish_isbn = $result_wish -> wish_Isbn;
	$wish_publisher = $result_wish -> wish_Publisher;
	$wish_imageurl = $result_wish -> wish_ImageUrl;
	$wish_summary = $result_wish -> wish_Summary;
	$wish_publishdate = $result_wish -> wish_PublishDate;

	$sql_author = "select author from wish_author where wish_Id = '$wish_id'";
	$query_author = mysql_query($sql_author);
	$author = array();

	$sql_want = "select uwish_Id from user_wishlist where user_Id = '$user_id' and wish_Id = '$wish_id'";
	$query_want = mysql_query($sql_want);
	$want = mysql_num_rows($query_want)==0?false:true;

	while($result_author = mysql_fetch_object($query_author)){
		array_push($author, $result_author -> author);
	}

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
			'is_in_stock' => false,
			'amount_total' => 0,
			'is_wanted' => $want
			)
		);
	return $wish;
}
?>