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
    private Calendar startCalendar;
    private Calendar endCalendar;
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;
    private String queryFormatDate;

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
//                sendAlarm();
                Log.v("Calendar date: ", startCalendar.getTime().toString());
                Log.v("Calendar time: ", Long.toString(startCalendar.getTimeInMillis()));
            }
        });

        update();

    }

    private void sendAlarm(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 5);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 0);
        startActivity(intent);
        Log.d(LOG_TAG, "First intent sent");

        // Intent intent2 = new Intent();
        // intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // intent2.setAction(AlarmClock.ACTION_SET_ALARM);
        // intent2.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        // intent2.putExtra(AlarmClock.EXTRA_HOUR, 6);
        // intent2.putExtra(AlarmClock.EXTRA_MINUTES, 5);
        // startActivity(intent2);
        // Log.d(LOG_TAG, "After second intent's startActivity()");
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

    private void update() {
        dateView.setText(dateFormat.format(startCalendar.getTime()));
        timeView.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    public void onClick(View v){
        switch (v.getId()) {
            // Bring up Date Picker and Time Picker Dialog to set time and date
            case R.id.btnDatePickerStart:
                TimePickerDialog.newInstance(this,
                        startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE),
                        true).show(getFragmentManager(), "timePicker");
                DatePickerDialog.newInstance(this,
                        startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).
                        show(getFragmentManager(), "datePicker");
                break;
            case R.id.btnDatePickerEnd:
                TimePickerDialog.newInstance(this,
                        endCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.MINUTE),
                        true).show(getFragmentManager(), "timePicker");
                DatePickerDialog.newInstance(this,
                        endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).
                        show(getFragmentManager(), "datePicker");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        startCalendar.set(year, monthOfYear, dayOfMonth);
        // TODO: Need to find a way to set both calendars without overriding the other
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startCalendar.set(Calendar.MINUTE, minute);
        update();
    }


}
