<?php
include ("conn.php");

	$response = array();
	$flag = false;
	$conn = mysql_open();

	$sql = " select * from user where user_Name='叶虹江'";
	$query = mysql_query($sql);
	$result = mysql_fetch_object($query);
	echo '叶虹江';
	var_dump($result);
	
	// for($id=1;$id!=11;$id++)
	// {
	// 	// $sql = "insert into book values('$id','小工工','','9787505715660','中国友谊出版公司','http:\/\/img1.douban.com\/lpic\/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.','1','1','2000-9-1')";//ÓÃ»§ÃûÓÊÏäÊÖ»ú¶¼¿ÉµÇÂ½
	// 	// $query = mysql_query($sql);

	// 	// $sql = "insert into wish values('$id','小工工','','9787505715660','中国友谊出版公司','2000-9-1','http:\/\/img1.douban.com\/lpic\/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.','1')";//ÓÃ»§ÃûÓÊÏäÊÖ»ú¶¼¿ÉµÇÂ½
	// 	// $query = mysql_query($sql);
	// 	// echo mysql_error();
	// 	// $sql2 = "insert into user_wishlist values('$id','1','$id')";
	// 	// $query = mysql_query($sql2);

	// 	// $sql3 = "insert into book_author values('$id','$id','叶虹江')";
	// 	// $query = mysql_query($sql3);
	// 	$sql3 = "insert into wish_author values('$id','$id','叶虹江')";
	// 	$query = mysql_query($sql3);
	// }
	mysql_close($conn);
?>