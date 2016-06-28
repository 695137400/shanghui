package com.mynote.jilu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.*;
import android.widget.*;

/**
 * why
 * 2016-1-13
 */
public class DBHelper extends SQLiteOpenHelper
{

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}
	public SQLiteDatabase db=null;
    public DBHelper(Context context)
	{
        //CursorFactory设置为null,使用默认值
        super(context, null, null, 1);
		try
		{
			File f=new File("/sdcard/工作日志/");
			//创建文件夹
			if (!f.exists())
			{
				f.mkdirs();
			}
			f = new File("/sdcard/工作日志/gzrz.db");
			//创建文件
			if (!f.exists())
			{
				f.createNewFile();
			}
			db = SQLiteDatabase.openOrCreateDatabase(f, null);
			onCreate(db);
		}
		catch (Exception e)
		{
			Toast.makeText(context, "创建数据库：" + e.getMessage(), 0).show();
		}
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db)
	{
        db.execSQL("CREATE TABLE IF NOT EXISTS userInfo" +
				   "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				   "shijian VARCHAR, gongzuo VARCHAR, infos text, jiban VARCHAR," +
				   "jiguan VARCHAR, name VARCHAR,xingqi VARCHAR, age VARCHAR,"+
                   "sex VARCHAR, xingzhi VARCHAR,riqi VARCHAR,didian VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS userPwd" +
                   "(_id INTEGER PRIMARY KEY AUTOINCREMENT, zpwd VARCHAR, jpwd VARCHAR)");
		
    }

}

