package com.anupamchugh.smartdateandtimepicker;

/**
 * Created by anupamchugh on 31/05/17.
 */

public class DateTimePickerModel {

    public String day, date, month, year;
    public boolean highlghted;

    DateTimePickerModel(String day, String date, String month, String year, boolean highlghted) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
        this.highlghted = highlghted;
    }

    DateTimePickerModel()
    {

    }
}
