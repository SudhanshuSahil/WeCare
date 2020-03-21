package com.example.demoapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class form1 extends Fragment {

   DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_form1, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Patient");



        final EditText ht = view.findViewById(R.id.ht);
        final EditText wt = view.findViewById(R.id.wt);
        final Sharedpref sharedpref = new Sharedpref(getContext());
        Button submit = view.findViewById(R.id.submit);

        final TextView name = view.findViewById(R.id.name);
        final TextView village = view.findViewById(R.id.village);
        final TextView age = view.findViewById(R.id.age);

        final TextView haem = view.findViewById(R.id.haemo);
        final TextView bs = view.findViewById(R.id.sugar);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Float h, w, bmi , bs1 , homo;
                String nm,vl;
                int a;
                try {
                    h = Float.parseFloat(ht.getText().toString());
                    w = Float.parseFloat(wt.getText().toString());
                    bmi = (w/(h*h))* 100 * 100;
                    sharedpref.setBmi(bmi);
                    nm = name.getText().toString();
                    vl = village.getText().toString();
                    a= Integer.parseInt(age.getText().toString());
                    bs1 = Float.parseFloat(bs.getText().toString());
                    homo = Float.parseFloat(haem.getText().toString());

                    String id= databaseReference.push().getKey();

                    Patient patient = new Patient(id, nm, vl,a, h, w, bmi, homo, bs1);

                    databaseReference.child(id).setValue(patient);

                    Toast.makeText(getContext(),"Submited " , Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"Fill everything before submitting " , Toast.LENGTH_SHORT).show();
                }

            }
        });


//        Patient patient = new Patient()




        return view;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String gender;


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender="Male";
                break;
            case R.id.female:
                if (checked)
                    gender="Female";
                break;
        }
    }

}