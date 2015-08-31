<?php
/*
 * Created on 2010-5-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
function encode($str)
{
	return mb_convert_encoding($str,'UTF-8');
}
function parameter_filter($param)
{
	$arr=array("'"=>"''");
	$param = strtr($param,$arr);
	return $param;
}
function ParentRedirect($url)
{
	//Header("Location: $url");
	echo "<script languate=\"javascript\">";
	echo "parent.location.href='".$url."'";
	echo "</script>";
	exit();
}
function WindowRedirect($url)
{
	//Header("Location: $url");
	echo "<script languate=\"javascript\">";
	echo "window.location.href='".$url."'";
	echo "</script>";
	exit();
}

/*
 function name：remote_file_exists
 function：valid remote file is exists
 params： $url_file - remote file URL
 return：exists return true，else return false
 */
function remote_file_exists($url_file){
	if(@fclose(@fopen($url_file,"r")))
	{
		return true;
	}
	else
	{
		return false;
	}
}

function getMenuJson($menu){
	
	
$item["current"]=true;
$item["title"]="管理工具";
$item["link"]="#";
foreach ($menu as $val){
	
	$sm=$val["sub_function"];
	$subitemcontent=null;
	foreach ($sm as $vc){
		$url=null;
		$url["name"]=$vc["function_name"];
		$url["urlPathinfo"]=$vc["function_link"];
		$subitemcontent[$vc["function_link"]]=$url;
	}
	$list[$val["function_name"]]=$subitemcontent;
	
	
}
$item["list"]=$list;

return json_encode($item);
}

function outputXml($result){
header("Content-type: text/xml");
  $str="<table>";
  $row_count=count($result);
  for($i=0;$i<$row_count;$i++){
	$str.="<row>";
	$j=0;
	foreach($result[$i] as $key => $value){
		if($j%2==1){
			$value_change = array('&'=>'$amp;'
			,'<'=>'&lt;'
			,'>'=>'&gt;'
			,'"'=>'&quot;');
			$str.="<$key>".strtr($value,$value_change)."</$key>";
		}
		$j++;
	}
	$str.="</row>";
  }
  $str.="</table>";
  echo $str;
  exit;
}

function outResult($num,$message){
	$array=Array();
	$arr=Array();
	$arr["id"]=$num;
	$arr["result"]=$message;
	$array[]=$arr;
	return $array;
}

?>
