<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class OrderMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new OrderMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}

	
	public function getVideochatOrderList($doctor_id,$lastupdate_time)
	{
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}
		$doctor_id=parameter_filter($doctor_id);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,order_no,member_id,name,mobile,price,created_time,status,process_status,payment,order_date,order_time,doctor_id,chat_time 
		from v_order_videochat
		where doctor_id=$doctor_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

 }
 
 $orderMgr=OrderMgr::getInstance();
 $orderMgr->dbmgr=$dbmgr;
 
 
 
 
?>