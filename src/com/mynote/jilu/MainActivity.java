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

/**
 * 列表页
 * @author sun
 *
 */
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
    
    /**
     * 根据条件查询
     * @param c
     * @param tj
     */
	public void method(Context c, String tj)
	{
		try
		{
			UserInfo[] u=db.queryTheCursor(c, tj);
			Log.d("【查询到当前数据】：", "【 "+JSON.toJSONString(u)+" 】");
			ListAdapter lp=new MyAdapter(c, u);
			ls.setAdapter(lp);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "加载数据：" + e.getMessage(), 0).show();
			Log.d("【查询数据出错】：", "【 "+e.getMessage()+" 】");
		}
	}

    /**
     * 返回自动刷新
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        method(this, mainEdit.getText().toString());
    }


	/**
	 * 跳转到添加数据
	 * @param v
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
	
	/**
	 * 点击查询按钮
	 * @param v
	 */
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
	
	/**
	 * 跳转到详细信息
	 * @param v
	 */
	public void onXiang_Xi_Xin_Xi(View v)
	{
		Intent t=new Intent(MainActivity.this, XiangActivity.class);
		TextView tv=(TextView)v.findViewById(R.id.tid);
		t.putExtra("tid", tv.getTag().toString());
		startActivityForResult(t, RESULT_OK);
	}
}

/**
 * 封装的数据list填充类
 * @author sun
 *
 */
class MyAdapter extends ArrayAdapter<UserInfo>
{
	
	/**
	 * 初始化list传入数据数组
	 * @param cx
	 * @param us
	 */
	public MyAdapter(Context cx, UserInfo[] us)
	{
		super(cx, R.layout.listtxt, us);
	}

	/**
	 * 数据封装
	 * position 表示下标
	 */
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



