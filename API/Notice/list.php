<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/notice.cls.php';
  $doctor_id=$_REQUEST["doctor_id"];
  $result=$noticeMgr->getNoticeList($doctor_id);

  outputXml($result);

?>