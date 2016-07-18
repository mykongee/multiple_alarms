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

public class MainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TIME_PATTERN = "HH:mm";
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView dateView;
    private TextView timeView;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;
    private String queryFormatDate;
    private String queryFormatTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        // Initialize these values as current date and time
        // To prevent error with empty query parameters
        queryFormatTime = dateFormat.format(calendar.getTime());
        queryFormatDate = simpleDateFormat.format(calendar.getTime());

        dateView = (TextView) findViewById(R.id.lblDate);
        timeView = (TextView) findViewById(R.id.lblTime);
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
            }
        });

        update();

    }

    private void sendAlarm(){
        Intent intent = new Intent();
        intent.setAction(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 5);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 0);
        startActivity(intent);
        Log.d(LOG_TAG, "First intent sent");

        Intent intent2 = new Intent();
        intent2.setAction(AlarmClock.ACTION_SHOW_ALARMS);
//        intent2.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        intent2.putExtra(AlarmClock.EXTRA_HOUR, 6);
//        intent2.putExtra(AlarmClock.EXTRA_MINUTES, 5);
        startActivity(intent2);
        Log.d(LOG_TAG, "After second intent's startActivity()");
    };

    private void sendAlarmTwo(){
        Intent intent = new Intent();
        intent.setAction(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 5);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 5);
        startActivity(intent);
    };

    private void update() {
        dateView.setText(dateFormat.format(calendar.getTime()));
        timeView.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnDatePicker:
                DatePickerDialog.newInstance(this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).
                        show(getFragmentManager(), "datePicker");
                break;
            case R.id.btnTimePicker:
                TimePickerDialog.newInstance(this,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true).show(getFragmentManager(), "timePicker");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        // Store String-formatted date for API query
        queryFormatDate = Integer.toString(year) + "-" + Integer.toString(monthOfYear + 1) +
                "-" + Integer.toString(dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        // Store String-formatted time for API query
        queryFormatTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
        update();
    }


}
