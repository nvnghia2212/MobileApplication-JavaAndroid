package com.example.nghia.maplocation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentAddGroup extends Fragment {

    ImageView imgRemove,imgAddGroup;
    EditText etxtNameGroup;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
    String dateTimeAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_group,container,false);

        imgRemove = view.findViewById(R.id.imgRemove);
        imgAddGroup = view.findViewById(R.id.imgAddGroup);
        etxtNameGroup = view.findViewById(R.id.etxtNameGroup);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove();
            }
        });

        imgAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeAdd = simpleDateFormat.format(calendar.getTime());
                if (etxtNameGroup.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bạn phải nhập tên Group !", Toast.LENGTH_SHORT).show();
                }else {
                    ((GroupActivity)getActivity()).ReadJSONInsert(etxtNameGroup.getText().toString(),dateTimeAdd);
                    Remove();
                }
            }
        });
        return view;
    }

    private void Remove(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentAddGroup fragmentAddGroup = (FragmentAddGroup) getFragmentManager().findFragmentByTag("FragmentAddGroup");
        fragmentTransaction.remove(fragmentAddGroup);
        fragmentTransaction.commit();
    }

}
