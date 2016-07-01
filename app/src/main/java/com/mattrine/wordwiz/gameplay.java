package com.mattrine.wordwiz;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class gameplay extends AppCompatActivity {

    ArrayList<String> words = new ArrayList<String>();
    String word;
    String displayed_word;
    int tries_left = 0;
    int words_guessed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        try{
            loadWords();
        }catch (IOException e){

        }
        onNewWord();
        //Toast.makeText(getApplicationContext(), word, Toast.LENGTH_SHORT).show();
    }

    private void onNewWord() {
        word = getWord();
        tries_left = word.length()*2;
        update_stats();
        displayed_word = "";
        for (int i=1;i<word.length();i++){
            displayed_word += "_ ";
        }
        displayed_word += "_";
        setWord(displayed_word);


    }

    private void setWord(String text) {
        TextView word_textview = (TextView)findViewById(R.id.word);
        word_textview.setText(text);
    }



    private void loadWords() throws IOException{
        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.dictionary);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } finally {
            reader.close();
        }
    }

    public void update_stats(){

        TextView tries_left_text = (TextView)findViewById(R.id.tries_left_number);
        TextView words_guessed_text = (TextView)findViewById(R.id.words_guessed_number);

        String java_appeasment1 = "" + tries_left;
        String java_appeasment2 = "" + words_guessed;

        tries_left_text.setText(java_appeasment1);
        words_guessed_text.setText(java_appeasment2);

    }

    public void onGuess(View view) {
        tries_left--;
        EditText editText = (EditText)findViewById(R.id.guess);
        String guess = editText.getText().toString();
        guess = guess.toLowerCase();
        if (guess.length() != word.length()){
            Toast.makeText(getApplicationContext(), "Guess must be " + word.length() + " letters long.", Toast.LENGTH_LONG).show();
        }
        String new_displayed_word = "";
        for (int i=0; i<word.length();i++) {
            if (guess.charAt(i) == word.charAt(i)){
                new_displayed_word += guess.charAt(i) + " ";
            } else {
                new_displayed_word += displayed_word.charAt(i*2) + " ";
            }
        }
        displayed_word = new_displayed_word.trim();
        setWord(displayed_word);
        if (!displayed_word.contains("_")) {
            words_guessed++;
            update_stats();
            onNewWord();
        } else if (tries_left == 0) {
            onNewWord();
        } else {
            update_stats();
        }

    }

    private String getWord(){
        int n = (int)(Math.random()*words.size());
        return words.get(n);
    }

}
