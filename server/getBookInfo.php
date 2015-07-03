<?php
function get_book_info($book_id){
	$sql = "select * from book where book_id = '$book_id'";
	$query = mysql_query($sql);
	return $query;
}
function get_wish_info($wish_id){
	$sql = "select * from wish where wish_id = '$wish_id'";
	$query = mysql_query($sql);
	return $query;
}
?>