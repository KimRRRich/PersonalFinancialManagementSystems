package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
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

public class OutcomeUpdate extends AppCompatActivity {
    TextView text1,text2,text3,text4,text5,text6;
    EditText id,money,time,handler,mark;
    Spinner type;
    Button FindId,UpdateId,DeleteId;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    SQLiteDatabase db_write,db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(OutcomeUpdate.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            Toast.makeText(OutcomeUpdate.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists tb_outaccont");
            onCreate(sqLiteDatabase);
            Toast.makeText(OutcomeUpdate.this,"数据库更新成功",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_update);
        text1=findViewById(R.id.outcome_update_textView1);
        text2=findViewById(R.id.outcome_update_textView2);
        text3=findViewById(R.id.outcome_update_textView3);
        text4=findViewById(R.id.outcome_update_textView4);
        text5=findViewById(R.id.outcome_update_textView5);
        text6=findViewById(R.id.outcome_update_textView6);
        id=findViewById(R.id.outcome_update_idvalue);
        money=findViewById(R.id.outcome_update_money);
        time=findViewById(R.id.outcome_update_time);
        handler=findViewById(R.id.outcome_update_handler);
        mark=findViewById(R.id.outcome_update_mark);
        type=findViewById(R.id.outcome_update_spinner);
        FindId=findViewById(R.id.outcome_update_findidbutton);
        UpdateId=findViewById(R.id.outcome_update_updatebutton);
        DeleteId=findViewById(R.id.outcome_update_deletebutton);
        text1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        text6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        FindId.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        UpdateId.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        DeleteId.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        List<String> list=new ArrayList<String>();
        list.add("餐饮美食");
        list.add("交通出行");
        list.add("投资理财");
        list.add("日用百货");
        list.add("其他");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        type.setAdapter(adapter);
        calendar = Calendar.getInstance();
        id.requestFocus();
        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        db_read.close();
        db_write.close();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(OutcomeUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                time.setText(new StringBuilder()
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

        FindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                String idString="";
                String moneyString="";
                String timeString="";
                String typeString="";
                String handlerString="";
                String markString="";
                Cursor cursor = db_read.query("tb_outaccont", new String[]{"_id","money","time","type","handler","mark"}, "_id = ?", new String[]{id.getText().toString()}, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    cursor.moveToNext();
                    idString=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    moneyString=cursor.getString(cursor.getColumnIndexOrThrow("money"));
                    timeString=cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    typeString=cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    handlerString=cursor.getString(cursor.getColumnIndexOrThrow("handler"));
                    markString=cursor.getString(cursor.getColumnIndexOrThrow("mark"));
                    id.setText(idString);
                    money.setText(moneyString);
                    time.setText(timeString);
                    handler.setText(handlerString);
                    mark.setText(markString);
                    if(typeString.equals("餐饮美食")) type.setSelection(0);
                    else if(typeString.equals("交通出行")) type.setSelection(1);
                    else if(typeString.equals("投资理财")) type.setSelection(2);
                    else if(typeString.equals("日用百货")) type.setSelection(3);
                    else if(typeString.equals("其他")) type.setSelection(4);

                    Toast.makeText(OutcomeUpdate.this,"查询到id="+idString+"的记录",Toast.LENGTH_SHORT).show();
                }else{
                    id.setText("");
                    money.setText("");
                    time.setText("");
                    handler.setText("");
                    mark.setText("");
                    Toast.makeText(OutcomeUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_read.close();
            }
        });

        UpdateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                Cursor cursor = db_read.query("tb_outaccont", new String[]{"_id","money","time","type","handler","mark"}, "_id = ?", new String[]{id.getText().toString()}, null, null, null);
                if(cursor != null && cursor.getCount() > 0){
                    if(!money.getText().toString().equals("")&&!time.getText().toString().equals("")&&!handler.getText().toString().equals("")&&!mark.getText().toString().equals("")){
                        ContentValues contentValues=new ContentValues();
                        contentValues.put("money",Float.parseFloat(money.getText().toString()));
                        contentValues.put("time",time.getText().toString());
                        contentValues.put("type",type.getSelectedItem().toString());
                        contentValues.put("handler",handler.getText().toString());
                        contentValues.put("mark",mark.getText().toString());
                        db_write.update("tb_outaccont",contentValues,"_id="+id.getText().toString(),null);
                        Toast.makeText(OutcomeUpdate.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OutcomeUpdate.this,"有必填项没填！",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(OutcomeUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_write.close();
                db_read.close();
            }
        });

        DeleteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                Cursor cursor = db_read.query("tb_outaccont", new String[]{"_id","money","time","type","handler","mark"}, "_id = ?", new String[]{id.getText().toString()}, null, null, null);
                if(cursor != null && cursor.getCount() > 0) {
                    db_write.delete("tb_outaccont","_id="+Integer.parseInt(id.getText().toString()),null);
                    Toast.makeText(OutcomeUpdate.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OutcomeUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_write.close();
                db_read.close();
            }
        });



    }
}