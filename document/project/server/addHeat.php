<?php
include ("conn.php");
if(!is_null($_POST["book_isbn"]) && !is_null($_POST["user_id"]) && !is_null($_POST["is_owned"])){
	$conn = mysql_open();
	$response = array();

	//global $wish_tags = array();

	$response['error_code'] =$_POST["is_owned"]?add_heat():add_to_wish();

	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);

	clean_cache();
}

function add_heat(){
	require_once("lock.php");

	$book_isbn = $_POST['book_isbn'];
	$user_id = $_POST['user_id'];

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();


	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");
	$sql = "select wish_Id from wish where wish_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}
	if(!mysql_num_rows($query)){
		mysql_query("ROLLBACK");
		$lock -> release();
		return add_to_wish();
	}

	$wish_id = mysql_fetch_object($query) -> wish_Id;

	$sql_check = "select uwish_id from user_wishlist where user_id = '$user_id' and wish_Id = '$wish_id'";
	$query_check = mysql_query($sql_check);
	if(!$query_check){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}
	if(mysql_num_rows($query_check)){
		mysql_query("ROLLBACK");
		$lock -> release();
		return ALREADY_ADDED;
	}

	$sql_add = "insert into user_wishlist(user_Id,wish_Id) values('$user_id','$wish_id')";
	$query_add = mysql_query($sql_add);
	if(!$query_add){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	$sql_heat = "update wish_heat set wish_heat = wish_heat+1 where wish_id = '$wish_id'";
	$query_heat = mysql_query($sql_heat);
	if(!$query_heat){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}
	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}

function add_to_wish(){
	require_once("lock.php");

	global $wish_tags;

	$book_isbn = $_POST['book_isbn'];
	$user_id = $_POST['user_id'];

	$url = DOUBAN_API.$book_isbn;
	$info = file_get_contents($url);
	
	$book = json_decode($info);

	$wish_title = $book -> title;
	$wish_subtitle = $book -> subtitle;
	$wish_isbn = $book -> isbn13;
	$wish_publisher = $book -> publisher;
	$wish_publishdate = $book -> pubdate;
	$wish_images = ($book -> images);
	$wish_imageurl = $wish_images -> large;
	$wish_summary = $book -> summary;

	$wish_tags = $book -> tags;

	$wish_author = $book -> author;

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$sql = "insert into wish(wish_title,wish_subtitle,wish_isbn,wish_publisher,wish_publishdate,wish_imageurl,wish_summary)
	values('$wish_title','$wish_subtitle','$wish_isbn','$wish_publisher','$wish_publishdate','$wish_imageurl','$wish_summary')";
	
	$query = mysql_query($sql);
	if(!$query){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	$wish_id = mysql_insert_id();
	$sql_heat = "insert into wish_heat(wish_id,wish_heat) values('$wish_id',1)";
	$query_heat = mysql_query($sql_heat);
	if(!$query_heat){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	$sql_want = "insert into user_wishlist(user_Id,wish_Id) values('$user_id','$wish_id')";
	$query_want = mysql_query($sql_want);
	if(!$query_want){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	foreach ($wish_tags as $tag) {
		$tag_name = $tag -> name;
		$sql_tag = "insert into tag_index(tag_Content) values('$tag_name')";
		$query_tag = mysql_query($sql_tag);
		if(!$query_tag){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}

		$tag_id = mysql_insert_id();
		$sql_wish_tag = "insert into tag_wish(tag_id,wish_id) values('$tag_id','$wish_id')";
		$query_wish_tag = mysql_query($sql_wish_tag);
		if(!$query_wish_tag){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}

	foreach ($wish_author as $author) {
		$sql_author = "insert into wish_author(wish_id,author) values('$wish_id','$author')";
		$query_author = mysql_query($sql_author);
		if(!$query_author){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}

	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}

function clean_cache(){
	require_once("cachehandler.php");

	$cache = new cachehandler("getMyWishlist");
	$cache -> remove($_POST['user_id']);

	$cache = new cachehandler("getWishlist");
	$cache -> remove($_POST['user_id']);

	$cache = new cachehandler("search_wish");
	$cache -> clean();

}
?>