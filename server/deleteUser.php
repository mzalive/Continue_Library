<?php
include('conn.php');
if(!is_null($_POST['user_id'])){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo();
	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}

function foo(){
	$user_id = $_POST['user_id'];

	$sql = "delete from user where user_id = '$user_id'";
	$query = mysql_query($sql);
	if(!$query)
		return DATABASE_OPERATION_ERROR;
	else
		return RESULT_OK;
}
?>