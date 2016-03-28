<?php

require '../include/common.inc.php';
require ROOT.'/classes/datamgr/order.cls.php';
require ROOT.'/classes/paymentmgr/xbt.cls.php';

$payment=new XBTMgr();
$order_no=$_REQUEST["order_no"];
$info=$orderMgr->getOrderByOrderNo($order_no);

echo $payment->submit($info);

?>