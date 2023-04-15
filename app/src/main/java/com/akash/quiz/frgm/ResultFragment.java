package com.akash.quiz.frgm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akash.quiz.cls.ResultData;
import com.akash.quiz.databinding.FragmentResultBinding;


public class ResultFragment extends Fragment {


private FragmentResultBinding binding;
private ResultData data;
    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater,container,false);

        data = ResultFragmentArgs.fromBundle(getArguments()).getResultData();
        updateUi(data);

       binding.solution.setOnClickListener(v -> {
           if (data == null){
               return;
           }
           NavDirections directions = ResultFragmentDirections.actionResultFragmentToSolutionFragment(data.getId());
           NavHostFragment.findNavController(ResultFragment.this).navigate(directions);
       });

        return binding.getRoot();
    }

    private void updateUi(ResultData data){
        if (data==null){
            return;
        }
        binding.rightAnswer.setText(String.valueOf(data.getRightAnswer()));
        binding.wrongAnswer.setText(String.valueOf(data.getWrongAnswer()));
        binding.totalMarks.setText(String.valueOf(data.getTotalMarks()));

        binding.userMarks.setText(String.valueOf(data.getUserMarks()));

        int min = data.getUserTimeInSecond() / 60;
        int seconds = data.getUserTimeInSecond() % 60;

        String userTime = min + " min  " + seconds + " sec";

        binding.userTime.setText(userTime);

        binding.totalQuestion.setText(String.valueOf(data.getTotalQuestion()));
        binding.notAnswered.setText(String.valueOf(data.getNotAttendedQuestion()));
    }
}