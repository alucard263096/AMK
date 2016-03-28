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
		$arr["childMerchantNo"]=$CONFIG["xbt"]["merchantNo"];
		$arr["amount"]=1;//$info["price"];
		$arr["currency"]="CNY";
		$arr["orderBeginTime"]=date('Y-m-d H:i:s');
		$arr["orderExpireTime"]=date('Y-m-d H:i:s',time()+3600);
		$arr["orderName"]=$info["order_no"];
		$arr["orderNotifyUrl"]=$CONFIG["xbt"]["notify"];
		$arr["orderFrontNotifyUrl"]=$CONFIG["xbt"]["return"];
		$arr["productNo"]="DOCREMOTE";
		$arr["productName"]="远程医疗服务";
		$arr["paySource"]="ANDROID";
		
		$call=$sdk->cashDesk($mainParams, $arr);
		echo $call;
		$json=json_decode($call,true);
		print_r($json);

		if($json["code"]=="1"){
			logger_mgr::logInfo("xbt:return=$call");
			return $json["data"]["resultInfo"]["widgetPageUrl"];
		}else{
			logger_mgr::logError("xbt:return=$call");
			return "ERROR";
		}

	}
}
 
 
 
 
?>