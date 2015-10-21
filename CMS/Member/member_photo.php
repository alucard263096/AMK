<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  $action=$_REQUEST["action"];
  $model=new XmlModel("member_photo","member_photo.php");
  
  $smarty->assign("MyModule","member");
  
  
  $model->DefaultShow($smarty,$dbmgr,$action,"member_photo",$_REQUEST);

?>