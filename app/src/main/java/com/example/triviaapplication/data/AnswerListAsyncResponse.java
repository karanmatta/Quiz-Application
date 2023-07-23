package com.example.triviaapplication.data;

import com.example.triviaapplication.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
