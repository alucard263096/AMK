<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $doctor_id=$_REQUEST["doctor_id"];
  $sendside=$_REQUEST["sendside"];
  $type=$_REQUEST["type"];
  $content=$_REQUEST["content"];
  
  $result=$orderMgr->addContentToCharchat($order_id,$doctor_id,$sendside,$type,$content);

  outputXml($result);

?>