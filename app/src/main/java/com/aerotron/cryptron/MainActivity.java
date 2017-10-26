package com.aerotron.cryptron;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.aerotron.cryptron.R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(com.aerotron.cryptron.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
            new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(navigationButtonViewTarget)
                    .setStyle(com.aerotron.cryptron.R.style.NavBar)
                    .setContentText("Navigate through App")
                    .hideOnTouchOutside()
                    .build()
                    .show();
        } catch (ViewTargets.MissingViewException e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.aerotron.cryptron.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.aerotron.cryptron.R.string.navigation_drawer_open, com.aerotron.cryptron.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.aerotron.cryptron.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*arr = (ImageView) findViewById(R.id.);

        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(5);
        animation.setRepeatMode(Animation.REVERSE);
        arr.startAnimation(animation);*/

        //Adds
        AdBuddiz.setPublisherKey("e490d2bc-6b7b-439d-a89c-c98690d046ef");
        AdBuddiz.cacheAds(this); // this = current Activity
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.aerotron.cryptron.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.aerotron.cryptron.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        FragmentManager fragmentManager = getFragmentManager();
        if (id == com.aerotron.cryptron.R.id.action_settings) {
            return true;
        } else if (id == com.aerotron.cryptron.R.id.action_help) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new HelpFragment())
                    .commit();
        } else if (id == com.aerotron.cryptron.R.id.action_aboutus) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new AboutUsFragment())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == com.aerotron.cryptron.R.id.nav_first_layout) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new EncryptFragment())
                    .commit();
        } else if (id == com.aerotron.cryptron.R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new DecryptFragment())
                    .commit();
        } else if (id == com.aerotron.cryptron.R.id.nav_share) {
/*            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ShareFragment())
                    .commit();*/
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, (Uri.parse(("market://details?id="+getPackageName()))).toString());
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Cryptron Application");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (id == com.aerotron.cryptron.R.id.nav_send) {
            goToMyApp(true);
        } else if (id == com.aerotron.cryptron.R.id.nav_help) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new HelpFragment())
                    .commit();
        } else if(id == com.aerotron.cryptron.R.id.nav_aboutus) {
            fragmentManager.beginTransaction()
                    .replace(com.aerotron.cryptron.R.id.content_frame
                            , new AboutUsFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.aerotron.cryptron.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToMyApp(boolean googlePlay) {//true if Google Play, false if Amazone Store
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "market://details?id=" : "amzn://apps/android?p=") +getPackageName())));
        } catch (ActivityNotFoundException e1) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "http://play.google.com/store/apps/details?id=" : "http://www.amazon.com/gp/mas/dl/android?p=") +getPackageName())));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
