<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/DoctorXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new DoctorXmlModel("doctor.php");
  
  $smarty->assign("MyModule","doctor");
  
  
  $model->DefaultShow($smarty,$dbmgr,$action,"doctor",$_REQUEST);

?>