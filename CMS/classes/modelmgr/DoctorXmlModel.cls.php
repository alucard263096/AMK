<?php

class DoctorXmlModel extends XmlModel{
	
	public function __construct($pagename){
		parent::__construct("doctor",$pagename);
	}

	public function Save($dbMgr,$request,$sysuser){
	Global $SysLang; 
		if($request["primary_id"]==""){
			$code=$request["license"];
			$code=parameter_filter($code);
			$sql="select * from tb_doctor where license='$code' ";
			$query = $dbMgr->query($sql);
			$userRows = $dbMgr->fetch_array_all($query); 
			if(count($userRows)>0){
				return $SysLang["doctor"]["codehasbeenused"];
			}

			$login_id=$request["login_id"];
			$loginname=parameter_filter($login_id);
			$sql="select * from tb_doctor where login_id='$login_id' ";
			$query = $dbMgr->query($sql);
			$userRows = $dbMgr->fetch_array_all($query); 
			if(count($userRows)>0){
				return $SysLang["user"]["loginnameduplicate"];
			}
		}

		return parent::Save($dbMgr,$request,$sysuser);

	}

}

?>