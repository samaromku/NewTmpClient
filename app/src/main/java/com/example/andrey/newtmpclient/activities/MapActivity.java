package com.example.andrey.newtmpclient.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.fragments.map.MapFragment;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity{
    private Fragment createFragment(){
        return new MapFragment();
    }
    @BindString(R.string.map)String map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        ButterKnife.bind(this);
        changeToolbarTitle(map);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
