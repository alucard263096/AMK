<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class BannerMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new BannerMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}

	
	public function getBannerListByCondition($condition,$lastupdate_time)
	{
		$condition=parameter_filter($condition);
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,code,title,link,pic,status,updated_date from tb_banner where code like '$condition'  ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function getBannerList($lastupdate_time)
	{
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select  id,code,title,link,pic,status,updated_date  from tb_banner where 1=1  ";
		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	
 }
 
 $bannerMgr=BannerMgr::getInstance();
 $bannerMgr->dbmgr=$dbmgr;
 
 
 
 
?>