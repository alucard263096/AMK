<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/notice.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $member_id=$_REQUEST["member_id"];
  $payment_type=$_REQUEST["payment_type"];
  $result=$orderMgr->payment($order_id,$member_id,$payment_type);
  $order=$orderMgr->getOrder($order_id,$member_id);
  if(count($order)>0&&$result[0]["id"]==0){
	$noticeMgr->sendOrderForDoctor($order[0]);
	$noticeMgr->sendOrderForClient($order[0]);
  }
  outputXml($result);

?>