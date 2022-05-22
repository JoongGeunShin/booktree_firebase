package org.techtown.booktree_firebase;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner typeSpinner = (Spinner)findViewById(R.id.spinner_search_type);
        ArrayAdapter typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.search_type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        String search_type = typeSpinner.getSelectedItem().toString();

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.searchListView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        Toast.makeText(this, search_type, Toast.LENGTH_SHORT).show();
        // 검색에 사용할 데이터을 미리 저장한다.
        if(search_type.equals("게시글")){
            search_type = null;
            list.clear();
            settingPostList();
        }else if(search_type.equals("사용자")){
            search_type = null;
            list.clear();
            settingUserList();
        }else
            list.clear();



        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingPostList(){
        list.add("shin");
        list.add("joong");
        list.add("kelly");
        list.add("john");
        list.add("brown");
        list.add("purple");
        list.add("nasty");
        list.add("blanket");
        list.add("banana");
        list.add("apple");
        list.add("watermelon");
        list.add("sweet");
        list.add("my");
        list.add("name");
        list.add("is");
        list.add("hello");
        list.add("new");
        list.add("world");
        list.add("what");
        list.add("me");
        list.add("you");
        list.add("annoy");
        list.add("please");
        list.add("help");
        list.add("john");
        list.add("clarkson");
        list.add("smith");
    }
    private void settingUserList(){
        list.add("user1");
        list.add("user2");
        list.add("user3");
        list.add("user4");
        list.add("user5");
        list.add("user6");
        list.add("user8");
        list.add("user0");
        list.add("user12");
        list.add("user14");
        list.add("user122");
        list.add("user32");
        list.add("user44");
        list.add("user52");
    }
}
