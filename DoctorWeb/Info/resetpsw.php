<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];

  if($action=="submit"){
	require ROOT.'/classes/datamgr/doctor.cls.php';
	$old_psw=$_REQUEST["old_psw"];
	$new_psw=$_REQUEST["new_psw"];
	echo $doctorMgr->changsePassword($SysUser["id"],$old_psw,$new_psw);
	exit;
  }


  $smarty->assign("MyModule","info");
  $smarty->assign("MyMenuId","resetpsw");
  $smarty->display(ROOT.'/templates/resetpsw.html');

?>