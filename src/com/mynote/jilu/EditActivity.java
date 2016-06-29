package com.mynote.jilu;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import com.alibaba.fastjson.*;
import android.view.View.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * 编辑界面
 * @author sun
 *
 */
public class EditActivity extends Activity
{
	Button saves = null;
	EditText shijian=null;
	EditText gongzuo=null;
	EditText infos=null;
	EditText jiban=null;
	EditText jiguan=null;
	EditText name=null;
	EditText age=null;
	EditText xingzhi=null;
	EditText riqi=null;
	EditText dizhi=null;
	EditText xingqi=null;
	DBManager db=null;
	Button quxiao=null;
	RadioGroup rg=null;
	String sex ="男";
	RadioButton r1=null;
	String date=null;
	RadioButton r2=null;
    DateActivity d=new DateActivity();
    View v=null;
    private String[] shi=new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
    private String[] fen;
    public WheelView provinceView;
    public WheelView cityView;



	public void onCreate(Bundle savedInstanceState)
	{  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.edit);  
		saves = (Button)findViewById(R.id.saves);
		db = DBManager.getInstance(this);
        shijian = (EditText)findViewById(R.id.shijian);
		gongzuo = (EditText)findViewById(R.id.gongzuo);
		infos = (EditText)findViewById(R.id.infos);
		jiban = (EditText)findViewById(R.id.jiban);
		jiguan = (EditText)findViewById(R.id.jiguan);
		age = (EditText)findViewById(R.id.age);
		xingqi = (EditText)findViewById(R.id.exingqi);
		name = (EditText)findViewById(R.id.name);
		xingzhi = (EditText)findViewById(R.id.xingzhi);
		riqi = (EditText)findViewById(R.id.riqi);
		r1 = (RadioButton)findViewById(R.id.editRadioButton1);
		r2 = (RadioButton)findViewById(R.id.editRadioButton2);
		rg = (RadioGroup)findViewById(R.id.editRadioGroup1);     
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					if (p2 == r1.getId())
					{
						sex = r1.getText().toString();
					}
					else
					{
						sex = r2.getText().toString();
					}
				}
			});
		dizhi = (EditText)findViewById(R.id.didian);
		quxiao = (Button)findViewById(R.id.quxiao);
        try
		{
			Bundle ext=getIntent().getExtras();
			if (null != ext)
			{
				String tid=ext.getString("use");
				if (null != tid)
				{
					UserInfo u =db.queryTheCursorByid(this, tid);
					shijian.setText(u.getShijian());
					shijian.setTextColor(Color.RED);
					gongzuo.setText(u.getGongzuo());
					gongzuo.setTextColor(Color.RED);
					infos.setText(u.getInfos());
					infos.setTextColor(Color.RED);
					jiban.setText(u.getJiban());
					jiban.setTextColor(Color.RED);
					jiguan.setText(u.getJiguan());
					jiguan.setTextColor(Color.RED);
					name.setText(u.getName());
					name.setTextColor(Color.RED);
					age.setText(u.getAge());
					age.setTextColor(Color.RED);
					xingzhi.setText(u.getXingzhi());
					xingzhi.setTextColor(Color.RED);
					riqi.setText(u.getRiqi());
					riqi.setTextColor(Color.RED);
					dizhi.setText(u.getDizhi());
					dizhi.setTextColor(Color.RED);
					quxiao.setTag(u.getId());
					xingqi.setText(u.getXingqi());
					xingqi.setTextColor(Color.RED);
					if ("男".equals(u.getSex()))
					{
						r1.setChecked(true);
						sex = "男";
					}
					else
					{
						r2.setChecked(true);
						sex = "女";
					}
				}
			}
            else
            {
                SimpleDateFormat sf=new SimpleDateFormat("EEE");
                Date tt=new Date();
                String xqj=sf.format(tt);
                sf = new SimpleDateFormat("yyyy-MM-dd");
                String rqt=sf.format(tt);
                riqi.setText(rqt);
                xingqi.setText(xqj);
            }
		}
		catch (Exception e)
		{
			Toast.makeText(this, "=====" + e.getMessage(), 0).show();
		}

        fen = new String[60];
        for (int i=60;i > 0;i--)
        {
            if (i < 11)
            {
                String sd="0"+(i-1);
                fen[i - 1] = sd;
            }
            else
            {
                fen[i - 1] = "" + (i - 1);
            }
        }
        
        
        shijian.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1)
                {
                    try
                    {
                        LayoutInflater lf=LayoutInflater.from(EditActivity.this);
                        v = lf.inflate(R.layout.activity_main, null);
                        provinceView = (WheelView)v.findViewById(R.id.province);
                        cityView = (WheelView)v.findViewById(R.id.city);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                        initView();

                        builder.setView(v);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface p1, int p2)
                                {
                                    // TODO: Implement this method
                                    Click();
                                }
                            });
                        builder.show();
                    }
                    catch (Exception e)
                    {
                        String vh=e.getMessage();
                    }
                }
            });
        
    }  

    private void initView()
    {
        try
        {
            provinceView.setViewAdapter(new ArrayWheelAdapter<String>(this, shi));
            cityView.setViewAdapter(new ArrayWheelAdapter<String>(this, fen));
            provinceView.setCurrentItem(0);
            cityView.setCurrentItem(0);
        }
        catch (Exception e)
        {
            String s=e.getMessage();
        }
    }

    public void Click()
    {
        // 获得当前显示的省的下标
        int provinceIndex = provinceView.getCurrentItem();
        // 获得当前显示的省的名称
        String ss = shi[provinceIndex];
        int f=cityView.getCurrentItem();
        String ff=fen[f];
        shijian.setText(ss + ":" + ff);
    }

	public void onSaves(View v)
	{
        ContentValues cv=new ContentValues();
		cv.put("shijian", shijian.getText().toString());
		cv.put("gongzuo", gongzuo.getText().toString());
		cv.put("infos", infos.getText().toString());
		cv.put("jiban", jiban.getText().toString());
		cv.put("jiguan", jiguan.getText().toString());
		cv.put("name", name.getText().toString());
		cv.put("sex", sex);
		cv.put("xingqi", xingqi.getText().toString());
		cv.put("age", age.getText().toString());
		cv.put("xingzhi", xingzhi.getText().toString());
		cv.put("riqi", riqi.getText().toString());
		cv.put("didian", dizhi.getText().toString());
        try
        {
            if (null != quxiao.getTag())
            {
                cv.put("_id", quxiao.getTag().toString());
                db.updateInfo(cv);    
            }
            else
            {
                db.insert("userInfo", cv);
            }
            //关闭当前
            onBackPressed();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "添加数据：" + e.getMessage(), 0).show();
            infos.setText(e.getMessage());
        }
	}
	//取消
	public void onQuxiao(View v)
	{
        onBackPressed();
 	}

    public void onShiJian(View vt)
    {
       }

	public void dataRiqi(View v)
	{
		try
		{
			d.new PopupWindows(this, riqi, xingqi);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "" + e.getMessage(), 0).show();
		}
	}
}
