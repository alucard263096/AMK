<?php
/*
 * Created on 2012-6-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
  require 'include/common.inc.php';
  if($_REQUEST["action"]=="login"){
	if(trim($_REQUEST["login_id"])==""){
		$smarty->assign("ErrorMsg",$SysLang["index"]["logincannotbenull"]);
	}else{
		$smarty->assign("login_id",$_REQUEST["login_id"]);
	}
	if(trim($_REQUEST["password"])==""){
		$smarty->assign("ErrorMsg",$SysLang["index"]["pswcannotbenull"]);
	}else{
		$smarty->assign("password",$_REQUEST["password"]);
	}
	require ROOT.'/classes/datamgr/doctor.cls.php';
	$login_id=$_REQUEST["login_id"];
	$password=$_REQUEST["password"];
	$doctorRows=$doctorMgr->getDoctorByLoginName($login_id);

	if(count($doctorRows)==0||$doctorRows[0]["status"]!="A"){
		$smarty->assign("ErrorMsg",$SysLang["index"]["invaliduser"]);
	}else{
		$doctor=$doctorRows[0];
		if(md5($password)!=$doctor["password"]){
			$smarty->assign("ErrorMsg",$SysLang["index"]["incorrectpsw"]);
		}else{
			$doctor["user_name"]=$doctor["name"];
			$_SESSION[SESSIONNAME]["SysUser"]=$doctor;
			
			WindowRedirect($CONFIG['smarty']['rootpath']."/Admin/dashboard.php");
			exit();
		}
	}
  }
  
  $smarty->display(ROOT.'/templates/index.html');
  
?>