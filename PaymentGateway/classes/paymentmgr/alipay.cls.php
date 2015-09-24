<?php

class AlipayMgr implements IPayment  {
	private	$alipay_config;

	public function __construct()
	{
		Global $CONFIG;

		$this->alipay_config['partner']		= $CONFIG["alipay"]["partner"];

		$this->alipay_config['seller_email']	= $CONFIG["alipay"]["seller_id"];

		$this->alipay_config['key']			= $CONFIG["alipay"]["key"];


		$this->alipay_config['private_key_path']	= ROOT.'/libs/alipay_wap_lib/key/rsa_private_key.pem';

		$this->alipay_config['ali_public_key_path']= ROOT.'/libs/alipay_wap_lib/key/alipay_public_key.pem';

		$this->alipay_config['sign_type']    = 'RSA';

		$this->alipay_config['input_charset']= 'utf-8';

		$this->alipay_config['cacert']    = ROOT.'/libs/alipay_wap_lib/key/cacert.pem';

		$this->alipay_config['transport']    = 'http';

	}

	public function notify(){
		require_once(ROOT."/libs/alipay_wap_lib/alipay_notify.class.php");
		$alipayNotify = new AlipayNotify($this->alipay_config);
		$verify_result = $alipayNotify->verifyNotify();
		
		$ret=Array();
		if($verify_result) {
				$out_trade_no = $_POST['out_trade_no'];
				$trade_no = $_POST['trade_no'];
				$trade_status = $_POST['trade_status'];


				if($trade_status == 'TRADE_SUCCESS'
				||$trade_status == 'TRADE_FINISHED'){
					$ret["result"]="SUCCESS";
					$ret["trade_no"]=$trade_no;
				}else{
				$ret["result"]="FAIL";
			}
			
			
		}else{
			$ret["result"]="FAIL";
		}
		return $ret;
	}
}
 
 
 
 
?>