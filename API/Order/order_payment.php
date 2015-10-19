<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/notice.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $member_id=$_REQUEST["member_id"];
  $payment_type=$_REQUEST["payment_type"];
  $result=$orderMgr->payment($order_id,$member_id,$payment_type);

  $order=$orderMgr->getOrder($order_id,$member_id);
  if(count($order)>0&&$order[0]["status"]=="P"){
	$noticeMgr->sendOrderForDoctor($order[0]);
	$noticeMgr->sendOrderForClient($order[0]);
	$smsMgr->SendQueryConfirm($order[0]["mobile"],$order[0]["tag_name"],$order[0]["order_date"]." ".$order[0]["order_time"]);
  }
  
  outputXml($result);
?>