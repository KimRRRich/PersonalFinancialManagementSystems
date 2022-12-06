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
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class IncomeChart extends AppCompatActivity {
    SQLiteDatabase db_write,db_read;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(IncomeChart.this,"test.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table tb_inaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_outaccont(_id integer primary key autoincrement,money float,time date,type varchar(20),handler varchar(100),mark varchar(200))");
            sqLiteDatabase.execSQL("create table tb_note(_id integer primary key autoincrement,flag varchar(200))");
            sqLiteDatabase.execSQL("create table tb_pwd(password varchar(20))");
            Toast.makeText(IncomeChart.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists tb_inaccont");
            onCreate(sqLiteDatabase);
            Toast.makeText(IncomeChart.this,"数据库更新成功",Toast.LENGTH_SHORT).show();
        }
    };

    /*========== 数据相关 ==========*/
    boolean isChange;
    private ColumnChartData mColumnChartData;    //柱状图数据
    private ColumnChartView mColumnChartView;
    List<AxisValue> axisValues = new ArrayList<>();//自定义横轴坐标值


    private LineChartView lineChart;
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<Integer> DateMoney=new ArrayList<Integer>();
    ArrayList<Integer> TypeMoney=new ArrayList<Integer>();

    Button changeButton;

//    private List<PointValue> mPointValues = new ArrayList<PointValue>();
//    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
//    /**
//     * 设置X 轴的显示
//     */
//    private void getAxisXLables() {
//        for (int i = 0; i < date.size(); i++) {
//            mAxisXValues.add(new AxisValue(i).setLabel(date.get(i)));
//        }
//    }
//
//    /**
//     * 图表的每个点的显示
//     */
//    private void getAxisPoints() {
//        for (int i = 0; i < DateMoney.size(); i++) {
//            mPointValues.add(new PointValue(i, DateMoney.get(i)));
//        }
//    }
//    private void initLineChart() {
//        Line line = new Line(mPointValues).setColor(Color.parseColor("#2A3E52"));  //折线的颜色（橙色）
//        List<Line> lines = new ArrayList<Line>();
//        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
//        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
//        line.setFilled(false);//是否填充曲线的面积
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//        //line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
//        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
//        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
//        lines.add(line);
//        LineChartData data = new LineChartData();
//        data.setLines(lines);
//
//        //坐标轴
//        Axis axisX = new Axis(); //X轴
//        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
//        axisX.setTextColor(Color.BLACK);  //设置字体颜色
//        //axisX.setName("date");  //表格名称
//        axisX.setTextSize(10);//设置字体大小
//        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
//        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
//        data.setAxisXBottom(axisX); //x 轴在底部
//        //data.setAxisXTop(axisX);  //x 轴在顶部
//        axisX.setHasLines(true); //x 轴分割线
//
//        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
//        Axis axisY = new Axis();  //Y轴
//        axisY.setName("     ");//y轴标注
//        axisY.setTextSize(10);//设置字体大小
//        axisY.setTextColor(Color.BLACK);  //设置字体颜色
//        data.setAxisYLeft(axisY);  //Y轴设置在左边
//        //data.setAxisYRight(axisY);  //y轴设置在右边
//
//
//        //设置行为属性，支持缩放、滑动以及平移
//        lineChart.setInteractive(true);
//        lineChart.setZoomType(ZoomType.HORIZONTAL);
//        lineChart.setMaxZoom((float) 2);//最大方法比例
//        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        lineChart.setLineChartData(data);
//        lineChart.setVisibility(View.VISIBLE);
//        /**注：下面的7，10只是代表一个数字去类比而已
//         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
//         */
//        Viewport v = new Viewport(lineChart.getMaximumViewport());
//        v.left = 0;
//        v.right = 7;
//        lineChart.setCurrentViewport(v);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_chart);
        mColumnChartView=findViewById(R.id.columnChart);
        isChange=true;
        changeButton=findViewById(R.id.Income_ChartChange);
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
        Cursor cursor = db_read.query("tb_inaccont", new String[]{"sum(money) as SumMoney","time"}, null, null, "time", null, "time");
        if (cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String moneyString=cursor.getString(cursor.getColumnIndexOrThrow("SumMoney"));
                String timeString=cursor.getString(cursor.getColumnIndexOrThrow("time"));
                DateMoney.add(Integer.parseInt(moneyString));
                date.add(timeString);
            }
            Toast.makeText(IncomeChart.this,"查询到记录",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(IncomeChart.this,"未查询到相关记录",Toast.LENGTH_SHORT).show();
        }
        Cursor cursor1 = db_read.query("tb_inaccont", new String[]{"sum(money) as SumMoney","type"}, null, null, "type", null, "type");
        if (cursor1 != null && cursor1.getCount() > 0){
            while(cursor1.moveToNext()){
                String moneyString=cursor1.getString(cursor1.getColumnIndexOrThrow("SumMoney"));
                String typeString=cursor1.getString(cursor1.getColumnIndexOrThrow("type"));
                TypeMoney.add(Integer.parseInt(moneyString));
                type.add(typeString);
            }
            Toast.makeText(IncomeChart.this,"查询到记录",Toast.LENGTH_SHORT).show();
        }
        db_read.close();
        db_write.close();


        //date.
//        getAxisXLables();//获取x轴的标注
//        getAxisPoints();//获取坐标点
//        initLineChart();//初始化

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
                subcolumnValueList.add(new SubcolumnValue((float) DateMoney.get(i),Color.rgb(colorValue-30,colorValue,colorValue+30) ));
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