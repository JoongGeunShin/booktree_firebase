package org.techtown.booktree_firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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
    }

    private void CalculatePostRank() {
        String FirstTitle = null, SecondTitle = null, ThirdTitle = null, tempTitle;
        int FirstLikes = 3, SecondLikes = 2, ThirdLikes = 1, tempLikes;

        db.collection("posts")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<PostInfo> postInfo = new ArrayList<>();


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
                    // 여기서 순위 정하기
//                    if(tempLikes>ThirdLikes){
//                        ThirdLikes = tempLikes;
//                    }

                }else{
                    Log.e("Error", "task Error!");
                }
            }
        });

    }
}
