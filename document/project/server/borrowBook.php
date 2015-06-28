<?php
include ("conn.php");
if(!is_null($_GET['book_isbn']) && !is_null($_GET['user_id'])){

	$conn = mysql_open();
	$response = array();

	$flag = foo();
	$response["error_code"] = $flag;
	echo json_encode($response,JSON_UNESCAPED_UNICODE);

	mysql_close($conn);
}

function foo(){
	require_once("lock.php");

	$book_isbn = $_GET['book_isbn'];
	$user_id = $_GET['user_id'];

	//transaction lock
	$lock = new filelock($_GET['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	//check if the book is available
	$sql = "select book_Id, book_Amount_Available from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	$result = mysql_fetch_object($query);
	$count = $result -> book_Amount_Available;
	$book_Id = $result -> book_Id;
	if($count<0)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return BOOK_ALL_BORROWED;
	}

	//update book amount.
	$sql_book = "update book set book_Amount_Available = $count-1 where book_isbn = '$book_isbn'";
	$query_book = mysql_query($sql_book);
	if(!$query_book)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	//insert new borrow records into tables.
	$sql_borrowlist = "insert into borrowlist(book_Id,user_Id) values('$book_Id','$user_id')";
	$sql_user_borrowlist = "insert into user_borrowlist(user_Id,book_Id) values('$user_id','$book_Id')";
	$query_borrowlist = mysql_query($sql_borrowlist);
	$query_user_borrowlist = mysql_query($sql_user_borrowlist);
	if(!$query_borrowlist && !$query_user_borrowlist)
	{
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}

?>