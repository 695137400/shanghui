package com.mynote.jilu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import android.widget.*;
import org.json.*;
import android.util.*;
import com.alibaba.fastjson.*;


/*
 * @author why
 *         数据库操作类
 *         2016-1-13
 */
public class DBManager
{

	private static DBManager dbManager;
	private DBHelper helper;
	private SQLiteDatabase db;

	private DBManager(Context context)
	{
		helper = new DBHelper(context);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.db;
	}

    public boolean selectPwd(String pwd)
    {
        try{
        Cursor c=db.rawQuery("select * from userPwd ", null);
        String[] top =new String[2];
            c.moveToNext();
        top[0] = c.getString(c.getColumnIndex("zpwd"));
      
        if (!"".equals(pwd) && null != pwd && pwd.equals(top[0]))
        {
            return true;
        }
        else
        {
            return false;
        }
        }catch(Exception e){
            String s=e.getMessage();
            return false;
        }
    }

	public UserInfo queryTheCursorByid(Context cx, String tid)
	{
		Cursor c=db.rawQuery("select * from userInfo where _id =" + tid, null);
		UserInfo u= new UserInfo();
		try
		{
			while (c.moveToNext())
			{
				u.setId(c.getString(c.getColumnIndex("_id")));
				u.setJiban(c.getString(c.getColumnIndex("jiban")));
				u.setDizhi(c.getString(c.getColumnIndex("didian")));
				u.setGongzuo(c.getString(c.getColumnIndex("gongzuo")));
				u.setInfos(c.getString(c.getColumnIndex("infos")));
				u.setJiguan(c.getString(c.getColumnIndex("jiguan")));
				u.setName(c.getString(c.getColumnIndex("name")));
				u.setRiqi(c.getString(c.getColumnIndex("riqi")));
				u.setSex(c.getString(c.getColumnIndex("sex")));
				u.setAge(c.getString(c.getColumnIndex("age")));
				u.setXingqi(c.getString(c.getColumnIndex("xingqi")));
				u.setShijian(c.getString(c.getColumnIndex("shijian")));
				u.setXingzhi(c.getString(c.getColumnIndex("xingzhi")));
			}
		}
		catch (Exception e)
		{
			Toast.makeText(cx, "xingqu" + e.getMessage(), 0).show();
		}

		return u;
	}

	public void updateInfo(ContentValues u)
	{
		db.update("userInfo", u, "_id=?", new String[]{u.get("_id").toString()});
	}

	public static DBManager getInstance(Context context)
	{
		if (dbManager == null)
		{
			dbManager = new DBManager(context);
		}
		return dbManager;
	}

	/**
	 * @param tableName
	 * @param cValues
	 */
	public void insert(String tableName, ContentValues cValues) 
	{
		db.beginTransaction();  //开始事务
		try
		{
			//db.execSQL();
			db.insert(tableName, null, cValues);
			db.setTransactionSuccessful();  //设置事务成功完成
		}
		finally
		{
			db.endTransaction();  //结束事务
		}
	}


