package com.anupamchugh.smartdateandtimepicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DatesRecyclerViewAdapter.ItemListener {

    View mLayoutBottomSheet;
    List<DateTimePickerModel> dataModelList;
    DatesRecyclerViewAdapter adapter;
    NumberPicker npHours, npMins, npMode;
    private BottomSheetBehavior mBottomSheetBehavior;
    RecyclerView recyclerView;
    DatesRecyclerViewAdapter.ItemListener itemListener;
    TextView finalDate, timeText;

    String[] hours = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] minutes = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    String[] mode = {"AM", "PM", "AM", "PM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemListener = this;

        mLayoutBottomSheet= findViewById(R.id.layout_bottom_sheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        Button cancel = (Button) mLayoutBottomSheet.findViewById(R.id.cancel);
        Button btnSet = (Button) findViewById(R.id.btnSet);
        finalDate = (TextView) mLayoutBottomSheet.findViewById(R.id.dateText);
        timeText = (TextView) mLayoutBottomSheet.findViewById(R.id.timeText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        dataModelList = new ArrayList<>();


        Calendar c = Calendar.getInstance();


        for (int i = 1; i < 31; i++) {
            c.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = c.getTime();

            long timestamp = tomorrow.getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);


            DateTimePickerModel dataModel = new DateTimePickerModel();

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    dataModel.day = "Sunday";
                    break;
                case Calendar.MONDAY:
                    dataModel.day = "Monday";
                    break;
                case Calendar.TUESDAY:
                    dataModel.day = "Tuesday";
                    break;
                case Calendar.WEDNESDAY:
                    dataModel.day = "Wednesday";
                    break;
                case Calendar.THURSDAY:
                    dataModel.day = "Thursday";
                    break;
                case Calendar.FRIDAY:
                    dataModel.day = "Friday";
                    break;
                case Calendar.SATURDAY:
                    dataModel.day = "Saturday";
                    break;

            }

            dataModel.date = String.format("%02d", (cal.get(Calendar.DATE)));
            dataModel.month = String.format("%02d", (cal.get(Calendar.MONTH)));
            dataModel.year = String.valueOf(cal.get(Calendar.YEAR));
            dataModel.highlghted = i == 1;

            if (dataModel.highlghted) {
                finalDate.setText(dataModel.date + "/" + dataModel.month + "/" + dataModel.year);
            }

            dataModelList.add(dataModel);


            //Log.d("API123", " " + cal.get(Calendar.DATE) + " - " + cal.get(Calendar.MONTH) + " - " + cal.get(Calendar.YEAR) + " - " + cal.get(Calendar.DAY_OF_WEEK));
        }


        recyclerView = (RecyclerView) mLayoutBottomSheet.findViewById(R.id.recycler_view);
        npHours = (NumberPicker) mLayoutBottomSheet.findViewById(R.id.npHours);
        npMins = (NumberPicker) mLayoutBottomSheet.findViewById(R.id.npMinutes);
        npMode = (NumberPicker) mLayoutBottomSheet.findViewById(R.id.npMode);


        npHours.setMinValue(0);
        npHours.setMaxValue(hours.length - 1);
        npHours.setDisplayedValues(hours);

        npMins.setMinValue(0);
        npMins.setMaxValue(minutes.length - 1);
        npMins.setDisplayedValues(minutes);


        npMode.setMinValue(0);
        npMode.setMaxValue(mode.length - 1);
        npMode.setDisplayedValues(mode);


        npMode.setWrapSelectorWheel(true);
        npHours.setWrapSelectorWheel(true);
        npMins.setWrapSelectorWheel(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new DatesRecyclerViewAdapter(this, dataModelList, itemListener);
        recyclerView.setAdapter(adapter);
        String current_hour = hours[npHours.getValue()];
        String current_mins = minutes[npMins.getValue()];
        String current_mode = mode[npMode.getValue()];


        getFormatted24hourTime(current_hour, current_mins, current_mode);


        npHours.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                String current_hour = hours[npHours.getValue()];
                String current_mins = minutes[npMins.getValue()];
                String current_mode = mode[npMode.getValue()];


                getFormatted24hourTime(current_hour, current_mins, current_mode);


            }
        });

        npMins.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                String current_hour = hours[npHours.getValue()];
                String current_mins = minutes[npMins.getValue()];
                String current_mode = mode[npMode.getValue()];


                getFormatted24hourTime(current_hour, current_mins, current_mode);


            }
        });

        npMode.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                String current_hour = hours[npHours.getValue()];
                String current_mins = minutes[npMins.getValue()];
                String current_mode = mode[npMode.getValue()];


                getFormatted24hourTime(current_hour, current_mins, current_mode);
            }
        });

    }


    @Override
    public void onItemClick(DateTimePickerModel dataModel) {


        for (int i = 0; i < dataModelList.size(); i++) {

            DateTimePickerModel current = dataModelList.get(i);
            dataModelList.get(i).highlghted = dataModel.day.equals(current.day) && dataModel.date.equals(current.date);


            if (dataModelList.get(i).highlghted) {
                finalDate.setText(current.date + "/" + current.month + "/" + current.year);
            }
        }


        adapter.notifyDataSetChanged();

    }

    public void getFormatted24hourTime(String current_hour, String current_mins, String current_mode) {
        try {


            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(current_hour + ":" + current_mins + " " + current_mode);


            String s = displayFormat.format(date);
            timeText.setText(s + " ");

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date timestamp = formatter.parse(finalDate.getText().toString().trim() + " " + timeText.getText().toString().trim());



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
