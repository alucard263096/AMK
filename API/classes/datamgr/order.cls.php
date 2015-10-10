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

	
	public function getMemberCharchatOrderList($member_id,$lastupdate_time,$status)
	{
		if($member_id==""){
			return	outResult(-1,"member_id can not be null");
		}
		$member_id=parameter_filter($member_id);
		$status=parameter_filter($status);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,order_no,member_id,name,mobile,price,created_time,updated_date,status,process_status,payment,order_date,order_time,
		doctor_id,last_one ,description,SUBSTRING(last_one,1,1) sendside
		from v_order                 
		inner join dbo.tb_order_charchat AS ov ON act = 'CC' AND id = ov.order_id
		where member_id=$member_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		if($status!=""){
		$sql.=" and status='$status'  ";
		}
		$sql.=" order by sendside desc,updated_date desc";
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function getCharchatOrderList($doctor_id,$lastupdate_time,$status)
	{
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}
		$doctor_id=parameter_filter($doctor_id);
		$status=parameter_filter($status);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,order_no,member_id,name,mobile,price,created_time,updated_date,status,process_status,payment,order_date,order_time,
		doctor_id,last_one ,description,SUBSTRING(last_one,1,1) sendside
		from v_order                 
		inner join dbo.tb_order_charchat AS ov ON act = 'CC' AND id = ov.order_id
		where doctor_id=$doctor_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		if($status!=""){
		$sql.=" and status='$status'  ";
		}
		$sql.=" order by sendside,updated_date desc";
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	
	public function getCharchatOrderContent($doctor_id,$order_id,$lastupdate_time)
	{
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}
		if($order_id==""){
			return	outResult(-2,"order_id can not be null");
		}
		$doctor_id=parameter_filter($doctor_id);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,doctor_id,member_id,content,last_one,updated_date
		from v_order                 
		inner join dbo.tb_order_charchat AS ov ON act = 'CC' AND id = ov.order_id
		where doctor_id=$doctor_id and id=$order_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time' ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	
	public function getVideochatOrderList($doctor_id,$lastupdate_time,$onlyactive)
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
		if($onlyactive=="Y"){
		$sql.=" and status='P' and (order_date+' '+order_time)>GETDATE()  ";
		}
		$sql.=" order by order_date ,order_time ";
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	public function getMemberOrderList($member_id,$lastupdate_time){
		if($member_id==""){
			return	outResult(-1,"member_id can not be null");
		}
		$member_id=parameter_filter($member_id);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select * from (
( select v.*,v1.doctor_id tag from v_order v
inner join tb_order_videochat v1 on v.id=v1.order_id and v.act='VC')
  union ( select v.*,v1.doctor_id tag from v_order v
inner join tb_order_charchat v1 on v.id=v1.order_id and v.act='CC')
) v
		where member_id=$member_id ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;

	}

	public function createOrder($order_date,$order_time,$member_id,$name,$mobile,$description,$act,$price){
	
		
		$order_date=$this->formatOrderDate($order_date);
		$order_time=$this->formatOrderTime($order_time);
		
		if($member_id==""){
			return	outResult(-4,"member_id can not be null");
		}

		
		$member_id=parameter_filter($member_id);
		$sql="select * from tb_member where id=$member_id and status='A'";
		$query = $this->dbmgr->query($sql);
		$memberlist = $this->dbmgr->fetch_array_all($query); 
		if(count($memberlist)==0){
			return	outResult(-102,"member is inactived");
		}
		$member=$memberlist[0];
		
		

		if($name==""){
			$name=$member["name"];
		}
		if($mobile==""){
			$mobile=$member["mobile"];
		}


		$name=parameter_filter($name);
		$mobile=parameter_filter($mobile);
		$description=parameter_filter($description);

		$order_no=$this->genOrderNo($act);


		$id=$this->dbmgr->getNewId("tb_order");
		$sql="insert into tb_order (id,order_no,member_id,name,mobile,price,act,created_time,status,process_status,order_date,order_time,description,updated_user,updated_date)
		values ($id,'$order_no',$member_id,'$name','$mobile',$price,'$act',".$this->dbmgr->getDate().",'T','P','$order_date','$order_time','$description',-1, ".$this->dbmgr->getDate()." )   ";
		$query = $this->dbmgr->query($sql);

		$sql="insert into tb_order_payment (order_id,payment) values ($id ,'N')";
		$query = $this->dbmgr->query($sql);

		return $id;
	}

	public function formatOrderDate($order_date){
		if($order_date!=""){
			$arr=explode("-",$order_date);
			$year=intval($arr[0]);
			$month=intval($arr[1]);
			$day=intval($arr[2]);
			$str=$year."-".($month<10?"0".$month:$month)."-".($day<10?"0".$day:$day);
			return $str;
		}
		return $order_date;
	}
	public function formatOrderTime($order_time){
		if($order_time!=""){
			$arr=explode(":",$order_time);
			$hour=intval($arr[0]);
			$minute=intval($arr[1]);
			$second=intval($arr[2]);
			$str=($hour<10?"0".$hour:$hour).":".($minute<10?"0".$minute:$minute).":".($second<10?"0".$second:$second);
			return $str;
		}
		return $order_time;
	}

	public function createCharchatOrder($doctor_id,$member_id,$name,$mobile,$description){
		
		//read to create 
		$this->dbmgr->begin_trans();
		
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}

		$doctor_id=parameter_filter($doctor_id);
		$sql="select * from tb_doctor where id=$doctor_id and status='A'";
		$query = $this->dbmgr->query($sql);
		$doctorlist = $this->dbmgr->fetch_array_all($query); 
		if(count($doctorlist)==0){
			return	outResult(-101,"doctor is inactived");
		}
		$doctor=$doctorlist[0];

		if($doctor["enable_charchat"]!='Y'){
			return	outResult(-106,"doctor did not enable charchat");
		}
		$price=$doctor["charchat_price"];
		//enable_videochat

		
		
		$rs=$this->createOrder($order_date,$order_time,$member_id,$name,$mobile,$description,"CC",$price);
		if(is_array($rs)){
			return $rs;
		}
		$id=$rs;
		$sql="insert into tb_order_charchat (order_id,doctor_id,content,last_one) values ($id ,$doctor_id,
		'{|}C:TXT:$description','C:TXT:$description' )";
		$query = $this->dbmgr->query($sql);


		$this->dbmgr->commit_trans();
		return	outResult(0,"success",$id);
	}

	public function finishOrder($order_id){
		
		$order_id=parameter_filter($order_id);
		$sql="update tb_order set status='F',finished_time=getdate() where  id=$order_id  ";
		$query = $this->dbmgr->query($sql);
		return	outResult(0,"success",$id);
	}

	public function createVideochatOrder($doctor_id,$order_date,$order_time,$member_id,$name,$mobile,$description){
		
		$order_date=$this->formatOrderDate($order_date);
		$order_time=$this->formatOrderTime($order_time);
		//read to create 
		$this->dbmgr->begin_trans();
		
		if($order_date==""){
			return	outResult(-2,"order_date can not be null");
		}
		if($order_time==""){
			return	outResult(-3,"order_time can not be null");
		}

		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
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
		$price=$doctor["videochat_price"];
		//enable_videochat

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
		
		$rs=$this->createOrder($order_date,$order_time,$member_id,$name,$mobile,$description,"VC",$price);
		if(is_array($rs)){
			return $rs;
		}
		$id=$rs;
		
		$sql="insert into tb_order_videochat (order_id,doctor_id) values ($id ,$doctor_id )";
		$query = $this->dbmgr->query($sql);


		$this->dbmgr->commit_trans();
		return	outResult(0,"success",$id);
	}
	///$type=txt,img,doc
	public function addContentToCharchat($order_id,$doctor_id,$sendside,$type,$content){
		
		$order_id=parameter_filter($order_id);
		$sendside=parameter_filter($sendside);
		$type=parameter_filter($type);
		$content=parameter_filter($content);
		$doctor_id=parameter_filter($doctor_id);
		
		$str="$sendside:$type:$content";
		
		//read to create 
		$this->dbmgr->begin_trans();
		
		
		$sql="update tb_order_charchat set content=isnull(content,'')+N'{|}$str',last_one=N'$str' where order_id=$order_id and doctor_id=$doctor_id   ";
		$query = $this->dbmgr->query($sql);
		
		$sql="update tb_order set updated_date=".$this->dbmgr->getDate()." where id=$order_id   ";
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
		$sql="select * from v_order  where id=$order_id and member_id=$member_id ";
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
		$sql="select * from (
( select v.*,v1.doctor_id tag from v_order v
inner join tb_order_videochat v1 on v.id=v1.order_id and v.act='VC')
  union ( select v.*,v1.doctor_id tag from v_order v
inner join tb_order_charchat v1 on v.id=v1.order_id and v.act='CC')
) v1 where status<>'D' and id=$order_id and member_id=$member_id  ";
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;

	}

 }
 
 $orderMgr=OrderMgr::getInstance();
 $orderMgr->dbmgr=$dbmgr;
 
 
 
 
?>