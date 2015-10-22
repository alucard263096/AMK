<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  $member_id=$_REQUEST["member_id"];
  $title=$_REQUEST["title"];
  $description=$_REQUEST["description"];
  $photo=$_REQUEST["photo"];
  $result=$memberMgr->addMemberPhoto($member_id,$title,$description,$photo);
  outputXml($result);

?>