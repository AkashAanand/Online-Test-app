package com.akash.quiz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.akash.quiz.MyApplication;
import com.akash.quiz.adp.CategoryAdapter;
import com.akash.quiz.adp.ImageSliderAdapter;
import com.akash.quiz.cls.CategoryData;
import com.akash.quiz.cls.SliderData;
import com.akash.quiz.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CategoryAdapter.onCategoryClickListener {


    private FragmentHomeBinding binding;
    private ArrayList<String> arrayList;
    private CategoryAdapter categoryAdapter;
    private ImageSliderAdapter adapter;
    private ArrayList<CategoryData> categoryDataArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        loadCategoryData();
        loadImageSliderData();
        return root;
    }


    private void init() {
        arrayList = new ArrayList<>();
        adapter = new ImageSliderAdapter(arrayList);
        binding.imageSlider.setSliderAdapter(adapter);

        categoryDataArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryDataArrayList,this);
        binding.rvView.setAdapter(categoryAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        binding.rvView.setLayoutManager(layoutManager);


    }

    private void loadImageSliderData() {
        MyApplication.firestore.collection("sliders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots != null) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                SliderData data = documentSnapshot.toObject(SliderData.class);
                                if (data.getUrl() != null) {
                                    arrayList.add(data.getUrl());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error is : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadCategoryData(){
        MyApplication.firestore.collection("quizCategory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                      if (queryDocumentSnapshots != null) {
                          for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                              CategoryData data = documentSnapshot.toObject(CategoryData.class);
                              data.setId(documentSnapshot.getId());
                              categoryDataArrayList.add(data);
                              categoryAdapter.notifyDataSetChanged();
                          }
                      }
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error is : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCategoryClick(int position,View view) {
        CategoryData data = categoryDataArrayList.get(position);
        NavDirections action =
                HomeFragmentDirections.actionNavHomeToSubCategoryRootFragment(data,data.getName());

        Navigation.findNavController(getView()).navigate(action);

    }
}