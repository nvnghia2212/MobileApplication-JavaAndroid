package com.example.nghia.maplocation;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentDating extends Fragment {

    ImageView imgRemove,imgDateAddress;
    EditText editNameAddress;
    TextView txtDateAddress;
    Button btnSubmit;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormatDateAddress = new SimpleDateFormat("dd/MM/yyyy");

    Calendar calendarSubmit = Calendar.getInstance();
    SimpleDateFormat simpleDateFormatSubmit = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
    String dateTimeAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_dating,container,false);

        imgRemove = view.findViewById(R.id.imgRemove);
        editNameAddress = view.findViewById(R.id.editNameAddress);
        txtDateAddress = view.findViewById(R.id.txtDateAddress);
        imgDateAddress = view.findViewById(R.id.imgDateAddress);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove();
            }
        });

        imgDateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        txtDateAddress.setText(simpleDateFormatDateAddress.format(calendar.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        callBack,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeAdd = simpleDateFormatSubmit.format(calendarSubmit.getTime());
                if (editNameAddress.getText().toString().equals("") || txtDateAddress.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bạn phải nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else {
                    ((DatingActivity)getActivity()).ReadJSONInsertDating(editNameAddress.getText().toString(),txtDateAddress.getText().toString(),dateTimeAdd);
                    Remove();
                }
            }
        });

        return view;
    }

    private void Remove(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentDating fragmentDating = (FragmentDating) getFragmentManager().findFragmentByTag("FragmentDating");
        fragmentTransaction.remove(fragmentDating);
        fragmentTransaction.commit();
    }
}
