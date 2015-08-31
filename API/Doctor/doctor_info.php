<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/doctor.cls.php';
  $login_id=$_REQUEST["login_id"];
  $result=$doctorMgr->getDoctorInfoByLoginId($login_id);

  outputXml($result);

?>