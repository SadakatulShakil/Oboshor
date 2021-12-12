package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;

public class AboutUsActivity extends AppCompatActivity {
    private TextView linkOne, linkTwo, linkThree, linkForth, linkFifth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        inItView();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linkOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://terbb.gov.bd/nolink/-"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        linkTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://terbb.portal.gov.bd/site/page/6e5d3a97-4d1d-4ddd-925b-7025d4159794/-"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        linkThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://terbb.gov.bd/site/view/officer_list/-"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        linkForth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://terbb.gov.bd/site/page/da94285e-4546-4099-9118-96d38c9ff11e/-"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        linkFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://terbb.gov.bd/nolink/-"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void inItView() {
        linkOne = findViewById(R.id.linkOne);
        linkTwo = findViewById(R.id.linkTwo);
        linkThree = findViewById(R.id.linkThree);
        linkForth = findViewById(R.id.linkForth);
        linkFifth = findViewById(R.id.linkFifth);

    }
}