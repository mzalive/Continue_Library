<?php
	include ("conn.php");

	$conn = mysql_open();
// 	$target = "wish";
// 	$keywords = preprocess(" 的 了  	");
// 	$keywords = explode(' ', $keywords);
// 	search($target,$keywords);


// function preprocess($str){
// 	$str = trim($str);
// 	$str = preg_replace('/\s(?=\s)/', '', $str);// 接着去掉两个空格以上的 
// 	$str = preg_replace('/[\n\r\t]/', ' ', $str);// 最后将非空格替换为一个空格 
// 	return $str;
// }

// function search($target,$keywords){
// 	$reg = "";
// 	foreach ($keywords as $val) {
// 		$reg = $reg.$val.'|';
// 	}
// 	$reg = rtrim($reg,'|');
// 	$sql_search_isbn = "select "."$target"."_id from "."$target"." where "."$target"."_isbn regexp '$reg'";
// 	$sql_search_title = "select "."$target"."_id from "."$target"." where "."$target"."_title regexp '$reg'";
// 	$sql_search_publisher = "select "."$target"."_id from "."$target"." where "."$target"."_publisher regexp '$reg'";

// 	$sql_search_tag = "	select "."$target"."_id from (
// 						select a."."$target"."_id,count(1) as tag_count 
// 						from "."$target"." a 
// 						left outer join(
// 							select b."."$target"."_id, c.tag_content 
// 							from tag_"."$target"." b 
// 							join tag_index c 
// 							on b.tag_id =c.tag_id
// 							) as d
// 						on a."."$target"."_id = d."."$target"."_id
// 						where
// 						d.tag_content regexp '$reg'
// 						group by 
// 						a."."$target"."_id
// 						order by 
// 						tag_count desc
// 					) as f";
	
// 	$sql_search_author = "select "."$target"."_id from (
// 							select g."."$target"."_id from "."$target"." g left outer join 
// 							"."$target"."_author h on g."."$target"."_id=h."."$target"."_id 
// 							where h.author regexp '$reg')
// 							as i";

// 	$sql_union = "select "."$target"."_id from ("
// 					.$sql_search_isbn." union "
// 					.$sql_search_title." union "
// 					.$sql_search_tag." union "
// 					.$sql_search_author." union "
// 					.$sql_search_publisher.") as j";

	$sql_union = "select book_title from ";
	$query_search = mysql_query($sql_union);

	while($result = mysql_fetch_object($query_search))
		var_dump($result);
// }
	// define("DOUBAN_API","https://api.douban.com/v2/book/isbn/");

	// $ISBN = "9787505715660";

	// $url = DOUBAN_API.$ISBN;
	// $info = file_get_contents($url);
	// var_dump(json_decode($info));

	// $flag = false;
	// $conn = mysql_open();

	// $keyword = "工工";

	// $sql = "select wish_id from (
				
	// 				select wish_id
	// 				from wish 
	// 				where 
	// 				concat(wish_title,' ',wish_summary) 
	// 				like '%的%'
	// 			union 
					// select wish_id from (
					// 	select a.wish_id,count(1) as tag_count 
					// 	from wish a 
					// 	left outer join(
					// 		select b.wish_id, c.tag_content 
					// 		from tag_wish b 
					// 		join tag_index c 
					// 		on b.tag_id =c.tag_id
					// 		) as d
					// 	on a.wish_id = d.wish_id
					// 	where
					// 	d.tag_content regexp '的|了'
					// 	group by 
					// 	a.wish_id
					// 	order by 
					// 	tag_count desc
					// ) as f
	// 		) as e";

	// // $sql = "				
	// // 				select wish_id
	// // 				from wish 
	// // 				where 
	// // 				concat(wish_title,' ',wish_summary) 
	// // 				regexp '的|了'";
	// $query = mysql_query($sql);

	// echo mysql_error();
	// while($result = mysql_fetch_object($query))
	// 	var_dump($result);
	
	// for($id=1;$id!=11;$id++)
	// {
	// // 	// $sql = "insert into book values('$id','小工工','','9787505715660','中国友谊出版公司','http:\/\/img1.douban.com\/lpic\/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.','1','2000-9-1')";
	// // 	// $query = mysql_query($sql);

	// 	$sql = "insert into wish values('$id','小工工','','9787505715660','中国友谊出版公司','2000-9-1','http:\/\/img1.douban.com\/lpic\/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.')";
	// 	$query = mysql_query($sql);

	// 	$sql2 = "insert into user_wishlist values('$id','1','$id')";
	// 	$query = mysql_query($sql2);

	// // 	// $sql3 = "insert into book_author values('$id','$id','叶虹江')";
	// // 	// $query = mysql_query($sql3);

	// // 	$sql4 = "insert into wish_author values('$id','$id','叶虹江')";
	// // 	$query = mysql_query($sql4);

	// 	$sql5 = "insert into wish_heat values('$id','$id','1')";
	// 	$query = mysql_query($sql5);

	// // 	$sql6 = "insert into book_amount values('$id','$id','1')";
	// // 	$query = mysql_query($sql6);

	// // 	// echo mysql_error();
	// }
	mysql_close($conn);
?>