package com.example.msit_admin.DeleteNotice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.msit_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.noticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public noticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_feed_item_layout, parent, false);
        return new noticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.noticeViewAdapter holder, int position) {
        NoticeData currentItem = list.get(position);

        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        try {
            if (currentItem.getImage() != null)
                Picasso.get()
                        .load(currentItem.getImage())
                        .into(holder.deleteNoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are You Sure Want to Delete This Notice ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notice");
                                reference.child(currentItem.getKey()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                notifyItemRemoved(position);
                            }
                        }
                );
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dialog != null)
                    dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class noticeViewAdapter extends RecyclerView.ViewHolder {

        private Button deleteNoticeBtn;
        private TextView deleteNoticeTitle;
        private ImageView deleteNoticeImage;

        public noticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteNoticeBtn = itemView.findViewById(R.id.deleteNoticeBtn);
            deleteNoticeTitle = itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage = itemView.findViewById(R.id.deleteNoticeImage);
        }
    }

}
