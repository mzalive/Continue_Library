<?php 
function mysql_open()
{
    $mysql_servername = "localhost:3306";
	$mysql_username = "root";
	$mysql_password = "00000000";
	$mysql_databasename = "continue_lib_db";

	$conn = mysql_connect($mysql_servername, $mysql_username,$mysql_password);
	mysql_query("set names utf8");
	mysql_select_db($mysql_databasename,$conn);
	return $conn;
}
?>