package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private static final String Tag = "MainActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //  RecyclerView 생성
    private RecyclerView mRecyclerView;
    private PostAdaptor postAdaptor;
    private ArrayList<PostItem> postItems;
    private AppCompatButton btn_add;
    private AppCompatButton btn_ranking;
    private AppCompatButton btn_search;


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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclePostList);
        postAdaptor = new PostAdaptor(getActivity(), postItems);
        mRecyclerView.setAdapter(postAdaptor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postItems = new ArrayList<>();

        // Sample Data
        for(int i = 0; i<30; i++){
            postItems.add(new PostItem("책 제목을 찾습니다.", i));
        }
        postAdaptor.setPostlist(postItems);

        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            String state = data.getStringExtra("STATE");
            Log.i("StateSelectResult",state);
        }
    }
}
