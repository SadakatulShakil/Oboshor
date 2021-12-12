package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;

public class FormActivity extends AppCompatActivity {

    protected String type, doneBefore;
    private TextView school, college, karigori, madrasha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       /* myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);*/

        SharedPreferences sharedPref = this.getSharedPreferences(getResources().getString(R.string.app_name) , Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();

        TextView submit = (TextView) findViewById(R.id.submit_form);
        submit.setEnabled(false);
        submit.setBackground(getDrawable(R.drawable.btn_back3));
        submit.setTextColor(Color.BLACK);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = doneBefore.equals(getString(R.string.yes)) ? new Intent(view.getContext(), IndexSearchActivity.class): new Intent(view.getContext(), WebviewActivity.class);
                prefEditor.putString("institute_type", type);
                prefEditor.putString("done_before", doneBefore);
                prefEditor.apply();
                view.getContext().startActivity(intent);

            }
        });

        school = findViewById(R.id.school);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = school.getText().toString();
                school.setPressed(true);
                enableSubmit(submit);
                school.setBackground(getDrawable(R.drawable.btn_back2));
                school.setTextColor(Color.WHITE);
                college.setBackground(getDrawable(R.drawable.btn_back));
                karigori.setBackground(getDrawable(R.drawable.btn_back));
                madrasha.setBackground(getDrawable(R.drawable.btn_back));
            }
        });

        college = findViewById(R.id.college);
        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = college.getText().toString();
                enableSubmit(submit);
                college.setBackground(getDrawable(R.drawable.btn_back2));
                college.setTextColor(Color.WHITE);
                school.setBackground(getDrawable(R.drawable.btn_back));
                karigori.setBackground(getDrawable(R.drawable.btn_back));
                madrasha.setBackground(getDrawable(R.drawable.btn_back));
            }
        });

        karigori = findViewById(R.id.karigori);
        karigori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = karigori.getText().toString();
                enableSubmit(submit);
                karigori.setBackground(getDrawable(R.drawable.btn_back2));
                karigori.setTextColor(Color.WHITE);
                school.setBackground(getDrawable(R.drawable.btn_back));
                college.setBackground(getDrawable(R.drawable.btn_back));
                madrasha.setBackground(getDrawable(R.drawable.btn_back));
            }
        });

        madrasha = findViewById(R.id.madrasha);
        madrasha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = madrasha.getText().toString();
                enableSubmit(submit);
                madrasha.setBackground(getDrawable(R.drawable.btn_back2));
                madrasha.setTextColor(Color.WHITE);
                school.setBackground(getDrawable(R.drawable.btn_back));
                college.setBackground(getDrawable(R.drawable.btn_back));
                karigori.setBackground(getDrawable(R.drawable.btn_back));
            }
        });

        RadioGroup done_before = (RadioGroup) findViewById(R.id.done_before);
        done_before.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    doneBefore = checkedRadioButton.getText().toString();
                    enableSubmit(submit);
                }
            }
        });
    }

    protected void enableSubmit(TextView submit){
        if(type != null && doneBefore != null){
            submit.setEnabled(true);
            submit.setBackground(getDrawable(R.drawable.btn_back));
            submit.setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                // User chose the "Settings" item, show the app settings UI...
                Intent home = new Intent(getApplicationContext(), CurrentSituationActivity.class);
                startActivity(home);
                return true;
            case R.id.menu_notice:
                // User chose the "Settings" item, show the app settings UI...
                Intent notice = new Intent(getApplicationContext(), NoticeBoardActivity.class);
                startActivity(notice);
                return true;

            case R.id.menu_links:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent links = new Intent(getApplicationContext(), ImportantLinkActivity.class);
                startActivity(links);
                return true;
            case R.id.menu_about:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent about = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(about);
                return true;
            case R.id.menu_contact:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent contact = new Intent(getApplicationContext(), CommunicationActivity.class);
                startActivity(contact);
                return true;
            case R.id.menu_faq:
                Intent intent = new Intent(getApplicationContext(), FaqActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FormActivity.this, MainActivity.class);
        startActivity(intent);
    }
}