<?php
include ("conn.php");
require_once("getBookInfo.php");
require_once("buildBook.php");
if(!is_null($_POST['keyword']) && !is_null($_POST['user_id']) && !is_null($_POST['book_start']) && !is_null($_POST['book_count'])
	&& !is_null($_POST['wish_start']) && !is_null($_POST['wish_count'])){
	try{
		$conn = mysql_open();

		$keyword = $_POST['keyword'];

		$keyword = preprocess($keyword);

		$output_book = $_POST['book_count']==='0'?array('error_code' => NO_CONTENT,'book_count' => 0, 'book_start' => 0, 'book_total' => 0)
												:search_helper("book",$keyword);
		$output_wish = $_POST['wish_count']==='0'?array('error_code' => NO_CONTENT,'wish_count' => 0, 'wish_start' => 0, 'wish_total' => 0)
												:search_helper("wish",$keyword);

		$output = array();

		$output['error_code'] = $output_book['error_code']==RESULT_OK
								?RESULT_OK
								:($output_wish['error_code']==RESULT_OK)
									?RESULT_OK
									:$output_book['error_code'];
		if($output['error_code'] == RESULT_OK)
		{
			$output['book_count'] = $output_book['book_count'];
			$output['book_start'] = $output_book['book_start'];
			$output['book_total'] = $output_book['book_total'];
			$output['wish_count'] = $output_wish['wish_count'];
			$output['wish_start'] = $output_wish['wish_start'];
			$output['wish_total'] = $output_wish['wish_total'];
			
			$output['books'] =  $output_book['error_code']==RESULT_OK
								?($output_wish['error_code']==RESULT_OK
									?array_merge($output_book['books'],$output_wish['books'])
									:$output_book['books'])
								:$output_wish['books'];
		}
		echo json_encode($output,JSON_UNESCAPED_UNICODE);
		mysql_close($conn);
	}
	catch(Exception $e){
		$response = array('error_code' => '999');
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	}
}

function preprocess($str){
	$str = trim($str);
	$str = preg_replace('/\s(?=\s)/', '', $str);// 接着去掉两个空格以上的 
	$str = preg_replace('/[\n\r\t]/', ' ', $str);// 最后将非空格替换为一个空格 
	return $str;
}

function search_helper($target,$keyword){
	require_once("cachehandler.php");
	require_once("getWishHeat.php");

	$cache = new cachehandler($_POST['action']."_".$target);

	if($output = $cache -> get($keyword))
	{
		// echo 'has cache!';
	}else{
		$output = $cache -> save("foo",$keyword,$target,explode(" ", $keyword));
	}
	$output = json_decode($output,true);
	if($output['error_code'] == RESULT_OK){
		$books = $output['books'];
		$count = $output[$target.'_count'];
		$start = $output[$target.'_start'];
		$books = array_slice($books,$start,$count);
		if($target == "wish")
			$books = get_heat($books);

		$output['books'] = $books;
	}
	return $output;
}

function foo($target,$keywords){
	require_once("dieError.php");

	$response = array();

	$reg = "";
	foreach ($keywords as $val) {
		$reg = $reg.$val.'|';
	}
	$reg = rtrim($reg,'|');
	$sql_search_isbn = "select "."$target"."_id from "."$target"." where "."$target"."_isbn regexp '$reg'";
	$sql_search_title = "select "."$target"."_id from "."$target"." where "."$target"."_title regexp '$reg'";
	$sql_search_publisher = "select "."$target"."_id from "."$target"." where "."$target"."_publisher regexp '$reg'";

	$sql_search_tag = "	select "."$target"."_id from (
						select a."."$target"."_id,count(1) as tag_count 
						from "."$target"." a 
						left outer join(
							select b."."$target"."_id, c.tag_content 
							from tag_"."$target"." b 
							join tag_index c 
							on b.tag_id =c.tag_id
							) as d
						on a."."$target"."_id = d."."$target"."_id
						where
						d.tag_content regexp '$reg'
						group by 
						a."."$target"."_id
						order by 
						tag_count desc
					) as f";
	
	$sql_search_author = "select "."$target"."_id from (
							select g."."$target"."_id from "."$target"." g left outer join 
							"."$target"."_author h on g."."$target"."_id=h."."$target"."_id 
							where h.author regexp '$reg')
							as i";

	$sql_union = "select "."$target"."_id from ("
					.$sql_search_isbn." union "
					.$sql_search_title." union "
					.$sql_search_tag." union "
					.$sql_search_author." union "
					.$sql_search_publisher.") as j";

	$query_search = mysql_query($sql_union);

	if(!$query_search)
		return die_with_response(DATABASE_OPERATION_ERROR,$response);

	$total = mysql_num_rows($query_search);

	$response[$target.'_total'] = $total;
	$response[$target.'_start'] = $_POST[$target.'_start'];
	$response[$target.'_count'] = $total-$_POST[$target.'_start'] >= $_POST[$target.'_count']
									?$_POST[$target.'_count']
									:$total-$_POST[$target.'_start'];

	$response['books'] = array();
	while($result = mysql_fetch_object($query_search)){
		if($target == "book")
		{
			if(!books_handler($target,$result -> book_id, $response))
				return die_with_response(DATABASE_OPERATION_ERROR,$response);
		}
		else if($target == "wish")
		{
			if(!books_handler($target,$result -> wish_id, $response))
				return die_with_response(DATABASE_OPERATION_ERROR,$response);
		}
	}
	$response['error_code'] = $total==0?NO_CONTENT:RESULT_OK;
	return json_encode($response,JSON_UNESCAPED_UNICODE);
}

function books_handler($target, $book_id, &$response){

	$query = call_user_func("get_".$target."_info",$book_id);
	if(!$query || !mysql_num_rows($query))
		return false;	

	while($result = mysql_fetch_object($query)){
		$book = call_user_func("build_".$target,$result);
		if(!is_null($book))
			array_push($response['books'],$book);
		else
			return false;
	}
	return true;
}

?>