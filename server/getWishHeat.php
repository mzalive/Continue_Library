<?php

function get_heat($wish){
	foreach ($wish as &$value) {
		$isbn = $value['isbn'];
		$sql = "select wish_Id from wish where wish_Isbn = '$isbn'";
		$query = mysql_query($sql);
		$wish_id = mysql_fetch_object($query) -> wish_Id;

		$sql_heat = "select wish_Heat from wish_heat where wish_Id = '$wish_id'";
		$query_heat = mysql_query($sql_heat);
		$heat = mysql_fetch_object($query_heat) -> wish_Heat;

		$value['status']['heat'] = $heat;
	}
	return $wish;
}

?>