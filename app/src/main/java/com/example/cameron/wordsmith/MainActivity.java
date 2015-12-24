package com.example.cameron.wordsmith;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.textservice.SpellCheckerSession;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity  {

    TextView timerText;
    GridView gridView;
    public ArrayAdapter<String> adapter;
    private ArrayList<String> stringList;
    private static final String FORMAT = "%2d:%02d";
    public static JSONArray LETTERSET = generateLetterset.main();

    int seconds, minutes;
    private HashMap<String, Integer> wordsScores = new HashMap<>();



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

        // Initialize the letterset board with letters. Must be
        // altered to allow multiplayer functionality.
        populate();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void Back(View v) {
        TextView wordConstructor = (TextView) findViewById(R.id.wordConstructor);
        String word = wordConstructor.getText().toString();

        // Exit function if there are no letters left to delete
        if (word.length() < 1) {
            return;
        }
        char lastLetter = word.charAt(word.length()-1);
        wordConstructor.setText(word.substring(0, wordConstructor.length() - 1));

        gridView = (GridView) findViewById(R.id.lettersGrid);

        for (int i = 0; i < gridView.getChildCount(); i++) {
            TextView letter = (TextView) gridView.getChildAt(i);
            if (lastLetter == letter.getText().charAt(0)) {
                letter.setTextColor(Color.parseColor("#0000FF"));
                letter.setClickable(false);
            }
        }
    }
    public void populate(String[] opponentLetters) {
        if (args != null) {
            // If the player is joining another player's game,
            // he will inherit that players' letter set.
            LETTERSET = opponentLetters;
        }
        stringList = new ArrayList<>(Arrays.asList(LETTERSET));
        gridView = (GridView) findViewById(R.id.lettersGrid);
        adapter = new ArrayAdapter<>(this,
                R.layout.letter_text_view_item, stringList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                TextView wordConstructor = (TextView) findViewById(R.id.wordConstructor);
                wordConstructor.setText(wordConstructor.getText().toString() + ((TextView) v).getText());

                v.setClickable(true);
                ((TextView) v).setTextColor(Color.parseColor("#FF0000"));

            }

        });
    }
    public void Shuffle(View v) {

        Collections.shuffle(stringList);
        adapter.notifyDataSetChanged();
    }
    public void score() {
        TextView scoreBox = (TextView) findViewById(R.id.scoreTotal);
        LinearLayout scoresList = (LinearLayout) findViewById(R.id.scores);
        int sum = 0;

        for (int i = 0; i < scoresList.getChildCount(); ++i ) {
            TextView score = (TextView) scoresList.getChildAt(i);
            if (score.length() > 0) {
                String scoreText = score.getText().toString();
                Integer s = Integer.parseInt(scoreText);
                sum += s;
            }
            String formattedSum = NumberFormat.getNumberInstance(Locale.US).format(sum);
            scoreBox.setText(formattedSum);
        }
    }
    public void endGame(View v) {
        TextView scoreBox = (TextView) findViewById(R.id.scoreTotal);
        String finalScore = scoreBox.getText().toString();


    }
    public boolean checkDictionary(String word) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("wordsEn.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null)
                if (mLine.equals(word.toLowerCase())) {
                return true;
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("IOEXCEPTION ERROR");
        }
        System.out.println("Word not found");
        return false;
    }
    public boolean preventDups(String word) {
        return (!wordsScores.containsKey(word));
    }
    public void Submit(View v) {
        TextView wordView = (TextView) findViewById(R.id.wordConstructor);
        String word = wordView.getText().toString();
        if (checkDictionary(word) && preventDups(word)) {
            int score = wordScore.score(word);
            wordsScores.put(word, score);
            populateWordsList();
            score();
        } else {
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        wordView.setText("");
        clearLetterBoard();
    }
    public void clearLetterBoard() {
        gridView = (GridView) findViewById(R.id.lettersGrid);

        for (int i = 0; i < gridView.getChildCount(); i++) {
            TextView letter = (TextView) gridView.getChildAt(i);
                letter.setTextColor(Color.parseColor("#0000FF"));
                letter.setClickable(false);
        }
    }
    public void populateWordsList() {
        LinearLayout wordsList = (LinearLayout) findViewById(R.id.words);
        LinearLayout scoresList = (LinearLayout) findViewById(R.id.scores);

        List<Integer> wordsScoresValues = new ArrayList<>(wordsScores.values());
        Collections.sort(wordsScoresValues);
        Collections.reverse(wordsScoresValues);

        for (int i = 0; i < wordsScoresValues.size(); ++i ) {
            TextView scoresView = (TextView) scoresList.getChildAt(i);
            TextView wordsView = (TextView) wordsList.getChildAt(i);

            int score = wordsScoresValues.get(i);
            String word = getKeyFromValue(wordsScores, score).toString();

            scoresView.setText(Integer.toString(score));
            wordsView.setText(word);
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

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}

