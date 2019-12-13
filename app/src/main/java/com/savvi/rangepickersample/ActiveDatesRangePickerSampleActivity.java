package com.savvi.rangepickersample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * Create a calendar with a list of specific dates that can be selected by the user, and all the rest of the dates are deactivated.
 * Define the selectable dates using .withAllDatesDeactivatedExcept(list)
 * When a range is selected, onRangeSelected is called.
 * If a range is already selected and the user selects one more date, the previously selected range is deselected and the user can select it again.
 */
public class ActiveDatesRangePickerSampleActivity extends AppCompatActivity {

    CalendarPickerView calendar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);

        calendar = findViewById(R.id.calendar_view);
        button = findViewById(R.id.get_selected_dates);

        final ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

        final ArrayList<Date> possibleBeginningDates = getPossibleBeginningDates();
        final ArrayList<Date> possibleEndingDates = getPossibleEndingDates();

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault()))
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withAllDatesDeactivatedExcept(possibleBeginningDates)
                .withDeactivateDates(list);

        // When the beginning date of the range has been selected, let the user choose one of the possible ending dates.
        // When a range is selected call onRangeSelected(list).
        // If user selects another date once a range is already selected, that range will be deselected so that user can select it again.
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // If only beginning date has been selected, activate possibleEndingDates
                if (calendar.getSelectedDates().size() == 1)
                    calendar.replaceActivatedDates(possibleEndingDates);
                else // If a range has been selected
                    if (calendar.getSelectedDates().size() > 1) {
                        // Activate possibleBeginningDates again
                        calendar.replaceActivatedDates(possibleBeginningDates);
                        onRangeSelected(calendar.getSelectedDates());
                    }
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });

        // When user tries to select a third date, deselect the previous range and let the user choose one of the possible beginning dates again.
        calendar.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                // If a range has been selected and a third date was now clicked
                if (calendar.getSelectedDates().size() > 1) {
                    // Deselect all dates
                    calendar.clearSelectedDates();
                    // Activate possibleBeginningDates again
                    calendar.replaceActivatedDates(possibleBeginningDates);

                    if (possibleBeginningDates.contains(date))
                        calendar.selectDate(date);
                }
            }
        });

        calendar.scrollToDate(new Date());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActiveDatesRangePickerSampleActivity.this, "list " + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.get_calendar2).setVisibility(View.GONE);
    }

    void onRangeSelected(List<Date> selectedDates) {
        Toast.makeText(ActiveDatesRangePickerSampleActivity.this, "A range has been selected. " + selectedDates.toString(), Toast.LENGTH_LONG).show();
    }

    private ArrayList<Date> getPossibleEndingDates() {
        ArrayList<Date> possibleEndingDates = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "19-12-2019";
            String strdate1 = "21-12-2019";
            String strdate2 = "26-12-2019";
            String strdate3 = "31-12-2019";

            Date newdate = dateformat.parse(strdate);
            Date newdate1 = dateformat.parse(strdate1);
            Date newdate2 = dateformat.parse(strdate2);
            Date newdate3 = dateformat.parse(strdate3);
            possibleEndingDates.add(newdate);
            possibleEndingDates.add(newdate1);
            possibleEndingDates.add(newdate2);
            possibleEndingDates.add(newdate3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return possibleEndingDates;
    }

    private ArrayList<Date> getPossibleBeginningDates() {
        ArrayList<Date> possibleBeginningDates = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "15-12-2019";
            String strdate1 = "20-12-2019";
            String strdate2 = "25-12-2019";
            String strdate3 = "29-12-2019";

            Date newdate = dateformat.parse(strdate);
            Date newdate1 = dateformat.parse(strdate1);
            Date newdate2 = dateformat.parse(strdate2);
            Date newdate3 = dateformat.parse(strdate3);
            possibleBeginningDates.add(newdate);
            possibleBeginningDates.add(newdate1);
            possibleBeginningDates.add(newdate2);
            possibleBeginningDates.add(newdate3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return possibleBeginningDates;
    }
}
