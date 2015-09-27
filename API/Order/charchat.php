<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';

  $doctor_id=$_REQUEST["doctor_id"];
  $last_time=$_REQUEST["last_time"];
  $result=$orderMgr->getCharchatOrderList($doctor_id,$last_time);

  outputXml($result);

?>