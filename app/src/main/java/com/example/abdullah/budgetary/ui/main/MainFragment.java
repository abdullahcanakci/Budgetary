package com.example.abdullah.budgetary.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.databinding.FragmentMainBinding;
import com.example.abdullah.budgetary.piechart.PieChart;
import com.example.abdullah.budgetary.piechart.interfaces.PieChartInterface;
import com.example.abdullah.budgetary.ui.utils.TransactionRecyclerAdapter;
import com.example.abdullah.budgetary.utilities.ChartAdapter;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

import java.util.List;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    MainFragmentViewModel mViewModel;
    private FragmentMainBinding binding;
    TransactionRecyclerAdapter recyclerAdapter = new TransactionRecyclerAdapter();

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

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.transaction_summary_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerAdapter);

        mViewModel.getLastTransactions().observeForever((transactions) -> {
            Log.d(TAG, "name of the class " + transactions.getClass().getName());
            updateRecyclerList(transactions);
        });

        PieChart pieChart = binding.pieChart;

        ChartAdapter adapter = new ChartAdapter();
        pieChart.setPieSliceAdapter(adapter);


        pieChart.setChartListener(new PieChartInterface() {
            @Override
            public void onSliceFocusEntry(long sliceId) {
                mViewModel.setCategoryFocus(sliceId).observeForever((transactions -> {
                    updateRecyclerList(transactions);
                }));
            }

            @Override
            public void onSliceFocusExit(long sliceId) {
                mViewModel.clearCategoryFocus().observeForever(transactions -> {
                    updateRecyclerList(transactions);
                });
            }
        });
        mViewModel.getCategories().observeForever(categories -> {
            adapter.addSlices(categories);
        });


        return binding.getRoot();
    }

    private void updateRecyclerList(List<Transaction> transactionsList) {
        recyclerAdapter.updateList(transactionsList);
        binding.transactionSummaryRecycler.smoothScrollToPosition(0);
    }

    private void updateUI() {
        if(this.getView() != null)
            this.getView().invalidate();
    }

}
