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
import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewHolder> {

    private ArrayList<UserInfo> userInfo;
    private Context mContext;

    // search랑 연결위한 어댑터
    private Context context;
    private List<UserItem> list;
    private LayoutInflater inflate;
    private UserAdaptor.ViewHolder viewHolder;

    public UserAdaptor(Context context, ArrayList<UserInfo> list){
        this.mContext = context;
        this.userInfo = list;
    }

    public UserAdaptor(List<UserItem> list, SearchActivity searchActivity) {
    }

    public UserAdaptor(List<UserItem> list, Context context){
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new UserAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdaptor.ViewHolder holder, int position) {
        holder.userName.setText(userInfo.get(position).getUserName());
        holder.userEmail.setText(userInfo.get(position).getUserEmail());
    }

    public void setUserlist(ArrayList<UserInfo> list){
        this.userInfo = list;
        notifyDataSetChanged();
    }

    // get
    public int getCount() {
        return list.size();
    }
    public Object getItem(int i) {
        return null;
    }
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return userInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImg;
        TextView userName, userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImg = (ImageView) itemView.findViewById(R.id.post_img);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userEmail = (TextView) itemView.findViewById(R.id.user_email);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, MyTreeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("useremail",userEmail.toString());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }


}
