<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/banner.cls.php';
  $last_time=$_REQUEST["last_time"];
  $result=$bannerMgr->getBannerList($last_time);

  outputXml($result);

?>