package com.example.boulocalix.newspaper;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, constant {

    FragmentManager ft;
    RecyclerFragment recyclerFragment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        recyclerView = findViewById(R.id.recyclerview) ;
//        downloaderSpecial = new AsyncDownloaderSpecial(getApplicationContext(), this,  QUERY_URL+HOME_PAGE );
//        downloaderSpecial.execute();
        ft = getSupportFragmentManager() ;
        recyclerFragment = recyclerFragment.newInstance(QUERY_URL + HOME_PAGE);
        ft.beginTransaction().replace(R.id.main_recycler_fragment, recyclerFragment).commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String url = null ;

        if (id == R.id.nav_home_page) {
            url = QUERY_URL + HOME_PAGE;
        } else if (id == R.id.nav_daily_news) {
            url = QUERY_URL + DAYLY_NEWS ;
        } else if (id == R.id.nav_soccer) {
            url = QUERY_URL + SOCCER ;
        } else if (id == R.id.nav_euro) {
            url = QUERY_URL + EURO_2016 ;
        } else if (id == R.id.nav_security) {
            url = QUERY_URL + SECURITY ;
        } else if (id == R.id.nav_fashion) {
            url = QUERY_URL + FASHION ;
        } else if (id == R.id.nav_high_tech) {
            url = QUERY_URL + HIGH_TECH ;
        } else if (id == R.id.nav_finance) {
            url = QUERY_URL + FINANCE ;
        } else if (id == R.id.nav_cooking) {
            url = QUERY_URL + COOKING ;
        } else if (id == R.id.nav_make_up) {
            url = QUERY_URL + MAKE_UP ;
        } else if (id == R.id.nav_movie) {
            url = QUERY_URL + MOVIE ;
        } else if (id == R.id.nav_education) {
            url = QUERY_URL + EDUCATION ;
        } else if (id == R.id.nav_youth) {
            url = QUERY_URL + YOUTH ;
        } else if (id == R.id.nav_music) {
            url = QUERY_URL + MUSIC ;
        } else if (id == R.id.nav_sport) {
            url = QUERY_URL + SPORT ;
        } else if (id == R.id.nav_incredible) {
            url = QUERY_URL + INCREDIBLE ;
        } else if (id == R.id.nav_technology) {
            url = QUERY_URL + TECHNOLOGY ;
        } else if (id == R.id.nav_automobile) {
            url = QUERY_URL + AUTOMOBILE ;
        } else if (id == R.id.nav_consumer) {
            url = QUERY_URL + CONSUMER ;
        } else if (id == R.id.nav_travel) {
            url = QUERY_URL + TRAVEL ;
        } else if (id == R.id.nav_health) {
            url = QUERY_URL + HEALTH ;
        } else if (id == R.id.nav_humour) {
            url = QUERY_URL + HUMOUR ;
        } else if (id == R.id.nav_world) {
            url = QUERY_URL + WORLD ;
        } else if (id == R.id.nav_people) {
            url = QUERY_URL + PEOPLE ;
        } else if (id == R.id.nav_entertainment) {
            url = QUERY_URL + ENTERTAINMENT ;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
//        if (id != R.id.nav_home_page ){
//        downloader = new AsyncDownloader(getApplicationContext(), this, url );
//        downloader.execute();
//        }
//        else {
//            downloaderSpecial = new AsyncDownloaderSpecial(getApplicationContext(), this, url) ;
//            downloaderSpecial.execute() ;
//        }
        ft = getSupportFragmentManager() ;
        recyclerFragment = recyclerFragment.newInstance(url);
        ft.beginTransaction().replace(R.id.main_recycler_fragment, recyclerFragment).commit();
        return true;
    }

}
