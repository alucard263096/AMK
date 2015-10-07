<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $result=$orderMgr->finishOrder($order_id);

  outputXml($result);

?>