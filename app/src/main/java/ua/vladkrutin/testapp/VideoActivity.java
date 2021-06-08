package ua.vladkrutin.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    String videosInfo;
    String videoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_video_share);

        videosInfo = getIntent().getStringExtra("videosInfo");
        videoLink = getIntent().getStringExtra("videoLink");

        TextView textViewTop = (TextView) findViewById(R.id.textView);
        TextView linkTextView = (TextView) findViewById(R.id.linkTextView);

        textViewTop.setText(videosInfo);
        linkTextView.setText(videoLink);

        Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = videosInfo + " " + videoLink;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SHARE");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }
}
