<?php
include("conn.php");
if(!is_null($_POST["start"]) && !is_null($_POST["count"])){
	$conn = mysql_open();

	$json_result = get_book_list();
	$output = json_decode($json_result,true);
	if($output['error_code'] == RESULT_OK){
		$books = &$output['books'];
		foreach ($books as &$book) {
			$book_amount_available = foo($book);
			if($book_amount_available)
				$book['status']['book_amount_available'] = $book_amount_available;
			else{
				$output['error_code'] = DATABASE_OPERATION_ERROR;
				break;
			}
		}
	}
	echo json_encode($output,JSON_UNESCAPED_UNICODE);

	mysql_close($conn);
}

function foo($book){
	$book_isbn = $book ["isbn"];

	$sql = "select book_amount_available from book_amount where book_id in(select book_id from book where book_isbn='$book_isbn')";
	$query = mysql_query($sql);
	if(!$query || !mysql_num_rows($query)){
		return null;
	}
	$result =  mysql_fetch_object($query);
	return $result -> book_amount_available;
}

function get_book_list(){
	$data = array(
			'action' => "getBookList",
			'start' => $_POST['start'],
			'count' => $_POST['count']);

		$opts = array('http' =>
                      array(
                          'method'  => 'POST',
                          'header'  => 'Content-type: application/x-www-form-urlencoded',
                          'content' => http_build_query($data)
                      )
 
        );

        $context = stream_context_create($opts);
        
		$url = "http://continuelibrary.mzalive.org/server/index.php";
		return file_get_contents($url,false,$context);
}
?>