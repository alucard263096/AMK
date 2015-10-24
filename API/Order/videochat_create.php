<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $order_date=$_REQUEST["order_date"];
  $order_time=$_REQUEST["order_time"];
  $member_id=$_REQUEST["member_id"];
  $name=$_REQUEST["name"];
  $mobile=$_REQUEST["mobile"];
  $description=$_REQUEST["description"];
  $age=$_REQUEST["age"];
  $sex=$_REQUEST["sex"];


  $result=$orderMgr->createVideochatOrder($doctor_id,$order_date,$order_time,$member_id,$name,$mobile,$age,$sex,$description);
  $doctorMgr->updateVideoQueryCount($doctor_id);
  outputXml($result);

?>