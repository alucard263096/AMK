package com.helpfooter.steve.amkdoctor.DAO;

import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BannerObj;

import java.util.ArrayList;
import java.util.List;

public class BannerDao extends AbstractDao {

	public BannerDao(Context ctx) {
		super(ctx,"tb_banner");
		// TODO Auto-generated constructor stub
	}

	public ArrayList<AbstractObj> getIndexBanner(){
		return super.getList(" status='A' and code like 'index_banner_%' order by code  ");
	}

	@Override
	public void insertObj(AbstractObj abobj) {
		// TODO Auto-generated method stub
		util.open();

		BannerObj obj=(BannerObj)abobj;

		StringBuffer sql = new StringBuffer();
		sql.append("insert into tb_banner (id,code  ,title,link,pic,status ) values (?,?,?,?,?,?)");
		Object[] bindArgs = {obj.getId(),obj.getCode(),obj.getTitle(),obj.getLink(),obj.getPic(),obj.getStatus()};
		util.execSQL(sql.toString(), bindArgs);

		util.close();
	}

	@Override
	public void updateObj(AbstractObj abobj) {
		// TODO Auto-generated method stub
		util.open();

		BannerObj obj=(BannerObj)abobj;

		StringBuffer sql = new StringBuffer();
		sql.append("update tb_banner set code=?  ,title=?,link=?,pic=?,status=? where id=? ");
		Object[] bindArgs = {obj.getCode(),obj.getTitle(),obj.getLink(),obj.getPic(),obj.getStatus(),obj.getId()};
		util.execSQL(sql.toString(),bindArgs);

		util.close();
	}

	@Override
	AbstractObj newRealObj() {
		return new BannerObj();
	}

	@Override
	void gotoCreateTableSql() {
		util.open();
		StringBuffer sql = new StringBuffer();
		sql.append("create table IF NOT EXISTS  tb_banner " +
				"(id int,code varchar,title varchar,link varchar,pic varchar,status varchar)");
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
