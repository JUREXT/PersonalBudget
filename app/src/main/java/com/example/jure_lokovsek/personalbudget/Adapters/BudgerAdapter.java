package com.example.jure_lokovsek.personalbudget.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.jure_lokovsek.personalbudget.Database.Budget;

public class BudgerAdapter extends ArrayAdapter<Budget> {

    public BudgerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
