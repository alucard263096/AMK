<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/notice.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $id=$_REQUEST["id"];
  $result=$noticeMgr->setNoticeRead($doctor_id,$id);

  outputXml($result);
?>