<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/member.cls.php';
  $member_id=$_REQUEST["member_id"];
  $id=$_REQUEST["id"];
  $result=$memberMgr->deleteMemberPhoto($id,$member_id);
  outputXml($result);

?>