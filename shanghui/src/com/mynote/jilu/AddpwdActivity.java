package com.mynote.jilu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;

public class AddpwdActivity extends Activity
{

    EditText zpwd=null;
    EditText jpwd=null;
    DBManager db=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newpwd);
        db = DBManager.getInstance(this);
        zpwd = (EditText)findViewById(R.id.zpwd);
    }


    public void addPwdSave(View v)
    {
        try
        {
            String zp=zpwd.getText().toString();  
            if ("".equals(zp) || null == zp)
            {
                Toast.makeText(this, "密码不能为空!", 0).show();
            }
            else
            {
                ContentValues cv=new ContentValues();
                cv.put("zpwd", zp);
                if (!db.selectPwd(zp))
                {
                    db.insert("userPwd", cv);
                    Intent t=new Intent(AddpwdActivity.this, SuoPingActivity.class);
                    AddpwdActivity.this.startActivity(t);
                    onBackPressed();
                    Toast.makeText(this, "添加成功！", 0).show();
                }else{
                    Toast.makeText(this, "已经存在密码，不能再添加！", 0).show();
                }
            }
        }
        catch (Exception e)
        {
            String er=e.getMessage();
        }
    }

}
