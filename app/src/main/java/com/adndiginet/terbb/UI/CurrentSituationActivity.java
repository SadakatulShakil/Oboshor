package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.adndiginet.terbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CurrentSituationActivity extends AppCompatActivity {
    public static final String TAG = "Current";
    private FloatingActionButton fab;
    private LinearLayout fabLayout1, fabLayout2;
    boolean isFABOpen = false;
    private String numberOne = "02223362182", numberTwo = "0255152097";
    private CardView reviewLayout, faqLayout, importantLinkLayout, aboutUsLayout, communicationLayout, noticeBoardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_situation);

        inItView();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
       /* myToolbar.setNavigationIcon(R.drawable.ic_back);*/

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+ "clicked" );
                finish();
            }
        });

      /*  myToolbar.inflateMenu(R.menu.main_menu);
        setActionBar(myToolbar);*/

        Intent intent = getIntent();
        String instituteType = intent.getStringExtra("institute_type");
        String doneBefore = intent.getStringExtra("done_before");
        String indexNumber = intent.getStringExtra("index_number");
        String otp = intent.getStringExtra("otp");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberOne)));
            }
        });

        fabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberTwo)));
            }
        });

        reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, ReviewActivity.class);
                startActivity(review);
            }
        });

        faqLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, FaqActivity.class);
                startActivity(review);
            }
        });

        importantLinkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, ImportantLinkActivity.class);
                startActivity(review);
            }
        });

        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, AboutUsActivity.class);
                startActivity(review);
            }
        });

        communicationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, CommunicationActivity.class);
                startActivity(review);
            }
        });

        noticeBoardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review = new Intent(CurrentSituationActivity.this, NoticeBoardActivity.class);
                startActivity(review);
            }
        });

    }

    private void closeFABMenu() {

        isFABOpen = false;
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));

    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void inItView() {
        fab = findViewById(R.id.fab);
        fabLayout1 = findViewById(R.id.fab1);
        fabLayout2 = findViewById(R.id.fab2);
        reviewLayout = findViewById(R.id.reviewLayout);
        faqLayout = findViewById(R.id.faqLayout);
        importantLinkLayout = findViewById(R.id.importantLinkLayout);
        aboutUsLayout = findViewById(R.id.aboutUsLayout);
        communicationLayout = findViewById(R.id.communicationLayout);
        noticeBoardLayout = findViewById(R.id.noticeBoardLayout);
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
}