package com.adgvit.meme_o_mania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private ArrayList<questionObject> questionsList = new ArrayList<>();
    private questionObject currentQues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button startQuizButton  = view.findViewById(R.id.startQuizButton);
        final DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference("test_questions");
        final AVLoadingIndicatorView progressRing = view.findViewById(R.id.progressRing);

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("attempt", Context.MODE_PRIVATE);
        final boolean quizAttempted = sharedPref.getBoolean("quizAttempted",false);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quizAttempted){

                    Toast.makeText(getActivity(), "Quiz already Attempted", Toast.LENGTH_SHORT).show();

                }else {
                    startQuizButton.setEnabled(false);
                    startQuizButton.setAlpha(0.3f);
                    progressRing.smoothToShow();

                    dataBase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            questionsList = new ArrayList<>();

                            for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {

                                String key = itemSnapShot.getKey();

                                currentQues = itemSnapShot.getValue(questionObject.class);
                                questionsList.add(currentQues);

                                progressRing.smoothToHide();
                                Intent intent = new Intent(getActivity(), Quiz.class);
                                intent.putExtra("QuestionList", questionsList);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            progressRing.smoothToHide();
                            Toast.makeText(getActivity(), "Error Loading Quiz", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }


}
