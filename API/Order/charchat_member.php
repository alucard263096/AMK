<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $member_id=$_REQUEST["member_id"];
  $last_time=$_REQUEST["last_time"];
  $status=$_REQUEST["status"];
  $result=$orderMgr->getMemberCharchatOrderList($member_id,$last_time,$status);

  outputXml($result);

?>