<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';
  $order_id=$_REQUEST["order_id"];
  $doctor_id=$_REQUEST["doctor_id"];
  $minute=$_REQUEST["minute"];


  $result=$orderMgr->updateVideoChatTime($order_id,$minute);
  $doctorMgr->updateChatTime($doctor_id,$minute);
  outputXml($result);
?>