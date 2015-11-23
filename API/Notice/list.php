<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/notice.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $last_time=$_REQUEST["last_time"];
  $result=$noticeMgr->getNoticeList($doctor_id);

  outputXml($result);

?>