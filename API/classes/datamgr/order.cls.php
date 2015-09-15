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
		$sql="select id,order_no,member_id,name,mobile,price,created_time,status,process_status,payment,order_date,order_time,doctor_id,chat_time ,description
		from v_order                 
		inner join dbo.tb_order_videochat AS ov ON act = 'VC' AND id = ov.order_id
		where doctor_id=$doctor_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	public function getMemberOrderList($doctor_id,$lastupdate_time){
		if($member_id==""){
			return	outResult(-1,"member_id can not be null");
		}
		$member_id=parameter_filter($member_id);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,order_no,member_id,name,mobile,price,created_time,status,process_status,payment,order_date,order_time,doctor_id,chat_time ,description
		from v_order    
		where member_id=$member_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;

	}

	public function createVideochatOrder($doctor_id,$order_date,$order_time,$member_id,$name,$mobile,$description){
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}
		if($order_date==""){
			return	outResult(-2,"order_date can not be null");
		}
		if($order_time==""){
			return	outResult(-3,"order_time can not be null");
		}
		if($member_id==""){
			return	outResult(-4,"member_id can not be null");
		}
		
		$doctor_id=parameter_filter($doctor_id);
		$sql="select * from tb_doctor where id=$doctor_id and status='A'";
		$query = $this->dbmgr->query($sql);
		$doctorlist = $this->dbmgr->fetch_array_all($query); 
		if(count($doctorlist)==0){
			return	outResult(-101,"doctor is inactived");
		}
		$doctor=$doctorlist[0];

		if($doctor["enable_videochat"]!='Y'){
			return	outResult(-106,"doctor did not enable videochat");
		}

		
		$member_id=parameter_filter($member_id);
		$sql="select * from tb_member where id=$member_id and status='A'";
		$query = $this->dbmgr->query($sql);
		$memberlist = $this->dbmgr->fetch_array_all($query); 
		if(count($memberlist)==0){
			return	outResult(-102,"member is inactived");
		}
		$member=$memberlist[0];
		
		$order_date=parameter_filter($order_date);
		$order_time=parameter_filter($order_time);
		$sql="select count(1) from tb_order o
		inner join tb_order_videochat ov on o.id=ov.order_id
		where ov.doctor_id=$doctor_id and o.order_date='$order_date'  and o.order_time='$order_time'  and o.status not in ('F','C','D')  ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		if($result[0]>0){
			return	outResult(-103,"order date have been used");
		}

		if($name==""){
			$name=$member["name"];
		}
		if($mobile==""){
			$mobile=$member["mobile"];
		}

		//read to create 
		$this->dbmgr->begin_trans();

		$name=parameter_filter($name);
		$mobile=parameter_filter($mobile);
		$description=parameter_filter($description);

		$act="VC";
		$order_no=$this->genOrderNo($act);

		$price=$doctor["videochat_price"];

		$id=$this->dbmgr->getNewId("tb_order");
		$sql="insert into tb_order (id,order_no,member_id,name,mobile,price,act,created_time,status,process_status,order_date,order_time,description)
		values ($id,'$order_no',$member_id,'$name','$mobile',$price,'$act',".$this->dbmgr->getDate().",'T','P','$order_date','$order_time','$description'  )   ";
		$query = $this->dbmgr->query($sql);

		$sql="insert into tb_order_videochat (order_id,doctor_id) values ($id ,$doctor_id )";
		$query = $this->dbmgr->query($sql);

		
		$sql="insert into tb_order_payment (order_id,payment) values ($id ,'N')";
		$query = $this->dbmgr->query($sql);


		$this->dbmgr->commit_trans();
		return	outResult(0,"success",$id);
	}

	
	public function payment($order_id,$member_id,$payment_type){
		if($order_id==""){
			return	outResult(-1,"order_id can not be null");
		}
		if($member_id==""){
			return	outResult(-2,"member_id can not be null");
		}
		if($payment_type==""){
			return	outResult(-3,"payment_type can not be null");
		}
		
		$order_id=parameter_filter($order_id);
		$member_id=parameter_filter($member_id);
		$payment_type=parameter_filter($payment_type);
		$sql="select * from v_order where id=$order_id and member_id=$member_id ";
		$query = $this->dbmgr->query($sql);
		$orderlist = $this->dbmgr->fetch_array_all($query); 
		if(count($orderlist)==0){
			return	outResult(-103,"找不到有效订单");
		}
		$order=$orderlist[0];
		if($order["payment"]=="Y"){
			return	outResult(-101,"订单已支付");
		}
		
		//read to create 
		$this->dbmgr->begin_trans();

		$sql="update tb_order set status='P' where  id=$order_id and member_id=$member_id ";
		$query = $this->dbmgr->query($sql);

		
		$sql="update tb_order_payment set payment='Y',payment_type='$payment_type',payment_time=".$this->dbmgr->getDate()." where order_id=$order_id ";
		$query = $this->dbmgr->query($sql);

		$this->dbmgr->commit_trans();
		return	outResult(0,"success");
	}

	
	public function genOrderNo($prefix){
		
		$d=date('Ym',time());
		$sql="select seq from tb_order_no_gen
		where prefix='$prefix' and datemark='$d' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		$seq= $result[0];
		if($seq==""){
			$sql="insert into tb_order_no_gen (prefix,datemark,seq) values ('$prefix','$d',2)";
			$query = $this->dbmgr->query($sql);
			$seq= 1;
		}else{
			$sql="update tb_order_no_gen set seq=seq+1 where prefix='$prefix' and datemark='$d' ";
			$query = $this->dbmgr->query($sql);
		}
		return $prefix.$d.sprintf("%06d", $seq);

	}

	
	public function getOrder($order_id,$member_id){
		
		if($order_id==""){
			return	outResult(-1,"order_id can not be null");
		}
		if($member_id==""){
			return	outResult(-2,"member_id can not be null");
		}
		$order_id=parameter_filter($order_id);
		$member_id=parameter_filter($member_id);
		$sql="select * from v_order where status<>'D' and id=$order_id and member_id=$member_id  ";
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;

	}

 }
 
 $orderMgr=OrderMgr::getInstance();
 $orderMgr->dbmgr=$dbmgr;
 
 
 
 
?>