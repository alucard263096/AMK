<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $doctor_id=$_REQUEST["doctor_id"];
  $member_id=$_REQUEST["member_id"];
  $name=$_REQUEST["name"];
  $mobile=$_REQUEST["mobile"];
  $description=$_REQUEST["description"];
  
  $result=$orderMgr->createCharchatOrder($doctor_id,$member_id,$name,$mobile,$description);

  outputXml($result);

?>