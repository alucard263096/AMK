<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  $mobile=$_REQUEST["mobile"];
  $password=$_REQUEST["password"];
  $verifycode=$_REQUEST["verifycode"];
  $name=$_REQUEST["name"];
  $result=$memberMgr->registerMember($mobile,$verifycode,$password,$name);
  outputXml($result);

?>