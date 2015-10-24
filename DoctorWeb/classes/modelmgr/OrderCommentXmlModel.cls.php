<?php

class OrderCommentXmlModel extends XmlModel{
	
	public function __construct($pagename,$doctor_id){
		parent::__construct("ordercomment",$pagename);
		$this->XmlData["searchcondition"]=$this->XmlData["searchcondition"]."  and doctor_id=".$doctor_id;
		
	} 
	
	public function Save($dbMgr,$request,$sysuser){
		$reply=parameter_filter($request["reply"]);

		$id=$request["primary_id"];
		$sql="update tb_order_comment set reply='$reply',reply_date=getdate() where order_id=$id ";
		$query = $dbMgr->query($sql);
		return "right".$id;
	}
}

?>