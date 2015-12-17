package com.example.cameron.wordsmith;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    GridView gridView;
    TextView timerText;
    private static final String FORMAT = "%2d:%02d";

    int seconds, minutes;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = (TextView) findViewById(R.id.timer);

        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(""+String.format(FORMAT,

                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timerText.setText("Game Over!");
            }
        }.start();

//        Letterset buttons logic
        gridView = (GridView) findViewById(R.id.lettersGrid);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.letter_text_view_item, generateLetterset.main());

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                TextView wordConstructor = (TextView) findViewById(R.id.wordConstructor);
                wordConstructor.setText(wordConstructor.getText().toString() + ((TextView) v).getText());

                ((TextView) v).setClickable(true);
                ((TextView) v).setTextColor(Color.parseColor("#FF0000"));


            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void Back(View v) {

        TextView wordConstructor = (TextView) findViewById(R.id.wordConstructor);
        String word = wordConstructor.getText().toString();
        char lastLetter = word.charAt(word.length());
        wordConstructor.setText(word.substring(0, wordConstructor.length()-1));

        gridView = (GridView) findViewById(R.id.lettersGrid);

        for (int i = 0; i < gridView.getChildCount(); i++) {
            TextView letter = (TextView) gridView.getChildAt(i);
            if (lastLetter = letter.getText())
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.cameron.wordsmith/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.cameron.wordsmith/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
