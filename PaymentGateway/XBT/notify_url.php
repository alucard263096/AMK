<?php

  require '../include/common.inc.php'; 
  require ROOT.'/classes/datamgr/order.cls.php';
  require ROOT.'/classes/paymentmgr/xbt.cls.php';
  require ROOT.'/classes/datamgr/sms.cls.php';

   logger_mgr::logInfo("notify XBT parameter".ArrayToString($_REQUEST));
  

	  if($_REQUEST["tradeStatus"]=="SUCCESS"){
	  $info=$orderMgr->getOrderByOrderNo($ret["outTradeNo"]);
		$orderMgr->updateOrderPayment($info["id"],"XBT",$ret["trade_no"]);
		$smsMgr->SendQueryConfirm($info["mobile"],$info["tag_name"],$info["order_date"]." ".$info["order_time"]);
	  }

?>