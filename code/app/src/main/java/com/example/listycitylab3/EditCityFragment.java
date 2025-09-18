package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener{
        void editCity(City city);
    }
    static EditCityFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private EditCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof EditCityDialogListener){
            listener = (EditCityDialogListener) context;
        }
        else {
            throw new RuntimeException(context + "must implement\n" +
                    "AddCityDialogListener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        City city;
        if(getArguments() != null){
            city = (City) getArguments().getSerializable("city");
        } else {
            city = null;
        }

        if(city != null){
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }
        return builder.setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ok", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if(city != null){
                        city.setName(cityName);
                        city.setProvince(provinceName);
                        listener.editCity(city);
                    }
                })
                .create();
    }

}
