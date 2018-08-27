package com.example.abdullah.budgetary.ui.newTransaction;

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
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.databinding.FragmentTransactionAddBinding;
import com.example.abdullah.budgetary.ui.utils.CategoryRecyclerAdapter;
import com.example.abdullah.budgetary.ui.utils.DialogInteractionInterface;
import com.example.abdullah.budgetary.utilities.DateUtilities;
import com.example.abdullah.budgetary.utilities.InjectorUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import static com.example.abdullah.budgetary.ui.main.MainFragment.TAG;

public class NewTransactionFragment extends Fragment implements DialogInteractionInterface{
    FragmentTransactionAddBinding binding;
    CategoryRecyclerAdapter adapter;
    NewTransactionViewModel model;
    boolean isExpense = true;

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
        binding.categorySwitch.setOnCheckedChangeListener((view, var) -> {
            model.setCategoryType(view.isChecked());
            isExpense = view.isChecked();
        });

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
        return insertTransaction();
    }

    private boolean insertTransaction(){
        Long amount = binding.amountEntry.getRawValue();
        if(amount == null || amount == 0L){
            Log.d(TAG, "insertTransaction: " + "No amount entered");
            binding.errorView.setText(R.string.no_amount_error);
            binding.errorView.setVisibility(View.VISIBLE);
            return false;
        }

        Category category = adapter.getSelectedCategory();
        if(category == null){
            Log.d(TAG, "insertTransaction: " + "No category selected");
            binding.errorView.setText(R.string.no_category_error);
            binding.errorView.setVisibility(View.VISIBLE);
            return false;
        }
        Transaction t = new Transaction();
        t.setDate(DateUtilities.now());
        t.setIncome(!isExpense);
        t.setCategory(category);
        t.setAmount(amount);
        model.insert(t);
        return true;


    }
}
