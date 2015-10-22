<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $order_id=$_REQUEST["order_id"];
  $doctor_id=$_REQUEST["doctor_id"];
  $service=$_REQUEST["service"];
  $ability=$_REQUEST["ability"];
  $comment=$_REQUEST["comment"];
  $result=$orderMgr->commentOrder($order_id,$service,$ability,$comment);
  $doctorMgr->updateDoctorScore($doctor_id,$service,$ability);
  outputXml($result);

  

?>