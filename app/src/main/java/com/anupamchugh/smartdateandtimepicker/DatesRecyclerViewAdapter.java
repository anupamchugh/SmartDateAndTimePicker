package com.anupamchugh.smartdateandtimepicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anupamchugh on 30/05/17.
 */

public class DatesRecyclerViewAdapter extends RecyclerView.Adapter<DatesRecyclerViewAdapter.RecyclerViewHolder> {
    private List<DateTimePickerModel> arrayList;
    private Context context;
    ItemListener mListener;


    public DatesRecyclerViewAdapter(Context context, List<DateTimePickerModel> arrayList, ItemListener itemListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.mListener = itemListener;

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // View holder for gridview recycler view as we used in listview
        public TextView day;
        public TextView date;
        LinearLayout ll;
        public DateTimePickerModel model;


        public RecyclerViewHolder(View view) {
            super(view);
            // Find all views ids

            this.day = (TextView) view.findViewById(R.id.myDay);
            this.date = (TextView) view.findViewById(R.id.myDate);
            this.ll = (LinearLayout) view.findViewById(R.id.ll);
            view.setOnClickListener(this);

        }

        public void setData(DateTimePickerModel model, RecyclerViewHolder holder) {

            this.model = model;
            this.day = holder.day;
            this.date= holder.date;

            day.setText(model.day);
            date.setText(model.date);

            if (model.highlghted) {
                ll.setBackgroundColor(context.getResources().getColor(R.color.red));
                day.setTextColor(context.getResources().getColor(R.color.white));
                date.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                ll.setBackgroundColor(context.getResources().getColor(R.color.white));
                day.setTextColor(context.getResources().getColor(R.color.grey_500));
                date.setTextColor(context.getResources().getColor(R.color.black));
            }

        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(model);
            }

        }
    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.setData(arrayList.get(holder.getAdapterPosition()), holder);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.date_cards, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }

    public interface ItemListener {
        void onItemClick(DateTimePickerModel model);
    }

}
