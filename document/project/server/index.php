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
//define path used for cache
define("CACHE_PATH","D:/tmp/");
//define path used for file lock
define("FILE_LOCK_PATH","D:/tmp/lock/");

$action = $_GET['action'] == '' ? 'index' : $_GET['action'];
include('/' . $action . '.php');
?>