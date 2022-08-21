package com.paolabora.projects.mobilecare.Activities.PatientActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Adapters.PatientUpdateAdapter;
import com.paolabora.projects.mobilecare.Adapters.PatientsAdapter;
import com.paolabora.projects.mobilecare.Modules.PatientUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;


public class PatientFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Patient Ongoing History");
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private String fUser;

    private RecyclerView recyclerView;
    private List<PatientUpdate> patientUpdates = new ArrayList<>();
    private List<String> patientList = new ArrayList<>();
    private PatientUpdateAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientFragment newInstance(String param1, String param2) {
        PatientFragment fragment = new PatientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_patient_fragment, container, false);

        recyclerView = view.findViewById(R.id.patient_updates_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        clearList();
        getBooks();

        return view;
    }

    private void getBooks() {
        fUser = mUser.getUid();

        mDatabase.child("Updates From Patient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    PatientUpdate module = snapshots.getValue(PatientUpdate.class);

                    if (fUser.equals(module.getSenderId()) || fUser.equals(module.getReceiverId())) {
                        patientUpdates.add(module);
                    }

                }
                Log.d("the List has length", patientUpdates.size()+"");
                adapter = new PatientUpdateAdapter(getActivity().getApplicationContext(), patientUpdates);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList() {
        if (patientUpdates != null) {
            patientUpdates.clear();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        patientUpdates = new ArrayList<>();
    }
}
