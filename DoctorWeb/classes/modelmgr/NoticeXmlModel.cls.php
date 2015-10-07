<?php

class NoticeXmlModel extends XmlModel{
	
	public function __construct($pagename,$doctor_id){
		parent::__construct("notice",$pagename);
		$this->XmlData["searchcondition"]=" doctor_id=".$doctor_id;
		//print_r($this->XmlData);
	}
	public function Edit($dbMgr,$smartyMgr,$id){
		 parent::Edit($dbMgr,$smartyMgr,$id);
		 Global $SysUser;
		 $doctor_id=$SysUser["id"];
		 $sql="select * from tb_notice_doctor where notice_id=$id and doctor_id=$doctor_id ";
		 $query = $dbMgr->query($sql);
		 $result = $dbMgr->fetch_array_all($query); 
		 if(count($result)==0){
			$sql="insert into tb_notice_doctor (doctor_id,notice_id,haveread) values ($doctor_id,$id,'Y') ";
			$query = $dbMgr->query($sql);
		 }

	}

}

?>