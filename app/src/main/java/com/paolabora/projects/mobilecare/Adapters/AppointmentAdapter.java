package com.paolabora.projects.mobilecare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paolabora.projects.mobilecare.Activities.DoctorsActivity.AppointmentDetails;
import com.paolabora.projects.mobilecare.Modules.AppointmentModule;
import com.paolabora.projects.mobilecare.Modules.AvailabilityModule;
import com.paolabora.projects.mobilecare.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewModel>  {
    private static final String TAG = "AppointmentAdapter";
    private Context context;
    public List<AppointmentModule> appointmentList;

    public AppointmentAdapter(Context context, List<AppointmentModule> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctor_schedule_item, parent,false);
        return new AppointmentViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentViewModel holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        AvailabilityModule module = new AvailabilityModule();

        holder.dateAndTime.setText(appointmentList.get(position).getDateSet() + " " +
                appointmentList.get(position).getTimeSet());
        holder.status.setText(module.getScheduleStatus());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class AppointmentViewModel extends RecyclerView.ViewHolder {
        TextView dateAndTime, status;

        public AppointmentViewModel(@NonNull View itemView) {
            super(itemView);

            dateAndTime = itemView.findViewById(R.id.schedule_date_and_time);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, AppointmentDetails.class);
                        intent.putExtra("patientName", appointmentList.get(position).getPatientName());
                        intent.putExtra("doctorName", appointmentList.get(position).getPatientName());
                        intent.putExtra("patientId", appointmentList.get(position).getPatientId());
                        intent.putExtra("doctorId", appointmentList.get(position).getDoctorId());
                        intent.putExtra("appointmentReason", appointmentList.get(position).getAppointmentReason());
                        intent.putExtra("medications", appointmentList.get(position).getMedications());
                        intent.putExtra("symptoms", appointmentList.get(position).getSymptoms());
                        intent.putExtra("symptomDesc", appointmentList.get(position).getSymptomDesc());
                        intent.putExtra("date", appointmentList.get(position).getDateSet());
                        intent.putExtra("time", appointmentList.get(position).getTimeSet());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
