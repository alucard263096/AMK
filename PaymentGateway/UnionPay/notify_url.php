<?php
  require '../include/common.inc.php';
include_once ROOT . 'libs/unionpay/utf8/func/common.php';
include_once ROOT . 'libs/unionpay/utf8/func/secureUtil.php';
  require ROOT.'/classes/datamgr/order.cls.php';
  require ROOT.'/classes/datamgr/sms.cls.php';

			
			foreach ( $_POST as $key => $val ) {
			 echo isset($mpi_arr[$key]) ?$mpi_arr[$key] : $key ;
			 echo $val ;
			}
			
			if (isset ( $_POST ['signature'] )) {
				
				if( verify ( $_POST )){
				
				$orderId = $_POST ['orderId']; //其他字段也可用类似方式获取
				$respCode = $_POST ['respCode']; //判断respCode=00或A6即可认为交易成功
				if($respCode=="00"||$respCode=="A6"){
					$info=$orderMgr->getOrderByOrderNo($orderId);
					$orderMgr->updateOrderPayment($info["id"],"UNION",$ret["trade_no"]);
					$smsMgr->SendQueryConfirm($info["mobile"],$info["tag_name"],$info["order_date"]." ".$info["order_time"]);
				}
				//如果卡号我们业务配了会返回且配了需要加密的话，请按此方法解密
        //if(array_key_exists ("accNo", $_POST)){
				//	$accNo = decryptData($_POST["accNo"]);
				//}
				}
			} else {
				echo '签名为空';
			}
?>