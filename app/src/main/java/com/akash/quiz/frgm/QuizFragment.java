package com.akash.quiz.frgm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akash.quiz.MyApplication;
import com.akash.quiz.adp.QuizAdapter;
import com.akash.quiz.cls.QuizData;
import com.akash.quiz.databinding.FragmentQuizBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class QuizFragment extends Fragment implements QuizAdapter.OnStartQuizClickListener {


    private FragmentQuizBinding binding;
    private String subCatId = null;
    private QuizAdapter adapter;
    private ArrayList<QuizData> arrayList;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            subCatId = bundle.getString("id");
        }

        init();
        loadQuizData(subCatId);


        return binding.getRoot();

    }

    private void init() {
        arrayList = new ArrayList<>();
        adapter = new QuizAdapter(arrayList, this);
        binding.rvView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvView.setAdapter(adapter);

    }


    private void loadQuizData(String id) {
        if (id == null) {
            return;
        }

        MyApplication.firestore.collection("quizzes")
                .whereEqualTo("subCategory", id)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        QuizData data = documentSnapshot.toObject(QuizData.class);
                        data.setId(documentSnapshot.getId());
                        arrayList.add(data);
                        adapter.notifyDataSetChanged();
                        Log.d("Quiz Fragment", "onSuccess: Name : " + data.getName());

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Error is : " + e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStartClick(int position) {

            // Open Question Screen
            QuizData data = arrayList.get(position);

            NavDirections directions = SubCategoryRootFragmentDirections.actionSubCategoryRootFragmentToQuestionRootFragment(data,data.getName());
           if(getView() != null){
               Navigation.findNavController(getView()).navigate(directions);
           }


        }


    }
