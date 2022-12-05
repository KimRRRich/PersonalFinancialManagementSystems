package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class NoteUpdate extends AppCompatActivity {
    TextView title;
    EditText idValue,textValue;
    Button query,update,delete;
    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(NoteUpdate.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time varchar(10),type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            Toast.makeText(NoteUpdate.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_update);
        title=findViewById(R.id.NoteUpdate_textview);
        idValue=findViewById(R.id.NoteUpdate_idValue);
        textValue=findViewById(R.id.NoteUpdate_textvalue);
        query=findViewById(R.id.NoteUpdate_queryButton);
        update=findViewById(R.id.NoteUpdate_updateButton);
        delete=findViewById(R.id.NoteUpdate_deleteButton);

        title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        query.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        update.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        delete.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        db_write.close();
        db_read.close();

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                String flagString="";
                String idString="";
                Cursor cursor = db_read.query("tb_note", new String[]{"_id","flag"}, "_id = ?", new String[]{idValue.getText().toString()}, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    cursor.moveToNext();
                    idString=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    flagString=cursor.getString(cursor.getColumnIndexOrThrow("flag"));
                    textValue.setText(flagString);
                    Toast.makeText(NoteUpdate.this,"查询到id="+idString+"的记录",Toast.LENGTH_SHORT).show();
                }else{
                    textValue.setText("");
                    Toast.makeText(NoteUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_read.close();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                Cursor cursor = db_read.query("tb_note", new String[]{"_id","flag"}, "_id = ?", new String[]{idValue.getText().toString()}, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("flag",textValue.getText().toString());
                    db_write.update("tb_note",contentValues,"_id="+idValue.getText().toString(),null);
                    Toast.makeText(NoteUpdate.this,"修改成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NoteUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_write.close();
                db_read.close();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_read=helper.getReadableDatabase();
                db_write=helper.getWritableDatabase();
                Cursor cursor = db_read.query("tb_note", new String[]{"_id","flag"}, "_id = ?", new String[]{idValue.getText().toString()}, null, null, null);
                if (cursor != null && cursor.getCount() > 0){
                    db_write.delete("tb_note","_id="+Integer.parseInt(idValue.getText().toString()),null);
                    Toast.makeText(NoteUpdate.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NoteUpdate.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
                }
                db_write.close();
                db_read.close();
            }
        });

    }
}