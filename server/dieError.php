<?php
function die_with_message($lock,$message){
		mysql_query("ROLLBACK");
		$lock -> release();
		return $message;
}
?>