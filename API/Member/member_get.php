<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  $mobile=$_REQUEST["mobile"];
  $member_id=$_REQUEST["member_id"];
  $result=$memberMgr->getMemberInfo($mobile,$member_id);
  outputXml($result);

?>