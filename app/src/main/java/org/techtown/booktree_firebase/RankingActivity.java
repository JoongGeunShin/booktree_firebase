package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    Button PostRanking1, PostRanking2, PostRanking3;
    Button UserRanking1, UserRanking2, UserRanking3;

    private PostAdaptor postAdaptor;

    ArrayList<PostInfo> postInfo = new ArrayList<>();

    private String tempTitle[];
    private String tempLikes[];

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        PostRanking1 = (Button) findViewById(R.id.PostRanking1);
        PostRanking2 = (Button) findViewById(R.id.PostRanking2);
        PostRanking3 = (Button) findViewById(R.id.PostRanking3);

        UserRanking1 = (Button) findViewById(R.id.UserRanking1);
        UserRanking2 = (Button) findViewById(R.id.UserRanking2);
        UserRanking3 = (Button) findViewById(R.id.UserRanking3);

        CalculatePostRank();

        PostRanking1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempTitle = new String[postInfo.size()];
                tempLikes = new String[postInfo.size()];

                for(int i = 0; i < postInfo.size() ; i++){
                    tempTitle[i] = postInfo.get(i).getPostTitle();
                    tempLikes[i] = postInfo.get(i).getLikesCount();
                }
                // likes 배열도 만들어서 해보면 될듯
                for(int i = 0;i < postInfo.size(); i++) {
                    for(int j = i+1 ; j < postInfo.size() ; j++){

                        if(Integer.parseInt(tempLikes[i]) < Integer.parseInt(tempLikes[j])){
                            String temp_title = tempTitle[i];
                            tempTitle[i] = tempTitle[j];
                            tempTitle[j] = temp_title;
                            String temp_likes = tempLikes[i];
                            tempLikes[i] = tempLikes[j];
                            tempLikes[j] = temp_likes;
                        }
                    }
                }

                Intent intent = new Intent(RankingActivity.this,PostDetailActivity.class);
                intent.putExtra("KEY",tempTitle[0]);
                intent.putExtra("postTitle",tempTitle[0]);
//                Toast.makeText(RankingActivity.this, tempTitle[0], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        PostRanking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempTitle = new String[postInfo.size()];
                tempLikes = new String[postInfo.size()];

                for(int i = 0; i < postInfo.size() ; i++){
                    tempTitle[i] = postInfo.get(i).getPostTitle();
                    tempLikes[i] = postInfo.get(i).getLikesCount();
                }
                // likes 배열도 만들어서 해보면 될듯
                for(int i = 0;i < postInfo.size(); i++) {
                    for(int j = i+1 ; j < postInfo.size() ; j++){

                        if(Integer.parseInt(tempLikes[i]) < Integer.parseInt(tempLikes[j])){
                            String temp_title = tempTitle[i];
                            tempTitle[i] = tempTitle[j];
                            tempTitle[j] = temp_title;
                            String temp_likes = tempLikes[i];
                            tempLikes[i] = tempLikes[j];
                            tempLikes[j] = temp_likes;
                        }
                    }
                }

                Intent intent = new Intent(RankingActivity.this,PostDetailActivity.class);
                intent.putExtra("KEY",tempTitle[1]);
                intent.putExtra("postTitle",tempTitle[1]);
//                Toast.makeText(RankingActivity.this, tempTitle[0], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        PostRanking3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempTitle = new String[postInfo.size()];
                tempLikes = new String[postInfo.size()];

                for(int i = 0; i < postInfo.size() ; i++){
                    tempTitle[i] = postInfo.get(i).getPostTitle();
                    tempLikes[i] = postInfo.get(i).getLikesCount();
                }
                // likes 배열도 만들어서 해보면 될듯
                for(int i = 0;i < postInfo.size(); i++) {
                    for(int j = i+1 ; j < postInfo.size() ; j++){

                        if(Integer.parseInt(tempLikes[i]) < Integer.parseInt(tempLikes[j])){
                            String temp_title = tempTitle[i];
                            tempTitle[i] = tempTitle[j];
                            tempTitle[j] = temp_title;
                            String temp_likes = tempLikes[i];
                            tempLikes[i] = tempLikes[j];
                            tempLikes[j] = temp_likes;
                        }
                    }
                }

                Intent intent = new Intent(RankingActivity.this,PostDetailActivity.class);
                intent.putExtra("KEY",tempTitle[2]);
                intent.putExtra("postTitle",tempTitle[2]);
//                Toast.makeText(RankingActivity.this, tempTitle[2], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        UserRanking1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MyTreeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("useremail","shinjg97@gmail.com");
//        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        UserRanking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MyTreeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("useremail","test@test.test");
//        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        UserRanking3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MyTreeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("useremail","test1@test1.test1");
//        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });



    }

    private void CalculatePostRank() {
        db.collection("posts")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    postInfo = new ArrayList<>();


                    for(QueryDocumentSnapshot document : task.getResult()) {
                        String book_genre = document.getData().get("book_genre").toString();
                        String book_style = document.getData().get("book_style").toString();

                        postInfo.add(new PostInfo(
                                document.getData().get("postTitle").toString(),
                                document.getData().get("postContent").toString(),
                                book_genre,
                                book_style,
                                document.getData().get("userId").toString(),
                                (document.getData().get("likesCount").toString())));
                    }
                    postAdaptor = new PostAdaptor(getApplicationContext(), postInfo);
                }else{
                    Log.e("Error", "task Error!");
                }
            }
        });
    }

}
