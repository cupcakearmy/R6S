package io.nicco.r6s;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class main extends AppCompatActivity {

    // Alert settings
    static final int NUM_ALERTS = 5;
    static final String TAG_ALERT_MAP = "TAG_ALERT_MAP";
    static final String TAG_ALERT_OP = "TAG_ALERT_OP";

    // Links
    static final String ABOUT_GIT = "https://github.com/CupCakeArmy/R6S/";
    static final String ABOUT_CHANGES = "https://github.com/CupCakeArmy/R6S/blob/master/README.md#versions";
    static final String ABOUT_BUG = "https://github.com/CupCakeArmy/R6S/issues/new";
    static final String ABOUT_EMAIL = "nicco.borgioli@gmail.com";

    // Settings
    static final String SORT_OPS = "GROUPS_OPS_SORT";
    static final String SORT_WEAPONS = "GROUPS_WEAPONS_SORT";

    // Accessed by fragments in static manner
    static AssetManager am;
    static FragmentManager fm;
    private static ActionBar actionBar;
    private NavigationView nv;

    // Vars
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Class prev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        am = getAssets();
        fm = getSupportFragmentManager();
        MenuInflater mi = getMenuInflater();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        nv = (NavigationView) findViewById(R.id.nvView);
        nv.inflateHeaderView(R.layout.drawer_header);
        nv.inflateMenu(R.menu.drawer);

        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_drawer_open, R.string.app_drawer_closed) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);


        // SETUP INITIAL VIEW
        nv.getMenu().getItem(0).setChecked(true);
        prev = single_home.class;
        clearBackStack();
        transition(new single_home());

    }

    static void transition(final Fragment f) {
        f.setMenuVisibility(false);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.flContent, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    static void clearBackStack() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void setActionBarTitle(final String s) {
        actionBar.setTitle(s);
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.drawer_item_home:
                fragmentClass = single_home.class;
                break;
            case R.id.drawer_item_maps:
                fragmentClass = single_maps.class;
                break;
            case R.id.drawer_item_ops:
                fragmentClass = group_ops.class;
                break;
            case R.id.drawer_item_weapons:
                fragmentClass = group_weapons.class;
                break;
            case R.id.drawer_item_shuffle:
                fragmentClass = single_rand.class;
                break;
//            case R.id.drawer_item_settings:
//                fragmentClass = error.class;
//                break;
            case R.id.drawer_item_info:
                fragmentClass = single_about.class;
                break;
            default:
                fragmentClass = error.class;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        // If the fragment is already loaded
        if (prev == fragmentClass)
            return;
        prev = fragmentClass;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        clearBackStack();
        transition(fragment);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            // If the drawer is open, close it
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (fm.getBackStackEntryCount() == 1 && !actionBar.getTitle().equals(getString(R.string.nav_home))) {
            // If there is only one left, return to home
            clearBackStack();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.flContent, new single_home());
            transaction.commit();
        } else if (fm.getBackStackEntryCount() < 1 || (fm.getBackStackEntryCount() < 2 && actionBar.getTitle().equals(getString(R.string.nav_home))))
            // If already home -> close the app
            finish();
        else
            // Just go back to previous window
            fm.popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
