<?php

#[Root]
$CONFIG['rootpath']		= '/AMK/PaymentGateway';  
//$CONFIG['charset']		= 'utf-8'; 
$CONFIG['Title']             = '阿米卡-爱我宝贝';
$CONFIG['URL']="http://localhost/AMK/API";
$CONFIG['lang']="zh-cn";//en-us
$CONFIG["frontendurl"]="http://localhost/AMK/";
$CONFIG["SessionName"]="AMKPaymentGateway";


#[log]
$CONFIG['logsavedir'] 		= 'logs/';	
$CONFIG['error_handler'] ="E_ALL";




#[Database]
$CONFIG['database']['provider']	= 'sqlsrv';  //mssql,sqlsrv
$CONFIG['database']['host']		= '120.24.239.49';  
$CONFIG['database']['database']	= 'AMK150818';  
$CONFIG['database']['user']		= 'sa';  
$CONFIG['database']['psw']		= 'hack2rpc'; 


#[SMS]
$CONFIG["sms"]["AccountSid"]="aaf98f894bfd8efd014c0c06c970099e";
$CONFIG["sms"]["AccountToken"]="bcc5f1180d3ff9282b7af1a2e4a2a8f3";
$CONFIG["sms"]["AppId"]="8a48b5514fa577af014fa675e7840459";
$CONFIG["sms"]["ServerIP"]="sandboxapp.cloopen.com";
$CONFIG["sms"]["ServerPort"]="8883";
$CONFIG["sms"]["SoftVersion"]="2013-12-26";
$CONFIG["sms"]["timeout"]="30";//mins
$CONFIG["sms"]["templeteid"]["bookingsuccess"]="35125";



#[ALIPAY_WAP]
$CONFIG["alipay"]["partner"]="2088911829797670";
$CONFIG["alipay"]["seller_email"]="3188577198@qq.com";
$CONFIG["alipay"]["key"]="70yr74ztz7uwjtjdxmxrqrlmwuvql63i";




?>