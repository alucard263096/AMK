<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $count=$_REQUEST["count"];
  $result=$doctorMgr->getDoctorComment($doctor_id,$count);

  outputXml($result);

?>