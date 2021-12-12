package com.adndiginet.terbb.UI;

import static com.adndiginet.terbb.UI.CurrentSituationActivity.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toolbar;

import android.os.Bundle;

import com.adndiginet.terbb.Adapter.AuthorityAdapter;
import com.adndiginet.terbb.Model.AuthorityModel;
import com.adndiginet.terbb.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.skydoves.powermenu.PowerMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private RecyclerView authorityListRevView;
    private AuthorityAdapter authorityAdapter;
    private String token;
    ArrayList<AuthorityModel> authorityModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        token =  preferences.getString("token",null);
        Log.d(TAG, "onCreate: "+ token);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        authorityListRevView = findViewById(R.id.authorityListRevView);
        myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);

        ExtendedFloatingActionButton btn = findViewById(R.id.open_form);
        btn.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(view.getContext(), FormActivity.class);
            view.getContext().startActivity(intent);});


        authorityModelArrayList.add(new AuthorityModel("ডা. দীপু মনি", R.drawable.dipu_moni, "মাননীয় মন্ত্রী (শিক্ষা মন্ত্রনালয়)", getResources().getString(R.string.qoutes1)));
        authorityModelArrayList.add(new AuthorityModel("মহিবুল হাসান চৌধুরী", R.drawable.third, "এম.পি (মাননীয় উপমন্ত্রী)", getResources().getString(R.string.qoutes2)));
        authorityModelArrayList.add(new AuthorityModel("মোঃ মাহবুব হোসেন", R.drawable.second, "মাননীয় সচিব (মাধ্যমিক ও উচ্চ শিক্ষা বিভাগ)", getResources().getString(R.string.qoutes3)));
        authorityModelArrayList.add(new AuthorityModel("মোঃ আমিনুল ইসলাম খান", R.drawable.forth, "মাননীয় সচিব (কারিগরি ও মাদ্রাসা শিক্ষা বিভাগ)", getResources().getString(R.string.qoutes4)));
        authorityModelArrayList.add(new AuthorityModel("প্রফেসর ড. সৈয়দ মোঃ গোলাম ফারুক", R.drawable.fifth, "মহাপরিচালক (মাধ্যমিক ও উচ্চ শিক্ষা বিভাগ)", getResources().getString(R.string.qoutes5)));
        authorityModelArrayList.add(new AuthorityModel("অধ্যক্ষ শরীফ আহমদ সাদী", R.drawable.secratary, "মাননীয় সচিব(বেসরকারি শিক্ষক-কর্মচারী অবসর সুবিধা)", getResources().getString(R.string.qoutes6)));

        authorityAdapter = new AuthorityAdapter(authorityModelArrayList,MainActivity.this );

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, RecyclerView.VERTICAL, false);
        authorityListRevView.setLayoutManager(gridLayoutManager);
        authorityListRevView.setAdapter(authorityAdapter);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        if(token == null){

            menu.getItem(1).setVisible(false);
        }
        return true;
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
}