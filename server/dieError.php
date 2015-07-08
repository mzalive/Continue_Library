<?php
function die_with_message($lock,$message){
		mysql_query("ROLLBACK");
		$lock -> release();
		return $message;
}

function die_with_response($message,$response){
		$response['error_code'] = $message;
		return json_encode($response,JSON_UNESCAPED_UNICODE);;
}
?>