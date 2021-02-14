package com.diazs.notify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
    private ArrayList<NotifModel> list_notif;

    public NotifAdapter(ArrayList<NotifModel> list_notif){
        this.list_notif = list_notif;
    }

    @NonNull
    @Override
    public NotifAdapter.NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_notif, parent, false);
        return  new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        holder.txtTitle.setText(list_notif.get(position).getTitle());
        holder.txtDetail.setText(list_notif.get(position).getDetail());
    }

    @Override
    public int getItemCount(){
        return  (list_notif != null) ? list_notif.size() : 0;
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtDetail;

        public NotifViewHolder(View itemView){
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title_notif);
            txtDetail = (TextView) itemView.findViewById(R.id.detail_notif);
        }
    }
}
