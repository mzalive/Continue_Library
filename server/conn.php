<?php 
function mysql_open()
{
    $mysql_servername = "localhost:3306";
	$mysql_username = "root";
	$mysql_password = "";
	$mysql_databasename = "continuelibrary";

	$conn = mysql_connect($mysql_servername, $mysql_username,$mysql_password);
	mysql_query("set names utf8");
	mysql_select_db($mysql_databasename,$conn);
	return $conn;
}
?>