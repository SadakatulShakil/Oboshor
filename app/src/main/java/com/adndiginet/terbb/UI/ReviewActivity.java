package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.adndiginet.terbb.Adapter.ReviewAdapter;
import com.adndiginet.terbb.Api.ApiInterface;
import com.adndiginet.terbb.Api.RetrofitClient;
import com.adndiginet.terbb.ApiModel.Review.Datum;
import com.adndiginet.terbb.ApiModel.Review.ReviewResponses;
import com.adndiginet.terbb.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String token;
    private ProgressBar progressBar;
    private TextView textMessage;
    private LinearLayout holding_errorLay, recycler_updates;
    private RecyclerView reviewListRevView;
    private ArrayList<Datum> reviewArrayList = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    public static final String TAG = "review";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        preferences = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        token =  preferences.getString("token",null);
        inItView();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Log.e(TAG, "onCreate: "+ token);

        progressBar.setVisibility(View.VISIBLE);
        getReview(token);

    }

    private void inItView() {
        progressBar = findViewById(R.id.progressBar);
        //textMessage = findViewById(R.id.text_message1);
        recycler_updates = findViewById(R.id.recycler_updates);
        holding_errorLay = findViewById(R.id.holding_errorLay);
        reviewListRevView = findViewById(R.id.reviewListRevView);
    }

    private void getReview(String token) {

        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<ReviewResponses> reviewResponseCall = api.postByReview("Bearer " + token);

        reviewResponseCall.enqueue(new Callback<ReviewResponses>() {
            @Override
            public void onResponse(Call<ReviewResponses> call, Response<ReviewResponses> response) {
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    ReviewResponses reviewResponse = response.body();
                    Log.e(TAG, "onResponse: "+ reviewResponse.toString());
                    if(reviewResponse.getSuccess() != null && reviewResponse.getSuccess() == true){

                        reviewArrayList.addAll(reviewResponse.getData());
                        if(reviewArrayList.size() == 0){
                            holding_errorLay.setVisibility(View.VISIBLE);
                            reviewListRevView.setVisibility(View.GONE);
                        }else {
                            holding_errorLay.setVisibility(View.GONE);
                            reviewListRevView.setVisibility(View.VISIBLE);
                        }
                    }
                }else if(response.code() == 401){
                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), "Please login Again !", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(response.code() == 500){
                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), "There is Server site Error !", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                reviewAdapter = new ReviewAdapter(reviewArrayList, ReviewActivity.this);
                reviewListRevView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
                reviewListRevView.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponses> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                holding_errorLay.setVisibility(View.VISIBLE);
                reviewListRevView.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: "+ t.getLocalizedMessage() );
            }
        });
    }
}