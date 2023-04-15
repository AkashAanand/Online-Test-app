package com.akash.quiz.adp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.akash.quiz.cls.SubCategoryData;
import com.akash.quiz.frgm.QuizFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<SubCategoryData> arrayList;

    public ViewPagerAdapter(@NonNull @NotNull Fragment fragment,ArrayList<SubCategoryData> arrayList) {
        super(fragment);
        this.arrayList = arrayList;

    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new QuizFragment();
        SubCategoryData data = arrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id",data.getId());
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
