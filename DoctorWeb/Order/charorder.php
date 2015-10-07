<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OrderXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OrderXmlModel("charorder.php",$SysUser["id"],"CC","图文咨询订单");
	$smarty->assign("MyModule","order");
  $model->DefaultShow($smarty,$dbmgr,$action,"charorder",$_REQUEST);
?>