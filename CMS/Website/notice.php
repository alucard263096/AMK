<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("notice","notice.php");
  
  $smarty->assign("MyModule","website");
  if($action=="save"){
	if($_REQUEST["primary_id"]==""){
		$_REQUEST["publish_date"]=date('Y-m-d H:i:s',time());
	}
  }
  $model->DefaultShow($smarty,$dbmgr,$action,"notice",$_REQUEST);
?>