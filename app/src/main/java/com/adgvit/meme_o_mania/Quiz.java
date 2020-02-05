package com.adgvit.meme_o_mania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class Quiz extends AppCompatActivity {

    private TextView timeTextView, questionNumberTextView, questionTextView, answer1, answer2, answer3, answer4;
    private ImageView memeImageView;
    private ConstraintLayout cl1, cl2, cl3, cl4;
    private CountDownTimer countDownTimer;
    private long quizTime = 30000;
    private int score = 0, questionNo = 0, maxQuestions = 2;
    private StorageReference storage,storageRef;
    private DatabaseReference dataBase,databaseRef;
    private questionObject currentQues;
    private ArrayList<questionObject> questionsList = new ArrayList<>();
    private ArrayList<questionObject> temp = new ArrayList<>();


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

        setCountDownTimer();
        getMeme("Capture.PNG");
        //setNewQuestion(questionNo);


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

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
//    void getAllQuestions()
//    {
//
//        dataBase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                questionsList = new ArrayList<>();
//
//                for(DataSnapshot itemSnapShot : dataSnapshot.getChildren())
//                {
//
//                    String key = itemSnapShot.getKey();
//
//                    currentQues = itemSnapShot.getValue(questionObject.class);
//                    questionsList.add(currentQues);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//    }
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
    void endQuiz()
    {
        Toast.makeText(this, "Quiz ended", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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


        dataBase = FirebaseDatabase.getInstance().getReference("test_questions");
        storage = FirebaseStorage.getInstance().getReference();


        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questionsList = new ArrayList<>();

                for(DataSnapshot itemSnapShot : dataSnapshot.getChildren())
                {

                    String key = itemSnapShot.getKey();

                    currentQues = itemSnapShot.getValue(questionObject.class);
                    questionsList.add(currentQues);


                }
                Log.i("InFO",""+questionsList.get(0).getQuestion());
                temp = questionsList;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("INFOTEMP",""+temp.size());

        //loadNewQuestion(questionNo);


        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl1.setBackgroundColor(getColor(R.color.colorOrange));
                answer1.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(1,questionNo);
            }
        });
        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl2.setBackgroundColor(getColor(R.color.colorOrange));
                answer2.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(2,questionNo);
            }
        });
        cl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl3.setBackgroundColor(getColor(R.color.colorOrange));
                answer3.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(3,questionNo);
            }
        });
        cl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl4.setBackgroundColor(getColor(R.color.colorOrange));
                answer4.setTextColor(getColor(R.color.colorWhite));
                checkSubmission(4,questionNo);
            }
        });

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}
