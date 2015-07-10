<?php

function clean_book_cache(){
	require_once("cachehandler.php");
	$cache = new cachehandler("getBookList");
	$cache -> remove('1');

	if(isset($_POST['user_id']) &&!is_null($_POST['user_id'])){
		$cache = new cachehandler("getMyBorowList");
		$cache -> remove($_POST['user_id']);
	}

	$cache = new cachehandler("search_book");
	$cache -> clean();
}
function clean_wish_cache(){
	require_once("cachehandler.php");

	$cache = new cachehandler("getMyWishList");
	$cache -> clean();

	$cache = new cachehandler("getWishList");
	$cache -> clean();
	
	$cache = new cachehandler("search_wish");
	$cache -> clean();
}
?>