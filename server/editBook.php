<?php
include ("conn.php");
include ("editBookHelper.php");
include ("cleanCache.php");
if(!is_null($_POST['book_title']) && !is_null($_POST['book_subtitle']) && !is_null($_POST['book_isbn']) 
	&& !is_null($_POST['book_publisher']) && !is_null($_POST['book_summary'])
	&& !is_null($_POST['book_amount_total']) && !is_null($_POST['book_publish_date']) && !is_null($_POST['book_amount_available'])
	&& !is_null($_POST['book_author'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo("book");
	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
	clean_book_cache();
}

?>