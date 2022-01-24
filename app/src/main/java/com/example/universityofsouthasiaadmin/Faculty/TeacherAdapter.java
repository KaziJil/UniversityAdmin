package com.example.universityofsouthasiaadmin.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityofsouthasiaadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {
    private final ArrayList<TeacherData> list;
    private final Context context;

    public TeacherAdapter(ArrayList<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateTeacherActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {
        private  TextView name;
        private  TextView email;
        private  TextView post;
        private  Button update;
        private CircleImageView image;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teacher_name_update_info);
            email=itemView.findViewById(R.id.teacher_email_update_info);
            post=itemView.findViewById(R.id.teacher_post_update_info);
            image=itemView.findViewById(R.id.selete_teacher_pic_update_info);
            update=itemView.findViewById(R.id.teacher_info_upload_update_ino);


        }
    }
}
