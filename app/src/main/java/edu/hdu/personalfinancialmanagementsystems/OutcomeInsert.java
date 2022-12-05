package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OutcomeInsert extends AppCompatActivity {

    Spinner spinner;
    SQLiteDatabase db_write,db_read;
    Button insert_button,outcome_clear;
    EditText outcome_money,outcome_time,outcome_handler,outcome_mark;
    TextView text1,text2,text3,text4,text5;

    SQLiteOpenHelper helper=new SQLiteOpenHelper(OutcomeInsert.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            Toast.makeText(OutcomeInsert.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists tb_outaccont");
            onCreate(sqLiteDatabase);
            Toast.makeText(OutcomeInsert.this,"数据库更新成功",Toast.LENGTH_SHORT).show();
        }
    };

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
        setContentView(R.layout.activity_outcome_insert);
        insert_button=findViewById(R.id.outcome_insert_button);
        outcome_clear=findViewById(R.id.outcome_insert_clear);
        spinner=findViewById(R.id.outcome_insert_spinner);
        outcome_money=findViewById(R.id.outcome_insert_money);
        outcome_time=findViewById(R.id.outcome_insert_time);
        outcome_handler=findViewById(R.id.outcome_insert_handler);
        outcome_mark=findViewById(R.id.outcome_insert_mark);
        text1=findViewById(R.id.outcome_insert_textView1);
        text2=findViewById(R.id.outcome_insert_textView2);
        text3=findViewById(R.id.outcome_insert_textView3);
        text4=findViewById(R.id.outcome_insert_textView4);
        text5=findViewById(R.id.outcome_insert_textView5);
        db_write=helper.getWritableDatabase();
        db_read=helper.getReadableDatabase();
        db_write.close();
        db_read.close();

        calendar = Calendar.getInstance();
        insert_button.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        outcome_clear.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));

        List<String> list=new ArrayList<String>();
        list.add("餐饮美食");
        list.add("交通出行");
        list.add("投资理财");
        list.add("日用百货");
        list.add("其他");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);

        outcome_money.requestFocus();
        outcome_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(OutcomeInsert.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                outcome_time.setText(new StringBuilder()
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
                if(!outcome_money.getText().toString().equals("")&&!outcome_time.getText().toString().equals("")&&!outcome_handler.getText().toString().equals("")&&!outcome_mark.getText().toString().equals("")){
                    contentValues.put("money",Float.parseFloat(outcome_money.getText().toString()));
                    contentValues.put("time",outcome_time.getText().toString());
                    contentValues.put("type",spinner.getSelectedItem().toString());
                    contentValues.put("handler",outcome_handler.getText().toString());
                    contentValues.put("mark",outcome_mark.getText().toString());
                    db_write.insert("tb_outaccont",null,contentValues);
                    Toast.makeText(OutcomeInsert.this,"新增成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OutcomeInsert.this,"有必填项未填！",Toast.LENGTH_SHORT).show();
                }
                db_write.close();
                db_read.close();
            }
        });

        outcome_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outcome_money.setText("");
                outcome_time.setText("");
                outcome_handler.setText("");
                outcome_mark.setText("");
                db_write=helper.getWritableDatabase();
                db_write.delete("tb_outaccont",null,null);
                db_write.close();
            }
        });


    }
}