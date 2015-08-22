<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OfficeOpenHourXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OfficeOpenHourXmlModel("officeopenhour.php");
  
  $smarty->assign("MyModule","doctor");
  
  $model->DefaultShow($smarty,$dbmgr,$action,"officeopenhour",$_REQUEST);

?>