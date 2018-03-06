package com.example.andrey.newtmpclient.activities.maindrawer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpModule;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;
import com.example.andrey.newtmpclient.fragments.map.MapNewFragment;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.service.gps.GpsService;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.utils.Utils.hideKeyboard;

public class MainTmpActivity extends BaseActivity implements
        MainTmpView,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainTmpActivity.class.getSimpleName();
    @Inject
    MainTmpPresenter presenter;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private int stateCount;
    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        ((MainTmpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new MainTmpModule(this))).inject(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDialogTitleAndText("Выход", PLEASE_WAIT);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        openFragmentOnStateCount();
    }

    private void openFragmentOnStateCount() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        TextView tvVersion = navigationView.getHeaderView(0).findViewById(R.id.tvVersion);
        if(usersManager.getUser()!=null) {
            tvUserName.setText("Привет, " + usersManager.getUser().getLogin());
        }
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Версия: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_current_tasks));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (findViewById(R.id.search_toolbar).getVisibility() == View.VISIBLE) {
            findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
            findViewById(R.id.search_toolbar).setVisibility(View.GONE);
            hideKeyboard(this, findViewById(R.id.etSearch));
        }else if(stateCount!=0){
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_current_tasks));
        }
        else {
            super.onBackPressed();
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                AllTasksFragment notDone = new AllTasksFragment();
                notDone.setDone(false);
                return openFragment(notDone, 0);
            case R.id.nav_done_tasks:
                AllTasksFragment done = new AllTasksFragment();
                done.setDone(true);
                return openFragment(done, 1);
            case R.id.nav_users:
                return openFragment(new UsersMvpFragment(), 2);
            case R.id.nav_addresses:
                return openFragment(new AddressMvpFragment(), 3);
            case R.id.nav_map:
                return openFragment(new MapNewFragment(), 4);
            case R.id.nav_exit:
                presenter.logout();
                return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean openFragment(Fragment fragment, int stateCount) {
        this.stateCount = stateCount;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
    public void stopServices() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        stopService(new Intent(GpsService.newIntent(this)));
        System.out.println("стопарим сервис");
    }
}
