<?php

//define constants used for error_code.
define("UNKNOWN_ERROR","999");
define("RESULT_OK","1000");
define("NO_CONTENT", "2000");
define("NETWORK_ERROR", "2001");
define("AUTHORIZATION_ERROR","2002");
define("BOOK_ALL_BORROWED","3001");
define("BOOK_ALL_RETURNED", "3002");
define("DATABASE_OPERATION_ERROR", "4001");
define("ALREADY_ADDED","5001");
//define path used for cache
define("CACHE_PATH","tmp/");
//define path used for file lock
define("FILE_LOCK_PATH","tmp/lock/");
//define url for douban api
define("DOUBAN_API","https://api.douban.com/v2/book/isbn/");

$action = $_POST['action'] == '' ? 'index' : $_POST['action'];
include('/' . $action . '.php');
?>