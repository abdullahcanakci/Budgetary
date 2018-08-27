package com.example.abdullah.budgetary.ui.newTransaction;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.databinding.FragmentTransactionAddBinding;
import com.example.abdullah.budgetary.ui.utils.CategoryRecyclerAdapter;
import com.example.abdullah.budgetary.ui.utils.DialogInteractionInterface;
import com.example.abdullah.budgetary.ui.utils.TransactionRecyclerAdapter;
import com.example.abdullah.budgetary.utilities.InjectorUtils;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class NewTransactionFragment extends Fragment implements DialogInteractionInterface{
    FragmentTransactionAddBinding binding;
    CategoryRecyclerAdapter adapter;
    NewTransactionViewModel model;

    public static Fragment getInstance() {
        return new NewTransactionFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryRecyclerAdapter();
        NewTransactionViewModelFactory factory = InjectorUtils.provideNewFragmentViewModelFactory(this.getContext());
        model = ViewModelProviders.of(this, factory).get(NewTransactionViewModel.class);
        model.getCategories().observeForever((categories) -> {
            adapter.updateList(categories);
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_add, container, false);
        //https://github.com/google/flexbox-layout
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this.getContext());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.SPACE_AROUND);
        binding.categoryRecycler.setLayoutManager(manager);
        binding.categoryRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return binding.getRoot();
    }

    @Override
    public void onCancel() {
        Log.d("NewTransactionFragment", "onCancel:");
    }

    @Override
    public boolean onConfirm() {
        Log.d("NewTransactionFragment", "onConfirm: ");
        return true;
    }
}
