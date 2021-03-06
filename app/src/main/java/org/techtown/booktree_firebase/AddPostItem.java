package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPostItem extends AppCompatActivity {

    private static final String TAG = "AddPostItemActivity";
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button btnClose;

    // 입력 내용
    private EditText postTitle,postContent;
    private DocumentReference mDatabase;
    private static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_item);

        // 책 장르(로맨스, 소설..)
        Spinner hourSpinner = (Spinner)findViewById(R.id.spinner_add_post_book_genre);
        ArrayAdapter hourAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_genre, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);

        // 책 스타일(베스트셀러, 최신 ...)
        Spinner minuteSpinner = (Spinner)findViewById(R.id.spinner_add_post_book_style);
        ArrayAdapter minuteAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_style, android.R.layout.simple_spinner_item);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(minuteAdapter);

        btnClose = (Button) findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();
                startMainActivity();
            }
        });
    }


    private void addPost(){

        // 제목, 내용, 장르, 스타일일
        postTitle = (EditText) findViewById(R.id.add_post_title);
        String title = postTitle.getText().toString();
        postContent = (EditText) findViewById(R.id.add_post_content);
        String content = postContent.getText().toString();

        Spinner genreS = (Spinner) findViewById(R.id.spinner_add_post_book_genre);
        String book_genre = genreS.getSelectedItem().toString();

        Spinner syleS = (Spinner) findViewById(R.id.spinner_add_post_book_style);
        String book_style = syleS.getSelectedItem().toString();

        userEmail = user.getEmail();

       if(title.length()>0 && content.length()>0 && book_genre.length()>0 && book_style.length()>0){
           PostInfo postInfo = new PostInfo(title, content, book_genre, book_style, userEmail, "0");
           uploader(postInfo);
       }
    }

    private void uploader(PostInfo postInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        db.collection("posts").document(postInfo.getPostTitle())
                .set(postInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("AddPost Activity", "DocumentSnapShot" + documentReference);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AddPost Activity", "Error adding post" + e);

                    }
                });
    }


    private  void startMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}