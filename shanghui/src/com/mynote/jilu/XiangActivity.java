package com.mynote.jilu;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class XiangActivity extends Activity
{
	MainActivity m= new MainActivity();
	TextView x=null;
	Activity a=null;

	DBManager db=null;
	TextView xriqi=null;
	TextView xshijian=null;
	TextView xdidian=null;
	TextView xxingzhi=null;
	TextView xname=null;
	TextView xsex=null;
	TextView xage=null;
	TextView xjiguan=null;
	TextView xjiban=null;
	TextView xgongzuo=null;
	TextView xinfos=null;
	TextView xxingqi=null;
	TextView usid=null;
    Button de=null;
	MainActivity mm= new MainActivity();

	public void onCreate(Bundle savedInstanceState)
	{  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.xiang);  
		a = this;
		Bundle ext=getIntent().getExtras();
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
		UserInfo u=  db.queryTheCursorByid(this, tid);
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
	public void fanhui(View v)
	{
		//mm.method(this,null);
		a.onBackPressed();
	}

	public void onUpdateInfo(View v)
	{
		//db.updateInfo((UserInfo)usid.getTag());
		try
		{
			Intent t=new Intent(this, EditActivity.class);
			t.putExtra("use", usid.getText());
			//Toast.makeText(this,"当前选择："+tv.getText(),0).show();
			startActivityForResult(t, RESULT_OK);
           this.onBackPressed();
		}
		catch (Exception e)
		{
			Toast.makeText(this, "新增出错：" + e.getMessage(), 0).show();
		}
	}
    public void onDeletese(View v){
        //Toast.makeText(this,"删除",0).show();
        db.delete(usid.getText().toString());
        onBackPressed();
    }
}
