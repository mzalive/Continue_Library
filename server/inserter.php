<?php
	include ("conn.php");

	$conn = mysql_open();

	$ISBN = "9787111407010";
	$amount = "1";

	$url = "http://continuelibrary.mzalive.org/server/index.php?action=login&username=liuziyi&password=13";

	$info = file_get_contents($url);
	var_dump($info);


	mysql_close($conn);
?>