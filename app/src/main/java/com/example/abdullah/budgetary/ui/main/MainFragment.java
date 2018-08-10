package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.Observer;
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
import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.data.database.TransactionDao;
import com.example.abdullah.budgetary.databinding.FragmentMainBinding;
import com.example.abdullah.budgetary.utilities.AppExecutors;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

import java.util.List;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    MainFragmentViewModel mViewModel;
    private FragmentMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragmentViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this.getContext());
        mViewModel = ViewModelProviders.of(this, factory).get(MainFragmentViewModel.class);
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    private void bindUI() {
        binding.setModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setModel(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    private void updateUI() {
        if(this.getView() != null)
            this.getView().invalidate();
    }
}
