package com.example.andrey.newtmpclient.activities.maindrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpModule;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;
import com.example.andrey.newtmpclient.fragments.map.MapFragment;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;
import com.example.andrey.newtmpclient.login.LoginActivity;
import com.example.andrey.newtmpclient.service.GpsService;


import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class MainTmpActivity extends AppCompatActivity implements MainTmpView, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainTmpActivity.class.getSimpleName();
    @Inject
    MainTmpPresenter presenter;
    private WeakReference<Fragment> baseFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        ((MainTmpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new MainTmpModule(this))).inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (baseFragment != null && baseFragment.get() != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, baseFragment.get())
                            .commit();
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
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
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (id == R.id.nav_current_tasks) {
            return openFragment(new AllTasksFragment());
        } else if (id == R.id.nav_done_tasks) {

        } else if (id == R.id.nav_users) {
            return openFragment(new UsersMvpFragment());
        } else if (id == R.id.nav_addresses) {
            return openFragment(new AddressMvpFragment());
        } else if (id == R.id.nav_map) {
            return openFragment(new MapFragment());
        } else if (id == R.id.nav_exit) {
            presenter.logout();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean openFragment(Fragment fragment) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        baseFragment = new WeakReference<>(fragment);
        return true;
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoginActivity() {

    }

    @Override
    public void stopServices() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        stopService(new Intent(GpsService.newIntent(this)));
        System.out.println("стопарим сервис");
    }
}
