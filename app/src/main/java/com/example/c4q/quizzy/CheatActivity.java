package com.example.c4q.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by huilin on 10/3/16.
 */
public class CheatActivity extends QuizActivity {

    private Question currentQuestion;
    private Question[] copyofQuestions;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.cheat_button);
        button.setVisibility(View.GONE);
        copyofQuestions = super.questionBank;

        TextView questionTV = (TextView) findViewById(R.id.question_text_view);
        Intent intent = getIntent();

//        String myQuestion = intent.getExtras().getString("CURRENT QUESTION");
//        boolean myAnswer = intent.getExtras().getBoolean("CURRENT ANSWER");
//        questionTV.setText(myQuestion + " : " + myAnswer);

//        int myIndex = intent.getExtras().getInt("CURRENT QUESTION");
//        currentQuestion = copyofQuestions[myIndex];
//        boolean myAnswer = currentQuestion.isAnswerTrue();
//        questionTV.setText(currentQuestion.getTextResId());
//        questionTV.append(" " + currentQuestion.isAnswerTrue());
    }
}
