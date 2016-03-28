<?php

require '../include/common.inc.php';
require ROOT.'/classes/datamgr/order.cls.php';

require ROOT."/libs/xbt_lib/SDK.php";
$sdk = new SDK(OpenSdkConfig::APPID, OpenSdkConfig::KEYSECRET, OpenSdkConfig::SOURCENO);
$mainParams=array(
    'timestamp' => time()*1000,
    'channel' => 'PC',
    'ipAddress' => '',
    'sessionId' => '',
    'deviceFinger' => '',
    'deviceToken' => '',
    'longitude' => '',
    'latitude' => ''
);


$order_no=$_REQUEST["order_no"];
$info=$orderMgr->getOrderByOrderNo($order_no);
$time=date("YmdHis");
$price=$info["price"]*100;
$price=1;
echo $info["mobile"];
$arr=array();
$arr["urlKey"]="cash_desk";
$arr["loginName"]=$info["mobile"];
$arr["mobile"]=$info["mobile"];
//$arr["outCustomerId"]="18503054520";
$arr["outTradeNo"]=$info["order_no"];
$arr["merchantNo"]="mer500220160000011801093094151";
$arr["childMerchantNo"]="mer500220160000011801093094151";
$arr["amount"]=$price;
$arr["currency"]="CNY";
$arr["orderBeginTime"]=date('Y-m-d H:i:s');
$arr["orderExpireTime"]=date('Y-m-d H:i:s',time()+3600);
$arr["orderName"]=$info["order_no"];
$arr["orderNotifyUrl"]="http://www.myhkdoc.com/PaymentGateway/XBT/notify_url.php";
$arr["orderFrontNotifyUrl"]="http://www.myhkdoc.com/PaymentGateway/XBT/return_url.php";
$arr["productNo"]="DOCREMOTE";
$arr["productName"]="远程医疗服务";
$arr["paySource"]="ANDROID";
echo  htmlspecialchars($sdk->cashDesk($mainParams, $arr));





?>