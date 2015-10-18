<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $result=$doctorMgr->getDoctorStatistic($doctor_id);

  outputXml($result);

?>