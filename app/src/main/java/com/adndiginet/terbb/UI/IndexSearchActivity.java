package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class IndexSearchActivity extends AppCompatActivity {

    protected String index_input, index_no, index_prefix;
    private ProgressBar progressBar;
    public static final String TAG = "logIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        /*myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);*/

        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.app_name) ,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();

        String instituteType = sharedPref.getString("institute_type", "");
        String doneBefore = sharedPref.getString("done_before", "");

        RequestQueue queue = Volley.newRequestQueue(this);
        String baseUrl = getString(R.string.api_base_url);

        TextView submit = (TextView) findViewById(R.id.submit_form);
        submit.setEnabled(false);
        submit.setBackground(getDrawable(R.drawable.btn_back3));
        submit.setTextColor(Color.BLACK);
        submit.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(view.getContext(), OtpActivity.class);

            String otp = OTP();

            JSONObject reqParams = new JSONObject();
            try {
                reqParams.put("index_no", index_no);
                reqParams.put("otp", otp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.POST,
            baseUrl+"app_login",
                reqParams,
                    response -> {
                        try{
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "onToken "+response.getJSONObject("data").getString("token"));
                            sharedPref.edit().putString("token", response.getJSONObject("data").getString("token")).apply();
                            prefEditor.putString("mobile_no", response.getJSONObject("data").getString("mobile_no"));
                            prefEditor.putString("index_no", response.getJSONObject("data").getString("index_no"));
                            prefEditor.putString("name", response.getJSONObject("data").getString("name"));
                            prefEditor.putString("token", response.getJSONObject("data").getString("token"));
                            prefEditor.apply();
                            finish();
                        }catch (Exception e){
                            Snackbar.make(findViewById(R.id.nav_toolbar), e.toString(),
                                    Snackbar.LENGTH_SHORT)
                                    .show();

                        }
                        intent.putExtra("institute_type", instituteType);
                        intent.putExtra("done_before", doneBefore);
                        intent.putExtra("index_number", index_input);
                        intent.putExtra("givenOtp", otp);
                        view.getContext().startActivity(intent);

                    },
                    error -> Snackbar.make(findViewById(R.id.nav_toolbar), error.toString(),
                            Snackbar.LENGTH_SHORT)
                            .show()
            );

            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(loginRequest);

        });

        EditText indexNumberInput = (EditText) findViewById(R.id.index_number_input);
        indexNumberInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                index_input = s.toString();
                if(s.length() > 7 && s.length() < 9){
                    String letterOne = Character.toString(index_input.charAt(0)), letterTwo = Character.toString(index_input.charAt(1));

                    if(Pattern.matches("[a-zA-Z]", letterOne) && letterTwo.equals("-")){

                        index_prefix = index_input.split("-")[0];
                        index_no = index_input.split("-")[1];
                        submit.setEnabled(true);
                        submit.setBackground(getDrawable(R.drawable.btn_back));
                        submit.setTextColor(Color.WHITE);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Please check your index Again !", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    submit.setEnabled(false);
                }

            }
        });
        
    }

    protected String OTP()
    {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
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
        Intent intent = new Intent(IndexSearchActivity.this, FormActivity.class);
        startActivity(intent);
    }
}