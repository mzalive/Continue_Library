<?php
include ('conn.php');
if(!is_null($_POST["book_isbn"]))
{
	$conn = mysql_open();

	echo json_encode(foo(),JSON_UNESCAPED_UNICODE);
}

function foo(){
	require_once("dieError.php");
	$book_isbn = $_POST["book_isbn"];
	$response = array();

	$sql = "select book_Id from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);

	if(!$query)
		die_with_response(DATABASE_OPERATION_ERROR,$response);

	if(!mysql_num_rows($query))
		die_with_response(NO_CONTENT,$response);

	$book_id = mysql_fetch_object($query) -> book_Id;

	$sql_count = "select book_amount_available from book_amount where book_Id = '$book_id'";
	$query_count = mysql_query($sql_count);

	if(!$query_count)
		die_with_response(DATABASE_OPERATION_ERROR,$response);

	$response['error_code'] = RESULT_OK;
	$response['amount_available'] = mysql_fetch_object($query_count) -> book_amount_available;

	return $response;
}
?>