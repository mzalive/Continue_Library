<?php
include ("conn.php");
include ("deleteBookHelper.php");
include ("cleanCache.php");
if(!is_null($_POST['book_isbn'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo("book");
	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
	clean_book_cache();
}

?>