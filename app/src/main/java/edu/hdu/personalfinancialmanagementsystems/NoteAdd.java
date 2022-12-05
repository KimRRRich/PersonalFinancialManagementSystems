package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteAdd extends AppCompatActivity {
    Button insert,clear;
    EditText Note_Text;
    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(NoteAdd.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            Toast.makeText(NoteAdd.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        db_read=helper.getWritableDatabase();
        db_write=helper.getWritableDatabase();
        db_read.close();
        db_write.close();
        insert=findViewById(R.id.InsertNote_Button);
        clear=findViewById(R.id.ClearNote_Button);
        Note_Text=findViewById(R.id.Note_Text);
        insert.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        clear.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                if(!Note_Text.getText().toString().equals("")){
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("flag",Note_Text.getText().toString());
                    db_write.insert("tb_note",null,contentValues);
                    Toast.makeText(NoteAdd.this,"新增成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(NoteAdd.this,"有必填项未填！",Toast.LENGTH_SHORT).show();
                }
                db_read.close();
                db_write.close();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note_Text.setText("");
            }
        });
    }
}