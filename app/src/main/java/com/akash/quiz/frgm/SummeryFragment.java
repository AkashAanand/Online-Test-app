package com.akash.quiz.frgm;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akash.quiz.MainActivity;
import com.akash.quiz.adp.SummeryAdapter;
import com.akash.quiz.databinding.FragmentSummeryBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SummeryFragment extends BottomSheetDialogFragment implements SummeryAdapter.OnNumberClickListener {


 private FragmentSummeryBinding binding;
 private SummeryAdapter adapter;

    public SummeryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSummeryBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();

    }

    private void init(){

        adapter = new SummeryAdapter(MainActivity.summeryList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),5);
        binding.rvView.setLayoutManager(layoutManager);
        binding.rvView.setAdapter(adapter);
    }

    @Override
    public void onNumberClick(int position) {
     MainActivity.summeryViewModel.setSelectedPosition(position);
     dismiss();
    }
}