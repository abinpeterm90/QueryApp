package com.teksine.queryapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.teksine.queryapplication.*;
import com.teksine.queryapplication.model.User;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    SharedPreferencesManager msharedManger=SharedPreferencesManager.getSharedPreferanceManager();
     Context mContext;
    private static final int RC_SIGN_IN = 9001;
    private static final String loggerTag = "Login Activity::";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=getApplicationContext();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("sucess:", account.getEmail());
            User user=validateAndSaveUserInfo(account);
            msharedManger.setUserInformation(mContext,user);
            Toast.makeText(getApplicationContext(), "successful" + account.getEmail(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("googleId", account.getEmail());
            startActivity(intent);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("eroor:", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private User validateAndSaveUserInfo(GoogleSignInAccount account) {
        User user = new User();
        if (account.getEmail() != null) {
            user.setEmail(account.getEmail());
        }
        if (account.getGivenName() != null) {

            user.setFirstName(account.getGivenName());
        }
        if (account.getFamilyName() != null) {

            user.setLastName(account.getFamilyName());
        }
        if (account.getPhotoUrl() != null) {

            user.setPhotUrl(account.getPhotoUrl().toString());
        }
        if (account.getId() != null) {

            user.setGoogleId(account.getId());
        }
        return user;
    }
}
