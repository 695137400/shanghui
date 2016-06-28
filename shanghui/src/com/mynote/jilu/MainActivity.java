package com.mynote.jilu;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.content.*;
import java.util.*;
import com.alibaba.fastjson.*;
import android.util.*;
import android.content.pm.PackageManager;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
	Button mainInsert=null;
	Button mainSelect=null;
	DBManager db=null;
	EditText mainEdit=null;
	ListView ls=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mainInsert = (Button)findViewById(R.id.mainInsert);
		mainSelect = (Button)findViewById(R.id.mainSelect);
		db = DBManager.getInstance(this);
		ls = (ListView)findViewById(R.id.mainListView1);
		mainEdit = (EditText)findViewById(R.id.mainEditText);
		method(this, null);
	}

	public void method(Context c, String tj)
	{
		try
		{
			//mainEdit.setText(tj);
			UserInfo[] u=db.queryTheCursor(c, tj);
			ListAdapter lp=new MyAdapter(c, u);
			//Toast.makeText(this, "//////"+JSON.toJSON(u).toString(), 0).show();
			ls.setAdapter(lp);
		}
		catch (Exception e)
		{
			//mainEdit.setText(e.getMessage());
			Toast.makeText(this, "加载数据：" + e.getMessage(), 0).show();
		}
	}

    //自动刷新
    @Override
    protected void onResume()
    {
        // TODO: Implement this method
        super.onResume();
        method(this, mainEdit.getText().toString());
    }


	/**
	 *新增
	 */
	public void onInsert(View v)
	{
		try
		{
			Intent t=new Intent(MainActivity.this, EditActivity.class);
			MainActivity.this.startActivity(t);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "新增出错：" + e.getMessage(), 0).show();
		}
	}
	public void onSelect(View v)
	{
		EditText etx=(EditText)findViewById(R.id.mainEditText);
		String tj=etx.getText().toString();
		if (tj == "" || null == tj || "".equals(tj))
		{
			method(this, null);
		}
		else
		{
			method(this, tj);
		}
	}
	public void onXiang_Xi_Xin_Xi(View v)
	{
		Intent t=new Intent(MainActivity.this, XiangActivity.class);
		TextView tv=(TextView)v.findViewById(R.id.tid);
		t.putExtra("tid", tv.getTag().toString());
		//Toast.makeText(this,"当前选择："+tv.getText(),0).show();
		startActivityForResult(t, RESULT_OK);
	}
}

class MyAdapter extends ArrayAdapter<UserInfo>
{
	public MyAdapter(Context cx, UserInfo[] us)
	{
		super(cx, R.layout.listtxt, us);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater lf=LayoutInflater.from(getContext());
		View v=lf.inflate(R.layout.listtxt, parent, false);
		try
		{
			UserInfo u=getItem(position);
			TextView t0=(TextView)v.findViewById(R.id.tid);
			t0.setText((position + 1) + "");	
			t0.setTag(u.getId());
			TextView t1=(TextView)v.findViewById(R.id.sriqi);
			t1.setText(u.getRiqi());
			TextView t=(TextView)v.findViewById(R.id.xingqi);
			t.setText(u.getXingqi());

			TextView t2=(TextView)v.findViewById(R.id.sshijian);
			t2.setText(u.getShijian());
			TextView t5=(TextView)v.findViewById(R.id.xxingzhi);
			t5.setText(u.getXingzhi());
			TextView t3=(TextView)v.findViewById(R.id.sname);
			t3.setText(u.getName());
			TextView t4=(TextView)v.findViewById(R.id.sdidian);
			t4.setText(u.getDizhi());
		}
		catch (Exception e)
		{
			Toast.makeText(getContext(), "填充列表出错：" + e.getMessage(), 0).show();
		}
		return v;
	}
}



