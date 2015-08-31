<?php
/*
 * Created on 2011-2-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */  
 class NewsMgr
 {
 	private static $instance = null;
	public static $dbmgr = null;
	public static $webServiceClient = null;
	public static function getInstance() {
		return self :: $instance != null ? self :: $instance : new NewsMgr();
	}

	private function __construct() {
		
	}
	
	public  function __destruct ()
	{
		
	}

	
	public function getNews($id)
	{
		if($id==""){
			return	outResult(-1,"news_id can not be null");
		}
		$id=parameter_filter($id);
		$sql="select id,title,publish_date,news_type,doctor_id,summary,status,context,thumbnail,upvote
		from tb_news m
		inner join tb_news_statistic ms on m.id=ms.news_id
		where m.id=$id ";
		
		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}
	
	public function getNewsList($lastupdate_time)
	{
		$lastupdate_time=parameter_filter($lastupdate_time);
		$sql="select id,title,publish_date,news_type,doctor_id,summary,status,thumbnail,upvote
		from tb_news m
		inner join tb_news_statistic on m.id=ms.news_id 
		where 1=1 ";

		if($lastupdate_time!=""){
		$sql.=" and updated_date>'$lastupdate_time'  ";
		}

		//echo $sql;
		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 
		return $result;
	}

	public function voteNews($newsid){
		$news_id=parameter_filter($news_id);

		if($news_id==""){
			return	outResult(-1,"news_id can not be null");
		}

		$sql="select 1
		from tb_news_statistic news_id=$news_id";

		$query = $this->dbmgr->query($sql);
		$result = $this->dbmgr->fetch_array_all($query); 

		if(count($result)==0){
			$sql="insert into tb_news_statistic (news_id) value ($news_id)";
			$this->dbmgr->query($sql);
		}

		$sql="update tb_news_statistic set upvote=".$this->getIsNull("upvote",0)."+1 where news_id=$news_id";
		$this->dbmgr->query($sql);

		return	outResult(0,"success");
	}


 }
 
 $newsMgr=NewsMgr::getInstance();
 $newsMgr->dbmgr=$dbmgr;
 
 
 
 
?>