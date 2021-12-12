package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.adndiginet.terbb.Model.AuthorityModel;
import com.adndiginet.terbb.R;

public class MemberDetailsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView name, designation, quotes;
    private AuthorityModel authorityModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        inItView();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        authorityModel = (AuthorityModel) intent.getSerializableExtra("info");

        image.setImageResource(authorityModel.getImages());
        name.setText(authorityModel.getName());
        designation.setText(authorityModel.getDesignation());
        quotes.setText(authorityModel.getQuotes());
    }

    private void inItView() {
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        designation = findViewById(R.id.designation);
        quotes = findViewById(R.id.quotes);

    }
}