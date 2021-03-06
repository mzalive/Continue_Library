<?php
//lock a file and no other process may access it.
class filelock{
	private $parent_dir = CACHE_PATH;
	private $default_dir = FILE_LOCK_PATH;
	private $filename;
	private $dir;
	private $fp;
	public function __construct($filename)
	{
		$this->dir = $this->default_dir.$filename.'/';
		$this->filename = $this->dir.$filename.'.txt';
		if(!is_dir($this->parent_dir))
			mkdir($this->parent_dir);
		if(!is_dir($this->default_dir))
			mkdir($this->default_dir);
		if(!is_dir($this->dir))
			mkdir($this->dir);
		if(!file_exists($this->filename)){
			fclose(fopen($this->filename,'w+'));
		}
	}

	public function __destruct()
	{
		if($this->fp)
			fclose($this->fp);
	}

	public function lock(){
		$this->fp = fopen($this->filename,'w+');
		if(is_null($this->fp)){
			ServerLogger::d("File Openning Permission Denied!");
			die("File Openning Permission Denied!");
		}
		while(flock($this->fp,LOCK_EX)==false);
	}

	public function release(){
		if($this->fp)
				fclose($this->fp);
		$this->fp = NULL;
	}
}
?>