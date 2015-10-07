<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OrderXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OrderXmlModel("videoorder.php",$SysUser["id"],"VC","视频会诊订单");
	$smarty->assign("MyModule","order");
  $model->DefaultShow($smarty,$dbmgr,$action,"videoorder",$_REQUEST);
?>