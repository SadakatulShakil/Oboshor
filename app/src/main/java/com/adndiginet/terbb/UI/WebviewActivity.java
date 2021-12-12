package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);*/

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl(getString(R.string.registration_url));
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
        Intent intent = new Intent(WebviewActivity.this, FormActivity.class);
        startActivity(intent);
    }
}