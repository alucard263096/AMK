<?php
/*
 * Created on 2012-6-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
  require 'include/common.inc.php';
  include ROOT.'/include/init.inc.php';
  //print_r($SysUser);
  $doctor_id=$SysUser["id"];
  $doctor_name=$SysUser["name"];
  $doctor_photo=$SysUser["photo"];
  $smarty->assign("doctor_id",$doctor_id);
  $smarty->assign("doctor_name",$doctor_name);
  $smarty->assign("doctor_photo",$doctor_photo);

  
  $smarty->display(ROOT.'/templates/charchat.html');
  
?>