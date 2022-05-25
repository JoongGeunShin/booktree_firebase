package org.techtown.booktree_firebase;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    // 검색어를 입력창
    private EditText editSearch;

    // view 띄우기
    private RecyclerView mRecyclerView;

    // 게시글
    private PostAdaptor postAdaptor;
    private ArrayList<PostInfo> postInfo = new ArrayList<>();
    // 게시글 데이터를 넣을 리스트
    private ArrayList<PostInfo> postInfoInput = new ArrayList<>();

    // 사용자
    private UserAdaptor userAdaptor;
    private ArrayList<UserInfo> userInfo = new ArrayList<>();
    // 사용자 데이터 넣을 리스트
    private ArrayList<UserInfo> userInfoInput = new ArrayList<>();



    // 파베 연동
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // spinner 변경시 리스트 변경위해
    String[] search_types = {"게시글","사용자"};
    int option = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // xml 리스너 설정
        Spinner typeSpinner = (Spinner)findViewById(R.id.spinner_search_type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,search_types
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //search_type = typeSpinner.getSelectedItem().toString();
        editSearch = (EditText) findViewById(R.id.editSearch);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecycleSearchPostList);

        // 검색에 사용할 데이터 전부 가져오기

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            // input창에 문자를 입력할때마다 호출된다.
            @Override
            public void afterTextChanged(Editable editable) {
                if(option == 0) {
                    String text = editSearch.getText().toString();
                    search_post(text);
                }else if(option == 1){
                    String text = editSearch.getText().toString();
                    search_user(text);
                }
            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 게시글 검색시
                if(search_types[i].equals("게시글")){
                   settingPostList();
                   option = 0;
                }
                // 사용자 검색시
                else if(search_types[i].equals("사용자")){
                    settingUserList();
                    option = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    // 검색을 수행하는 메소드
    public void search_post(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        postInfoInput.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            settingPostList();
        }
        // 문자 입력 발생
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < postInfo.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (postInfo.get(i).getPostTitle().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    postInfoInput.add(postInfo.get(i));
                    Log.i("postinfo", postInfo.get(i).getPostTitle());
                    //postAdaptor = new PostAdaptor(getApplicationContext(), postInfo);
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        postAdaptor = new PostAdaptor(getApplicationContext(), postInfoInput);
        mRecyclerView.setAdapter(postAdaptor);
        postAdaptor.notifyDataSetChanged();

    }

    // 초기 게시글 리스트
    private void settingPostList(){
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mRecyclerView.setAdapter(postAdaptor);
                        }else{
                            Log.e("Error", "task Error!");
                        }

                    }
                });

    }

    // 여기서부터 사용자
    private void settingUserList(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            userInfo = new ArrayList<>();

                            for(QueryDocumentSnapshot document : task.getResult()) {
                                userInfo.add(new UserInfo(
                                        document.getData().get("name").toString(),
                                        (document.getData().get("userEmail").toString())));
                            }
                            userAdaptor = new UserAdaptor(getApplicationContext(), userInfo);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mRecyclerView.setAdapter(userAdaptor);
                        }else{
                            Log.e("Error", "task Error!");
                        }

                    }
                });

    }
    // 검색을 수행하는 메소드
    public void search_user(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        userInfoInput.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            settingUserList();
        }
        // 문자 입력 발생
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < userInfo.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (userInfo.get(i).getUserName().toLowerCase().contains(charText))
                {
                    userInfoInput.add(userInfo.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        userAdaptor = new UserAdaptor(getApplicationContext(), userInfoInput);
        mRecyclerView.setAdapter(userAdaptor);
        userAdaptor.notifyDataSetChanged();

    }

}
