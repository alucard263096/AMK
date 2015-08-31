<?php
  require 'include/common.inc.php';
  include ROOT.'/classes/datamgr/news.cls.php';
  $last_time=$_REQUEST["last_time"];
  $result=$newsMgr->getNewsList($last_time);

  outputXml($result);

?>