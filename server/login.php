<?php
include ("conn.php");
include ("cachehandler.php");
if(!is_null($_POST["username"]) && !is_null($_POST["password"]))
{
	$conn = mysql_open();
	$response = array();
	echo json_encode(foo());
	mysql_close($conn);
}

function foo(){
		$response = array();

		$username = $_POST["username"];
		$password = $_POST["password"];
		$sql = "select user_Id from user where user_Name='$username' and user_Password='$password'";
		$query = mysql_query($sql);
		if(!$query){
			$response['error_code'] = DATABASE_OPERATION_ERROR;
			return $response;
		}
		
		if($result = mysql_fetch_object($query)){
			$response['error_code'] = RESULT_OK;
			$response['user_id'] = $result -> user_Id;
			return $response;
		}
		else{
			$response['error_code'] = DATABASE_OPERATION_ERROR;
			return $response;
		}
	}

?>