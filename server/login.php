<?php
include ("conn.php");
include ("cachehandler.php");
if(!is_null($_GET["username"]) && !is_null($_GET["password"]))
{
	$conn = mysql_open();
	$response = array();
	$response['error_code'] = foo();
	echo json_encode($response);
	mysql_close($conn);
}

function foo(){
		$username = $_GET["username"];
		$password = $_GET["password"];
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