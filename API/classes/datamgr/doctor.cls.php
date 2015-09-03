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

	
	public function getDoctorInfoByLoginId($login_id)
	{
		$login_id=parameter_filter($login_id);
		$sql="select id, license,name,photo,office,title,bookingtime,introduce,credentials,expert,status,login_id,password 
		from tb_doctor where login_id='$login_id' ";
		
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function getDoctorList($lastupdate_time)
	{
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id, license,name,photo,office,title,bookingtime,introduce,credentials,expert
		,enable_videochat,videochat_price,enable_charchat,charchat_price,status
		,".$this->dbmgr->getIsNull("ms.general",5)." as general_score,".$this->dbmgr->getIsNull("ms.querycount",120)." as querycount
		from tb_doctor m
		left join tb_doctor_statistic ms on m.id=ms.doctor_id where 1=1  ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

 }
 
 $doctorMgr=DoctorMgr::getInstance();
 $doctorMgr->dbmgr=$dbmgr;
 
 
 
 
?>