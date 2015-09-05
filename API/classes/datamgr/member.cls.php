<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class MemberMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new MemberMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}
	
	public function sendLoginVerifyCode($mobile){
		if($mobile==""){
			return	outResult(-1,"mobile can not be null");
		}
		$mobile=parameter_filter($mobile);
		$sql="select count(1) from tb_member where mobile='$mobile' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		if($result[0]==0){
			return	outResult(-2,"no this mobile no");
		}

		Global $smsMgr;
		$smsMgr->SendLoginVerifyCodeMessage($mobile);

		return	outResult(0,"sent success");

	}

	
	public function getMemberLoginInfo($mobile){
		if($mobile==""){
			return	outResult(-1,"mobile can not be null");
		}
		$mobile=parameter_filter($mobile);
		$sql="select * from tb_member where mobile='$mobile' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		if(count($result)==0){
			return	outResult(-2,"no this mobile no");
		}

		Global $smsMgr;
		$res=$smsMgr->getLastSent($mobile,"L");
		$lastsent_verifycode=$res["code"];

		$result[0][count($result[0])/2]=$lastsent_verifycode;
		$result[0]["verifycode"]=$lastsent_verifycode;

		return	$result;

	}

	public function sendRegisterVerifyCode($mobile){
		if($mobile==""){
			return	outResult(-1,"mobile can not be null");
		}
		$mobile=parameter_filter($mobile);
		$sql="select count(1) from tb_member where mobile='$mobile' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		if($result[0]>0){
			return	outResult(-2,"mobile has been used");
		}

		Global $smsMgr;
		$smsMgr->SendRegisterVerifyCodeMessage($mobile);

		
			return	outResult(0,"sent success");

	}
	

	public function registerMember($mobile,$verifycode,$password,$name){
		if($mobile==""){
			return	outResult(-1,"mobile can not be null");
		}
		if($verifycode==""){
			return	outResult(-11,"verify code can not be null");
		}
		if($password==""){
			return	outResult(-12,"password can not be null");
		}
		$mobile=parameter_filter($mobile);
		$sql="select count(1) from tb_member where mobile='$mobile' ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 
		if($result[0]>0){
			return	outResult(-2,"mobile has been used");
		}

		Global $smsMgr;
		$lastsent_verifycode=$smsMgr->getLastSent($mobile,"G");
		if($verifycode!=$lastsent_verifycode["code"]){
			return	outResult(-3,"verify code is incorrect");
		}

		$id=$this->dbmgr->getNewId("tb_member");

		$sql="insert into tb_member (id,mobile,password,name,status,created_date) values
		($id,'$mobile','$password','$name','A',".$this->dbmgr->getDate().")";

		$query = $this->dbmgr->query($sql);

		return	outResult(0,"register success");
	}

 }
 
 $memberMgr=MemberMgr::getInstance();
 $memberMgr->dbmgr=$dbmgr;
 
 
 
 
?>