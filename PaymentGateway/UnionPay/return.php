<?php
  require '../include/common.inc.php';
include_once ROOT . '/libs/unionpay/utf8/func/common.php';
include_once ROOT . '/libs/unionpay/utf8/func/secureUtil.php';
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
					$order_id=$info["id"];
					?>
					<?php
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
					
<html>
<head>
</head>
<body>
<script>
function openApp() {  
  
        if (/android/i.test(navigator.userAgent)) {  
             var isrefresh = getUrlParam('refresh'); // 获得refresh参数  
             if(isrefresh == 1) {  
                 return  
             }  
             window.location.href = 'myapp://amklovebaby/success?order_no=<?php echo $order_id; ?>';  
             window.setTimeout(function () {  
                     window.location.href += '&refresh=1' // 附加一个特殊参数，用来标识这次刷新不要再调用myapp:// 了  
             }, 500);  
         }  
  
}
openApp();
</script>
</body>
</html>