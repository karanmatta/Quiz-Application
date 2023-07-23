package com.example.triviaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapplication.controller.AppController;
import com.example.triviaapplication.data.AnswerListAsyncResponse;
import com.example.triviaapplication.data.Repository;
import com.example.triviaapplication.databinding.ActivityMainBinding;
import com.example.triviaapplication.model.Question;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Question> questionList;
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionList = new Repository().getQuestions(questionArrayList -> {


                    updateCounter(questionArrayList);
                }

        );


        binding.buttonNext.setOnClickListener(view -> {

            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();

        });
        binding.buttonTrue.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();

        });
        binding.buttonFalse.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();


        });


    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();

int snackMessageId=0;
if(userChoseCorrect== answer){
    snackMessageId = R.string.correct_answer;
    fadeAnimation();
}
else{
    snackMessageId = R.string.incorrrect_answer;
    shakeAnimation();
}
        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();

    }


  private void updateCounter(ArrayList<Question> questionArrayList) {
      binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted),
              currentQuestionIndex, questionArrayList.size()));


    }


private void updateQuestion() {
    String question = questionList.get(currentQuestionIndex).getAnswer();
   binding.questionTextView.setText(question);    
   updateCounter((ArrayList<Question>) questionList);
  }

  private void fadeAnimation(){
      AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f , 0.0f);
      alphaAnimation.setDuration(300);
      alphaAnimation.setRepeatCount(1);
      alphaAnimation.setRepeatMode(Animation.REVERSE);

      binding.cardView.setAnimation(alphaAnimation);

      alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {

              binding.questionTextView.setTextColor(Color.GREEN);
          }

          @Override
          public void onAnimationEnd(Animation animation) {
              binding.questionTextView.setTextColor(Color.WHITE);
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
      });

      binding.buttonNext.setAnimation(alphaAnimation);

      alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {
              binding.buttonNext.setTextColor(Color.BLUE);
          }

          @Override
          public void onAnimationEnd(Animation animation) {
              binding.buttonNext.setTextColor(Color.WHITE);
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
      });

  }

  private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                binding.questionTextView.setTextColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation)  {
            binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
  }


    }


