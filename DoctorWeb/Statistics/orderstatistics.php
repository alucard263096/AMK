<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OrderStatisticsXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OrderStatisticsXmlModel("orderstatistics.php",$SysUser["id"]);
  $smarty->assign("MyModule","statistics");
  $model->DefaultShow($smarty,$dbmgr,$action,"orderstatistics",$_REQUEST);
?>