package com.course.dietapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    TextView tx1,tv;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tx1 = (TextView) findViewById(R.id.date);
            tx1.setText(sdf.format(myCalendar.getTime()));

            total=0;
            mFoodItems.clear();
            mFoodItems.addAll(database.mainDao().getAllByDate(tx1.getText().toString()));
            for(int i=0;i<mFoodItems.size();i++){
                total+=Integer.parseInt(mFoodItems.get(i).getKcal());
            }
            tv = (TextView) findViewById(R.id.calorie);
            tv.setText(total+"kcal");

            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton bt1 = (ImageButton) findViewById(R.id.calendar);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("date",((TextView) findViewById(R.id.date)).getText().toString());
                startActivityForResult(intent, 101);
            }
        });


        tx1 = (TextView) findViewById(R.id.date);
        tx1.setText(getTime);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        database = RoomDB.getInstance(this);
        mFoodItems = database.mainDao().getAllByDate(tx1.getText().toString());
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new ItemAdapter(MainActivity.this, mFoodItems);
        myRecyclerView.setAdapter(adapter);

        total=0;
        mFoodItems.clear();
        mFoodItems.addAll(database.mainDao().getAllByDate(tx1.getText().toString()));
        for(int i=0;i<mFoodItems.size();i++){
            total+=Integer.parseInt(mFoodItems.get(i).getKcal());
        }
        tv = (TextView) findViewById(R.id.calorie);
        tv.setText(total+"kcal");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 101) {
            if (intent != null) {
                mFoodItems.clear();
                mFoodItems.addAll(database.mainDao().getAllByDate(tx1.getText().toString()));
                adapter.notifyDataSetChanged();
                total=0;
                mFoodItems.clear();
                mFoodItems.addAll(database.mainDao().getAllByDate(tx1.getText().toString()));
                for(int i=0;i<mFoodItems.size();i++){
                    total+=Integer.parseInt(mFoodItems.get(i).getKcal());
                }
                tv = (TextView) findViewById(R.id.calorie);
                tv.setText(total+"kcal");
            }
        }
    }



}