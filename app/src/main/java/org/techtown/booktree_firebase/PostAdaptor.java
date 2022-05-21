package org.techtown.booktree_firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdaptor extends RecyclerView.Adapter<PostAdaptor.ViewHolder> {

    private ArrayList<PostInfo> postInfo;
    private Context mContext;

    public PostAdaptor(Context context, ArrayList<PostInfo> list){
        this.mContext = context;
        this.postInfo = list;
    }
    @NonNull
    @Override
    public PostAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdaptor.ViewHolder holder, int position) {
        holder.postTitle.setText(postInfo.get(position).getPostTitle());
        holder.book_info.setText(postInfo.get(position).getBook_genre());
        holder.book_info.setText(postInfo.get(position).getBook_style());
        holder.likesCount.setText(postInfo.get(position).getLikesCount());

    }

    public void setPostlist(ArrayList<PostInfo> list){
        this.postInfo = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return postInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImg;
        TextView postTitle, book_info, likesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImg = (ImageView) itemView.findViewById(R.id.post_img);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            book_info = (TextView) itemView.findViewById(R.id.book_info);
            likesCount = (TextView) itemView.findViewById(R.id.likes);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, PostDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("KEY", postInfo.get(pos).getPostTitle());
                        intent.putExtra("postTitle",postTitle.getText().toString());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}