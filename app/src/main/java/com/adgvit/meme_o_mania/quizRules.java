package com.adgvit.meme_o_mania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class quizRules extends AppCompatActivity {

    private ArrayList<questionObject> questionsList = new ArrayList<>();
    private questionObject currentQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);

        final Button startQuizButton  = findViewById(R.id.startQuizButton);
        final DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference("test_questions");
        final AVLoadingIndicatorView progressRing = findViewById(R.id.progressRing);


        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startQuizButton.setEnabled(false);
                startQuizButton.setAlpha(0.3f);
                progressRing.smoothToShow();

                dataBase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        questionsList = new ArrayList<>();

                        for(DataSnapshot itemSnapShot : dataSnapshot.getChildren())
                        {

                            String key = itemSnapShot.getKey();

                            currentQues = itemSnapShot.getValue(questionObject.class);
                            questionsList.add(currentQues);

                            progressRing.smoothToHide();
                            Intent intent = new Intent(quizRules.this, Quiz.class);
                            intent.putExtra("QuestionList",questionsList);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        progressRing.smoothToHide();
                        Toast.makeText(quizRules.this, "Error Loading Quiz", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}
