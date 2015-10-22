<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  $mobile=$_REQUEST["mobile"];
  $member_id=$_REQUEST["member_id"];
  if($mobile!=""){
	$result=$memberMgr->getMemberInfoByMobile($mobile);
  }else{
	$result=$memberMgr->getMemberInfoById($member_id);
  }
  outputXml($result);

?>