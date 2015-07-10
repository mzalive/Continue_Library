<?php
function foo($target){

	require_once("lock.php");
	require_once("dieError.php");

	$is_book = ($target=="book")?true:false;

	$book_title = $_POST[$target."_title"];
	$book_subtitle = $_POST[$target."_subtitle"];
	$book_isbn = $_POST[$target."_isbn"];
	$book_publisher = $_POST[$target."_publisher"];
	$book_summary = $_POST[$target."_summary"];
	$book_publish_date = $_POST[$target."_publish_date"];
	$book_author = $_POST[$target."_author"];

	$sql_addition = "";
	if($is_book){
		$book_amount_total = $_POST[$target."_amount_total"];
		$book_amount_available = $_POST[$target."_amount_available"];

		$sql_addition = " book_amount_total='$book_amount_total' "; 
	}
	//should not edit heat
	// else{
	// 	$book_heat = $_POST[$target."_heat"];
	// 	$sql_addition = " wish_heat='$book_heat' ";
	// }

		//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$book_id = -1;

	$sql = "select ".$target."_Id from ".$target." where ".$target."_isbn = '$book_isbn'";
	$query = mysql_query($sql);
	if(!$query || !mysql_num_rows($query))
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	if($is_book){
		$book_id = mysql_fetch_object($query) -> book_Id;
	}else{
		$book_id = mysql_fetch_object($query) -> wish_Id;
	}

	$sql_update_book = "update ".$target." set ".$target."_title = '$book_title', ".$target."_subtitle='$book_subtitle', "
	.$target."_isbn='$book_isbn', ".$target."_publisher='$book_publisher', "
	.$target."_summary='$book_summary', ".$target."_publishDate='$book_publish_date', ".$sql_addition."where "
	.$target."_id='$book_id'";
	$query_update_book = mysql_query($sql_update_book);
	if(!$query_update_book)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	$authors = explode(",", $book_author);
	$sql_delete_author = "delete from ".$target."_author where ".$target."_id = '$book_id'";
	$query_delete_author = mysql_query($sql_delete_author);
	if(!$query_delete_author)
		return die_with_message($lock,DATABASE_OPERATION_ERROR);

	foreach ($authors as $value) {
		$sql_insert_author = "insert into ".$target."_author(".$target."_id,author) values('$book_id','$value')";
		$query_insert_author = mysql_query($sql_insert_author);
		if(!$query_insert_author)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);
	}

	if($is_book){
		$sql_update_amount = "update book_amount set book_amount_available = '$book_amount_available' where book_id ='$book_id'";
		$query_update_amount = mysql_query($sql_update_amount);
		if(!$query_insert_author)
			return die_with_message($lock,DATABASE_OPERATION_ERROR);
	}

	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;

}

?>