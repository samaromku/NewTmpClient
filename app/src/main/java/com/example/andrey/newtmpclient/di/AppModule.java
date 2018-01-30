package com.example.andrey.newtmpclient.di;

import com.example.andrey.newtmpclient.activities.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.activities.createTask.di.CreateTaskComponent;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserActivity;
import com.example.andrey.newtmpclient.activities.createuser.di.CreateNewUserComponent;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.activities.login.di.LoginComponent;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.di.MainTmpComponent;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksActivity;
import com.example.andrey.newtmpclient.activities.needdoingtasks.di.NeedDoingTasksComponent;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserActivity;
import com.example.andrey.newtmpclient.activities.oneuser.di.OneUserComponent;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskActivity;
import com.example.andrey.newtmpclient.activities.updatenewtask.di.UpdateNewTaskComponent;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRoleActivity;
import com.example.andrey.newtmpclient.activities.userrole.di.NewUserRoleComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.dialogs.filter.FilterDialog;
import com.example.andrey.newtmpclient.dialogs.filter.di.FilterComponent;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.fragments.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;
import com.example.andrey.newtmpclient.fragments.alltasks.di.DoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.map.MapNewFragment;
import com.example.andrey.newtmpclient.fragments.map.di.MapNewComponent;
import com.example.andrey.newtmpclient.fragments.users.UsersMvpFragment;
import com.example.andrey.newtmpclient.fragments.users.di.UsersMvpComponent;
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

import static com.example.andrey.newtmpclient.di.ChangeUrlInterceptor.BASE_URL_OUTER;


/**
 * Created by Andrey on 06.10.2017.
 */
@Module(subcomponents = {
        AddressMvpComponent.class,
        UsersMvpComponent.class,
        MainTmpComponent.class,
        CreateTaskComponent.class,
        DoneTasksComponent.class,
        LoginComponent.class,
        OneUserComponent.class,
        CreateNewUserComponent.class,
        UpdateNewTaskComponent.class,
        MapNewComponent.class,
        NewUserRoleComponent.class,
        NeedDoingTasksComponent.class,
        FilterComponent.class,
})
class AppModule {

    @Provides
    @IntoMap
    @ClassKey(AddressMvpFragment.class)
    ComponentBuilder provideNewOrder(AddressMvpComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(FilterDialog.class)
    ComponentBuilder provideFilter(FilterComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(NeedDoingTasksActivity.class)
    ComponentBuilder provideNeedDoing(NeedDoingTasksComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(NewUserRoleActivity.class)
    ComponentBuilder provideUserRole(NewUserRoleComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(MapNewFragment.class)
    ComponentBuilder provideMap(MapNewComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(OneUserActivity.class)
    ComponentBuilder provideOneUser(OneUserComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(CreateNewUserActivity.class)
    ComponentBuilder provideCreateUser(CreateNewUserComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(UpdateNewTaskActivity.class)
    ComponentBuilder provideUpdateTask(UpdateNewTaskComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(LoginActivity.class)
    ComponentBuilder provideLogin(LoginComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(UsersMvpFragment.class)
    ComponentBuilder provideUsers(UsersMvpComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(MainTmpActivity.class)
    ComponentBuilder provideMain(MainTmpComponent.Builder builder) {
        return builder;
    }

    @Provides
    @IntoMap
    @ClassKey(CreateTaskActivity.class)
    ComponentBuilder provideCreateTask(CreateTaskComponent.Builder builder) {
        return builder;
    }


    @Provides
    @IntoMap
    @ClassKey(AllTasksFragment.class)
    ComponentBuilder provideTasksPager(DoneTasksComponent.Builder builder) {
        return builder;
    }

    @Singleton
    @Provides
    TmpService tmpService(ChangeUrlInterceptor changeUrlInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(changeUrlInterceptor)
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
                .baseUrl(BASE_URL_OUTER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(TmpService.class);
    }



    @Singleton
    @Provides
    ChangeUrlInterceptor changeUrlInterceptor(){
        return new ChangeUrlInterceptor();
    }

    @Provides
    @Singleton
    AddressManager addressManager() {
        return AddressManager.INSTANCE;
    }

    @Provides
    @Singleton
    UsersManager usersManager() {
        return UsersManager.INSTANCE;
    }
}
