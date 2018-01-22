package com.example.andrey.newtmpclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.interfaces.OnSearch;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.utils.Utils.hideKeyboard;
import static com.example.andrey.newtmpclient.utils.Utils.showKeyboard;

/**
 * Created by savchenko on 22.01.18.
 */

public abstract class BaseSearchFragment extends BaseFragment  {
    public static final String TAG = "BaseSearchFragment";
    @BindView(R.id.search_toolbar)
    Toolbar searchToolbar;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.etSearch)protected EditText etSearch;
    @OnClick(R.id.ivClose)
    void onCloseClick(){
        etSearch.setText("");
    }
    @OnClick(R.id.ivBack)
    void onBackClick(){
        backClick();
    }

    private void backClick(){
        toolbar.setVisibility(View.VISIBLE);
        searchToolbar.setVisibility(View.GONE);
        hideKeyboard(getActivity(), etSearch);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                openToolbarSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openToolbarSearch() {
        toolbar.setVisibility(View.GONE);
        searchToolbar.setVisibility(View.VISIBLE);
        showKeyboard(getActivity(), etSearch);
    }

    public abstract void onSearch(String search);
}
