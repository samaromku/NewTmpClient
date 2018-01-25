package com.example.andrey.newtmpclient.activities.taskactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskPageFragment;

/**
 * Created by andrey on 19.07.2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private int taskId;

    public ScreenSlidePagerAdapter(FragmentManager fm, int taskId) {
        super(fm);
        this.taskId = taskId;
    }

    @Override
    public Fragment getItem(int position) {
        return OneTaskPageFragment.newInstance(position, taskId);
    }

    @Override
    public int getCount() {
        return 2;
    }
}