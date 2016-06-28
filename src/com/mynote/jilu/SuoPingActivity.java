package com.mynote.jilu;
import android.app.Activity;
import android.os.Bundle;
import java.security.PublicKey;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.net.Uri;

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
//登录
    public void onM(View v)
    {
        try
        {
            if (db.selectPwd(edm.getText().toString()))
            {
                Intent t=new Intent(SuoPingActivity.this, MainActivity.class);
                SuoPingActivity.this.startActivity(t);
            }else{
                Uri uri = Uri.parse("https://settings.adobe.com/");
                
                Intent it  = new Intent(Intent.ACTION_VIEW,uri);
                
                startActivity(it);
            }
        }
        catch (Exception e)
        {
            String er=e.getMessage();
        }
    }

    //设置密码
    public void addpwd(View v)
    {
        try
        {
            Intent t=new Intent(SuoPingActivity.this, AddpwdActivity.class);
            SuoPingActivity.this.startActivity(t);
        }
        catch (Exception e)
        {
            String er=e.getMessage();
        }
    }

}
