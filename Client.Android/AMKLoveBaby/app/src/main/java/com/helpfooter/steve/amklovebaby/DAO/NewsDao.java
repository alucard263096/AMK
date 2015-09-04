package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;

import java.util.ArrayList;

public class NewsDao extends AbstractDao {

	public NewsDao(Context ctx) {
		super(ctx,"tb_news");
		// TODO Auto-generated constructor stub
	}

	public ArrayList<AbstractObj> getNewsList(){
		return super.getList(" status='A' order by publish desc  ");
	}

	@Override
	public void insertObj(AbstractObj abobj) {
		// TODO Auto-generated method stub
		util.open();

		NewsObj obj=(NewsObj)abobj;

		StringBuffer sql = new StringBuffer();
		sql.append("insert into tb_news (id,title  ,publish_date,news_type,doctor_id,summary,thumbnail,status,upvote ) values (?,?,?,?,?,?,?,?,?)");
		Object[] bindArgs = {obj.getId(),obj.getTitle(),obj.getPublish_date(),obj.getNews_type(),obj.getDoctor_id(),obj.getSummary(),obj.getThumbnail(),obj.getStatus(),obj.getUpvote()};
		util.execSQL(sql.toString(), bindArgs);

		util.close();
	}

	@Override
	public void updateObj(AbstractObj abobj) {
		// TODO Auto-generated method stub
		util.open();

		NewsObj obj=(NewsObj)abobj;

		StringBuffer sql = new StringBuffer();
		sql.append("update tb_news set title=?  ,publish_date=?,news_type=?,doctor_id=?,summary=?,thumbnail=?,status=?,upvote=? where id=? ");
		Object[] bindArgs = {obj.getTitle(),obj.getPublish_date(),obj.getNews_type(),obj.getDoctor_id(),obj.getSummary(),obj.getThumbnail(),obj.getStatus(),obj.getUpvote(),obj.getId()};
		util.execSQL(sql.toString(),bindArgs);

		util.close();
	}

	@Override
	AbstractObj newRealObj() {
		return new NewsObj();
	}

	@Override
	void gotoCreateTableSql() {
		util.open();
		StringBuffer sql = new StringBuffer();
		sql.append("create table IF NOT EXISTS  tb_banner " +
				"(id int,title varchar ,publish_date varchar,news_type varchar,doctor_id int,summary varchar,thumbnail varchar,status varchar,upvote int )");
		util.execSQL(sql.toString(), new Object[]{});

	}
	static boolean hascheckcreate=false;
	public void createTable(){
		if(hascheckcreate==false){
			gotoCreateTableSql();
			hascheckcreate=true;
		}
	}
}
