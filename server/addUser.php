<?php
include("conn.php");
if(!is_null($_POST['username']) && !is_null($_POST['password']) ){
	$conn = mysql_open();
	echo foo();
	mysql_close($conn);
}

function foo(){
	require_once("dieError.php");
	$response = array();

	$username = $_POST['username'];
	$password = $_POST['password'];
	$avatar = !isset($_POST['avatar'])?null:$_POST['avatar'];

	$sql = "insert into user(user_Name, user_Password, user_Avator) values('$username','$password','$avatar')";
	$query = mysql_query($sql);
	if(!$query)
		echo die_with_response(DATABASE_OPERATION_ERROR,$response);
	else{
		$response['error_code'] =  RESULT_OK;
		$response['user_id'] = mysql_insert_id();
		return json_encode($response,JSON_UNESCAPED_UNICODE);
	}
}
?>