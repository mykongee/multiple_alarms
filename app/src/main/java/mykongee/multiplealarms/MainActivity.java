package mykongee.multiplealarms;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
//        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
//        View.OnClickListener{

    private static final String TIME_PATTERN = "HH:mm";
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView startDateView;
    private TextView startTimeView;
    private TextView endDateView;
    private TextView endTimeView;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        // Initialize these values as current date and time
        // To prevent error with empty query parameters

        startDateView = (TextView) findViewById(R.id.lblStartDate);
        startTimeView = (TextView) findViewById(R.id.lblStartTime);
        endDateView = (TextView) findViewById(R.id.lblEndDate);
        endTimeView = (TextView) findViewById(R.id.lblEndTime);

        Button alarmButton = (Button) findViewById(R.id.btnAlarm);

        // Set an alarm with the main alarm app.
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(AlarmClock.ACTION_SET_ALARM);
//                intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//                intent.putExtra(AlarmClock.EXTRA_HOUR, 23);
//                intent.putExtra(AlarmClock.EXTRA_MINUTES, 53);
//                startActivity(intent);
                sendAlarm();
                Log.v("Calendar date: ", startCalendar.getTime().toString());
                Log.v("Calendar time: ", Long.toString(startCalendar.getTimeInMillis()));
            }
        });

        updateStartCalendarView();
        updateEndCalendarView();

    }

    private void sendAlarm(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 5);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 0);
        startActivity(intent);
        Log.d(LOG_TAG, "First intent sent");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent2 = new Intent();
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setAction(AlarmClock.ACTION_SET_ALARM);
        intent2.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent2.putExtra(AlarmClock.EXTRA_HOUR, 6);
        intent2.putExtra(AlarmClock.EXTRA_MINUTES, 5);
        startActivity(intent2);
        Log.d(LOG_TAG, "After second intent's startActivity()");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent3 = new Intent();
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent3.setAction(AlarmClock.ACTION_SET_ALARM);
        intent3.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent3.putExtra(AlarmClock.EXTRA_HOUR, 6);
        intent3.putExtra(AlarmClock.EXTRA_MINUTES, 15);
        startActivity(intent3);
        Log.d(LOG_TAG, "After third intent's startActivity()");

//        Intent intent4 = new Intent();
//        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent4.setAction(AlarmClock.ACTION_SET_ALARM);
//        intent4.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        intent4.putExtra(AlarmClock.EXTRA_HOUR, 7);
//        intent4.putExtra(AlarmClock.EXTRA_MINUTES, 1);
//        startActivity(intent4);
//        Log.d(LOG_TAG, "After fourth intent's startActivity()");
    }

    private void setAlarms(int numberofAlarms, int startDate, int endDate, int startTime,
                           int endTime){
        // set [numberOfAlarms] alarms based on start/end date and start/end time
        for (int i = 0; i < numberofAlarms; i++){
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // more logic
        }
    }

    private void updateStartCalendarView() {
        startDateView.setText(dateFormat.format(startCalendar.getTime()));
        startTimeView.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    private void updateEndCalendarView(){
        endDateView.setText(dateFormat.format(endCalendar.getTime()));
        endTimeView.setText(simpleDateFormat.format(endCalendar.getTime()));
    }

    public void onClick(View v) {

        mYear = startCalendar.get(Calendar.YEAR);
        mMonth = startCalendar.get(Calendar.MONTH);
        mDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = startCalendar.get(Calendar.HOUR);
        mMinute = startCalendar.get(Calendar.MINUTE);

        switch (v.getId()) {
            // Bring up Date Picker and Time Picker Dialog to set time and date
            case R.id.btnDatePickerStart:

                TimePickerDialog timePickerDialogStart = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startCalendar.set(Calendar.MINUTE, minute);
                                updateStartCalendarView();
                            }
                        }, mHour, mMinute, false);

                timePickerDialogStart.show(getFragmentManager(), "timePicker");

                DatePickerDialog datePickerDialogStart = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                                startCalendar.set(year, monthOfYear, dayOfMonth);
                                updateStartCalendarView();
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialogStart.show(getFragmentManager(), "datePicker");


                break;
            case R.id.btnDatePickerEnd:

                TimePickerDialog timePickerDialogEnd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                endCalendar.set(Calendar.MINUTE, minute);
                                updateEndCalendarView();
                            }
                        }, mHour, mMinute, true);

                timePickerDialogEnd.show(getFragmentManager(), "timePicker");

                DatePickerDialog datePickerDialogEnd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                                endCalendar.set(year, monthOfYear, dayOfMonth);
                                updateEndCalendarView();
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialogEnd.show(getFragmentManager(), "datePicker");

                break;
        }
    }

    // Use only if MainActivity is implementing Listeners
//    @Override
//    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
//        startCalendar.set(year, monthOfYear, dayOfMonth);
//        // TODO: Need to find a way to set both calendars without overriding the other
//        updateStartCalendarView();
//    }
//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        startCalendar.set(Calendar.MINUTE, minute);
//        updateStartCalendarView();
//    }

}
