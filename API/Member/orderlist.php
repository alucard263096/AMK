<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';
  $last_time=$_REQUEST["last_time"];
  $member_id=$_REQUEST["member_id"];
  $result=$orderMgr->getMemberOrderList($member_id,$last_time);

  outputXml($result);

?>