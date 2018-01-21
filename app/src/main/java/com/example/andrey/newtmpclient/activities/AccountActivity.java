package com.example.andrey.newtmpclient.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerFragment;
import com.example.andrey.newtmpclient.login.LoginActivity;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.service.GpsService;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.utils.Const;
import com.google.firebase.iid.FirebaseInstanceId;

public class AccountActivity extends AppCompatActivity {
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private static final String TAG = "AccountActivity";
    private Client client = Client.INSTANCE;
    private TokenManager tokenManager = TokenManager.instance;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    ViewPager pager;
    PagerAdapter pagerAdapter;
//    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_view_pager);
        if(usersManager.getUser()!=null) {
            getSupportActionBar().setTitle("Привет, " + usersManager.getUser().getLogin());

            pager = (ViewPager) findViewById(R.id.pager);
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

//            sharedPref = getPreferences(Context.MODE_PRIVATE);
            addFireBaseTokenIfFromAuth();
            buttonAddTask();
            addPagerActions();
        }else {
            Toast.makeText(this, "Вы не авторизованы", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!client.isAuth()){
            Log.i(TAG, "onResume: accountactivity " + client.isAuth());
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void addPagerActions(){
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addFireBaseTokenIfFromAuth(){
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            ConverterMessages converter = new ConverterMessages();
            new Thread(() -> {
                if (getIntent().getBooleanExtra(Const.FROM_AUTH, false)) {
                    converter.sendMessageToServer(Request.addFireBase(Request.ADD_FIREBASE_TOKEN, usersManager.getUser(), FirebaseInstanceId.getInstance().getToken()));
                }
            }).start();
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TasksPagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void firstTimeAddAddresses(){
        Intent intent = new Intent(this, CreateTaskActivity.class);
        if(addressManager.getAddresses().size()==0) {
            new Updater(this, new Request(Request.GIVE_ME_ADDRESSES_PLEASE), intent).execute();
        }else startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void buttonAddTask(){
        //кнопка добавить задание
        FloatingActionButton addTask = (FloatingActionButton) findViewById(R.id.add_task_btn);
        UserRole userRole = userRolesManager.getUserRole();
        if(userRole!=null){
            if(userRole.isMakeTasks()) {
                addTask.setVisibility(View.VISIBLE);
            }
            else addTask.setVisibility(View.INVISIBLE);
        }
        addTask.setOnClickListener(v -> firstTimeAddAddresses());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                new LogoutUpdater(this, new Request(Request.LOGOUT), intent).execute();
                return true;

            case R.id.users:
                startActivity(new Intent(AccountActivity.this, UsersActivity.class));
            return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }



    private class LogoutUpdater extends AsyncTask<Void, Void, Void>{
        private ProgressDialog dialog;
        private Context context;
        private Request request;
        private Intent intent;
        private ConverterMessages converter = new ConverterMessages();

        LogoutUpdater(Context context, Request request, Intent intent){
            this.context = context;
            this.dialog = new ProgressDialog(context);
            this.request = request;
            this.intent = intent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            converter.sendMessageToServer(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            client.setAuth(false);
            allClear();
            stopService(new Intent(GpsService.newIntent(context)));
            System.out.println("стопарим сервис");
            context.startActivity(intent);
            super.onPostExecute(aVoid);
            dialog.dismiss();
            usersManager.setUser(null);
            tokenManager.setToken(null);
        }
    }
    private void allClear(){
        tasksManager.removeAll();
        AddressManager.INSTANCE.removeAll();
        CommentsManager.INSTANCE.removeAll();
        userRolesManager.removeAll();
        UsersManager.INSTANCE.removeAll();
    }
}
