package com.adndiginet.terbb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adndiginet.terbb.ApiModel.Review.Datum;
import com.adndiginet.terbb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.viewHolder> {
    //make constructor with context arraylist of data//
    private ArrayList<Datum> reviewList;
    private Context context;

    public ReviewAdapter(ArrayList<Datum> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        return new ReviewAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.viewHolder holder, int position) {

        Datum reviewInfo = reviewList.get(position);
        holder.text_update1.setText(reviewInfo.getMessage());

        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        final String NEW_FORMAT = "dd MMM, yyyy";

// August 12, 2010
        String oldDateString = reviewInfo.getDate();
        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = new Date();
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);

        holder.text_date1.setText(newDateString);

    }

    @Override
    public int getItemCount() {
        return reviewList.size(); // do return type arraylist.size()//
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView text_date1, text_update1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            text_date1 = itemView.findViewById(R.id.text_date1);
            text_update1 = itemView.findViewById(R.id.text_update1);
        }
    }
}
