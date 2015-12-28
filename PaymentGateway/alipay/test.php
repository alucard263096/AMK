<?php

  require '../include/common.inc.php';
  require ROOT.'/classes/paymentmgr/payment.interface.php'; 
  require ROOT.'/classes/datamgr/order.cls.php';
  require ROOT.'/classes/datamgr/sms.cls.php';

  $info=$orderMgr->getOrderByOrderNo($_REQUEST["out_trade_no"]);
		$orderMgr->updateOrderPayment($info["id"],"ALIPAY","aa");
		$smsMgr->SendQueryConfirm($info["mobile"],$info["tag_name"],$info["order_date"]." ".$info["order_time"]);

?>