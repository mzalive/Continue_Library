<?php
include("conn.php");
if(!is_null($_POST['username']) && !is_null($_POST['password']) ){
	$conn = mysql_open();
	echo json_encode($foo(),JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}

function foo(){
	$response = array();

	$username = $_POST['username'];
	$password = $_POST['password'];
	$avatar = is_null($_POST['avatar'])?null:$_POST['avatar'];

	$sql = "insert into user(user_Name, user_Password, user_Avatar) values('$username','$password','$avatar')"
	$query = mysql_query($sql);
	if(!$query){
		$response['error_code'] =  DATABASE_OPERATION_ERROR;
		return $response;
	}
	else{
		$response['error_code'] =  RESULT_OK;
		$response['user_id'] = mysql_insert_id();
		return $response;
	}
}
?>