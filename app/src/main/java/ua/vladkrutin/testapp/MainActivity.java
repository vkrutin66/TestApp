package ua.vladkrutin.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ArrayList<VideoButton> videoButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });




        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToScreen2();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




        signOut();
    }

    private void setButtons() {
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.button_container);
        for (int i = 0; i < videoButtons.size(); i ++) {

            Button newButton = new Button(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 20, 0, 20);
            params.gravity = Gravity.CENTER;

            final String nameScreen3 = videoButtons.get(i).getName();
            final String linkScreen3 = videoButtons.get(i).getLink();

            newButton.setText(nameScreen3);

            mainLayout.addView(newButton, params);

            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToScreen3(nameScreen3, linkScreen3);
                }
            });
        }
    }



    // метод для получения списка видео
    private void getVideoList() {
        videoButtons = new ArrayList<>();
        videoButtons.add(new VideoButton("eKreative Egypt Team Weekend - 2021", "https://www.youtube.com/embed/U9XOr9fNdlg"));
        videoButtons.add(new VideoButton("Lektorium Graduation 2020", "https://www.youtube.com/embed/F_ZwQxiaOFk"));
        videoButtons.add(new VideoButton("Victor’s Birthday", "https://www.youtube.com/embed/eVt3se6cy3Y"));
        videoButtons.add(new VideoButton("eKonf 2019", "https://www.youtube.com/embed/0tMgdX1bhDo"));
        videoButtons.add(new VideoButton("LektoriumGraduation2019", "https://www.youtube.com/embed/c40Z04Ct9zk"));
        for (int i = 0; i < 15; i ++) {
            videoButtons.add(new VideoButton());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    private void signOut() {
        mGoogleSignInClient.signOut();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MainActivity", "onActivityResult");
        if (requestCode == 1) {
            Log.i("MainActivity", "requestCode");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }




    private void handleSignInResult(GoogleSignInResult result) {
        Log.i("MainActivity", "handle sign in result");
        if (result.isSuccess()) {
            goToScreen2();
        } else {
            Log.i("MainActivity", "sign is failed");
        }
    }


    private void goToScreen2() {
        Log.i("MainActivity", "change screen");
        setContentView(R.layout.screen2);
        getVideoList();
        setButtons();
    }

    private void goToScreen3(String videosInfo, String videoLink) {
        setContentView(R.layout.screen3);

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(videosInfo);
        TextView textLink = (TextView) findViewById(R.id.linkTextView);
        textLink.setText(videoLink);
    }

}