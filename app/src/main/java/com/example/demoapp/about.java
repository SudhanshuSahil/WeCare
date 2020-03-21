package com.example.demoapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by coderzpassion on 05/04/16.
 */
public class about extends Fragment {

    ArrayList<Patient> patients;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.about, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Patient");
        Sharedpref sharedpref = new Sharedpref(getContext());

//        if(sharedpref.getSetup()){
//            return recyclerView;
//        }
        patients = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients.clear();

                for(DataSnapshot patient : dataSnapshot.getChildren()){
                    Patient patient1 = patient.getValue(Patient.class);
                    Log.d("asss", "onDataChange: " + patient.toString());
                    patients.add(patient1);
                }
                Collections.reverse(patients);
                setUpRecyclerView(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sharedpref.setSetup(true);
        return recyclerView;
    }

    private void setUpRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new AboutUsAdapter(rv.getContext(), patients));
    }


    public static class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
        ArrayList<Patient> aboutlist;
        Context aboutuscontext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView items, status;
            public final CardView cardView;

            public ViewHolder(View view) {
                super(view);
                items = (TextView) view.findViewById(R.id.itemname);
                cardView = view.findViewById(R.id.cardinlist);
                status = view.findViewById(R.id.state);

            }
        }

        public AboutUsAdapter(Context context, ArrayList<Patient> list) {
            aboutuscontext = context;
            aboutlist = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.items.setText(aboutlist.get(position).getName());

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patient");
            databaseReference.child(aboutlist.get(position).getId()).child("state").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        Boolean bool = dataSnapshot.getValue(Boolean.class);
                        if(bool){
                            holder.status.setText("Done");
                        }
                        else {
                            holder.status.setText("Pending");
                        }
                    }
                    catch (Exception e){
                        holder.status.setText("Pending");
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity) aboutuscontext;
                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                    Profile profile = Profile.newInstance(aboutlist.get(position));
                    profile.show(fragmentTransaction , "show me the money");

                }
            });



        }

        @Override
        public int getItemCount() {
            return aboutlist.size();
        }
    }
}
