<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/order.cls.php';
  $last_time=$_REQUEST["last_time"];
  $doctor_id=$_REQUEST["doctor_id"];
  $onlyactive=$_REQUEST["onlyactive"];
  $result=$orderMgr->getVideochatOrderList($doctor_id,$last_time,$onlyactive);

  outputXml($result);

?>