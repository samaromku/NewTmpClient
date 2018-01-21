package com.example.andrey.newtmpclient.di;

import com.example.andrey.newtmpclient.activities.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpComponent;
import com.example.andrey.newtmpclient.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.createTask.di.CreateTaskComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;
import com.example.andrey.newtmpclient.fragments.alltasks.di.AllTasksComponent;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerFragment;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.di.TasksPagerComponent;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;
import com.example.andrey.newtmpclient.fragments.users.di.UsersMvpComponent;
import com.example.andrey.newtmpclient.login.LoginActivity;
import com.example.andrey.newtmpclient.login.di.LoginComponent;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.TmpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Andrey on 06.10.2017.
 */
@Module(subcomponents = {
    AddressMvpComponent.class,
    AllTasksComponent.class,
    UsersMvpComponent.class,
    MainTmpComponent.class,
    CreateTaskComponent.class,
    TasksPagerComponent.class,
    LoginComponent.class,
})
class AppModule {
    private static final String BASE_URL = "http://81.23.123.230:60123";

    @Provides
    @IntoMap
    @ClassKey(AddressMvpFragment.class)
    ComponentBuilder provideNewOrder(AddressMvpComponent.Builder builder){
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(LoginActivity.class)
    ComponentBuilder provideLogin(LoginComponent.Builder builder){
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(AllTasksFragment.class)
    ComponentBuilder provideAllTasks(AllTasksComponent.Builder builder){
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(UsersMvpFragment.class)
    ComponentBuilder provideUsers(UsersMvpComponent.Builder builder){
        return builder;
    }

     @Provides
    @IntoMap
    @ClassKey(MainTmpActivity.class)
    ComponentBuilder provideMain(MainTmpComponent.Builder builder){
        return builder;
    }

     @Provides
    @IntoMap
    @ClassKey(CreateTaskActivity.class)
    ComponentBuilder provideCreateTask(CreateTaskComponent.Builder builder){
        return builder;
    }


     @Provides
    @IntoMap
    @ClassKey(TasksPagerFragment.class)
    ComponentBuilder provideTasksPager(TasksPagerComponent.Builder builder){
        return builder;
    }

    @Singleton
    @Provides
    TmpService tmpService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
//                .addInterceptor(chain -> {
//                    Request request = chain.request()
//                            .newBuilder()
//                            .addHeader("Content-Type", "application/json")
//                            .build();
//                    return chain.proceed(request);
//                })
                .build();

//        Gson gson = new GsonBuilder()
//                .setDateFormat(DATE_PATTERN)
//                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(TmpService.class);
    }

    @Provides
    @Singleton
    AddressManager addressManager(){
        return AddressManager.INSTANCE;
    }

    @Provides
    @Singleton
    UsersManager usersManager(){
        return UsersManager.INSTANCE;
    }
}
