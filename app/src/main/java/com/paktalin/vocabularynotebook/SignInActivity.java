package com.paktalin.vocabularynotebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "VN/" + SignInActivity.class.getSimpleName();

    private EditText mEmailEt, mPasswordEt;
    private Button mSignInBtn, mSignUpBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeViews();
        mAuth = FirebaseAuth.getInstance();
        mSignInBtn.setOnClickListener(signInClickListener);
        mSignUpBtn.setOnClickListener(signUpClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) startUserActivity();
    }

    private void initializeViews() {
        mEmailEt = findViewById(R.id.email);
        mPasswordEt = findViewById(R.id.password);
        mSignInBtn = findViewById(R.id.btn_sign_in);
        mSignUpBtn = findViewById(R.id.btn_sign_up);
    }

    private View.OnClickListener signInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            signIn();
        }
    };

    private View.OnClickListener signUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signUp();
        }
    };

    private void signIn() {
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        if(fieldsNotEmpty(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                startUserActivity();
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void signUp() {
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        if(fieldsNotEmpty(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                startUserActivity();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private void startUserActivity() {
        Intent userActivityIntent = new Intent(SignInActivity.this, UserActivity.class);
        startActivity(userActivityIntent);
    }

    private boolean fieldsNotEmpty(String email, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(SignInActivity.this, "Please, enter email and password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
