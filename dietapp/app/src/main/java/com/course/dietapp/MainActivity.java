package com.course.dietapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    int total=0;
    public static final int REQUEST_CODE = 100;
    RecyclerView myRecyclerView;

    List<FoodItem> mFoodItems = new ArrayList<>();
    RoomDB database;
    ItemAdapter adapter;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = sdf.format(date);
    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton bt1 = (ImageButton) findViewById(R.id.calendar);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });


        tx1 = (TextView) findViewById(R.id.date);
        tx1.setText(getTime);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        database = RoomDB.getInstance(this);
        mFoodItems = database.mainDao().getAll();
        Log.d("ddd",tx1.getText().toString());
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new ItemAdapter(MainActivity.this, mFoodItems);
        myRecyclerView.setAdapter(adapter);

        TextView tv = (TextView) findViewById(R.id.calorie);
        tv.setText(total+"kcal");
    }

//    public void getInput() {
//        String[] columns = new String[]{"_id","image","name","date", "kcal", "count", "time", "review"};
//        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, columns,null,
//                null, null, null);
//        if (c != null) {
//            while (c.moveToNext()) {
//                if (c.getString(3)!=tx1.getText())
//                    continue;
//                int _id = c.getInt(0);
//                String image = c.getString(1);
//                String name = c.getString(2);
//                String date = c.getString(3);
//                String kcal = c.getString(4);
//                String count = c.getString(5);
//                String time = c.getString(6);
//                String review = c.getString(7);
//                //FoodItem foodItem = new FoodItem(_id,image,name,date,kcal,count,time,review);
//                //mFoodItems.add(foodItem);
//                total+=Integer.parseInt(kcal);
//            }// 수량 *칼로리 계산하는 방법 찾기
//            c.close();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
        }
        tx1 = (TextView) findViewById(R.id.date); /*TextView선언*/
        int year=data.getExtras().getInt("year");
        int month1 = data.getExtras().getInt("month"); /*int형*/
        String month2 = String.format("%02d", month1);
        int day1 = data.getExtras().getInt("day"); /*int형*/
        String day2 = String.format("%02d", day1);

        tx1.setText(String.valueOf(year)+'-'+month2+'-'+day2);


    }

}