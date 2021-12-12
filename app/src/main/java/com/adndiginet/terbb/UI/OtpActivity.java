package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

public class OtpActivity extends AppCompatActivity {

    protected String otp_input;
    private TextView countDownText, otpResendBtn;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 300000; //5 minutes in milliseconds
    private boolean timeRemaining;
    private String indexNumber, instituteType, doneBefore, givenOtp, baseUrl;
    private RequestQueue queue;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        countDownText = findViewById(R.id.countDownText);
        otpResendBtn = findViewById(R.id.otpResendBtn);
       /* myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);*/
        queue = Volley.newRequestQueue(this);
        baseUrl = getString(R.string.api_base_url);

        sharedPref = this.getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        prefEditor = sharedPref.edit();

        TextView mobile_no = (TextView) findViewById(R.id.mobile_no);
        mobile_no.setText(sharedPref.getString("mobile_no", getResources().getString(R.string.otp_phone_number)));

        Intent intent = getIntent();
        givenOtp = intent.getStringExtra("givenOtp");

        indexNumber = sharedPref.getString("index_no", null);

        startCountDown();

        TextView submit = (TextView) findViewById(R.id.submit_form);
        submit.setEnabled(false);
        submit.setBackground(getDrawable(R.drawable.btn_back3));
        submit.setTextColor(Color.BLACK);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CurrentSituationActivity.class);
                if (otp_input.equals(givenOtp)) {
                    intent.putExtra("institute_type", instituteType);
                    intent.putExtra("done_before", doneBefore);
                    intent.putExtra("index_number", indexNumber);
                    intent.putExtra("otp", otp_input);
                    view.getContext().startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.nav_toolbar), "Invalid OTP",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        EditText otpInput = (EditText) findViewById(R.id.otp_input);
        otpInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                otp_input = s.toString();
                if (s.length() > 3 && s.length() < 5 && otp_input.equals(givenOtp)) {
                    submit.setEnabled(true);
                    submit.setBackground(getDrawable(R.drawable.btn_back));
                    submit.setTextColor(Color.WHITE);
                } else {
                    submit.setEnabled(false);
                    submit.setBackground(getDrawable(R.drawable.btn_back3));
                    submit.setTextColor(Color.BLACK);
                }

            }
        });
    }

    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                otpResendBtn.setTextColor(Color.RED);
                otpResendBtn.setEnabled(true);

                otpResendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startCountDown();
                        resendOtp();
                        //Toast.makeText(getApplicationContext(), "Otp code need to run now", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }

    private void resendOtp() {
        givenOtp = OTP();

        JSONObject reqParams = new JSONObject();
        try {
            reqParams.put("index_no", indexNumber);
            reqParams.put("otp", givenOtp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.POST,
                baseUrl + "app_login",
                reqParams,
                response -> {
                    try {
                        prefEditor.putString("mobile_no", response.getJSONObject("data").getString("mobile_no"));
                        prefEditor.putString("index_no", response.getJSONObject("data").getString("index_no"));
                        prefEditor.putString("name", response.getJSONObject("data").getString("name"));
                        prefEditor.putString("token", response.getJSONObject("data").getString("token"));
                        prefEditor.apply();
                    } catch (Exception e) {
                        Snackbar.make(findViewById(R.id.nav_toolbar), e.toString(),
                                Snackbar.LENGTH_SHORT)
                                .show();

                    }
                },
                error -> Snackbar.make(findViewById(R.id.nav_toolbar), error.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show()
        );

        queue.add(loginRequest);

    }

    private String OTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMilliseconds / 60000);
        int seconds = (int) (timeLeftInMilliseconds % 60000 / 1000);

        String timeLeftText = "" + minutes;
        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
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
        Intent intent = new Intent(OtpActivity.this, IndexSearchActivity.class);
        startActivity(intent);
    }
}