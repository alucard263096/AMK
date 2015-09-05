<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $date=$_REQUEST["date"];
  $result=$doctorMgr->getDoctorWorktime($doctor_id,$date);

  outputXml($result);

?>