<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class NewsMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new NewsMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	
	public function getContent($id)
	{
		$id=parameter_filter($id);
		$sql="select * from tb_news where id='$id' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result[0];
	}
 }
 
 $newsMgr=NewsMgr::getInstance();
 $newsMgr->dbmgr=$dbmgr;
 
 
 
 
?>