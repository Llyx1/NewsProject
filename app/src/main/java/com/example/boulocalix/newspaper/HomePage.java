package com.example.boulocalix.newspaper;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SpinnerAdapter;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,constant {

    FragmentManager ft;
    RecyclerFragment recyclerFragment ;
    Toolbar toolbar ;

    public interface homeToFragmentCallBack{
        void onHomeToFragment(String textResearchTool) ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentCreation(QUERY_URL+HOME_PAGE+RSS);
    }

    public void fragmentCreation(String url) {
        ft = getSupportFragmentManager() ;
        recyclerFragment = recyclerFragment.newInstance(url);
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
        toolbar.setTitle(HOME_PAGE_T);
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
        switch (id ) {
            case R.id.nav_home_page :
                url = QUERY_URL + HOME_PAGE + RSS;
                toolbar.setTitle(HOME_PAGE_T);
                break;
            case R.id.nav_daily_news:
                url = QUERY_URL + DAYLY_NEWS + RSS;
                toolbar.setTitle(DAYLY_NEWS_T);
                break;
            case R.id.nav_soccer :
                url = QUERY_URL + SOCCER + RSS;
                toolbar.setTitle(SOCCER_T);
                break;
            case R.id.nav_euro :
                url = QUERY_URL + EURO_2016 + RSS;
                toolbar.setTitle(EURO_2016_T);
                break;
            case R.id.nav_security :
                url = QUERY_URL + SECURITY + RSS;
                toolbar.setTitle(SECURITY_T);
                break;
            case R.id.nav_fashion :
                url = QUERY_URL + FASHION + RSS;
                toolbar.setTitle(FASHION_T);
                break;
            case R.id.nav_high_tech :
                url = QUERY_URL + HIGH_TECH + RSS;
                toolbar.setTitle(HIGH_TECH_T);
                break;
            case R.id.nav_finance :
                url = QUERY_URL + FINANCE + RSS;
                toolbar.setTitle(FINANCE_T);
                break;
            case R.id.nav_cooking :
                url = QUERY_URL + COOKING + RSS;
                toolbar.setTitle(COOKING_T);
                break;
            case R.id.nav_make_up :
                url = QUERY_URL + MAKE_UP + RSS;
                toolbar.setTitle(MAKE_UP_T);
                break;
            case R.id.nav_movie :
                url = QUERY_URL + MOVIE + RSS;
                toolbar.setTitle(MOVIE_T);
                break;
            case R.id.nav_education :
                url = QUERY_URL + EDUCATION + RSS;
                toolbar.setTitle(EDUCATION_T);
                break;
            case R.id.nav_youth :
                url = QUERY_URL + YOUTH + RSS;
                toolbar.setTitle(YOUTH_T);
                break;
            case R.id.nav_music :
                url = QUERY_URL + MUSIC + RSS;
                toolbar.setTitle(MOVIE_T);
                break;
            case R.id.nav_sport:
                url = QUERY_URL + SPORT + RSS;
                toolbar.setTitle(SPORT_T);
                break;
            case R.id.nav_incredible :
                url = QUERY_URL + INCREDIBLE + RSS;
                toolbar.setTitle(INCREDIBLE_T);
                break;
            case R.id.nav_technology :
                url = QUERY_URL + TECHNOLOGY + RSS;
                toolbar.setTitle(TECHNOLOGY_T);
                break;
            case R.id.nav_automobile :
                url = QUERY_URL + AUTOMOBILE + RSS;
                toolbar.setTitle(AUTOMOBILE_T);
                break;
            case R.id.nav_consumer :
                url = QUERY_URL + CONSUMER + RSS;
                toolbar.setTitle(CONSUMER_T);
                break;
            case R.id.nav_travel :
                url = QUERY_URL + TRAVEL + RSS;
                toolbar.setTitle(TRAVEL_T);
                break;
            case R.id.nav_health :
                url = QUERY_URL + HEALTH + RSS;
                toolbar.setTitle(HEALTH_T);
                break;
            case R.id.nav_humour :
                url = QUERY_URL + HUMOUR + RSS;
                toolbar.setTitle(HUMOUR_T);
                break;
            case R.id.nav_world:
                url = QUERY_URL + WORLD + RSS;
                toolbar.setTitle(WORLD_T);
                break;
            case R.id.nav_people :
                url = QUERY_URL + PEOPLE + RSS;
                toolbar.setTitle(PEOPLE_T);
                break;
            case R.id.nav_entertainment :
                url = QUERY_URL + ENTERTAINMENT + RSS;
                toolbar.setTitle(ENTERTAINMENT_T);
                break;
            }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        fragmentCreation(url);
        return true;
    }

}
