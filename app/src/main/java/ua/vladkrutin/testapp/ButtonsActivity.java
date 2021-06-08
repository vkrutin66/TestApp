package ua.vladkrutin.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class ButtonsActivity extends AppCompatActivity {

    ArrayList<VideoButton> videoButtons;

    private final String API_KEY = "AIzaSyAOwqWgNCX9l7FrE9pX0YAB3ZGN09jEZuA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_with_buttons);

        getVideoList();
        setButtons();


    }


    private void goToVideoScreen(String videosInfo, String videoLink) {
        Intent intent = new Intent(ButtonsActivity.this, VideoActivity.class);
        intent.putExtra("videosInfo", videosInfo);
        intent.putExtra("videoLink", videoLink);
        startActivity(intent);
    }


    private void setButtons() {
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.button_container);
        for (int i = 0; i < videoButtons.size(); i ++) {

            Button newButton = new Button(ButtonsActivity.this);
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
                    goToVideoScreen(nameScreen3, linkScreen3);
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
        for (int i = 0; i < 15; i++) {
            videoButtons.add(new VideoButton());
        }


        doGet();

    }

    // get запросс но он не работает с ютуб
    public void doGet() {
        String urlString = "https://youtube.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCP_IYZTiqbmUqmI3KXHIEoQ&key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlString)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i("doGet", response.toString());
                    throw new IOException("Unexpected code " + response);
                } else {
                    Log.i("doGet", "OK");
                    Log.i("doGet", response.toString());
                }
            }
        });
    }
}
