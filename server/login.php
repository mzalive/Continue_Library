<?php
include ("conn.php");
include ("cachehandler.php");
if(!is_null($_POST["username"]) && !is_null($_POST["password"]))
{
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo();
	echo json_encode($response);
	mysql_close($conn);
}

function foo(){
		$username = $_POST["username"];
		$password = $_POST["password"];
		$sql = "select user_Password from user where user_Name='$username' and user_Password='$password'";
		$query = mysql_query($sql);
		if(!$query)
			return DATABASE_OPERATION_ERROR;
		
		if($result = mysql_fetch_object($query))
			return RESULT_OK;
		else
			return AUTHORIZATION_ERROR;
	}

?>