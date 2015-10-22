<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $lastcommentdate=$_REQUEST["lastcommentdate"];
  $result=$doctorMgr->getDoctorComment($doctor_id);

  outputXml($result);

?>