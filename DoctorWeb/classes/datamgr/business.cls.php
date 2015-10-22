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
		$sql="
		select count(1) count,'v_notice_doctor' name 
from v_notice_doctor where doctor_id=$user_id and haveread='N'
union select count(1) count, 'v_order_comment' name
from v_order_full where doctor_id=$user_id and isnull(reply,'')='' and hascomment='Y' and status='F'
";

		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		//print_r($result);
		for($i=0;$i<count($result);$i++){
			$sum=$sum+$result[$i]["count"];
		}
		return $sum;
	}

	public function getReminderAll($user_id){
		Global $CONFIG;
		$Array=Array();

		$arr=Array();
		$arr["name"]="未读通知";
		$user_id=parameter_filter($user_id);
		$sql="select top 3 id,title as first,
 case notice_type when 'I' then '重要' else  '一般' end as second,publish_date  as third 
 from v_notice_doctor where doctor_id=$user_id and haveread='N'
order by publish_date desc ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		$arr["result"]=$result;
		$arr["first"]="标题";
		$arr["second"]="重要性";
		$arr["third"]="发布日期";
		$arr["count"]=count($result);
		$arr["link"]=$CONFIG['rootpath']."/Info/notice.php#";
		$Array[]=$arr;

		

		$arr=Array();
		$arr["name"]="未回复评价";
		$user_id=parameter_filter($user_id);
		$sql="select top 3 id,order_no as first,
 comment as second,comment_date  as third 
 from v_order_full 
 where doctor_id=$user_id and isnull(reply,'')='' and hascomment='Y' and status='F'
order by comment_date desc ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		$arr["result"]=$result;
		$arr["first"]="订单编号";
		$arr["second"]="评价";
		$arr["third"]="评价日期";
		$arr["count"]=count($result);
		$arr["link"]=$CONFIG['rootpath']."/Order/ordercomment.php#";
		$Array[]=$arr;

		return $Array;

	}
	
 }
 
 $businessMgr=BusinessMgr::getInstance();
 $businessMgr->dbmgr=$dbmgr;
 
 
 
 
?>