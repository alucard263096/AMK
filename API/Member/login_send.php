<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  include ROOT.'/classes/datamgr/sms.cls.php';
  $mobile=$_REQUEST["mobile"];
  $result=$memberMgr->sendLoginVerifyCode($mobile);
  outputXml($result);

?>