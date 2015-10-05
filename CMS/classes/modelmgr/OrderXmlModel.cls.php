<?php

class OrderXmlModel extends XmlModel{
	
	public function __construct($pagename){
		parent::__construct("order",$pagename);
	}

	public function Save($dbMgr,$request,$sysuser){
		$remark=$request["remark"];
		$id=$request["primary_id"];
		$sql="update tb_order set remark='$remark'  where id=$id ";
		$query = $dbMgr->query($sql);
		return "right".$id;
	}

}

?>