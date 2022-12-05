package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    TextView text1,text2,text3;
    EditText OldPassword,NewPassword1,NewPassword2;
    Button change;
    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(ChangePassword.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            sqLiteDatabase.execSQL("create table tb_pwd(password varchar(20))");
            Toast.makeText(ChangePassword.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        text1=findViewById(R.id.ChangePassword_textview1);
        text2=findViewById(R.id.ChangePassword_textview2);
        text3=findViewById(R.id.ChangePassword_textview3);
        OldPassword=findViewById(R.id.OldPassword);
        NewPassword1=findViewById(R.id.NewPassword1);
        NewPassword2=findViewById(R.id.NewPassword2);
        change=findViewById(R.id.ChangePassword_button);

        text1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        change.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));

        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        db_read.close();
        db_write.close();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OldPasswordString;
                String NewPasswordString1;
                String NewPasswordString2;
                OldPasswordString=OldPassword.getText().toString();
                NewPasswordString1=NewPassword1.getText().toString();
                NewPasswordString2=NewPassword2.getText().toString();
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                Cursor cursor = db_read.query("tb_pwd", new String[]{"password"}, "password = ?", new String[]{OldPasswordString}, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    //TODO  去掉Toast
                    //Toast.makeText(ChangePassword.this,"原密码正确",Toast.LENGTH_SHORT).show();
                    if(NewPasswordString1.equals(NewPasswordString2)&&!NewPasswordString1.equals("")){
                        ContentValues contentValues=new ContentValues();
                        contentValues.put("password",NewPasswordString1);
                        db_write.update("tb_pwd",contentValues,"password="+OldPasswordString,null);
                        Toast.makeText(ChangePassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ChangePassword.this,Login.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ChangePassword.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePassword.this,"原密码错误",Toast.LENGTH_SHORT).show();
                }
                db_read.close();
                db_write.close();

            }
        });
    }
}