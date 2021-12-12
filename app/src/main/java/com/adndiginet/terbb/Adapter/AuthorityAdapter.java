package com.adndiginet.terbb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adndiginet.terbb.Model.AuthorityModel;
import com.adndiginet.terbb.R;
import com.adndiginet.terbb.UI.MemberDetailsActivity;

import java.util.ArrayList;

public class AuthorityAdapter extends RecyclerView.Adapter<AuthorityAdapter.viewHolder> {
    private ArrayList<AuthorityModel> authorityModels;
    private Context context;

    public AuthorityAdapter(ArrayList<AuthorityModel> authorityModels, Context context) {
        this.authorityModels = authorityModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorityAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authority_layout, parent, false);
        return new AuthorityAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorityAdapter.viewHolder holder, int position) {
        AuthorityModel authorityInfo = authorityModels.get(position);

        holder.image.setImageResource(authorityInfo.getImages());
        holder.name.setText(authorityInfo.getName());
        holder.designation.setText(authorityInfo.getDesignation());
        holder.quotes.setText(authorityInfo.getQuotes());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(context, MemberDetailsActivity.class);
                details.putExtra("info", authorityInfo);
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return authorityModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, designation, quotes;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            designation = itemView.findViewById(R.id.designation);
            quotes = itemView.findViewById(R.id.quotes);
        }
    }
}
