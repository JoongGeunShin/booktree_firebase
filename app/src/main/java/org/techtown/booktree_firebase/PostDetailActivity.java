package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    String title, what, content;
    String username;
//    TextView post_title, post_content;
//    TextView book_genre, book_style;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        final TextView post_title = (TextView) findViewById(R.id.post_detail_title);
        final TextView post_content = (TextView) findViewById(R.id.post_detail_content);
        final TextView book_genre = (TextView) findViewById(R.id.post_detail_book_genre);
        final TextView book_style = (TextView) findViewById(R.id.post_detail__book_style);

        Intent intent = getIntent();
        String key = intent.getStringExtra("KEY");


        //book_genre.setText(intent.getStringExtra("bookInfo"));

        // post 정보
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("posts").document(key);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            post_title.setText(document.getData().get("postTitle").toString());
                            post_content.setText(document.getData().get("postContent").toString());
                            book_genre.setText(document.getData().get("book_genre").toString());
                            book_style.setText(document.getData().get("book_style").toString());

                        }
                    }
                }
            }
        });
//        // user 정보(current)
//        DocumentReference documentUserReference = FirebaseFirestore.getInstance().collection("users").document(user.getEmail());
//        documentUserReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document != null) {
//                        if (document.exists()) {
//                            username = document.getData().get("name").toString();
//                        }
//                    }
//                }
//            }
//        });


    }


}