package org.techtown.booktree_firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class PostDetailActivity extends AppCompatActivity {

    String username;
    TextView post_title, post_content, book_genre, book_style;

    // comment 관련 선언
    EditText comment_edit;
    ListView comment_view;
    ArrayList<CommentDTO> commentDTOS = new ArrayList<>();
    CommentAdaptor adaptor;

    // 파베 db(realtime)
    FirebaseDatabase firebaseDatabase;
    DatabaseReference commentRef;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // 좋아요 관련 선언
    ScaleAnimation scaleAnimation;
    BounceInterpolator bounceInterpolator;//애니메이션이 일어나는 동안의 회수, 속도를 조절하거나 시작과 종료시의 효과를 추가 할 수 있다
    CompoundButton button_favorite;
    int likesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        // 게시글 파트 view 찾기
        post_title = (TextView) findViewById(R.id.post_detail_title);
        post_content = (TextView) findViewById(R.id.post_detail_content);
        book_genre = (TextView) findViewById(R.id.post_detail_book_genre);
        book_style = (TextView) findViewById(R.id.post_detail__book_style);

        // comment 파트 view 찾기
        comment_edit = findViewById(R.id.comment_edit);
        comment_view = (ListView) findViewById(R.id.comment_view);
        adaptor = new CommentAdaptor(commentDTOS,getLayoutInflater());
        comment_view.setAdapter(adaptor);

        firebaseDatabase = FirebaseDatabase.getInstance();
        commentRef = firebaseDatabase.getReference("comment");


        Intent intent = getIntent();
        String key = intent.getStringExtra("KEY");
        String TITLE = intent.getStringExtra("postTitle");


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
                            likesCount = Integer.parseInt(document.getData().get("likesCount").toString());
                        }
                    }
                }
            }
        });
        // user 정보(current)
        DocumentReference documentUserReference = FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        documentUserReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            username = document.getData().get("name").toString();
                        }
                    }
                }
            }
        });

        //Toast.makeText(this, TITLE, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        // comment view ( 코멘트 정보 )
        commentRef.child(TITLE).addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                CommentDTO commentDTO = dataSnapshot.getValue(CommentDTO.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                commentDTOS.add(commentDTO);

                // 여기가 문제
                adaptor.notifyDataSetChanged();
                comment_view.setSelection(commentDTOS.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 좋아요 관련
        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);

        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        button_favorite = findViewById(R.id.button_favorite);

        button_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                compoundButton.startAnimation(scaleAnimation);

                likesCount += 1;
                PostInfo postInfo = new PostInfo(post_title.getText().toString(), post_content.getText().toString(), book_genre.getText().toString(),
                        book_style.getText().toString(), user.getEmail(), String.valueOf(likesCount));

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document(TITLE)
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
        });

    }




    // comment 달기
    public void clickSend(View view) {

        // firebase DB에 저장할 값들( 닉네임, 메세지, 시간 )
        String message= comment_edit.getText().toString();

        // 공백의 메시지 보내지 못하게
        if(message.equals("")){
            Toast.makeText(this,"댓글을 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        //firebase DB에 저장할 값(MessageItem객체) 설정
        CommentDTO comment = new CommentDTO(username, comment_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
        commentRef.child(post_title.getText().toString()).push().setValue(comment); // 데이터 푸쉬
        comment_edit.setText("");

        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

}