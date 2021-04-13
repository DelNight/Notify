package com.diazs.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PilihPostinganActivity extends BottomSheetDialogFragment {
//    private PilihPostinganListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pilihan_posting_layout, container, false);

        Button forum = v.findViewById(R.id.btn_forum);
        Button voting = v.findViewById(R.id.btn_voting);
        Button learn = v.findViewById(R.id.btn_learn);
        Button event = v.findViewById(R.id.btn_event);

        //user pilih posting forum
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct ke halaman form forum
//                Intent i = new Intent(HomeActivity.this,FormForum.class);
//                startActivity(i);
            }
        });

        //user pilih posting voting
        voting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct ke halaman form voting
                dismiss();
            }
        });

        //user pilih posting e-learning
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct ke halaman form e-learning
                dismiss();
            }
        });

        //user pilih posting event
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct ke halaman form event
                dismiss();
            }
        });

        return v;
    }
//
//    public interface PilihPostinganListener {
//        void OnButtonClicked(String text);
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        try {
//            mListener = (PilihPostinganListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " harus implementntasi PilihPostinganListener");
//        }
//    }
}
