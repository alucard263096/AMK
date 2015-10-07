<?php

class OrderXmlModel extends XmlModel{
	
	public function __construct($pagename,$doctor_id,$act,$name){
		parent::__construct("order",$pagename);
		$this->XmlData["searchcondition"]=$this->XmlData["searchcondition"]." and r_main.act='".$act."' and doctor_id=".$doctor_id;
		$this->XmlData["name"]=$name;
		if($act=='CC'){
			$arr=$this->XmlData["fields"]["field"];
			$this->XmlData["fields"]["field"]=Array();
			foreach ($arr as $value){
				if(!($value["key"]=="order_date"||$value["key"]=="order_time")){
					$this->XmlData["fields"]["field"][]=$value;
				}
			}
		}
	} 

	public function Save($dbMgr,$request,$sysuser){
		$remark=parameter_filter($request["remark"]);
		$status=parameter_filter($request["status"]);
		if($status=='T'){
			return "你不能把状态改为<订单创建>";
		}
		$id=$request["primary_id"];
		$sql="update tb_order set remark='$remark',status='$status'  where id=$id ";
		$query = $dbMgr->query($sql);
		return "right".$id;
	}

}

?>