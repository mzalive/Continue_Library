<?php
include ("conn.php");
include ("addBookHelper.php");
if(!is_null($_GET['book_isbn']) && !is_null($_GET['total_amount'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = add_to_book("book");

	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);

	clean_cache();
}

function clean_cache(){
	require_once("cachehandler.php");
	$cache = new cachehandler("getBooklist");
	$cache -> remove('1');

	$cache = new cachehandler("search_book");
	$cache -> clean();
}

?>