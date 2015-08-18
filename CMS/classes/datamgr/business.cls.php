<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class BusinessMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new BusinessMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	
	public function getReminderCount($user_id)
	{
		$sum=0;
		
		return $sum;
	}

	public function getReminderAll($user_id){
		Global $CONFIG;
		$Array=Array();

		return $Array;

	}
	
 }
 
 $businessMgr=BusinessMgr::getInstance();
 $businessMgr->dbmgr=$dbmgr;
 
 
 
 
?>