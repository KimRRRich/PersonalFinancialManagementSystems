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

public class Login extends AppCompatActivity {
    EditText Account,Password;
    Button Login;
    TextView Title;
    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(Login.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            sqLiteDatabase.execSQL("create table tb_pwd(password varchar(20))");
            Toast.makeText(Login.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };
    public long allCaseNum( ){
        String sql = "select count(*) from tb_pwd";
        Cursor cursor = db_read.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        long count=allCaseNum();
        //TODO  去掉Toast
        //Toast.makeText(Login.this,"共有"+count+"条密码",Toast.LENGTH_SHORT).show();
        if(count==0){
            ContentValues contentValues=new ContentValues();
            //String temp="456";
            contentValues.put("password","456");
            db_write.insert("tb_pwd",null,contentValues);
            //TODO  去掉Toast
            //Toast.makeText(Login.this,"密码插入成功",Toast.LENGTH_SHORT).show();
        }

        Account=findViewById(R.id.Login_Account);
        Password=findViewById(R.id.Login_Password);
        Login=findViewById(R.id.Login_Login);
        Title=findViewById(R.id.Login_Title);
        Title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountString;
                String passwordString;
                String CorrectPasswordString;
                accountString=Account.getText().toString();
                passwordString=Password.getText().toString();
                Cursor cursor = db_read.query("tb_pwd", new String[]{"password"}, null,null, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    cursor.moveToNext();
                    CorrectPasswordString=cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    //TODO  去掉Toast
                    //Toast.makeText(Login.this,CorrectPasswordString,Toast.LENGTH_SHORT).show();
                }else{
                    CorrectPasswordString="345";
                    //TODO  去掉Toast
                    //Toast.makeText(Login.this,"数据库中无密码",Toast.LENGTH_SHORT).show();
                }

                if(accountString.equals("123")&&passwordString.equals(CorrectPasswordString)){
                    Intent intent=new Intent(Login.this,MainPage.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        db_write.close();
        db_read.close();
    }

}