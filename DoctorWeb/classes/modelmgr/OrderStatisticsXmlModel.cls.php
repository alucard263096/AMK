<?php

class OrderStatisticsXmlModel extends XmlModel{
	private $doctor_id;
	public function __construct($pagename,$doctor_id){
		parent::__construct("orderstatistics",$pagename);
		$this->doctor_id=$doctor_id;
	}

	public function ShowSearchResult($dbMgr,$smartyMgr,$request){
		$doctor_id=$this->doctor_id;


		$dimension=$request["dimension"];
		$dateranger_from=trim($request["dateranger_from"]);
		$dateranger_to=trim($request["dateranger_to"]);
		//set default value
		if($dateranger_from==""){
			$dateranger_from=date('Y-m-d',time());;
		}
		if($dateranger_to==""){
			$dateranger_to=date('Y-m-d',time());;
		}

		$symbol=11;

		if($dimension=="Y"){
			$dateranger_from=$this->getYear($dateranger_from)."-01-01";
			$dateranger_to=$this->getYear($dateranger_to)."-12-31";
			$symbol=5;
		}elseif($dimension=="M"){
			$dateranger_from=$this->getYearMonth($dateranger_from)."-01";
			$dateranger_to=date('Y-m-t',strtotime($this->getYearMonth($dateranger_to)."-01"));;
			$symbol=8;
		}

		$dateranger_from.=" 0:0:0";
		$dateranger_to.=" 23:59:59";


		$sql="select distinct 0 id, SUBSTRING(CONVERT(varchar, finished_time, 120 ),0,$symbol) dateranger,0 totalincome,0 totalordercount,'' order_list 
		from v_order_full 
		where doctor_id=$doctor_id and status='F' and finished_time>'$dateranger_from' and finished_time<'$dateranger_to' 
		order by dateranger ";

		
		$query = $dbMgr->query($sql);
		$result = $dbMgr->fetch_array_all($query);

		$sql="select SUBSTRING(CONVERT(varchar, finished_time, 120 ),0,$symbol) dateranger,finished_time,price,order_no 
		from v_order_full 
		where doctor_id=$doctor_id and status='F' and finished_time>'$dateranger_from' and finished_time<'$dateranger_to' 
		order by finished_time ";
		$query = $dbMgr->query($sql);
		$detail = $dbMgr->fetch_array_all($query);

		$count=count($result);
		$detailcount=count($detail);

		for($i=0;$i<$count;$i++){
			for($j=0;$j<$detailcount;$j++){
				if($result[$i]["dateranger"]==$detail[$j]["dateranger"]){
					$result[$i]["totalincome"]=$result[$i]["totalincome"]+$detail[$j]["price"];
					$result[$i]["totalordercount"]=$result[$i]["totalordercount"]+1;
					$result[$i]["order_list"]=$result[$i]["order_list"].$detail[$j]["order_no"]."-".$detail[$j]["price"]."元<br />";
				}
			}
		}


		$smartyMgr->assign("ModelData",$this->XmlData);
		$smartyMgr->assign("PageName",$this->PageName);
		$smartyMgr->assign("result",$result);
		$smartyMgr->display(ROOT.'/templates/model/result.html');
	}

	public function getYear($datestr){
		$arr=explode("-",$datestr);
		return $arr[0];
	}
	public function getYearMonth($datestr){
		$arr=explode("-",$datestr);
		return $arr[0]."-".$arr[1];
	}
}

?>