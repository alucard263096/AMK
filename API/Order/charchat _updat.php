<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order=$_REQUEST["order"];
  $doctor_id=$_REQUEST["doctor_id"];
  $sendside=$_REQUEST["sendside"];
  $type=$_REQUEST["type"];
  $content=$_REQUEST["content"];
  
  $result=$orderMgr->addContentToCharchat($order,$doctor_id,$sendside,$type,$content);

  outputXml($result);

?>