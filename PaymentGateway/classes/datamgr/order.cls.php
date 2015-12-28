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
	
	public function getOrderByOrderNo($order_no){
		$sql="select * from (
( select v.*,v.doctor_id tag,d.name tag_name from v_order_full v
inner join tb_doctor d on v.doctor_id=d.id )
) v where status<>'D' and order_no='$order_no'  ";
		$query = $this->dbmgr->query($sql);
		$return = $this->dbmgr->fetch_array($query);

		return $return;
	}
	public function updateOrderPayment($order_id,$payment_type,$trade_no){
		
		$this->dbmgr->begin_trans();

		$sql="update tb_order set status='P' where  id=$order_id ";
		$query = $this->dbmgr->query($sql);

		
		$sql="update tb_order_payment set payment='Y',payment_type='$payment_type',payment_time=".$this->dbmgr->getDate().",trade_no='$trade_no' where order_id=$order_id ";
		$query = $this->dbmgr->query($sql);

		$this->dbmgr->commit_trans();
	}
	public  function __destruct ()
	{
		
	}
 }
 
 $orderMgr=OrderMgr::getInstance();
 $orderMgr->dbmgr=$dbmgr;
 
 
 
 
?>