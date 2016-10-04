package com.example.c4q.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CHEAT_REQUEST = 111;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;
//    private TextView quizTakerName;
    private TextView questionTextView;

    public Question[] questionBank;
    private int currentIndex = 0;
    private int score;

    private static Map<String, Integer> userScore = new HashMap<>();

    public void initializeQuestions() {
        questionBank = new Question[]{
                new Question(R.string.question_static, false),
                new Question(R.string.question_private_class, true),
                new Question(R.string.question_method, false),
                new Question(R.string.question_method_2, false),
                new Question(R.string.question_void, true),
                new Question(R.string.question_defaultmodifier, false),
                new Question(R.string.question_androidmanifest, false)
        };
        updateQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initializeViews(); //find view by id methods
        initializeQuestions(); // loads Question objects into array of questions called questionBank
        initializeListeners(); //sets onClickListeners for button views.
//        quizTakerName = (TextView) findViewById(R.id.quizzer_name);
//        quizTakerName.setText(getIntent().getExtras().getString(EXTRA_MESSAGE));

    }


    public void initializeViews() {
        trueButton = (Button) findViewById(R.id.true_btn);
        falseButton = (Button) findViewById(R.id.false_btn);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        nextButton = (Button) findViewById(R.id.next_btn);
        prevButton = (Button) findViewById(R.id.prev_btn);
//        quizTakerName = (TextView) findViewById(R.id.quizzer_name);
        cheatButton = (Button) findViewById(R.id.cheat_button);


        resetButtonColors();
    }


    public void initializeListeners() {
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        cheatButton.setOnClickListener(this);
    }


    public void resetButtonColors() {
        trueButton.setBackgroundResource(android.R.drawable.btn_default);
        falseButton.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fixme - implement restartQuiz and add a way to save the quiz taker's score
        switch (item.getItemId()) {
            case R.id.restart_quiz_action:
                restartQuiz();
                break;

            case R.id.save_score:
                String name = getIntent().getExtras().getString(EXTRA_MESSAGE);
                userScore.put(name, score);

                Toast.makeText(this, "Your score of " + score + " has been saved under your name, " + name, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateQuestion() {
        if (currentIndex >= 0 && currentIndex < questionBank.length) {
            Question currentQuestion = questionBank[currentIndex];
            int textResId = currentQuestion.getTextResId();
            questionTextView.setText(textResId);
        } else {
            currentIndex = 0;
            Toast.makeText(this, "Your score is " + score, Toast.LENGTH_SHORT).show();
        }
    }


    //fixme
    public void restartQuiz() {
//        Toast.makeText(this, "No implementation found. Implement the restartQuiz method", Toast.LENGTH_LONG).show();
//        MenuItem menu = (MenuItem) findViewById(R.id.restart_quiz_action);
//        menu.setOnMenuItemClickListener()
        score = 0;
        currentIndex = 0;
        updateQuestion();
        resetButtonColors();

    }

    public Question getCurrentQuestion() {
        return questionBank[currentIndex];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHEAT_REQUEST) {
            Toast.makeText(this, "You cheated!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Your result code was " + resultCode, Toast.LENGTH_LONG).show();
        }


    @Override  //overriding on click method of OnClickListener interface.
    public void onClick(View v) {
        Question question = getCurrentQuestion();

        resetButtonColors();
        switch (v.getId()) {
            case R.id.true_btn:
                // they clicked true
                if (question.isAnswerTrue()) {
                    falseButton.setBackgroundResource(R.color.red);
                    trueButton.setBackgroundResource(R.color.green);
                    score++;
                } else {
                    falseButton.setBackgroundResource(R.color.green);
                    trueButton.setBackgroundResource(R.color.red);
                }
                break;
            case R.id.false_btn:
                if (question.isAnswerTrue()) {
                    falseButton.setBackgroundResource(R.color.red);
                    trueButton.setBackgroundResource(R.color.green);
                } else {
                    falseButton.setBackgroundResource(R.color.green);
                    trueButton.setBackgroundResource(R.color.red);
                    score++;
                }
                break;
            case R.id.prev_btn:
                currentIndex--;
                updateQuestion();
                break;
            case R.id.next_btn:
                currentIndex++;
                updateQuestion();
                break;
            case R.id.cheat_button:
                Toast.makeText(this, "Showing cheat mode", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);

//                Question currentQuestion = questionBank[currentIndex];
                // use currentIndex to pass as extraa

                // chaining methods
//                String questionStr = getResources().getString(currentQuestion.getTextResId());
                // add extras to this
                intent.putExtra("CURRENT QUESTION", currentIndex);
//                boolean answer = currentQuestion.isAnswerTrue();
//                intent.putExtra("CURRENT ANSWER", answer);
//                startActivity(intent);
                startActivityForResult(intent, CHEAT_REQUEST);
                break;

        }
    }
}
