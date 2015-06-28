<?php
include ("conn.php");
if(!is_null($_GET['book_isbn']) && !is_null($_GET['user_id']))
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
	$conn = mysql_open();

	$book_isbn = $_GET['book_isbn'];
	$user_id = $_GET['user_id'];
	$response = array();

	//transaction lock
	$lock = new filelock($_GET['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$sql = "select book_id from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	$result = mysql_fetch_object($query);
	$book_id = $result -> book_Id;

	$sql_return = "delete from borrowlist where book_id = '$book_id' and user_id = '$user_id' limit 1";
	$query_return = mysql_query($sql_return);
	if(!$query_return)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return BOOK_ALL_RETURNED;
	}

	$sql_add_to_stock = "update book set count = count+1 where book_id = 'book_id'";

	$query_add_to_stock = mysql_query($sql_add_to_stock);
	if(!$query_add_to_stock)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;	
	}

	//need to add functions to check the value of 'count' here?
	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}
?>