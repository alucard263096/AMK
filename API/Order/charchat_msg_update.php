<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $doctor_id=$_REQUEST["doctor_id"];
  $old=$_REQUEST["old"];
  $new=$_REQUEST["new"];
  
  $result=$orderMgr->updateCharchatMessage($order_id,$doctor_id,$old,$new);

  outputXml($result);

?>