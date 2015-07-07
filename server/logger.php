<?php

class ServerLogger{

	private static $parent_dir = CACHE_PATH;
	private static $default_dir = LOGGER_PATH;
	private static $log_name = "log_info";

	public static function d($content){
		$dir =self::$default_dir.self::$log_name.'.txt';
		if(!is_dir(self::$parent_dir))
			mkdir(self::$parent_dir);
		if(!is_dir(self::$default_dir))
			mkdir(self::$default_dir);

		$fp = fopen($dir,'w+');

		$prefix = "\n".'['.date("Y-m-d H:i:s", time()).'] : '.$_POST['action']."\n";
		fwrite($fp, $prefix.$content."\n");
		fclose($fp);
		}
	}

?>