	/**
	 * @param tableName
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public UserInfo[] queryTheCursor(Context cx, String selection)
	{
		String sql ="select * from userInfo ";
		if (null != selection)
		{
			sql += " where _id = '" + selection + "' or xingqi like '%"  + selection + "' or riqi like '%" + selection + "%' or shijian like '%" + selection + "%' or name like '%" + selection + "%' or sex = '" + selection + "' or didian like '%" + selection + "%' or xingzhi like '%" + selection + "%' or jiguan like '%" + selection + "%' or jiban like '%" + selection + "%' or gongzuo like '%" + selection + "%'";
		}
		Cursor c=db.rawQuery(sql, null);

		//Cursor c=db.query("userInfo", null,null,null, null, null, null);
		//Toast.makeText(cx,"当前数据："+JSON.toJSON(c),0).show();
		UserInfo[]	us= new UserInfo[c.getCount()];
		//c.moveToFirst();
		int i=0;
		while (c.moveToNext())
		{
			UserInfo u=new UserInfo();
			u.setId(c.getString(c.getColumnIndex("_id")));
			u.setJiban(c.getString(c.getColumnIndex("jiban")));
			u.setDizhi(c.getString(c.getColumnIndex("didian")));
			u.setGongzuo(c.getString(c.getColumnIndex("gongzuo")));
			u.setInfos(c.getString(c.getColumnIndex("infos")));
			u.setJiguan(c.getString(c.getColumnIndex("jiguan")));
			u.setName(c.getString(c.getColumnIndex("name")));
			u.setRiqi(c.getString(c.getColumnIndex("riqi")));
			u.setSex(c.getString(c.getColumnIndex("sex")));
			u.setShijian(c.getString(c.getColumnIndex("shijian")));
			u.setXingzhi(c.getString(c.getColumnIndex("xingzhi")));
			u.setAge(c.getString(c.getColumnIndex("age")));
			u.setXingqi(c.getString(c.getColumnIndex("xingqi")));
			us[i] = u;
			i++;
		}
		return paixu(us);
	}

    /**
     *
     *排序
     */
	public UserInfo[] paixu(UserInfo[] tu)
	{
		UserInfo temp=null;
		for (int i=0;i < tu.length;i++)
		{  
			for (int j=i + 1;j < tu.length;j++)
			{  
				if (null != tu[i] && null != tu[j].getRiqi())
				{
                    String tj=null;
                    if (tu[i].getRiqi().split("-").length > 1)
                    {
                        tj = tu[i].getRiqi().split("-")[0] + tu[i].getRiqi().split("-")[1] + tu[i].getRiqi().split("-")[2];
                    }
                    else
                    {
                        tj = tu[i].getRiqi();
                    }
					Integer a=Integer.parseInt(tj);
					if (null != tu[j])
					{
                        String ts=null;
                        if (tu[j].getRiqi().split("-").length > 1)
                        {
                            ts = tu[j].getRiqi().split("-")[0] + tu[j].getRiqi().split("-")[1] + tu[j].getRiqi().split("-")[2];
                        }
                        else
                        {
                            ts = tu[j].getRiqi();
                        }
                        Integer b=Integer.parseInt(ts);
						if (a < b)
						{  
							temp = tu[j];  
							tu[j] = tu[i];  
							tu[i] = temp;  
						}
                    }
				}	
			}  
		}  
		return paishi(tu);
	}

    /**
     *排序时间
     *
     */
    public UserInfo [] paishi(UserInfo[]tu)
    {
        UserInfo st=null;
        for (int o=0;o < tu.length;o++)
        {
            for (int p=o + 1;p < tu.length;p++)
            {
                String a=null;
                if (tu[o].getRiqi().split("-").length > 1)
                {
                    a = tu[o].getRiqi().split("-")[0] + tu[o].getRiqi().split("-")[1] + tu[o].getRiqi().split("-")[2];
                }
                else
                {
                    a = tu[o].getRiqi();
                }
                String b=null;
                if (tu[p].getRiqi().split("-").length > 1)
                {
                    b = tu[p].getRiqi().split("-")[0] + tu[p].getRiqi().split("-")[1] + tu[p].getRiqi().split("-")[2];
                }
                else
                {
                    b = tu[p].getRiqi();
                }
                if (a.equals(b))
                {
                    int c=Integer.parseInt(tu[o].getShijian().split(":")[0]);
                    int d=Integer.parseInt(tu[p].getShijian().split(":")[0]);
                    if (c > d)
                    {
                        st = tu[o];
                        tu[o] = tu[p];
                        tu[p] = st;
                    }
                }
            }
        }
        return tu;
    }

    public void delete(String id)
    {
        String sql="delete from userInfo where _id=" + id;
        db.execSQL(sql);
    }
}

