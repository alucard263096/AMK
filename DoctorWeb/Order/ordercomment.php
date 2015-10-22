<?php
  require '../include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  require ROOT.'/classes/modelmgr/OrderCommentXmlModel.cls.php';
  $action=$_REQUEST["action"];
  $model=new OrderCommentXmlModel("ordercomment.php",$SysUser["id"]);
  $smarty->assign("MyModule","order");
  $model->DefaultShow($smarty,$dbmgr,$action,"ordercomment",$_REQUEST);
?>