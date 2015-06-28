<?php
define("FILE_LOCK_PATH","D:/tmp/lock/");
include ("lock.php");
$lock = new filelock("setPassword");
$lock -> lock();
while(1);
?>