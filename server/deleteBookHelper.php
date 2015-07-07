<?php

function foo($target){
	require_once("lock.php");
	require_once("dieError.php");

	$is_book = $target=="book"?true:false;

	$book_isbn = $_POST[$target.'_isbn'];

	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$sql = "select ".$target."_Id from ".$target." where ".$target."_isbn = '$book_isbn'";
	$query = mysql_query($sql);

	if(!$query)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	$book_Id = -1;
	if($is_book)
		$book_id = mysql_fetch_object($query) -> book_Id;
	else
		$book_id = mysql_fetch_object($queyr) -> wish_Id;

	$sql_delete_author = "delete from ".$target."_author where ".$target."_id = '$book_id'";
	$query_delete_author = mysql_query($sql_delete_author);
	if(!$query_delete_author)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	$sql_delete_tag_book = "delete from tag_".$target." where ".$target."_id = '$book_id'";
	$query_delete_tag_book = mysql_query($sql_delete_tag_book);
	if(!$query_delete_tag_book)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	//should corresponding records in table tag_index be deleted as well?

	if($is_book){
		$sql_delete_borrow = "delete from borrowlist where book_id = '$book_id'";
		$query_delete_borrow = mysql_query($sql_delete_borrow);
		if(!$query_delete_borrow)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);

		$sql_delete_user_borrow = "delete from user_borrowlist where book_id = '$book_id'";
		$query_delete_user_borrow = mysql_query($sql_delete_user_borrow);
		if(!$query_delete_user_borrow)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);

		$sql_delete_amount = "delete from book_amount where book_id = '$book_id'";
		$query_delete_amount = mysql_query($sql_delete_amount);
		if(!$query_delete_amount)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);
	}else{
		$sql_delete_heat = "delete from heat where wish_id = '$book_id'";
		$query_delete_heat = mysql_query($sql_delete_heat);
		if(!$query_delete_heat)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);

		$sql_delete_user_wish = "delete from user_wishlist where wish_id = '$book_id'";
		$query_delete_user_wish = mysql_query($sql_delete_user_wish);
		if(!$query_delete_user_wish)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);			
	}

	$sql_delete_book = "delete from ".$target." where ".$target."_id = '$book_id'";
	$query_delete_book = mysql_query($sql_delete_book);
	if(!$query_delete_book)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}
?>