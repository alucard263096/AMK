package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;

import java.util.ArrayList;
import java.util.List;

public class BannerDao extends AbstractDao {

	public BannerDao(Context ctx) {
		super(ctx,"tb_banner");
		// TODO Auto-generated constructor stub
	}

	public ArrayList<BannerObj> getIndexBanner(){
		ArrayList<BannerObj> bannerList=new ArrayList<BannerObj>();
		
		Cursor cursor = null;
		try {
			util.open();
			cursor = util
					.rawQuery(
							"select * from tb_banner where status='A' and code like 'index_banner_%' order by code  ",new String[] {  });
			while (cursor.moveToNext()) {
				int id=cursor.getInt(cursor.getColumnIndex("id"));
				String title=cursor.getString(cursor.getColumnIndex("title"));
				String link=cursor.getString(cursor.getColumnIndex("link"));
				String pic=cursor.getString(cursor.getColumnIndex("pic"));

				BannerObj obj=new BannerObj();
				obj.setId(id);
				obj.setTitle(title);
				obj.setLink(link);
				obj.setPic(pic);
				bannerList.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			util.close();
		}
		return bannerList;
	}

	public BannerObj getBanner(int id) {
		// TODO Auto-generated method stub
		BannerObj obj=null;
		Cursor cursor = null;
		try {
			cursor = util
					.rawQuery(
							"select  * from tb_banner where id=?  ",new String[] {String.valueOf(id)  });
			while (cursor.moveToNext()) {
				String code=cursor.getString(cursor.getColumnIndex("code"));
				String title=cursor.getString(cursor.getColumnIndex("title"));
				String link=cursor.getString(cursor.getColumnIndex("link"));
				String pic=cursor.getString(cursor.getColumnIndex("pic"));
				String status=cursor.getString(cursor.getColumnIndex("status"));
				String updated_date=cursor.getString(cursor.getColumnIndex("updated_date"));

				obj.setId(id);
				obj.setCode(code);
				obj.setTitle(title);
				obj.setLink(link);
				obj.setPic(pic);
				obj.setStatus(status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return obj;
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
