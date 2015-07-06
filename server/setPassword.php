<?php
include ("conn.php");
if(!is_null($_POST['old_password']) && !is_null($_POST['new_password']) && !is_null($_POST['user_id'])){
	$response = array();

	$flag = foo();

	$response["error_code"] = $flag;

	echo json_encode($response,JSON_UNESCAPED_UNICODE);
}

function foo(){

	require_once("lock.php");

	$conn = mysql_open();
	$old_password = $_POST['old_password'];
	$new_password = $_POST['new_password'];
	$user_id = $_POST['user_id'];

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();


	$sql = "select user_Name from user where user_id = '$user_id' and user_password = '$old_password'";
	$query = mysql_query($sql);
	if(mysql_num_rows($query))
	{
		$sql_update = "update user set user_password = '$new_password' where user_id = '$user_id'";
		$query_update = mysql_query($sql_update);
		$lock -> release();

		if($query_update){
			return RESULT_OK;
		}

		return DATABASE_OPERATION_ERROR;
	}
	else
	{
		$lock -> release();
		return AUTHORIZATION_ERROR;
	}
	mysql_close($conn);
}

?>