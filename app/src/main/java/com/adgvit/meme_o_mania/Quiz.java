package com.adgvit.meme_o_mania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


public class Quiz extends AppCompatActivity {

    private TextView timeTextView, questionNumberTextView, questionTextView, answer1, answer2, answer3, answer4,heading,unit;
    private CardView c1,c2,c3,c4;
    private ImageView memeImageView;
    private ConstraintLayout cl1, cl2, cl3, cl4;
    private CountDownTimer countDownTimer;
    private long quizTime = 30900;
    private int score = 0, questionNo = 0, maxQuestions = 3;
    private StorageReference storage,storageRef;
    private ArrayList<questionObject> questionsList = new ArrayList<>();
    private AVLoadingIndicatorView avi;


    void setCountDownTimer()
    {
        countDownTimer = new CountDownTimer(quizTime, 1000) {
            @Override
            public void onTick(long l) {
                updateTimerTextView(l);
            }

            @Override
            public void onFinish() {

                questionNo++;
                if(questionNo < maxQuestions)
                {
                    loadNewQuestion(questionNo);
                }
                else
                {
                    endQuiz();
                }

            }
        }.start();
    }
    void updateTimerTextView(long timeLeft)
    {
        int seconds = (int) timeLeft % 60000 / 1000;

        String timeLeftText = "";

        if(seconds < 10)
            timeLeftText += "0";
        timeLeftText += seconds;

        timeTextView.setText(timeLeftText);
    }
    void loadNewQuestion(int questionNo){

        hideUi();
        getMeme(questionsList.get(questionNo).getImg());



    }
    void getMeme(String memeLoc)
    {

        storageRef = storage.child(memeLoc);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(memeImageView);

                setCountDownTimer();
                reset();
                setNewQuestion(questionNo);
                showUi();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    void setNewQuestion(int i)
    {
        String temp = "QUESTION " + (i+1) + ".";
        questionNumberTextView.setText(temp);
        questionTextView.setText(questionsList.get(i).getQuestion());
        answer1.setText(questionsList.get(i).getA1());
        answer2.setText(questionsList.get(i).getA2());
        answer3.setText(questionsList.get(i).getA3());
        answer4.setText(questionsList.get(i).getA4());

    }
    void checkSubmission(int i, int j)
    {
        if(i == questionsList.get(j).getCorrect())
        {
            score++;
        }
        questionNo++;
        if(questionNo < maxQuestions)
        {
            loadNewQuestion(questionNo);
        }
        else
        {
            endQuiz();
        }
    }
    void reset()
    {
        cl1.setBackgroundColor(getColor(R.color.cardBackground));
        answer1.setTextColor(getColor(R.color.optionText));

        cl2.setBackgroundColor(getColor(R.color.cardBackground));
        answer2.setTextColor(getColor(R.color.optionText));

        cl3.setBackgroundColor(getColor(R.color.cardBackground));
        answer3.setTextColor(getColor(R.color.optionText));

        cl4.setBackgroundColor(getColor(R.color.cardBackground));
        answer4.setTextColor(getColor(R.color.optionText));
    }
    void endQuiz()
    {
        Toast.makeText(this, "Quiz ended with score "+ score, Toast.LENGTH_SHORT).show();
        cl1.setEnabled(false);
        cl2.setEnabled(false);
        cl3.setEnabled(false);
        cl4.setEnabled(false);
    }
    void hideUi()
    {
        heading.setVisibility(View.INVISIBLE);
        timeTextView.setVisibility(View.INVISIBLE);
        unit.setVisibility(View.INVISIBLE);
        memeImageView.setVisibility(View.INVISIBLE);
        questionNumberTextView.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);
        c4.setVisibility(View.INVISIBLE);
        cl1.setEnabled(false);
        cl2.setEnabled(false);
        cl3.setEnabled(false);
        cl4.setEnabled(false);
        avi.smoothToShow();

    }
    void showUi()
    {
        heading.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        unit.setVisibility(View.VISIBLE);
        memeImageView.setVisibility(View.VISIBLE);
        questionNumberTextView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.VISIBLE);
        c3.setVisibility(View.VISIBLE);
        c4.setVisibility(View.VISIBLE);
        cl1.setEnabled(true);
        cl2.setEnabled(true);
        cl3.setEnabled(true);
        cl4.setEnabled(true);
        avi.smoothToHide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        questionsList = intent.getParcelableArrayListExtra("QuestionList");

        memeImageView = findViewById(R.id.memeImageView);
        memeImageView.setClipToOutline(true);
        timeTextView = findViewById(R.id.timeTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        questionTextView = findViewById(R.id.questionTextView);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        cl1 = findViewById(R.id.cl1);
        cl2 = findViewById(R.id.cl2);
        cl3 = findViewById(R.id.cl3);
        cl4 = findViewById(R.id.cl4);
        c1 = findViewById(R.id.answerCardView1);
        c2 = findViewById(R.id.answerCardView2);
        c3 = findViewById(R.id.answerCardView3);
        c4 = findViewById(R.id.answerCardView4);
        heading = findViewById(R.id.quizHeadingTextView);
        unit = findViewById(R.id.timeUnitTextView);
        avi = findViewById(R.id.progressRing2);

        storage = FirebaseStorage.getInstance().getReference();

        loadNewQuestion(questionNo);


        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl1.setBackgroundColor(getColor(R.color.colorOrange));
                answer1.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(1,questionNo);
                countDownTimer.cancel();
            }
        });
        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl2.setBackgroundColor(getColor(R.color.colorOrange));
                answer2.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(2,questionNo);
                countDownTimer.cancel();
            }
        });
        cl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl3.setBackgroundColor(getColor(R.color.colorOrange));
                answer3.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(3,questionNo);
                countDownTimer.cancel();
            }
        });
        cl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl4.setBackgroundColor(getColor(R.color.colorOrange));
                answer4.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(4,questionNo);
                countDownTimer.cancel();
            }
        });

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);

    }
}
