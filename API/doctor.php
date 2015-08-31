<?php
  require 'include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $last_time=$_REQUEST["last_time"];
  $result=$doctorMgr->getDoctorList($last_time);

  outputXml($result);

?>