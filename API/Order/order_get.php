<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $member_id=$_REQUEST["member_id"];
  $result=$orderMgr->getOrder($order_id,$member_id);

  outputXml($result);

?>