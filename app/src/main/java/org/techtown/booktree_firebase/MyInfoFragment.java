package org.techtown.booktree_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MyInfoFragment extends Fragment {
    TextView nameTextView;
    TextView nameTextView2;
    FirebaseAuth user_;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "fragmentMyInfo";


    public MyInfoFragment() {
        // Required empty public constructor
    }



    public static MyInfoFragment newInstance(String param1, String param2) {
        MyInfoFragment fragment = new MyInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);


        view.findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.myTree).setOnClickListener(onClickListener);


        nameTextView = view.findViewById(R.id.nameText);
        final TextView phoneNumberTextView = view.findViewById(R.id.phoneNumberText);
        final TextView genderTextView = view.findViewById(R.id.genderText);
        final TextView emailTextView = view.findViewById(R.id.emailText);
        final TextView birthDayTextView = view.findViewById(R.id.birthdayText);
        nameTextView2 = view.findViewById(R.id.nameText1);

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.e(TAG, "DocumentSnapshot data: " + document.getData());

                            nameTextView.setText(document.getData().get("name").toString());
                            phoneNumberTextView.setText(document.getData().get("phoneNumber").toString());
                            genderTextView.setText(document.getData().get("gender").toString());
                            emailTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            birthDayTextView.setText(document.getData().get("birthday").toString());
                            nameTextView2.setText(document.getData().get("name").toString());
                        } else {
                            Log.e(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    startMainActivity();
                    break;

                case R.id.myTree:
                    startMyTreeActivity();
                    break;

                case R.id.userDeleteButton:
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error deleting document", e);
                                }
                            });
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    FirebaseAuth.getInstance().signOut();

                    startMainActivity();
                    break;
            }
        }
    };

    private  void startMainActivity(){
        Intent intent=new Intent(getContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private  void startMyTreeActivity(){
        Intent intent=new Intent(getContext(), MyTreeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("useremail",user.getEmail());
//        Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}