<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/BannerXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new BannerXmlModel("banner.php");
	$smarty->assign("MyModule","website");
  $model->DefaultShow($smarty,$dbmgr,$action,"banner",$_REQUEST);
?>