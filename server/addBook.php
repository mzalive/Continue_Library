<?php
include ("conn.php");
include ("addBookHelper.php");
include ("cleanCache.php");
if(!is_null($_POST['book_isbn']) && !is_null($_POST['total_amount'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = add_to_book("book");

	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);

	clean_book_cache();
}

?>