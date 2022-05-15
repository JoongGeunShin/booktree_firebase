package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    String title, what, content;
    TextView post_title, post_what, post_content;
    TextView book_genre, book_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        post_title = (TextView) findViewById(R.id.post_title);
        post_what = (TextView) findViewById(R.id.post_what);
        post_content = (TextView) findViewById(R.id.post_content);

        book_genre = (TextView) findViewById(R.id.spinner_add_post_book_genre);
        book_style = (TextView) findViewById(R.id.spinner_add_post_book_style);

        Intent intent = getIntent();

    }


}