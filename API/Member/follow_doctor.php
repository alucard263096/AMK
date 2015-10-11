<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  $member_id=$_REQUEST["member_id"];
  $doctor_id=$_REQUEST["doctor_id"];
  $is_follow=$_REQUEST["is_follow"];
  $result=$memberMgr->followDoctor($member_id,$doctor_id,$is_follow);
  outputXml($result);

?>