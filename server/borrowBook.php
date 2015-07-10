<?php
include ("conn.php");
include ("cleanCache.php");
if(!is_null($_POST['book_isbn']) && !is_null($_POST['user_id'])){

	$conn = mysql_open();
	$response = array();

	$flag = foo();
	$response["error_code"] = $flag;
	echo json_encode($response,JSON_UNESCAPED_UNICODE);

	mysql_close($conn);
	clean_book_cache();
}

function foo(){
	require_once("lock.php");
	require_once("dieError.php");

	$book_isbn = $_POST['book_isbn'];
	$user_id = $_POST['user_id'];

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	//check if the book is available
	$sql = "select book_Id from book where book_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query)
		return die_with_message($lock,NO_CONTENT);
	$result = mysql_fetch_object($query);
	$book_Id = $result -> book_Id;

	$sql_amount = "select book_Amount_Available from book_amount where book_Id = '$book_Id'";
	$query_amount = mysql_query($sql_amount);
	if(!$query_amount)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);
	$result_amount = mysql_fetch_object($query_amount);
	$count = $result_amount -> book_Amount_Available;

	if($count<=0)
		return die_with_message($lock,BOOK_ALL_BORROWED);

	//update book amount.
	$sql_book = "update book_amount set book_Amount_Available = $count-1 where book_id = '$book_Id'";
	$query_book = mysql_query($sql_book);
	if(!$query_book)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	//insert new borrow records into tables.
	$sql_borrowlist = "insert into borrowlist(book_Id,user_Id) values('$book_Id','$user_id')";
	$sql_user_borrowlist = "insert into user_borrowlist(user_Id,book_Id) values('$user_id','$book_Id')";
	$query_borrowlist = mysql_query($sql_borrowlist);
	$query_user_borrowlist = mysql_query($sql_user_borrowlist);
	if(!$query_borrowlist && !$query_user_borrowlist)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);
	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}
?>