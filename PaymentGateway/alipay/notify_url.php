<?php

  require '../include/common.inc.php';
  require ROOT.'/classes/datamgr/order.cls.php';
  require ROOT.'/classes/paymentmgr/alipay.cls.php';
  require ROOT.'/classes/datamgr/sms.cls.php';

  logger_mgr::logInfo("notify alipay start :".$_SERVER["REQUEST_URI"]);
   logger_mgr::logInfo("notify alipay parameter".ArrayToString($_REQUEST));
  $alipay=new AlipayMgr();
  $ret=$alipay->notify();
  
   logger_mgr::logInfo("notify alipay verify return ".ArrayToString($ret));

	  if($ret["result"]=="SUCCESS"){
	  $info=$orderMgr->getOrderByOrderNo($ret["out_trade_no"]);
		$orderMgr->updateOrderPayment($info["id"],"ALIPAY",$ret["trade_no"]);
		$smsMgr->SendQueryConfirm($info["mobile"],$info["tag_name"],$info["order_date"]." ".$info["order_time"]);
	  }

?>