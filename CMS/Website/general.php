<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/GeneralTextXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new GeneralTextXmlModel("general.php");
  
  $smarty->assign("MyModule","website");
  $model->DefaultShow($smarty,$dbmgr,$action,"general",$_REQUEST);
?>