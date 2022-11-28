package com.course.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    private RecyclerView mRecyclerView;
    private ItemAdapter mRecyclerAdapter;
    private ArrayList<FoodItem> mFoodItems = new ArrayList<FoodItem>();

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

        createDate();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerAdapter = new ItemAdapter(mFoodItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        TextView tv = (TextView) findViewById(R.id.calorie);
        tv.setText(total+"cal");

    }
    int total=0;
    public void createDate(){
        for(int i = 1; i < 5; i++){
            FoodItem foodItem = new FoodItem(R.drawable.food,i*100);
            mFoodItems.add(foodItem);
            total+=i*100;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
        }
        TextView tx1 = (TextView) findViewById(R.id.month); /*TextView선언*/
        TextView tx2 = (TextView) findViewById(R.id.day);
        int month = data.getExtras().getInt("month"); /*int형*/
        int day = data.getExtras().getInt("day"); /*int형*/
        tx1.setText(String.valueOf(month)+'월');
        tx2.setText(String.valueOf(day)+'일');

    }
}
