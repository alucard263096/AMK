<?php

require ROOT."/libs/xbt_lib/SDK.php";
class XBTMgr   {

	public function __construct()
	{
		Global $CONFIG;
		//OpenSdkConfig::APPID=$CONFIG["xbt"]["APPID"];
		//OpenSdkConfig::KEYSECRET=$CONFIG["xbt"]["KEYSECRET"];
		//OpenSdkConfig::SOURCENO=$CONFIG["xbt"]["SOURCENO"];
		//OpenSdkConfig::API_HOST=$CONFIG["xbt"]["API_HOST"];
	}

	public function submit($info){
		Global $CONFIG;
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
		
		$arr=array();
		$arr["urlKey"]="cash_desk";
		$arr["loginName"]=$info["mobile"];
		$arr["mobile"]=$info["mobile"];
		$arr["outCustomerId"]=$info["mobile"];
		$arr["outTradeNo"]=$info["order_no"];
		$arr["merchantNo"]=$CONFIG["xbt"]["merchantNo"];
		$arr["childMerchantNo"]=OpenSdkConfig::SOURCENO;
		$arr["amount"]=1;//$info["price"];
		$arr["currency"]="CNY";
		$arr["orderBeginTime"]=date('Y-m-d H:i:s');
		$arr["orderName"]=$info["order_no"];
		$arr["orderNotifyUrl"]=$CONFIG["xbt"]["notify"];
		$arr["orderFrontNotifyUrl"]=$CONFIG["xbt"]["return"];
		$arr["productNo"]="DOCREMOTE";
		$arr["productName"]="远程医疗服务";
		$arr["paySource"]="ANDROID";
		$call=htmlspecialchars($sdk->cashDesk($mainParams, $arr));
		$json=json_decode($call,true);

		if($json["code"]=="1"){
			return $json["data"]["resultInfo"]["widgetPageUrl"];
		}else{
			return "ERROR";
		}

	}
}
 
 
 
 
?>