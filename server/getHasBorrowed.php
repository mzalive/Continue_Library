<?php
include("conn.php");
if(!is_null($_POST['user_id']) && !is_null($_POST['book_isbn'])){
	$conn = mysql_open();
	echo json_encode(foo(),JSON_UNESCAPED_UNICODE);
	mysql_close($conn);
}

function foo(){
	require_once("dieError.php");
	$response = array();

	$book_isbn = $_POST['book_isbn'];
	$user_id = $_POST['user_id'];

	$sql = "select book_Id from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query || !mysql_num_rows($query))
		return die_with_response(DATABASE_OPERATION_ERROR,$response);

	$book_id = mysql_fetch_object($query) -> book_Id;

	$sql_has_borrowed = "select borrow_Id from borrowlist where book_id = '$book_id' and user_id = '$user_id'";
	$query_has_borrowed = mysql_query($sql_has_borrowed);
	if(!$query)
		return die_with_response(DATABASE_OPERATION_ERROR,$response);
	else{
		$response['error_code'] = RESULT_OK;
		if(mysql_num_rows($query_has_borrowed))
			$response['has_borrowed'] = true;
		else
			$response['has_borrowed'] = false;
	}
	return $response;
}
?>