package com.example.andrey.newtmpclient.di;

import com.example.andrey.newtmpclient.activities.address.AddressMvpActivity;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.network.TmpService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Andrey on 06.10.2017.
 */
@Module(subcomponents = {
    AddressMvpComponent.class,

})
class AppModule {
    private static final String BASE_URL = "81.23.123.230";

    @Provides
    @IntoMap
    @ClassKey(AddressMvpActivity.class)
    ComponentBuilder provideNewOrder(AddressMvpComponent.Builder builder){
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
}
