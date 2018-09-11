package com.example.abdullah.budgetary.ui.newcategory;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.databinding.FragmentCategoryAddBinding;
import com.example.abdullah.budgetary.ui.utils.DialogInteractionInterface;
import com.example.abdullah.budgetary.utilities.IconRecyclerAdapter;
import com.example.abdullah.budgetary.utilities.InjectorUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class NewCategoryFragment extends Fragment implements DialogInteractionInterface {
    private static final String TAG = "NewCategoryFragment";
    private IconRecyclerAdapter adapter;
    private NewCategoryViewModel model;
    private FragmentCategoryAddBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewCategoryViewModelFactory factory = InjectorUtils.provideNewCategoryViewModelFactory(getContext());
        model = ViewModelProviders.of(this, factory).get(NewCategoryViewModel.class);
        adapter = new IconRecyclerAdapter(model);

        model.getIcons().observe(this, (icons) -> {
            adapter.setList(icons);
            Log.d(TAG, "number of icons received: " + icons.size());
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_add, container, false);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(this.getContext());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.SPACE_AROUND);

        binding.iconRecycler.setLayoutManager(manager);
        binding.iconRecycler.setAdapter(adapter);
        binding.iconRecycler.setHasFixedSize(true);

        binding.colorSeekBar.setBarHeight(5);
        binding.colorSeekBar.setOnColorChangeListener((int barPos, int alpPos, int color) -> {
            model.setSeekBarPosition(barPos);
            model.setColor(color);
        });
        binding.colorSeekBar.setColorBarPosition(model.getSeekBarPosition());
        return binding.getRoot();
    }

    public static Fragment getInstance() {
        return new NewCategoryFragment();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public boolean onConfirm(){
        Category cat = model.getCategory();
        model.insertCategory(cat);
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
