package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeShow extends AppCompatActivity {
    TextView income_textview;
    SQLiteDatabase db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(IncomeShow.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            Toast.makeText(IncomeShow.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };
    public long allCaseNum( ){
        String sql = "select count(*) from tb_inaccont";
        Cursor cursor = db_read.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_show);
        income_textview=findViewById(R.id.income_textview);
        db_read=helper.getReadableDatabase();
        income_textview.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        long count=allCaseNum();
        Toast.makeText(IncomeShow.this,"共有"+count+"条记录，上滑查看更多",Toast.LENGTH_SHORT).show();
        if(count==0) income_textview.setText("暂时没有记录呢，快去记录一笔吧！");
        else{
            String msg="";
            Cursor cursor=db_read.query("tb_inaccont",new String[]{"_id","money","time","type","handler","mark"},null,null,null,null,null);
            while(cursor.moveToNext()){
                String idString=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                String nameString=cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String timeString=cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String sexString=cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String departmentString=cursor.getString(cursor.getColumnIndexOrThrow("handler"));
                String salaryString=cursor.getString(cursor.getColumnIndexOrThrow("mark"));
                msg=msg+"收入记录id："+idString+"\n金额："+nameString+"元"+"                 时间:    "+timeString+"\n类型："+sexString+"          付款方："+departmentString+"\n备注："+salaryString+"\n\n\n";
                income_textview.setText(msg);
            }
        }
        db_read.close();
    }
}