<?php
include ("conn.php");
include ("editBookHelper.php");
if(!is_null($_POST['wish_title']) && !is_null($_POST['wish_subtitle']) && !is_null($_POST['wish_isbn']) 
	&& !is_null($_POST['wish_publisher']) && !is_null($_POST['wish_imageurl']) && !is_null($_POST['wish_summary'])
	&& !is_null($_POST['wish_publish_date']) && !is_null($_POST['wish_author'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = $foo("wish");
	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}
?>