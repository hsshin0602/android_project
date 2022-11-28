package com.course.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView food;
    TextView calorie;
    int id;
    int cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        food = findViewById(R.id.food);
        calorie = findViewById(R.id.calorie);

        //메인엑티비티에서 받아온 데이터
        Intent intent = getIntent();
        id = intent.getExtras().getInt("resourceId");
        cal = intent.getExtras().getInt("name");

        food.setImageResource(id);
        calorie.setText(cal+"cal");

    }
}