<?php
include ("conn.php");
if(!is_null($_POST['start']) && !is_null($_POST['count'])){
	$conn = mysql_open();
	echo json_encode(foo(),JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}

function foo(){
	$response = array();

	$start = $_POST['start'];
	$count = $_POST['count'];

	$sql = "select * from user";
	$query = mysql_query($sql);
	if(!$query){
		$response['error_code'] = DATABASE_OPERATION_ERROR;
		return $response;
	}

	$response['error_code'] = mysql_num_rows($query)?RESULT_OK:NO_CONTENT;
	$response['start'] = $_POST['start'];
	$response['total'] = mysql_num_rows($query);
	if($count+$start>$response['total'])
		$response['count'] = $response['total']-$start;
	else
		$response['count'] = $count;

	$users = array();

	while($result = mysql_fetch_object($query)){
		$user = array(
			'user_id' => ($result -> user_Id),
			'username' => ($result -> user_Name),
			'password' => ($result -> user_Password),
			'avatar' => ($result -> user_Avatar)
			);
		array_push($users, $user);
	}
	$response['users'] = $users;
	return $response;
}
?>