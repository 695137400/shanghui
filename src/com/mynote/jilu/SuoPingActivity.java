package com.mynote.jilu;
import android.app.Activity;
import android.os.Bundle;
import java.security.PublicKey;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.net.Uri;

/**
 * 用户登录界面
 * @author sun
 *
 */
public class SuoPingActivity extends Activity
{
    DBManager db=null;
    EditText edm=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suoping);
        db = DBManager.getInstance(this);
        edm = (EditText)findViewById(R.id.editM);
    }
	/**
	 * 登录
	 * @param v
	 */
    public void onM(View v)
    {
        try
        {
            if (db.selectPwd(edm.getText().toString()))//查询密码
            {
                Intent t=new Intent(SuoPingActivity.this, MainActivity.class);
                SuoPingActivity.this.startActivity(t);
            }else{
                Uri uri = Uri.parse("https://settings.adobe.com/");//密码不存在直接跳转走
                Intent it  = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        }
        catch (Exception e)
        {
            String er=e.getMessage();
            Log.d("【用户登录失败】：", "【 "+e.getMessage()+" 】");
        }
    }

    /**
     * 跳转到设置密码
     * @param v
     */
    public void addpwd(View v)
    {
        try
        {
            Intent t=new Intent(SuoPingActivity.this, AddpwdActivity.class);
            SuoPingActivity.this.startActivity(t);
            onBackPressed();
        }
        catch (Exception e)
        {
            String er=e.getMessage();
        }
    }

}
