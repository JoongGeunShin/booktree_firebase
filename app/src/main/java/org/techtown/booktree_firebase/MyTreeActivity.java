package org.techtown.booktree_firebase;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTreeActivity extends AppCompatActivity {
    private static final String TAG = "MultiImageActivity";
// 이미지를 갤러리에서 가져오게하고(사진찍지) 그 이미지를 firebase에 저장하여 불러오기 시도중
    ImageButton ownerment1, ownerment2, ownerment3, ownerment4, ownerment5, ownerment6,
            ownerment7, ownerment8, ownerment9;

    int clickedImageNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytree);

        findViewById(R.id.ownerment1).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment2).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment3).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment4).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment5).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment6).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment7).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment8).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment9).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ownerment1:
                    Toast.makeText(getApplicationContext(), "1번째 이미지 창", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityIfNeeded(intent, 2222);
                    break;
                case R.id.ownerment2:
                    Toast.makeText(getApplicationContext(), "2번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment3:
                    Toast.makeText(getApplicationContext(), "3번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment4:
                    Toast.makeText(getApplicationContext(), "4번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment5:
                    Toast.makeText(getApplicationContext(), "5번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment6:
                    Toast.makeText(getApplicationContext(), "6번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment7:
                    Toast.makeText(getApplicationContext(), "7번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment8:
                    Toast.makeText(getApplicationContext(), "8번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ownerment9:
                    Toast.makeText(getApplicationContext(), "9번째 이미지 창", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {   // 이미지 선택한 경우
            Uri imageUri = data.getData();
       }
    }

}
