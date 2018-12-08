package com.example.jure_lokovsek.personalbudget.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jure_lokovsek.personalbudget.Database.Budget;
import com.example.jure_lokovsek.personalbudget.R;
import com.example.jure_lokovsek.personalbudget.Room.BudgetR;

import org.greenrobot.greendao.annotation.Convert;

import java.util.List;

public class BudgetAdapter extends ArrayAdapter<BudgetR> implements View.OnClickListener {

    private List<BudgetR> mBudgetList;
    private Context mContext;

    public BudgetAdapter(List<BudgetR> data, Context context) {
        super(context, R.layout.budget_list_view_row, data);
        this.mBudgetList = mBudgetList;
        this.mContext = context;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        BudgetR budget = getItem(position);
       // Object object= getItem(position);
       // DataModel dataModel=(DataModel)object;
        Snackbar.make(view, "Komentar " +budget.getComment(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BudgetR budget = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.budget_list_view_row, parent, false);
            viewHolder.type = convertView.findViewById(R.id.textView_type);
            viewHolder.value = convertView.findViewById(R.id.textView_value);
            result = convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.value.setText(budget.getValue().toString()+" €");
        viewHolder.type.setText(budget.getBudgetType().toString());

        return convertView;
    }

    private static class ViewHolder{
        TextView value, type;
    }

}
