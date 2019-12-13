package com.savvi.rangepickersample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class SampleActivity extends AppCompatActivity {

    CalendarPickerView calendar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, - 10);

        calendar = findViewById(R.id.calendar_view);
        button = findViewById(R.id.get_selected_dates);

        /*
        * Option 1)
        *     Make certain week days unselectable by using .withDeactivateDates(list)
        *     Add subtitles to certain dates by using .withSubTitles(getSubTitles())
        *     Highlight certain dates and make them unselectable by using .withHighlightedDates(arrayList)
        */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-4-2019";
            String strdate2 = "26-4-2019";
          
            Date newdate = dateformat.parse(strdate);
            Date newdate2 = dateformat.parse(strdate2);
            arrayList.add(newdate);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault()))
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withDeactivateDates(list)
                .withSubTitles(getSubTitles())
                .withHighlightedDates(arrayList);

        /*
        * Options 2)
        *     You may as well opt for having only a list of specific dates that are selectable on your calendar,
        *      and all the rest of the dates are deactivated. If that's your case, you may use this:
        *
        ArrayList<Date> arrayList2 = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "20-12-2019";
            String strdate2 = "25-12-2019";
            String strdate3 = "30-12-2019";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = dateformat.parse(strdate2);
            Date newdate3 = dateformat.parse(strdate3);
            arrayList2.add(newdate);
            arrayList2.add(newdate2);
            arrayList2.add(newdate3);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault()))
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withAllDatesDeactivatedExcept(arrayList2);
        */

        calendar.scrollToDate(new Date());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SampleActivity.this, "list " + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.get_calendar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActiveDatesRangePickerSampleActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<SubTitle> getSubTitles() {
        final ArrayList<SubTitle> subTitles = new ArrayList<>();
        final Calendar tmrw = Calendar.getInstance();
        tmrw.add(Calendar.DAY_OF_MONTH, 1);
        subTitles.add(new SubTitle(tmrw.getTime(), "₹1000"));
        return subTitles;
    }
}
