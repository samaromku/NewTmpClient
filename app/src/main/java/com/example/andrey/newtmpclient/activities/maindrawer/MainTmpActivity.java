package com.example.andrey.newtmpclient.activities.maindrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpModule;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.fragments.donetasks.DoneTasksFragment;
import com.example.andrey.newtmpclient.fragments.map.MapFragment;
import com.example.andrey.newtmpclient.fragments.notdonetasks.NotDoneTasksFragment;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.service.GpsService;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.utils.Utils.hideKeyboard;

public class MainTmpActivity extends AppCompatActivity implements MainTmpView, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainTmpActivity.class.getSimpleName();
    @Inject
    MainTmpPresenter presenter;
    private UsersManager usersManager = UsersManager.INSTANCE;

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_current_tasks));
        TextView tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        tvUserName.setText("Привет, " + usersManager.getUser().getLogin());
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
        }else if(findViewById(R.id.search_toolbar).getVisibility()== View.VISIBLE){
            findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
            findViewById(R.id.search_toolbar).setVisibility(View.GONE);
            hideKeyboard(this, (EditText) findViewById(R.id.etSearch));
        }
        else {
            super.onBackPressed();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_current_tasks:
                return openFragment(new NotDoneTasksFragment());
            case R.id.nav_done_tasks:
                return openFragment(new DoneTasksFragment());
            case R.id.nav_users:
                return openFragment(new UsersMvpFragment());
            case R.id.nav_addresses:
                return openFragment(new AddressMvpFragment());
            case R.id.nav_map:
                return openFragment(new MapFragment());
            case R.id.nav_exit:
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

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
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
