package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class OutcomeChart extends AppCompatActivity {
    SQLiteDatabase db_write,db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(OutcomeChart.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            sqLiteDatabase.execSQL("create table tb_pwd(password varchar(20))");
            Toast.makeText(OutcomeChart.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists tb_inaccont");
            onCreate(sqLiteDatabase);
            Toast.makeText(OutcomeChart.this,"数据库更新成功",Toast.LENGTH_SHORT).show();
        }
    };

    /*========== 数据相关 ==========*/
    boolean isChange;
    private ColumnChartData mColumnChartData;    //柱状图数据
    private ColumnChartView mColumnChartView;
    List<AxisValue> axisValues = new ArrayList<>();//自定义横轴坐标值
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<Integer> DateMoney=new ArrayList<Integer>();
    ArrayList<Integer> TypeMoney=new ArrayList<Integer>();

    Button changeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome_chart);
        mColumnChartView=findViewById(R.id.Outcome_columnChart);
        isChange=true;
        changeButton=findViewById(R.id.Outcome_ChartChange);
        changeButton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChange) isChange=false;
                else isChange=true;
                InitData();
                InitLable();
            }
        });


        db_read=helper.getReadableDatabase();
        db_write=helper.getWritableDatabase();
        Cursor cursor = db_read.query("tb_outaccont", new String[]{"sum(money) as SumMoney","time"}, null, null, "time", null, "time");
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String moneyString=cursor.getString(cursor.getColumnIndexOrThrow("SumMoney"));
                String timeString=cursor.getString(cursor.getColumnIndexOrThrow("time"));
                DateMoney.add(Integer.parseInt(moneyString));
                date.add(timeString);
            }
            Toast.makeText(OutcomeChart.this,"查询到记录",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(OutcomeChart.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
        }
        Cursor cursor1 = db_read.query("tb_outaccont", new String[]{"sum(money) as SumMoney","type"}, null, null, "type", null, "type");
        if (cursor1 != null && cursor1.getCount() > 0){
            while(cursor1.moveToNext()){
                String moneyString=cursor1.getString(cursor1.getColumnIndexOrThrow("SumMoney"));
                String typeString=cursor1.getString(cursor1.getColumnIndexOrThrow("type"));
                TypeMoney.add(Integer.parseInt(moneyString));
                type.add(typeString);
            }
            Toast.makeText(OutcomeChart.this,"查询到记录",Toast.LENGTH_SHORT).show();
        }
        db_read.close();
        db_write.close();


        InitData();
        InitLable();
    }

    private void InitData(){
        /*========== 柱状图数据填充 ==========*/
        List<Column> columnList = new ArrayList<>(); //柱子列表
        List<SubcolumnValue> subcolumnValueList;     //子柱列表（即一个柱子，因为一个柱子可分为多个子柱）
        axisValues.clear();
        int colorValue=80;

        if(!isChange){
            for (int i = 0; i < date.size(); ++i) {
                subcolumnValueList = new ArrayList<>();
                subcolumnValueList.add(new SubcolumnValue((float) DateMoney.get(i), Color.rgb(colorValue-30,colorValue,colorValue+30)));
                colorValue=(colorValue+20)%256;

                Column column = new Column(subcolumnValueList);
                column.setHasLabels(true);
                columnList.add(column);

                //设置坐标值
                axisValues.add(new AxisValue(i).setLabel(date.get(i)));
            }
        }else{
            for (int i = 0; i < type.size(); ++i) {
                subcolumnValueList = new ArrayList<>();
                subcolumnValueList.add(new SubcolumnValue((float) TypeMoney.get(i), Color.rgb(colorValue-30,colorValue,colorValue+30)));
                colorValue=(colorValue+20)%256;

                Column column = new Column(subcolumnValueList);
                column.setHasLabels(true);
                columnList.add(column);

                //设置坐标值
                axisValues.add(new AxisValue(i).setLabel(type.get(i)));
            }
        }




        mColumnChartData = new ColumnChartData(columnList);               //设置数据
    }

    private void InitLable(){
        /*===== 坐标轴相关设置 =====*/
        //Axis axisX = new Axis();
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("  ");    //设置横轴名称
        axisX.setTextColor(Color.BLACK);
        axisY.setTextColor(Color.BLACK);
        axisX.setTextSize(20);
        axisY.setName("  ");    //设置竖轴名称
        axisX.setHasTiltedLabels(true);
        mColumnChartData.setAxisXBottom(axisX); //设置横轴
        mColumnChartData.setAxisYLeft(axisY);   //设置竖轴
        mColumnChartView.setInteractive(true);
        mColumnChartView.setZoomType(ZoomType.HORIZONTAL);
        mColumnChartView.setMaxZoom((float) 2);//最大方法比例
        mColumnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);


        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        mColumnChartView.setColumnChartData(null);
        mColumnChartView.setColumnChartData(mColumnChartData);
        Viewport v=new Viewport(mColumnChartView.getMaximumViewport());
        v.left=0;
        v.right=4;
        mColumnChartView.setCurrentViewport(v);

    }
}