<?php
function add_to_book($target){
	require_once("lock.php");

	$flag = false;

	$book_amount_total = -1;
	$user_id = -1;

	if($target == "book"){
		$book_amount_total = $_POST['total_amount'];
		$flag = true;
		$sql_insert_book1 = ",book_amount_total";
		$sql_insert_book2 = ",'$book_amount_total'";
	}else{
		$user_id = $_POST['user_id'];
	}

	$book_isbn = $_POST['book_isbn'];

	$url = DOUBAN_API.$book_isbn;
	$info = file_get_contents($url);
	
	$book = json_decode($info);

	$book_title = $book -> title;
	$book_subtitle = $book -> subtitle;
	$book_isbn = $book -> isbn13;
	$book_publisher = $book -> publisher;
	$book_publishdate = $book -> pubdate;
	$book_images = ($book -> images);
	$book_imageurl = $book_images -> large;
	$book_summary = $book -> summary;

	$book_tags = $book -> tags;

	$book_author = $book -> author;

	//transaction lock
	$lock = new filelock($_POST['action']);
	$lock -> lock();

	mysql_query("SET AUTOCOMMIT=0");
	mysql_query("BEGIN");

	$sql = "insert into ".$target."(".$target."_title,".$target."_subtitle,".$target."_isbn,"
		.$target."_publisher,".$target."_publishdate,".$target."_imageurl,".$target."_summary"
		.($flag?$sql_insert_book1:"").")
		values('$book_title','$book_subtitle','$book_isbn','$book_publisher'
		,'$book_publishdate','$book_imageurl','$book_summary'".($flag?$sql_insert_book2:"").")";
	$query = mysql_query($sql);
	if(!$query){
		mysql_query("ROLLBACK");
		$lock -> release();
		return DATABASE_OPERATION_ERROR;
	}

	$book_id = mysql_insert_id();

	if(!$flag){
		$sql_heat = "insert into wish_heat(wish_id,wish_heat) values('$book_id',1)";
		$query_heat = mysql_query($sql_heat);
		if(!$query_heat){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}

		$sql_want = "insert into user_".$target."list(user_Id,".$target."_Id) values('$user_id','$book_id')";
		$query_want = mysql_query($sql_want);
		if(!$query_want){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}else{
		$book_amount_available = $book_amount_total;
		$sql_set_amount_available = "insert into book_amount(book_id,book_amount_available) values('$book_id',
			'$book_amount_available')";
		$query_set_amount_available = mysql_query($sql_set_amount_available);
		if(!$query_set_amount_available){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}


	foreach ($book_tags as $tag) {
		$tag_name = $tag -> name;
		$sql_does_tag_exists = "select tag_id from tag_index where tag_content = '$tag_name'";
		$query_does_tag_exists = mysql_query($sql_does_tag_exists);
		$tag_id = -1;
		if(!mysql_num_rows($query_does_tag_exists)){
			$sql_tag = "insert into tag_index(tag_Content) values('$tag_name')";
			$query_tag = mysql_query($sql_tag);
			if(!$query_tag){
				mysql_query("ROLLBACK");
				$lock -> release();
				return DATABASE_OPERATION_ERROR;
			}
			$tag_id = mysql_insert_id();
		}else{
			$tag_id = mysql_fetch_object($query_does_tag_exists) -> tag_id;
		}	

		$sql_book_tag = "insert into tag_".$target."(tag_id,".$target."_id) values('$tag_id','$book_id')";
		$query_book_tag = mysql_query($sql_book_tag);
		if(!$query_book_tag){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}

	foreach ($book_author as $author) {
		$sql_author = "insert into ".$target."_author(".$target."_id,author) values('$book_id','$author')";
		$query_author = mysql_query($sql_author);
		if(!$query_author){
			mysql_query("ROLLBACK");
			$lock -> release();
			return DATABASE_OPERATION_ERROR;
		}
	}

	mysql_query("COMMIT");

	$lock -> release();
	return RESULT_OK;
}
?>