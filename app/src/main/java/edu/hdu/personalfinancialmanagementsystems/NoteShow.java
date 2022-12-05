package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NoteShow extends AppCompatActivity {
    TextView text;
    SQLiteDatabase db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(NoteShow.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            Toast.makeText(NoteShow.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };
    public long allCaseNum( ){
        String sql = "select count(*) from tb_note";
        Cursor cursor = db_read.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        text=findViewById(R.id.NoteShow_textview);
        text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        db_read=helper.getReadableDatabase();
        long count=allCaseNum();
        Toast.makeText(NoteShow.this,"共有"+count+"条记录，向上拖动查看更多",Toast.LENGTH_SHORT).show();
        if(count==0) text.setText("暂时没有记录呢，快去记录一笔吧！");
        else{
            String msg="";
            Cursor cursor=db_read.query("tb_note",new String[]{"_id","flag"},null,null,null,null,null);
            while(cursor.moveToNext()){
                String idString=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                String flagString=cursor.getString(cursor.getColumnIndexOrThrow("flag"));
                msg=msg+"便签id："+idString+"\n便签内容：\n"+flagString+"\n\n\n\n";
                text.setText(msg);
            }
        }
        db_read.close();
    }
}