package com.akash.quiz.frgm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akash.quiz.MyApplication;
import com.akash.quiz.adp.SolutionAdapter;
import com.akash.quiz.cls.QuestionData;
import com.akash.quiz.databinding.FragmentSolutionBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SolutionFragment extends Fragment {


 private FragmentSolutionBinding binding;
 private ArrayList<QuestionData> arrayList;
 private SolutionAdapter adapter;

    public SolutionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSolutionBinding.inflate(inflater,container,false);

        init();
        String resultId = SolutionFragmentArgs.fromBundle(getArguments()).getResultId();
        loadSolutionData(resultId);

        binding.next.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() < arrayList.size() - 1) {
                int position = binding.viewPager.getCurrentItem() + 1;
                binding.viewPager.setCurrentItem(position);
            } else {

                Toast.makeText(getContext(), "Reached the last page of the solution", Toast.LENGTH_LONG).show();
            }
        });

        binding.previous.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() > 0) {
                int position = binding.viewPager.getCurrentItem() - 1;
                binding.viewPager.setCurrentItem(position);


            } else {
                Toast.makeText(getContext(), "First page of the solution", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();

    }


    private void init(){
        arrayList = new ArrayList<>();
        adapter = new SolutionAdapter(arrayList);
        binding.viewPager.setAdapter(adapter);

    }


    private void loadSolutionData(String resultId){
        if (resultId == null){
            return;
        }

        MyApplication.firestore.collection("results").document(resultId).collection("solution")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots == null){
                    return;

                }

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    QuestionData data = documentSnapshot.toObject(QuestionData.class);
                    data.setId(documentSnapshot.getId());
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error : "+ e, Toast.LENGTH_SHORT).show();
        });
    }
}