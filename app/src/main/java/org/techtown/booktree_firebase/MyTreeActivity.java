package org.techtown.booktree_firebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyTreeActivity extends AppCompatActivity {
    private static final String TAG = "MultiImageActivity";
// 이미지를 갤러리에서 가져오게하고(사진찍지) 그 이미지를 firebase에 저장하여 불러오기 시도중
    ImageView ownerment1, ownerment2, ownerment3, ownerment4, ownerment5, ownerment6,
            ownerment7, ownerment8, ownerment9;
    int flag;

    //Uri imageuri;
    private Uri imgUri, photoURI, albumURI;
    String downloadUrl;

    private String mCurrentPhotoPath;

    private static final int FROM_CAMERA = 0;

    private static final int FROM_ALBUM = 1;

    int clickedImageNum = 0;

    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    final String cu = user.getUid();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    // 스토리지를 통한 이미지 저장 불러오기를 위해 선언
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytree);

        ownerment1 = (ImageView)findViewById(R.id.ownerment1);
        ownerment2 = (ImageView)findViewById(R.id.ownerment2);
        ownerment3 = (ImageView)findViewById(R.id.ownerment3);
        ownerment4 = (ImageView)findViewById(R.id.ownerment4);
        ownerment5 = (ImageView)findViewById(R.id.ownerment5);
        ownerment6 = (ImageView)findViewById(R.id.ownerment6);
        ownerment7 = (ImageView)findViewById(R.id.ownerment7);
        ownerment8 = (ImageView)findViewById(R.id.ownerment8);
        ownerment9 = (ImageView)findViewById(R.id.ownerment9);

        findViewById(R.id.ownerment1).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment2).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment3).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment4).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment5).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment6).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment7).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment8).setOnClickListener(onClickListener);
        findViewById(R.id.ownerment9).setOnClickListener(onClickListener);

        setPermission();
        initTree();
    }

    View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ownerment1:
                    clickedImageNum = 1;
                    Toast.makeText(getApplicationContext(), clickedImageNum + "번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    // 갤러리
                    break;
                case R.id.ownerment2:
                    clickedImageNum = 2;
                    Toast.makeText(getApplicationContext(), "2번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment3:
                    clickedImageNum = 3;
                    Toast.makeText(getApplicationContext(), "3번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment4:
                    clickedImageNum = 4;
                    Toast.makeText(getApplicationContext(), "4번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment5:
                    clickedImageNum = 5;
                    Toast.makeText(getApplicationContext(), "5번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment6:
                    clickedImageNum = 6;
                    Toast.makeText(getApplicationContext(), "6번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment7:
                    clickedImageNum = 7;
                    Toast.makeText(getApplicationContext(), "7번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment8:
                    clickedImageNum = 8;
                    Toast.makeText(getApplicationContext(), "8번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
                case R.id.ownerment9:
                    clickedImageNum = 9;
                    Toast.makeText(getApplicationContext(), "9번째 이미지 창", Toast.LENGTH_SHORT).show();
                    makeDialog();
                    break;
            }
        }
    };

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MyTreeActivity.this);
        alt_bld.setTitle("사진 업로드").setCancelable(false)
                .setPositiveButton("사진촬영",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 사진 촬영
                        flag = 0;
                        takePhoto();
                    }
                })
                .setNeutralButton("앨범선택",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // 앨범 선택
                        flag = 1;
                        selectAlbum();
                    }
                })
                .setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public void takePhoto(){
        // 촬영 후 이미지 가져옴
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(photoFile!=null){
                    Uri providerURI = FileProvider.getUriForFile(this,getPackageName(),photoFile);
                    imgUri = providerURI;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
            File photoFile = null;
            Uri providerURI = FileProvider.getUriForFile(this,getPackageName(),photoFile);
            imgUri = providerURI;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
            startActivityForResult(intent, FROM_CAMERA);
        }else{
            Log.v("알림", "저장공간에 접근 불가능");
            return;
        }
    }
    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile = null;

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        Log.v("디렉토리확인", Environment.getExternalStorageDirectory().toString());
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        //Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    //앨범 선택 클릭
    public void selectAlbum(){
        //앨범에서 이미지 가져옴
        //앨범 열기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");

        startActivityForResult(intent, FROM_ALBUM);
    }


    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        makeConfirmDialog();
        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case FROM_ALBUM : {
                //앨범에서 가져오기
                if(data.getData()!=null){
                    try{
                        File albumFile = null;
                        albumFile = createImageFile();
                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);
                        galleryAddPic();
                        switch (clickedImageNum){
                            case 1 : ownerment1.setImageURI(photoURI); break;
                            case 2 : ownerment2.setImageURI(photoURI); break;
                            case 3 : ownerment3.setImageURI(photoURI); break;
                            case 4 : ownerment4.setImageURI(photoURI); break;
                            case 5 : ownerment5.setImageURI(photoURI); break;
                            case 6 : ownerment6.setImageURI(photoURI); break;
                            case 7 : ownerment7.setImageURI(photoURI); break;
                            case 8 : ownerment8.setImageURI(photoURI); break;
                            case 9 : ownerment9.setImageURI(photoURI); break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            }
            case FROM_CAMERA : {
                //카메라 촬영
                try{
                    galleryAddPic();
                    ownerment1.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // firestore 저장
    public void makeConfirmDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MyTreeActivity.this);
        alt_bld.setTitle("이미지를 화면에 나타냈습니다.").setMessage("이미지 데이터를 저장하시겠습니까?").setCancelable(
                false)
                .setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //DB에 등록
                        //1. 사진을 storage에 저장하고 그 url을 알아내야함..
                        String filename = " user_" + cu + "_"  + clickedImageNum + "'st image";
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://booktree-d3cc4.appspot.com/").child("WriteClassImage/" + cu + "/" + filename );
                        UploadTask uploadTask;
                        Uri file = null;
                        if(flag ==0){
                            //사진촬영
                            file = Uri.fromFile(new File(mCurrentPhotoPath));
                        }else if(flag==1){
                            //앨범선택
                            file = photoURI;
                        }
                        uploadTask = storageRef.putFile(file);
                        final ProgressDialog progressDialog = new ProgressDialog(MyTreeActivity.this);
                        progressDialog.setMessage("업로드중...");
                        progressDialog.show();
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Log.v("알림", "사진 업로드 실패");
                                exception.printStackTrace();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String photoStringLink = uri.toString();
                                        downloadUrl = photoStringLink;
                                    }
                                });
                                Log.v("알림", "사진 업로드 성공 " + downloadUrl);
                            }
                        });
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 아니오 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }

    // 카메라 권한 설정 (tedpermssion library 사용)
    private void setPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MyTreeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MyTreeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("Need Permission to use camera")
                .setDeniedMessage("[settings] > [permission] 을 통하여 권한을 허가해 주세요")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                .check();

    }

    // 초기 저장된 데이터 있으면 가져오기
    private void initTree(){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://booktree-d3cc4.appspot.com/").child("WriteClassImage/" + cu + "user's/");
        if(storageRef == null){
            Toast.makeText(MyTreeActivity.this, "사용자의 저장된 이미지가 없어 빈 트리를 불러옵니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MyTreeActivity.this, "사용자의 트리를 불러옵니다.", Toast.LENGTH_SHORT).show();
            for(int i = 1; i<10 ; i++){
                if(storageRef.getPath().startsWith(cu + "user's/" + i)){
                    clickedImageNum = i;
                    switch (clickedImageNum){
                        case 1 : ownerment1.setImageURI(photoURI); break;
                        case 2 : ownerment2.setImageURI(photoURI); break;
                        case 3 : ownerment3.setImageURI(photoURI); break;
                        case 4 : ownerment4.setImageURI(photoURI); break;
                        case 5 : ownerment5.setImageURI(photoURI); break;
                        case 6 : ownerment6.setImageURI(photoURI); break;
                        case 7 : ownerment7.setImageURI(photoURI); break;
                        case 8 : ownerment8.setImageURI(photoURI); break;
                        case 9 : ownerment9.setImageURI(photoURI); break;
                    }
                }
            }
        }
    }
}


