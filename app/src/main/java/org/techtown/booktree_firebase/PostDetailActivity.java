package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    TextView postDetail;
    Button tmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postDetail = findViewById(R.id.post_info);

        tmBtn = findViewById(R.id.tmbtn);

        Intent intent = getIntent();

        PostItem postItem = intent.getParcelableExtra("post_indx");
        StringBuffer sb = new StringBuffer();

        sb.append("글 제목: " + postItem.getPostTitle() + "\n");
        sb.append("글 인덱스 번호: " + postItem.getPostIdx() + "\n");

        postDetail.setText(sb);
        tmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(PostDetailActivity.this, TransmissionActivity.class);
                startActivity(intent);
            }
        });


    }


}