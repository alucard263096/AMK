<?php

#[Root]
$CONFIG['rootpath']		= '/AMK/API';  
//$CONFIG['charset']		= 'utf-8'; 
$CONFIG['Title']             = '阿米卡-爱我宝贝';
$CONFIG['URL']="http://localhost/AMK/API";
$CONFIG['lang']="zh-cn";//en-us
$CONFIG["frontendurl"]="http://localhost/AMK/";
$CONFIG["SessionName"]="AMKAPI";

$CONFIG['smarty']['rootpath']		= '/AMK/API'; 
$CONFIG['solution_configuration']='debug';
$CONFIG['server']		= 'windows';   //windows or linux

#[Smarty config]
$CONFIG['smarty']['compile_check']=true; 
$CONFIG['smarty']['debugging']=false; 
$CONFIG['smarty']['caching']=false; 
$CONFIG['smarty']['cache_lifetime']=3600; //second,-1 is always on 


#[log]
$CONFIG['logsavedir'] 		= 'logs/';	
$CONFIG['error_handler'] ="E_ALL";




#[Database]
$CONFIG['database']['provider']	= 'sqlsrv';  //mssql,sqlsrv
$CONFIG['database']['host']		= '120.24.239.49';  
$CONFIG['database']['database']	= 'AMK150818';  
$CONFIG['database']['user']		= 'sa';  
$CONFIG['database']['psw']		= 'hack2rpc'; 


#[File upload]
$CONFIG['fileupload']['upload_path']	= "../FilesServer";
$CONFIG['fileupload']['nt_check']		= false;
$CONFIG['fileupload']['ftp_url']		= "127.0.0.1";
$CONFIG['fileupload']['ftp_user']		= "anonymous";
$CONFIG['fileupload']['ftp_password']		= "";
$CONFIG['fileupload']['ftp_dir']		= "/";
$CONFIG['fileupload']['try_time']		= "3";
$CONFIG['fileupload']['try_interval']		= "1";//second


#[Excel]
$CONFIG['excel']['version']	= "Excel2007";//2003:Excel5,2007:Excel2007


#[SMS]
$CONFIG["sms"]["AccountSid"]="aaf98f894bfd8efd014c0c06c970099e";
$CONFIG["sms"]["AccountToken"]="cdcb39a689d242f2af537b5ea4a86f61";
$CONFIG["sms"]["AppId"]="8a48b5514fa577af014fa675e7840459";
$CONFIG["sms"]["ServerIP"]="sandboxapp.cloopen.com";
$CONFIG["sms"]["ServerPort"]="8883";
$CONFIG["sms"]["SoftVersion"]="2013-12-26";
$CONFIG["sms"]["timeout"]="30";//mins
$CONFIG["sms"]["templeteid"]["reg"]="35123";
$CONFIG["sms"]["templeteid"]["login"]="35123";
$CONFIG["sms"]["templeteid"]["bookingsuccess"]="35125";


?>