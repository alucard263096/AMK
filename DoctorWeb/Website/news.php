<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("news","news.php");
  
  $smarty->assign("MyModule","website");
  $model->DefaultShow($smarty,$dbmgr,$action,"news",$_REQUEST);
?>