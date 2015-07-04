<?php 
function mysql_open()
{
    $mysql_servername = "127.0.0.1:3306";
	$mysql_username = "continue_library";
	$mysql_password = "CQU~ibmclub2011";
	$mysql_databasename = "continue_library";

	$conn = mysql_connect($mysql_servername, $mysql_username,$mysql_password);
	mysql_query("set names utf8");
	mysql_select_db($mysql_databasename,$conn);
	return $conn;
}
?>