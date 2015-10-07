<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  include ROOT.'/classes/modelmgr/NoticeXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new NoticeXmlModel("notice.php",$SysUser["id"]);
  
  $smarty->assign("MyModule","info");
  $smarty->assign("MyMenuId","notice_center");
  if($action=="save"){
	if($_REQUEST["primary_id"]==""){
		$_REQUEST["publish_date"]=date('Y-m-d H:i:s',time());
	}
  }
  $model->DefaultShow($smarty,$dbmgr,$action,"notice",$_REQUEST);
?>