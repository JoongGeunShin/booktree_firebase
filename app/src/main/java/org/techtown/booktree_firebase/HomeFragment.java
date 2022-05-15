package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private FirebaseAuth mAuth;
    private static final String Tag = "MainActivity";

    SwipeRefreshLayout swipeRefreshLayout;
    private static ViewGroup viewGroup;

    //  RecyclerView 생성
    private RecyclerView mRecyclerView;
    private PostAdaptor postAdaptor;
    final private ArrayList<PostItem> postItems = new ArrayList<>();
    private AppCompatButton btn_add;
    private AppCompatButton btn_ranking;
    private AppCompatButton btn_search;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        //swipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 글쓰기 버튼
        btn_add = (AppCompatButton) rootView.findViewById(R.id.btn_add_post);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), AddPostItem.class));
            }
        });

        // 랭킹 버튼
        btn_ranking = (AppCompatButton) rootView.findViewById(R.id.btn_ranking);
        btn_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    // 수정점, StateSelectActivity, StateSelectAdaptro 바꿔야됨
                getActivity().startActivityForResult(new Intent(getActivity(), RankingActivity.class),0);
            }
        });
        // 검색 버튼
        btn_ranking = (AppCompatButton) rootView.findViewById(R.id.btn_search);
        btn_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getActivity(), SearchActivity.class),0);
            }
        });


        // RecyclerView 생성
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<PostInfo> postInfo = new ArrayList<>();


                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String hour = document.getData().get("closeTime_hour").toString();
                                String minute = document.getData().get("closeTime_minute").toString();
                                String time = hour + minute;

                                postInfo.add(new PostInfo(
                                        document.getData().get("postTitle").toString(),
                                        document.getData().get("postContent").toString(),
                                        document.getData().get("meetingArea").toString(),
                                        document.getData().get("closeTime_hour").toString(),
                                        //document.getData().get("closeTime_minute").toString(),
                                        time,
                                        document.getData().get("maxPerson").toString(),
                                        document.getData().get("userId").toString()));
                                //Log.d("closeTime 확인", document.getData().get("closeTime").toString());
                            }
                            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclePostList);
                            postAdaptor = new PostAdaptor(getActivity(), postInfo);
                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            postAdaptor.setPostlist(postInfo);
                            mRecyclerView.setAdapter(postAdaptor);
                        }else{
                            Log.e("Error", "task Error!");
                        }
                    }
                });

        return rootView;
    }

    @Override
    public void onRefresh() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("posts")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    ArrayList<PostInfo> postInfo = new ArrayList<>();


                                    for(QueryDocumentSnapshot document : task.getResult()) {
                                        String hour = document.getData().get("closeTime_hour").toString();
                                        String minute = document.getData().get("closeTime_minute").toString();
                                        String time = hour + minute;

                                        postInfo.add(new PostInfo(
                                                document.getData().get("postTitle").toString(),
                                                document.getData().get("postContent").toString(),
                                                document.getData().get("meetingArea").toString(),
                                                document.getData().get("closeTime_hour").toString(),
                                                //document.getData().get("closeTime_minute").toString(),
                                                time,
                                                document.getData().get("maxPerson").toString(),
                                                document.getData().get("userId").toString()));
                                    }
                                    mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.RecyclePostList);
                                    postAdaptor = new PostAdaptor(getActivity(), postInfo);
                                    mRecyclerView.setHasFixedSize(true);
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    postAdaptor.setPostlist(postInfo);
                                    mRecyclerView.setAdapter(postAdaptor);
                                }else{
                                    Log.e("Error", "task Error!");
                                }
                            }
                        });
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    // 로그인으로 이동
    private  void startLoginActivity(){
        Intent intent=new Intent(getContext(),loginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    // 회원정보로 이동
    private  void startmemberInitActivity(){
        Intent intent=new Intent(getContext(),memberInitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
