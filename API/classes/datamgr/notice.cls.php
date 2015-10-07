<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class NoticeMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new NoticeMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	
	public function sendOrderForDoctor($order){
	
		$order_no=$order["order_no"];
		$name=$order["name"];
		$mobile=$order["mobile"];
		$description=$order["description"];
		if($order["act"]=="VC"){
			$doctor_id=$order["tag"];
			$order_datetime=$order["order_date"]." ".$order["order_time"];
			$title="你有一个新的视频会诊预约，预约日期为$order_datetime";
			$context="预约详情
			订单编号：$order_no
			姓名：$name 
			电话：$mobile 
			预约时间：$order_datetime 
			预约描述：$description ";
			$this->sendNotice($title,$context,'PD',$doctor_id);
		}
		if($order["act"]=="CC"){
			$doctor_id=$order["tag"];
			$title="你有一个新的图文咨询预约";
			$context="预约详情
			订单编号：$order_no
			姓名：$name 
			电话：$mobile 
			预约描述：$description ";
			$this->sendNotice($title,$context,'PD',$doctor_id);
		}
	}
	
	public function sendOrderForClient($order){
	
		$order_no=$order["order_no"];
		$name=$order["name"];
		$mobile=$order["mobile"];
		$description=$order["description"];
		$member_id=$order["member_id"];
		//echo "a".$order["act"]."b";
		if($order["act"]=="VC"){
			$order_datetime=$order["order_date"]." ".$order["order_time"];
			$title="你已经成功提交一个视频会诊，预约日期为：$order_datetime";
			$context="预约详情
			订单编号：$order_no
			姓名：$name 
			电话：$mobile 
			预约时间：$order_datetime 
			预约描述：$description ";
			$this->sendNotice($title,$context,'PM',$member_id);
		}
		if($order["act"]=="CC"){
			$title="你已经成功提交一个图文咨询预约";
			$context="预约详情
			订单编号：$order_no
			姓名：$name 
			电话：$mobile 
			预约描述：$description ";
			$this->sendNotice($title,$context,'PM',$member_id);
		}
	}

	private function sendNotice($title,$context,$send_type,$specify_id){
	
		$id=$this->dbmgr->getNewId("tb_notice");
		$publish_date=date('Y-m-d H:i:s',time());
		
		$id=parameter_filter($id);
		$title=parameter_filter($title);
		$context=parameter_filter($context);
		$doctor_id=parameter_filter($doctor_id);
		$member_id=parameter_filter($member_id);
		$send_type=parameter_filter($send_type);
		$sql="insert into tb_notice (id,created_date,created_user,updated_date,updated_user,
		title,publish_date,notice_type,context,remarks,status,specify_id,sent_type)
		values ($id,".$this->dbmgr->getDate().",-1,".$this->dbmgr->getDate().",-1,
		'$title','$publish_date','M','$context','系统自动发送','A',$specify_id,'$send_type')";

		$query = $this->dbmgr->query($sql);
	}
 }
 
 $noticeMgr=NoticeMgr::getInstance();
 $noticeMgr->dbmgr=$dbmgr;
 
 
 
 
?>