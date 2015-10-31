<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("member","member.php");
  
  $smarty->assign("MyModule","member");
  
  
  $model->DefaultShow($smarty,$dbmgr,$action,"member",$_REQUEST);

?>