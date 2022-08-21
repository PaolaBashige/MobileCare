package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.AboutTheDoctor;
import com.paolabora.projects.mobilecare.Activities.PatientActivity.UploadSymptoms;
import com.paolabora.projects.mobilecare.Modules.DoctorModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>  {
    private static final String TAG = "BooksAdapter";
    private Context context;
    public List<DoctorModule> doctorModules;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private String doctorsId;

    public DoctorsAdapter(Context context, List<DoctorModule> doctorModules) {
        this.context = context;
        this.doctorModules = doctorModules;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctor_item, parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.doctorsName.setText(doctorModules.get(position).getDoctorsFistName() + " " +
                doctorModules.get(position).getDoctorsLastName());
        holder.doctorsSpeciality.setText(doctorModules.get(position).getDoctorsSpeciality());
        holder.doctorsGender.setText(doctorModules.get(position).getDoctorsGender());
        Glide.with(context).load(doctorModules.get(position).getDoctorProfilePic()).into(holder.profilePic);

        holder.toolbar.inflateMenu(R.menu.doctor_profile);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about_doctors:
                        Intent intent = new Intent(context, AboutTheDoctor.class);
                        intent.putExtra("aboutDoctor", doctorModules.get(position).getDoctorProfile());
                        context.startActivity(intent);
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorModules.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView doctorsName, doctorsSpeciality, doctorsGender;
        Toolbar toolbar;
        CircleImageView profilePic;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorsName = itemView.findViewById(R.id.doctor_name);
            doctorsSpeciality = itemView.findViewById(R.id.doctor_speciality);
            doctorsGender = itemView.findViewById(R.id.doctor_gender);
            toolbar = itemView.findViewById(R.id.about_doc_menu);
            profilePic = itemView.findViewById(R.id.user_profile_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, UploadSymptoms.class);
                        intent.putExtra("doctorName", doctorModules.get(position).getDoctorsFistName());
                        intent.putExtra("doctorId", doctorModules.get(position).getDoctorId());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
