package com.mynote.jilu;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;

/**
 * 添加密码
 * @author 李志超
 *
 */
public class AddpwdActivity extends Activity
{

    EditText zpwd=null;//真密码
    EditText jpwd=null;//假密码
    DBManager db=null;//
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newpwd);//初始化展示页面
        db = DBManager.getInstance(this);//初始化数据连接
        zpwd = (EditText)findViewById(R.id.zpwd);//初始化密码输入框
    }

    /**
     * 点击添加(确定)按钮
     * @param v
     */
    public void addPwdSave(View v)
    {
        try
        {
            String zp=zpwd.getText().toString();  
            if ("".equals(zp) || null == zp)//非空验证
            {
                Toast.makeText(this, "密码不能为空!", 0).show();
            }
            else//用户输入有内容就可以进行下一步操作
            {
                ContentValues cv=new ContentValues();//定义一个集合(安卓特有)
                cv.put("zpwd", zp);//把用户输入的密码加入集合以方便传输
                if (!db.selectPwd(zp))//先查询用户是否有添加过密码(目前只支持添加唯一一个密码)
                {
                    db.insert("userPwd", cv);//用户没有添加过密码就可以把本次添加的直接保存
                    
                    //建立一个传输，跳转页面，第一个为当前页面，第二个为要跳转的页面
                    Intent t=new Intent(AddpwdActivity.this, SuoPingActivity.class);
                    AddpwdActivity.this.startActivity(t);//调用内置方法进行页面跳转
                    onBackPressed();//关闭当前页面
                    Toast.makeText(this, "添加成功！", 0).show();//添加完成后给出用户提示
                    Log.d("【addpwd-yes】: ", JSON.toJSONString(cv));
                }else{
                	//添加失败给出用户提示
                    Toast.makeText(this, "已经存在密码，不能再添加！", 0).show();
                    Log.d("【addpwd-error】: ", JSON.toJSONString(cv));
                }
            }
        }
        catch (Exception e)//捕捉数据保存过程中的错误
        {
            String er=e.getMessage();
            Log.d("【addpwd】", er);
        }
    }

}
