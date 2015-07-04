<?php
include ("conn.php");
include ("addBookHelper.php");
include ("logger.php");
if(!is_null($_GET["book_isbn"]) && !is_null($_GET["user_id"]) && !is_null($_GET["is_owned"])){
	$conn = mysql_open();
	$response = array();

	//global $wish_tags = array();

	$response['error_code'] =$_GET["is_owned"]?add_heat():add_to_book("wish");
	if($response['error_code'] != RESULT_OK){
		ServerLogger::d(json_encode($response,JSON_UNESCAPED_UNICODE)."\n".mysql_error());
	}

	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);

	clean_cache();
}

function add_heat(){
	require_once("lock.php");

	$book_isbn = $_GET['book_isbn'];
	$user_id = $_GET['user_id'];

	//transaction lock
	$lock = new filelock($_GET['action']);
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


function clean_cache(){
	require_once("cachehandler.php");

	$cache = new cachehandler("getMyWishlist");
	$cache -> remove($_GET['user_id']);

	$cache = new cachehandler("getWishlist");
	$cache -> remove($_GET['user_id']);
	
	$cache = new cachehandler("search_wish");
	$cache -> clean();
}
?>