package org.techtown.booktree_firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdaptor extends BaseAdapter {

    ArrayList<CommentDTO> commentDTOS;
    LayoutInflater layoutInflater;

    public CommentAdaptor(ArrayList<CommentDTO> commentDTOS, LayoutInflater layoutInflater) {
        this.commentDTOS = commentDTOS;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public int getCount() {
        return commentDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return commentDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        CommentDTO item = commentDTOS.get(position);

        //재활용할 뷰는 사용하지 않음!!
        View itemView = null;

        itemView= layoutInflater.inflate(R.layout.post_commentbox,viewGroup,false);


        //만들어진 itemView에 값들 설정
        TextView tvName = itemView.findViewById(R.id.tv_name);
        TextView tvMsg = itemView.findViewById(R.id.tv_msg);

        tvName.setText(item.getUserName());
        tvMsg.setText(item.getComment());

        return itemView;
    }
}
