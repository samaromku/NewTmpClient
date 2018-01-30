package com.example.andrey.newtmpclient.dialogs.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.dialogs.filter.di.FilterComponent;
import com.example.andrey.newtmpclient.dialogs.filter.di.FilterModule;
import com.example.andrey.newtmpclient.interfaces.OnDialogClosed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import javax.inject.Inject;

public class FilterDialog extends DialogFragment implements FilterView {
    private static final String TAG = FilterDialog.class.getSimpleName();
    @Inject
    FilterPresenter presenter;
    private OnDialogClosed onDialogClosed;

    public void setOnDialogClosed(OnDialogClosed onDialogClosed) {
        this.onDialogClosed = onDialogClosed;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOK = view.findViewById(R.id.btnOk);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        RadioGroup radioGroup = view.findViewById(R.id.rgFilter);
        final RadioButton[] radioButton = new RadioButton[1];

        btnOK.setOnClickListener(v -> {
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            radioButton[0] = view.findViewById(radioButtonID);

            if(radioButton[0]!=null){
                onDialogClosed.onDialogClosed(radioButton[0].getText().toString());
                closeDialog();
            }else {
                Toast.makeText(getActivity(), "Ничего не выбрано", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> closeDialog());
    }

    private void closeDialog(){
        getDialog().dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((FilterComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new FilterModule(this))).inject(this);
        getDialog().setTitle("Фильтр заявок");
        return inflater.inflate(R.layout.dialog_filter, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
