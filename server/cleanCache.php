<?php

function clean_book_cache(){
	require_once("cachehandler.php");
	$cache = new cachehandler("getBooklist");
	$cache -> remove('1');

	$cache = new cachehandler("search_book");
	$cache -> clean();
}
function clean_wish_cache(){
	require_once("cachehandler.php");

	$cache = new cachehandler("getMyWishlist");
	$cache -> remove($_POST['user_id']);

	$cache = new cachehandler("getWishlist");
	$cache -> remove($_POST['user_id']);
	
	$cache = new cachehandler("search_wish");
	$cache -> clean();
}
}