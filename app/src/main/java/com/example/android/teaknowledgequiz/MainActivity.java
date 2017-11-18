package com.example.android.teaknowledgequiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Show how much the answer knows about tea culture according to the answers he gives.
     *
     * @param view
     */
    public void showResult(View view) {
        int numberOfRightAnswers = judgeResult();
        Context context = getApplicationContext();
        String text = getString(R.string.result_title);
        if (0 == numberOfRightAnswers) {
            text += getString(R.string.result_worst);
        } else if (1 <= numberOfRightAnswers && numberOfRightAnswers <= 3) {
            text += getString(R.string.result_not_so_good);
        } else if (4 <= numberOfRightAnswers && numberOfRightAnswers <= 6) {
            text += getString(R.string.result_not_bad);
        } else if (7 <= numberOfRightAnswers && numberOfRightAnswers <= 9) {
            text += getString(R.string.result_good);
        } else if (10 == numberOfRightAnswers) {
            text += getString(R.string.result_excellent);
        }
        int duration = Toast.LENGTH_SHORT;
        Toast resultToast = Toast.makeText(context, text, duration);
        resultToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        resultToast.show();
        return;
    }

    /**
     * Count how many answers are correct.
     */
    private int judgeResult() {
        /**
         * Check if the answers of the single choices are correct
         * by checking if the right radio button is chosen.
         */
        int[] radioButtonIdsOfRightAnswers = new int[]{R.id.question_1_answer, R.id.question_2_answer, R.id.question_3_answer,
                R.id.question_4_answer, R.id.question_5_answer, R.id.question_6_answer};
        int[][] checkBoxIdsOfRightAnswers = new int[][]{{R.id.question_7_answer_1, R.id.question_7_answer_2, R.id.question_7_answer_3},
                {R.id.question_8_answer_1, R.id.question_8_answer_2, R.id.question_8_answer_3}};
        int[][] checkBoxIdsOfWrongAnswers = new int[][]{{R.id.question_7_wrong_answer}, {R.id.question_8_wrong_answer}};
        int[] completionsAnswers = new int[]{R.id.question_9_edittext, R.id.question_10_edittext};
        int[] completionsRightAnswers = new int[]{R.string.question_9_right_answer, R.string.question_10_right_answer};
        int numberOfRightAnswers = 0;
        for (int i = 0; i < radioButtonIdsOfRightAnswers.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(radioButtonIdsOfRightAnswers[i]);
            if (radioButton.isChecked()) {
                ++numberOfRightAnswers;
            }
        }
        /**
         * Check if the answers of the multiple choices are correct
         * by checking if all the right options have been chosen, and no wrong options are chosen.
         */
        for (int i = 0; i < checkBoxIdsOfRightAnswers.length; i++) {
            boolean ifAllRightAnswersHaveBeenChosen = true;
            boolean ifNoWrongAnswersAreChosen = true;
            for (int j = 0; j < checkBoxIdsOfRightAnswers[0].length; j++) {
                CheckBox checkBoxOfRightAnswers = (CheckBox) findViewById(checkBoxIdsOfRightAnswers[i][j]);
                if (!checkBoxOfRightAnswers.isChecked()) {
                    ifAllRightAnswersHaveBeenChosen = false;
                }
            }
            for (int k = 0; k < checkBoxIdsOfWrongAnswers[0].length; k++) {
                CheckBox checkBoxOfWrongAnswers = (CheckBox) findViewById(checkBoxIdsOfWrongAnswers[i][k]);
                if (checkBoxOfWrongAnswers.isChecked()) {
                    ifNoWrongAnswersAreChosen = false;
                }
            }
            if (ifAllRightAnswersHaveBeenChosen && ifNoWrongAnswersAreChosen) {
                ++numberOfRightAnswers;
            }
        }

        /**
         * Check if the answers of the completions are correct
         * by comparing the text of the edittextes with the right answers.
         */

        for (int i = 0; i < completionsAnswers.length; i++) {
            EditText editText = (EditText) findViewById(completionsAnswers[i]);
            String answer = editText.getText().toString();
            String rightAnswer = getString(completionsRightAnswers[i]);
            if (answer.compareTo(rightAnswer) == 0) {
                ++numberOfRightAnswers;
            }
        }
        return numberOfRightAnswers;
    }
}
