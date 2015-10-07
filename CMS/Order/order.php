<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OrderXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OrderXmlModel("order.php");
	$smarty->assign("MyModule","order");
  $model->DefaultShow($smarty,$dbmgr,$action,"order",$_REQUEST);
?>