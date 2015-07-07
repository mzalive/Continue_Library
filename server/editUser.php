<?php
include("conn.php");
if(!is_null($_POST['user_id']) && !is_null($_POST['username']) && !is_null($_POST['password']) ){
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo();
	echo json_encode($response,JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}

function foo(){
	$user_id = $_POST['user_id'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$avatar = is_null($_POST['avatar'])?null:$_POST['avatar'];

	$sql = "update user set user_Name='$username', user_Password='$password', user_Avatar='$avatar' where user_id = '$user_id'";
	$query = mysql_query($sql);
	if(!$query)
		return DATABASE_OPERATION_ERROR;
	else
		return RESULT_OK;
}
?>