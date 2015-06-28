<?php
require_once("/Cache_Lite-1.7.16/Cache/Lite.php");
class cachehandler{

	private $default_dir = CACHE_PATH;
	private $filename;
	private $dir;
	private $cache;

	public function makedir()
	{
		$this->dir = $this->default_dir.$this->filename.'/';
		if(!is_dir($this->dir))
			mkdir($this->dir);
	}
	public function __construct($filename)
	{
		$this->filename = $filename;
		$this->makedir();
		$options = array( 
		    'cacheDir' => $this->dir, 
		    'lifeTime' => 60 ,
		    'pearErrorMode' => CACHE_LITE_ERROR_DIE 
		); 
		$this->cache=new Cache_Lite($options); 
	}
	public function save($foo, $id)
	{
		if(function_exists($foo))
			$response = call_user_func($foo);	//以后需要做错误处理
		$this->cache->save($response,$id);
		return $response;
	}
	public function get($id)
	{
		return $this->cache->get($id);
	}
	public function remove($id)
	{
		if($this->cache->get($id))
			$this->cache->remove($id);
	}
}
?>