<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class DoctorMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new DoctorMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}

	public function getDoctorComment($doctor_id,$count){
		$doctor_id=parameter_filter($doctor_id);
		$lastcomment_date=parameter_filter($lastcomment_date);
		
		if($count==""){
			$count=100;
		}
		
		$sql="select top $count m.photo member_photo,m.name member_name,v.service,v.ability,v.comment,v.comment_date,v.reply,v.reply_date from v_order_full v
inner join tb_member m on v.member_id=m.id
where doctor_id=$doctor_id and hascomment='Y'
";
		$sql.=" order by comment_date desc";

		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	public function getDoctorFollowCount($doctor_id){

		$doctor_id=parameter_filter($doctor_id)+0;
		$sql="select COUNT(1) count from tb_doctor a
inner join tb_member_follow_doctor b on a.id=b.doctor_id
where a.id=$doctor_id";

		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;

	}

	
	public function getDoctorInfoByLoginId($login_id)
	{
		$login_id=parameter_filter($login_id);
		$sql="select id, license,name,photo,office,title,bookingtime,introduce,credentials,expert,status,login_id,password 
		from tb_doctor where login_id='$login_id' ";
		
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	public function getDoctorStatistic($doctor_id){

		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}


		$doctor_id=parameter_filter($doctor_id);
		$this->createStatistic($doctor_id);

		$sql=" select $doctor_id id
		,".$this->dbmgr->getIsNull("ms.general",1000)." as general_score
		,".$this->dbmgr->getIsNull("ms.service",1000)." as service_score
		,".$this->dbmgr->getIsNull("ms.ability",1000)." as ability_score
		,".$this->dbmgr->getIsNull("ms.videoquerycount",100)." as videoquerycount
		,".$this->dbmgr->getIsNull("ms.charquerycount",100)." as charquerycount
		,".$this->dbmgr->getIsNull("ms.chat_time",1500)." as chat_time from tb_doctor m
		left join tb_doctor_statistic ms on m.id=ms.doctor_id where doctor_id=$doctor_id  ";
		
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function getDoctorList($lastupdate_time)
	{
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id, license,name,photo,office,title,bookingtime,introduce,credentials,expert
		,enable_videochat,videochat_price,enable_charchat,charchat_price,status,is_taiwan
		,".$this->dbmgr->getIsNull("ms.general",1000)." as general_score
		,".$this->dbmgr->getIsNull("ms.service",1000)." as service_score
		,".$this->dbmgr->getIsNull("ms.ability",1000)." as ability_score
		,".$this->dbmgr->getIsNull("ms.videoquerycount",100)." as videoquerycount
		,".$this->dbmgr->getIsNull("ms.charquerycount",100)." as charquerycount
		,".$this->dbmgr->getIsNull("ms.chat_time",1500)." as chat_time
		from tb_doctor m
		left join tb_doctor_statistic ms on m.id=ms.doctor_id where 1=1  ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	

	public function updateDoctorScore($doctor_id,$service,$ability){
	
		
		$doctor_id=parameter_filter($doctor_id);
		$service=parameter_filter($service);
		$ability=parameter_filter($ability);

		$general=($service+$ability)/2;

		$this->createStatistic($doctor_id);

		$sql="update tb_doctor_statistic set 
		service=service+$service ,
		ability=ability+$ability ,
		general=general+$general 
		where doctor_id=$doctor_id";
		$query = $this->dbmgr->query($sql);

	}

	public function updateChatTime($doctor_id,$minute){
		
		$doctor_id=parameter_filter($doctor_id);
		$minute=parameter_filter($minute);

		$this->createStatistic($doctor_id);

		$sql="update tb_doctor_statistic set chat_time=chat_time+$minute where doctor_id=$doctor_id";
		$query = $this->dbmgr->query($sql);

	}
	

	public function updateVideoQueryCount($doctor_id){
		$doctor_id=parameter_filter($doctor_id);

		$this->createStatistic($doctor_id);
		$sql="update tb_doctor_statistic set videoquerycount=videoquerycount+1 where doctor_id=$doctor_id";
		$query = $this->dbmgr->query($sql);
	}
	

	public function updateCharQueryCount($doctor_id){
		$doctor_id=parameter_filter($doctor_id);

		$this->createStatistic($doctor_id);
		$sql="update tb_doctor_statistic set charquerycount=charquerycount+1 where doctor_id=$doctor_id";
		$query = $this->dbmgr->query($sql);
	}

	public function createStatistic($doctor_id){
		$sql="select 1 from tb_doctor_statistic where doctor_id=$doctor_id ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 

		if(count($result)>0){
			return;
		}
		
		$sql="insert into tb_doctor_statistic (doctor_id,general,charquerycount,videoquerycount,
		chat_time,service,ability) values
		($doctor_id,1000,100,100,100*15,1000,1000 ) ";
		$query = $this->dbmgr->query($sql);
	}

	public function getDoctorDay($doctor_id){
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}

		
		$numberOfWeek=date('w',strtotime($date));
		if($numberOfWeek==0){
			$numberOfWeek=7;
		}
		$doctor_id=parameter_filter($doctor_id);
		$sql="select worktime from tb_doctor where id=$doctor_id ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 

		$worktime_schedule=explode("\n",$result[0]);
		
		$workday_arr=Array();;

		foreach($worktime_schedule as $value){
			$value=trim(" ".$value);
			$ret=array();
			if($value!=""&&$value[0]=="#"){
				if(is_int(intval($value[1]))&&($value[1]<=7&&$value[1]>=1)){
					$acce=Array();
					$acce[0]=0;
					if($value[1]==0){
						$value[1]=7;
					}
					$acce["day"]=$value[1];
					$dayworktime_arr=explode("->",$value);
					$dayworktime=$dayworktime_arr[1];
					$invalworktime_arr=explode(",",$dayworktime);
					
					foreach($invalworktime_arr as $intval){
							$intval=trim($intval);
							$fromto=explode("-",$intval);
							$from=$fromto[0];
							$to=$fromto[1];
							$arr=$this->getFromToArray($from,$to,$ordertime);
							$ret=array_merge($ret,$arr);
					}

					$acce["worktime"]=$ret;
					$workday_arr[$value[1]]=$acce;
				}
			}
		}
		$today=date("Y-m-d");
		$lastday=date("Y-m-d", strtotime("+14 days", strtotime($today)));
		$sql="select order_date, order_time from tb_order o
			inner join tb_order_videochat ov on o.id=ov.order_id
			 where doctor_id=$doctor_id and order_date>'$today' and order_date<='$lastday' and status<>'F' and status<>'D' and status<>'C' ";
			$query = $this->dbmgr->query($sql);
			$ordertime = $this->dbmgr->fetch_array_all($query); 
		$today=date("Y-m-d");
		$lastday=date("Y-m-d", strtotime("+14 days", strtotime($today)));

		$ret=array();
		for($i=0;$i<14;$i++){
			$today=date("Y-m-d", strtotime("+1 days", strtotime($today)));
			$s=array();
			$s[0]=$today;
			$s["date"]=$today;
			$numberOfWeek=date('w',strtotime($today));
			if($numberOfWeek==0){
				$numberOfWeek=7;
			}
			$s[1]=$numberOfWeek;
			$s["w"]=$numberOfWeek;
			//echo  $numberOfWeek;
			//echo  $today;
			//print_r($workday_arr);
			//echo "<br />";
			
			if($this->inResultArray($workday_arr,$numberOfWeek)){
				$spworktime=$workday_arr[$numberOfWeek]["worktime"];
				$worktimecount=count($spworktime);
				$usedworktimecount=0;
				foreach($spworktime as $val){
					$date=$today;
					$time=$val["time"];
					$count=count($ordertime);
					for($t=0;$t<$count;$t++){
					//echo $ordertime[$t]["order_date"]."==".$date."&&".$ordertime[$t]["order_time"]."==".$time."<br />";
						if($ordertime[$t]["order_date"]==$date
							&&$ordertime[$t]["order_time"]==$time){
							$usedworktimecount++;
							//echo "here?";
						}
					}
				}
				$s[2]=$worktimecount;
				$s["totalcount"]=$worktimecount;
				$s[3]=$usedworktimecount;
				$s["usedcount"]=$usedworktimecount;
				$ret[]=$s;
			}
		}
		return $ret;
	}


	public function getDoctorWorktime($doctor_id,$date){
		
		if($doctor_id==""){
			return	outResult(-1,"doctor_id can not be null");
		}

		$workday_arr=Array();;


		$doctor_id=parameter_filter($doctor_id);
		$sql="select worktime from tb_doctor where id=$doctor_id ";
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array($query); 

		if($date!=""){
			$date=parameter_filter($date);
			//F:fail,D:delete,C:cancel
			$sql="select order_time from tb_order o
			inner join tb_order_videochat ov on o.id=ov.order_id
			 where doctor_id=$doctor_id and order_date='$date' and status<>'F' and status<>'D' and status<>'C' ";
			$query = $this->dbmgr->query($sql);
			$ordertime = $this->dbmgr->fetch_array_all($query); 
		}
		
		$numberOfWeek=date('w',strtotime($date));
		if($numberOfWeek==0){
			$numberOfWeek=7;
		}

		$worktime_schedule=explode("\n",$result[0]);

		$ret=Array();

		foreach($worktime_schedule as $value){
			$value=trim(" ".$value);

			if($value!=""&&$value[0]=="#"){
				if(is_int(intval($value[1]))&&($value[1]<=7&&$value[1]>=1)){
					$acce=Array();
					$acce[0]=0;
					$acce["day"]=$value[1];
					$workday_arr[$value[1]]=$acce;
					if($value[1]==$numberOfWeek){
						$dayworktime_arr=explode("->",$value);
						$dayworktime=$dayworktime_arr[1];
						$invalworktime_arr=explode(",",$dayworktime);
						foreach($invalworktime_arr as $intval){
							$intval=trim($intval);
							$fromto=explode("-",$intval);
							$from=$fromto[0];
							$to=$fromto[1];
							$arr=$this->getFromToArray($from,$to,$ordertime);
							$ret=array_merge($ret,$arr);
						}
					}
				}
			}
		}
		if($date!=""){
			return $ret;
		}else{
			return $workday_arr;
		}
	}

	public function getFromToArray($from,$to,$ordertime){
		$from_arr=explode(":",$from);
		$to_arr=explode(":",$to);
		$from_h=intval($from_arr[0]);
		$from_m=intval($from_arr[1]);
		$to_h=intval($to_arr[0]);
		$to_m=intval($to_arr[1]);

		

		$intvaltime=($to_h-$from_h)*2+($to_m-$from_m==30?1:0)*1;
		$arr=Array();

		$rh=$from_h;
		$rm=$from_m;
		for($i<0;$i<$intvaltime;$i++){
			$str=($rh<10?"0".$rh:$rh).":".($rm<10?"0".$rm:$rm);
			$used="N";
			if($this->inResultArray($ordertime,$str)){
				$used= "Y";
			}
			$arrc=Array();
			$arrc[0]="";
			$arrc["time"]=$str;
			$arrc[1]="";
			$arrc["used"]=$used;
			if($rm==0){
				$rm=30;
			}else{
				$rh++;
				$rm=0;
			}
			$arr[]=$arrc;
		}

		return $arr;
	}

	function inResultArray($arr,$str){
		$count=count($arr);
		//print_r($arr);
		for($i=0;$i<=$count;$i++){
		//echo $arr[$i]["day"]."==".$str."<br />\r\n";
			if(in_array($str,$arr[$i])){
				return true;
			}
		}
		return false;
	}

 }
 
 $doctorMgr=DoctorMgr::getInstance();
 $doctorMgr->dbmgr=$dbmgr;
 
 
 
 
?>