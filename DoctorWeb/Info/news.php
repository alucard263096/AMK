<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("news","news.php");

  
  $smarty->assign("MyModule","info");

   if($action=="search"||$action=="save"){
	$_REQUEST["doctor_id"]=$SysUser["id"];
  }
  $model->DefaultShow($smarty,$dbmgr,$action,"news",$_REQUEST);
?>