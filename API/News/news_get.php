<?php
  require '../include/common.inc.php';
  include ROOT.'/classes/datamgr/news.cls.php';
  $news_id=$_REQUEST["news_id"];
  $result=$newsMgr->getNews($news_id);
  outputXml($result);

?>