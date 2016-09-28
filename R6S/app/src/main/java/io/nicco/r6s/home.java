package io.nicco.r6s;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Fragment curFrag = null;
    private static Activity c;
    private static String DB_PATH;
    private static String DB_NAME;
    private final String DB_URL = "https://raw.githubusercontent.com/CupCakeArmy/static/master/R6S/R6S.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        c = this;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ChangeFragment(new ops_view());
        setTitle("Operators");

        // Save DB from assets to storage
        DB_PATH = getFilesDir().getAbsolutePath() + "/databases/";
        DB_NAME = "R6S.sqlite";
        byte[] buffer = new byte[1024];
        int length;
        OutputStream myOutput = null;
        InputStream myInput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (file.exists())
                file.delete();
            new File(DB_PATH).mkdirs();
            myInput = getAssets().open(DB_NAME);
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                this.finish();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_btn_git) {
            Intent open_git = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/CupCakeArmy/Android"));
            startActivity(open_git);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title = "";

        if (id == R.id.nav_weapon) {
            title = "Weapons";
            curFrag = new weapons_view();
        } else if (id == R.id.nav_op) {
            title = "Operators";
            curFrag = new ops_view();
        } else if (id == R.id.nav_maps) {
            title = "Maps";
            curFrag = new maps_view();
        } else if (id == R.id.nav_gen) {
            title = "Generator";
            curFrag = new random_view();
        }

        setTitle(title);
        c.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ChangeFragment(curFrag);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void ChangeFragment(Fragment f) {
        c.getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_cont, f).commit();
    }

    public static SQLiteDatabase mkdb() {
        return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 0);
    }

    public static Activity root() {
        return c;
    }
}
