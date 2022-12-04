package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class IncomeInsert extends AppCompatActivity {
    Spinner spinner;
    SQLiteDatabase db_write,db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(IncomeInsert.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            Toast.makeText(IncomeInsert.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("drop table if exists tb_inaccont");
                onCreate(sqLiteDatabase);
            Toast.makeText(IncomeInsert.this,"数据库更新成功",Toast.LENGTH_SHORT).show();
        }
    };
    Button insert_button,income_clear;
    EditText income_money,income_time,income_handler,income_mark;
    TextView showdatabase,text1,text2,text3,text4,text5;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_insert);
        insert_button=findViewById(R.id.insert_button);
        income_money=findViewById(R.id.income_money);
        income_time=findViewById(R.id.income_time);
        income_handler=findViewById(R.id.income_handler);
        income_mark=findViewById(R.id.income_mark);
        showdatabase=findViewById(R.id.showdatabase);
        income_clear=findViewById(R.id.income_clear);
        spinner=findViewById(R.id.spinner);
        text1=findViewById(R.id.textView6);
        text2=findViewById(R.id.textView7);
        text3=findViewById(R.id.textView8);
        text4=findViewById(R.id.textView9);
        text5=findViewById(R.id.textView10);
        calendar = Calendar.getInstance();

        text1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));


        List<String>list=new ArrayList<String>();
        list.add("工资、年薪");
        list.add("股息、红利");
        list.add("租赁收入");
        list.add("其他");
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);

        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        db_read.close();
        db_write.close();

        income_money.requestFocus();

        income_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(IncomeInsert.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                income_time.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                if(!income_money.getText().toString().equals("")&&!income_time.getText().toString().equals("")&&!income_handler.getText().toString().equals("")&&!income_mark.getText().toString().equals("")){
                    contentValues.put("money",Float.parseFloat(income_money.getText().toString()));
                    contentValues.put("time",income_time.getText().toString());
                    contentValues.put("type",spinner.getSelectedItem().toString());
                    contentValues.put("handler",income_handler.getText().toString());
                    contentValues.put("mark",income_mark.getText().toString());
                    db_write.insert("tb_inaccont",null,contentValues);
                    Toast.makeText(IncomeInsert.this,"新增成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(IncomeInsert.this,"有必填项未填！",Toast.LENGTH_SHORT).show();
                }


                String msg="";
                Cursor cursor=db_read.query("tb_inaccont",new String[]{"_id","money","time","type","handler","mark"},null,null,null,null,null);
                while(cursor.moveToNext()){
                    String idString=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    String nameString=cursor.getString(cursor.getColumnIndexOrThrow("money"));
                    String timeString=cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    String sexString=cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    String departmentString=cursor.getString(cursor.getColumnIndexOrThrow("handler"));
                    String salaryString=cursor.getString(cursor.getColumnIndexOrThrow("mark"));
                    msg=msg+"\n"+"_id："+idString+"  money："+nameString+"  time:"+timeString+"  type："+sexString+"  handler："+departmentString+"  mark："+salaryString+" ";
                }
                showdatabase.setText(msg);
                db_write.close();
                db_read.close();
            }
        });



        income_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income_money.setText("");
                income_time.setText("");
                income_handler.setText("");
                income_mark.setText("");
                db_write=helper.getWritableDatabase();
                db_write.delete("tb_inaccont",null,null);
                db_write.close();
            }
        });


    }
}