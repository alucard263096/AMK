<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  $member_id=$_REQUEST["member_id"];
  $field=$_REQUEST["field"];
  $value=$_REQUEST["value"];
  $result=$memberMgr->updateMemberInfo($member_id,$field,$value);
  outputXml($result);

?>