<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class StatisticsMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new StatisticsMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	

	public function getDataForDashboard($user_id){
		Global $CONFIG;
		$Array=Array();

		$datestr=date('Y-m-d',time());;


		
		$arr=Array();
		$arr["name"]="总订单完成";
		$sql="select (select COUNT(1) from v_order_full
where doctor_id=$user_id and status in ('P','F')) as total_count,
(select COUNT(1) from v_order_full
where doctor_id=$user_id and status='F') as finish_count";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		$arr["percent"]=$result["finish_count"]/$result["total_count"]*100;;
		$arr["percent"]=round($arr["percent"],2);
		$arr["link"]=$CONFIG['rootpath']."/Statistics/orderstatistics.php";
		$Array[]=$arr;

		
		$arr=Array();
		$arr["name"]="视频会诊完成";
		$sql="select (select COUNT(1) from v_order_full
where doctor_id=$user_id and status in ('P','F') and act='VC') as total_count,
(select COUNT(1) from v_order_full
where doctor_id=$user_id and status='F' and act='VC') as finish_count";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		$arr["percent"]=$result["finish_count"]/$result["total_count"]*100;;
		$arr["percent"]=round($arr["percent"],2);
		$arr["link"]=$CONFIG['rootpath']."/Order/videoorder.php";
		$Array[]=$arr;

		
		$arr=Array();
		$arr["name"]="图文咨询完成";
		$sql="select (select COUNT(1) from v_order_full
where doctor_id=$user_id and status in ('P','F') and act='CC') as total_count,
(select COUNT(1) from v_order_full
where doctor_id=$user_id and status='F' and act='CC') as finish_count";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		$arr["percent"]=$result["finish_count"]/$result["total_count"]*100;;
		$arr["percent"]=round($arr["percent"],0,2);
		$arr["link"]=$CONFIG['rootpath']."/Order/charorder.php";
		$Array[]=$arr;

		
		$arr=Array();
		$arr["name"]="已处理评价";
		$sql="select (select COUNT(1) from v_order_full 
 where doctor_id=$user_id  and hascomment='Y' and status='F') as total_count,
(select COUNT(1) from v_order_full 
 where doctor_id=$user_id and isnull(reply,'')<>'' and hascomment='Y' and status='F') as finish_count";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		$arr["percent"]=$result["finish_count"]/$result["total_count"]*100;;
		$arr["percent"]=round($arr["percent"],0,2);
		$arr["link"]=$CONFIG['rootpath']."/Order/ordercomment.php";
		$Array[]=$arr;

		//$arr=Array();
		//$arr["name"]="疫苗预约取消";
		//$user_id=parameter_filter($user_id);
		//$sql="select  m.id,m.name as first,
 //m.mobile  as second,m.order_date  as third 
//from dr_tb_member_vaccine_order m
		//where status='C' and h_status='P'
        //order by m.updated_date desc
		//limit 0,3 ";
		//$query = $this->dbmgr->query($sql);
		//$result = $this->dbmgr->fetch_array_all($query); 
		//$arr["result"]=$result;
		//$arr["first"]="客户姓名";
		//$arr["second"]="手机号码";
		//$arr["third"]="预约日期";
		//$arr["count"]=count($result);
		//$arr["link"]=$CONFIG['rootpath']."/Appointment/appiontment.php";
		//$Array[]=$arr;

		

		return $Array;

	}
	
 }
 
 $statisticsMgr=StatisticsMgr::getInstance();
 $statisticsMgr->dbmgr=$dbmgr;
 
 
 
 
?>