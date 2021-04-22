package com.diazs.notify.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diazs.notify.Model.ChatMessage;
import com.diazs.notify.Model.DateFormater;
import com.diazs.notify.Model.User;
import com.diazs.notify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ChatMessage> mChat;

    public MessageAdapter(Context mContext, ArrayList<ChatMessage> mChat){
        this.mChat = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage chatMessage = mChat.get(position);
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

        setListData(chatMessage.getSender(), holder, chatMessage);

        if (chatMessage.getSender().equals(fuser.getUid())){
            RelativeLayout.LayoutParams relativeContainerParams = (RelativeLayout.LayoutParams) holder.relativeContainer.getLayoutParams();
            RelativeLayout.LayoutParams cardContentLayoutParams = (RelativeLayout.LayoutParams) holder.cardContent.getLayoutParams();
            RelativeLayout.LayoutParams cardProfileLayoutParams = (RelativeLayout.LayoutParams) holder.cardProfile.getLayoutParams();

            relativeContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            cardContentLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.card_profile);
            cardProfileLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            holder.relativeContainer.setLayoutParams(relativeContainerParams);
            holder.cardContent.setLayoutParams(cardContentLayoutParams);
            holder.cardProfile.setLayoutParams(cardProfileLayoutParams);
        }else{
            RelativeLayout.LayoutParams cardContentParams = (RelativeLayout.LayoutParams) holder.cardContent.getLayoutParams();
            cardContentParams.addRule(RelativeLayout.RIGHT_OF, R.id.card_profile);
            holder.cardContent.setLayoutParams(cardContentParams);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardProfile, cardContent;
        RelativeLayout relativeContainer;
        TextView message, time;
        ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cardProfile = itemView.findViewById(R.id.card_profile);
            cardContent = itemView.findViewById(R.id.card_content);
            relativeContainer = itemView.findViewById(R.id.relative_container);
            profileImage = itemView.findViewById(R.id.profile_image);
            message = itemView.findViewById(R.id.tv_pesan);
            time = itemView.findViewById(R.id.waktu);
        }
    }

    public void setListData(String lawanBicara, ViewHolder holder, ChatMessage chatMessage){
        holder.message.setText(chatMessage.getMessage());
        holder.time.setText(DateFormater.getDateFormated(chatMessage.getCreatedAt(), DateFormater.FORMAT_SIX));
        FirebaseDatabase.getInstance().getReference("users")
                .child(lawanBicara)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getImageURL() != null){
                            Glide.with(mContext.getApplicationContext()).load(user.getImageURL()).into(holder.profileImage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}