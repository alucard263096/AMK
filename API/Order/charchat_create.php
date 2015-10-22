<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $doctor_id=$_REQUEST["doctor_id"];
  $member_id=$_REQUEST["member_id"];
  $name=$_REQUEST["name"];
  $mobile=$_REQUEST["mobile"];
  $description=$_REQUEST["description"];
  $age=$_REQUEST["age"];
  $sex=$_REQUEST["sex"];
  
  $result=$orderMgr->createCharchatOrder($doctor_id,$member_id,$name,$mobile,$age,$sex,$description);
  $doctorMgr->updateCharQueryCount($doctor_id);
  outputXml($result);

?>