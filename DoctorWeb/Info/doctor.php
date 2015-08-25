<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("doctor","doctor.php");
  
  $smarty->assign("MyModule","info");

  if($action=="save"){
	  if($_REQUEST["primary_id"]!=$SysUser["id"]){
		echo "hack";
	  }
  }else{
	$action="edit";
    $_REQUEST["id"]=$SysUser["id"];
  }
  $model->DefaultShow($smarty,$dbmgr,$action,"doctor",$_REQUEST);

?>