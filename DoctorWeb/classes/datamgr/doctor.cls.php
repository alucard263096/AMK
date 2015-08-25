<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class DoctorMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new DoctorMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	
	public function getDoctorByLoginName($loginname)
	{
		$loginname=parameter_filter($loginname);
		$sql="select * from tb_doctor where login_id='$loginname' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function changsePassword($doctor_id,$current_password,$new_password)
	{
		$new_password=parameter_filter($new_password);
		$doctor=$this->getDoctor($doctor_id);
		if(md5($current_password)!=$doctor["password"])
		{
			return "原密码错误";
		}
		$new_password=md5($new_password);
		$new_password=parameter_filter($new_password);
		$sql="update tb_doctor set password='$new_password',updated_user=-1,updated_date=".$this->dbmgr->getDate()." where id=$doctor_id";
		$query = $this->dbmgr->query($sql);
		
		return "SUCCESS";
	}

	
	public function getDoctor($doctor_id)
	{
		$sql="select * from tb_doctor where id='$doctor_id' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		return $result;
	}

 }
 
 $doctorMgr=DoctorMgr::getInstance();
 $doctorMgr->dbmgr=$dbmgr;
 
 
 
 
?>