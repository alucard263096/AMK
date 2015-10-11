<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  $member_id=$_REQUEST["member_id"];
  $result=$memberMgr->getFollowDoctor($member_id);
  outputXml($result);

?>