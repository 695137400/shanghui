package com.mynote.jilu;
import com.alibaba.fastjson.JSON;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * 查看详细信息
 * @author sun
 *
 */
public class XiangActivity extends Activity
{

	DBManager db=null;
	TextView xriqi=null;//日期
	TextView xshijian=null;//时间
	TextView xdidian=null;//地点
	TextView xxingzhi=null;//性质
	TextView xname=null;//姓名
	TextView xsex=null;//性别
	TextView xage=null;//年龄
	TextView xjiguan=null;//籍贯
	TextView xjiban=null;//工作班次
	TextView xgongzuo=null;//工作
	TextView xinfos=null;//本次内容
	TextView xxingqi=null;//星期
	TextView usid=null;//用户ID
    Button de=null;//删除
	MainActivity mm= new MainActivity();

	public void onCreate(Bundle savedInstanceState)
	{  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.xiang);  
		Bundle ext=getIntent().getExtras();//取到从列表页过来的用户ID
		db = DBManager.getInstance(this);
		String tid=ext.getString("tid");
		xriqi = (TextView)findViewById(R.id.xriqi);
		xshijian = (TextView)findViewById(R.id.xshijian);
		xdidian = (TextView)findViewById(R.id.xdidian);
		xxingzhi = (TextView)findViewById(R.id.xxingzhi);
		xname = (TextView)findViewById(R.id.xname);
		xsex = (TextView)findViewById(R.id.xsex);
		xjiguan = (TextView)findViewById(R.id.xjiguan);
		xjiban = (TextView)findViewById(R.id.xjiban);
		xgongzuo = (TextView)findViewById(R.id.xgongzuo);
		xinfos = (TextView)findViewById(R.id.xinfos);
		usid = (TextView)findViewById(R.id.usid);
		UserInfo u=  db.queryTheCursorByid(this, tid);//根据当前用户ID查询用户
		Log.d("【当前用户信息】：", "【 "+JSON.toJSONString(u)+" 】");
		xriqi.setText(u.getRiqi());
        de=(Button)findViewById(R.id.deletese);
		xxingqi = (TextView)findViewById(R.id.xxingqi);
		xage = (TextView)findViewById(R.id.xage);
		xage.setText(u.getAge());
		xshijian.setText(u.getShijian());
		xdidian.setText(u.getDizhi());
		xxingzhi.setText(u.getXingzhi());
		xname.setText(u.getName());
		xsex.setText(u.getSex());
		xjiguan.setText(u.getJiguan());
		xjiban.setText(u.getJiban());
		xgongzuo.setText(u.getGongzuo());
		xinfos.setText(u.getInfos());
		usid.setText(u.getId());
		xxingqi.setText(u.getXingqi());
		//Toast.makeText(this, "取到数据：" + JSON.toJSON(u), 0).show();
	}
	
	/**
	 * 返回列表页
	 * @param v
	 */
	public void fanhui(View v)
	{
		//mm.method(this,null);
		onBackPressed();
	}
	
	/**
	 * 点击修改信息按钮
	 * @param v
	 */
	public void onUpdateInfo(View v)
	{
		//db.updateInfo((UserInfo)usid.getTag());
		try
		{
			Intent t=new Intent(this, EditActivity.class);
			t.putExtra("use", usid.getText());//跳转并传输当前用户ID
			startActivityForResult(t, RESULT_OK);
           this.onBackPressed();
		}
		catch (Exception e)
		{
			Toast.makeText(this, "新增出错：" + e.getMessage(), 0).show();
			Log.d("【跳转新增页面出错】：", "【 "+e.getMessage()+" 】");
		}
	}
	
	/**
	 * 删除数据
	 * @param v
	 */
    public void onDeletese(View v){
    	try {
			db.delete(usid.getText().toString());
			onBackPressed();
		} catch (Exception e) {
			Toast.makeText(this, "删除当前数据出错：" + e.getMessage(), 0).show();
			Log.d("【删除当前数据出错】；", "【 "+e.getMessage()+" 】");
		}
    }
}
