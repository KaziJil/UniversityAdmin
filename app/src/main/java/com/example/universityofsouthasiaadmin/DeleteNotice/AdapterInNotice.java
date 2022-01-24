package com.example.universityofsouthasiaadmin.DeleteNotice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityofsouthasiaadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterInNotice extends RecyclerView.Adapter<AdapterInNotice.NoticeViewAdapter> {
     Context context;
     ArrayList<NoticeData>list;

    public AdapterInNotice(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.delete_notice_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {
        NoticeData currentItem=list.get(position);
        holder.delete_notice_title.setText(currentItem.getTitle());
        try {
            if (currentItem.getImage()!=null)
                Picasso.get().load(currentItem.getImage()).into(holder.delete_notice_imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.delete_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                reference.child(currentItem.getKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();


                            }
                        });
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
         Button delete_notice_btn;
         ImageView delete_notice_imageView;
         TextView delete_notice_title;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            delete_notice_btn=itemView.findViewById(R.id.deleteNoticebtn);
            delete_notice_title=itemView.findViewById(R.id.notice_title);
            delete_notice_imageView=itemView.findViewById(R.id.delete_notice_image);
        }
    }
}
