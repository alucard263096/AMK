<?php
/*
 * Created on 2012-6-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
  require 'include/common.inc.php';
  require ROOT.'/classes/datamgr/news.cls.php';
  
  $content=$newsMgr->getContent($_REQUEST["id"]);
  $smarty->assign("info",$content);
  
  $smarty->display(ROOT.'/templates/news.html');
?>