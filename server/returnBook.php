<?php
include ("conn.php");
if(!is_null($_POST['book_isbn']) && !is_null($_POST['user_id']))
{
	$conn = mysql_open();
	$response = array();

	$flag = foo();
	$response["error_code"] = $flag;
	echo json_encode($response,JSON_UNESCAPED_UNICODE);

	mysql_close($conn);
}

function foo()
{
	require_once("lock.php");
	require_once("dieError.php");
	$conn = mysql_open();

	$book_isbn = $_POST['book_isbn'];
	$user_id = $_POST['user_id'];
	$response = array();

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$sql = "select book_id from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	$result = mysql_fetch_object($query);
	$book_id = $result -> book_id;

	$sql_check = "select borrow_Id from borrowlist where book_id = '$book_id'";
	$query_check = mysql_query($sql_check);
	if(!$query_check)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);
	if(mysql_num_rows($query_check) == 0)
		return die_with_message($lock,BOOK_ALL_RETURNED);

	$sql_return = "delete from borrowlist where book_id = '$book_id' and user_id = '$user_id' limit 1";
	$query_return = mysql_query($sql_return);
	if(!$query_return)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	$sql_add_to_stock = "update book_amount set book_amount_available = book_amount_available+1 where book_id = '$book_id'";

	$query_add_to_stock = mysql_query($sql_add_to_stock);
	if(!$query_add_to_stock)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	//need to add functions to check the value of 'count' here?
	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}
?>