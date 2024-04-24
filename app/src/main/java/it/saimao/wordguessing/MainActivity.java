package it.saimao.wordguessing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord, tvScore, tvMatchPlayed;
    private EditText etYourGuess;
    private Button btGuess;
    private String[] words;
    private Set<String> guessedWords;

    private String guessWord;

    private final int MATCH_LIMIT = 10;
    private int matchPlayed, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initUi();
        initListeners();
    }

    private void initListeners() {
        btGuess.setOnClickListener(v -> {
            if (btGuess.getText().toString().startsWith("P")) {
                resetGame();
            } else {
                matchPlayed++;
                updateMatchPlayed();
                String yourGuess = etYourGuess.getText().toString();
                if (yourGuess.equals(guessWord)) {
                    // Correct and increase score
                    score++;
                    updateScore();
                }
                if (matchPlayed == MATCH_LIMIT) {
                    endGame();
                } else {
                    nextRound();
                }
            }
        });
    }

    private void updateMatchPlayed() {
        tvMatchPlayed.setText("Match Played : " + matchPlayed);
    }

    private void resetGame() {
        score = 0;
        updateScore();
        matchPlayed = 0;
        updateMatchPlayed();
        etYourGuess.setText("");
        guessedWords.clear();
        tvWord.setText(getGuessedWord());
        btGuess.setText("Guess");
    }

    private void nextRound() {
        etYourGuess.setText("");
        tvWord.setText(getGuessedWord());
    }

    private void endGame() {
        if (score > 5) {
            tvWord.setText("WINNER");
        } else {
            tvWord.setText("TRY AGAIN!");
        }
        btGuess.setText("Play Again");
    }

    private void updateScore() {
        String yourScore = score + "/" + MATCH_LIMIT;
        tvScore.setText(yourScore);
    }

    private void initData() {
        words = new String[]{"နေကောင်းလား", "ဘောလုံးကစားကြမယ်", "ဘယ်မှာနေသလဲ", "ရေသောက်ချင်တယ်", "ထမင်းစားပြီးပြီလား", "ချစ်သူရှိနေတာလား", "နင့်ရည်းစားကဘယ်သူလဲ",
        "မြန်မာနိုင်ငံ", "ကြက်သားဟင်းကြိုက်တယ်", "အသားကင်စားချင်တယ်", "မိုးရွာနေတယ်", "ကွန်ပျူတာ", "ရေကူးကြမယ်", "မင်းကဘယ်သူလဲ", "သူ့ကိုယုံလား", "အသည်းကွဲနေတယ်",
        "သူဒဏ်ရာရထားတယ်", "ဆိုင်ကယ်ဆီသွားထည့်နေတယ်"};

        guessedWords = new HashSet<>();

    }

    private void initUi() {

        tvWord = findViewById(R.id.tvWord);
        tvScore = findViewById(R.id.tvScore);
        tvMatchPlayed = findViewById(R.id.tvMatchPlayed);
        etYourGuess = findViewById(R.id.etYourGuess);
        btGuess = findViewById(R.id.btGuess);
        tvWord.setText(getGuessedWord());

    }

    private String getGuessedWord() {
        while (guessWord == null || guessedWords.contains(guessWord)) {
            guessWord = getRandomWord();
        }
        guessedWords.add(guessWord);
        List<String> characters = SyllableSegmentation.segment(guessWord);
        Collections.shuffle(characters);
        StringBuilder result = new StringBuilder();
        for (String ch : characters) {
            result.append(ch);
        }
        return result.toString();
    }

    private String getRandomWord() {
        return words[new Random().nextInt(words.length)];
    }

}