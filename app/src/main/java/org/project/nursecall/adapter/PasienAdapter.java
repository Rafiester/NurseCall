package org.project.nursecall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.project.nursecall.MainActivity;
import org.project.nursecall.R;
import org.project.nursecall.data.PasienData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PasienAdapter extends RecyclerView.Adapter<PasienAdapter.Holder> {

    private Context mContext;
    private ArrayList<PasienData> pasienDataList, listPasien;

    public PasienAdapter(ArrayList<PasienData> pasienDataArrayList, Context context) {
        this.pasienDataList = pasienDataArrayList;
        this.mContext = context;
        this.listPasien = pasienDataArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasien, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PasienData pasienData = listPasien.get(position);
        String[] separated = pasienData.getName().split(" \\(");
        holder.tvName.setText(separated[0].trim());
        holder.tvRuang.setText(separated[1].trim().replace(")",""));
        holder.tvTime.setText(new SimpleDateFormat("dd-MM-yyyy/HH:mm").format(new Date(pasienData.getTime() * 1000L)));
        if (pasienData.getMsg_status() == 0) holder.llPasien.setBackgroundColor(Color.parseColor("#C4DEA4"));
        else holder.llPasien.setBackgroundColor(Color.parseColor("#C1C6BA"));
    }

    @Override
    public int getItemCount() {
        return listPasien.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvName, tvRuang, tvTime;
        private LinearLayout llPasien;
        public Holder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRuang = itemView.findViewById(R.id.tv_ruang);
            tvTime = itemView.findViewById(R.id.tv_time);
            llPasien = itemView.findViewById(R.id.ll_pasien);
        }
    }
}
