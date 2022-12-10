package com.course.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class Calendar extends AppCompatActivity {
    private  CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        calendarView=findViewById(R.id.cal);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                Intent intent = new Intent();

                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("day",day);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}