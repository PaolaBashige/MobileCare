package com.paolabora.projects.mobilecare.Activities.DoctorsActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paolabora.projects.mobilecare.Adapters.DoctorUpdateAdapter;
import com.paolabora.projects.mobilecare.Modules.DoctorUpdate;
import com.paolabora.projects.mobilecare.R;

import java.util.ArrayList;
import java.util.List;


public class DoctorFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Patient Ongoing History");
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private String fUser;

    private RecyclerView recyclerView;
    private List<DoctorUpdate> doctorUpdates = new ArrayList<>();
    private DoctorUpdateAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorFragment() {
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
    public static DoctorFragment newInstance(String param1, String param2) {
        DoctorFragment fragment = new DoctorFragment();
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
        View view = inflater.inflate(R.layout.activity_doctor_fragment, container, false);

        recyclerView = view.findViewById(R.id.doc_updates_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        clearList();
        getBooks();

        return view;
    }

    private void getBooks() {
        fUser = mUser.getUid();

        mDatabase.child("Doctor's Update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    DoctorUpdate update = snapshots.getValue(DoctorUpdate.class);

                    if (fUser.equals(update.getSenderId()) || fUser.equals(update.getReceiverId())) {
                        doctorUpdates.add(update);
                    }
                }
                Log.d("the List has length", doctorUpdates.size()+"");
                adapter = new DoctorUpdateAdapter(getActivity(), doctorUpdates);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList() {
        if (doctorUpdates != null) {
            doctorUpdates.clear();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        doctorUpdates = new ArrayList<>();
    }
}
