<?php
include ("conn.php");
include ("cachehandler.php");
if(!is_null($_GET["username"]) && !is_null($_GET["password"]))
{

	$username = $_GET["username"];

	echo foo();
}

function foo(){
		$conn = mysql_open();
		$response = array();
		$flag = 0;
		$username = $_GET["username"];
		$password = $_GET["password"];
		$sql = "select user_Password from user where user_Name='$username' and user_Password='$password'";
		$query = mysql_query($sql);
		
		while($result = mysql_fetch_object($query))
		{
			$flag = 1;
			$response['error_code'] = '1000';
			break;
		}
		if($flag == 0)
		{
			$response['error_code'] = '2001';
		}
		$output = json_encode($response,JSON_UNESCAPED_UNICODE);
	    return $output;
		mysql_close($conn);
	}

?>