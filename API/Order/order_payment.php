<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $member_id=$_REQUEST["member_id"];
  $payment_type=$_REQUEST["payment_type"];
  $result=$orderMgr->payment($order_id,$member_id,$payment_type);
  outputXml($result);

?>