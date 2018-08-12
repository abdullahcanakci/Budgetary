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
import com.example.abdullah.budgetary.databinding.FragmentMainBinding;
import com.example.abdullah.budgetary.piechart.PieChart;
import com.example.abdullah.budgetary.piechart.PieSlice;
import com.example.abdullah.budgetary.piechart.data.PieData;
import com.example.abdullah.budgetary.piechart.data.PieSliceData;
import com.example.abdullah.budgetary.ui.utils.TransactionRecyclerAdapter;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    MainFragmentViewModel mViewModel;
    private FragmentMainBinding binding;
    TransactionRecyclerAdapter recyclerAdapter = new TransactionRecyclerAdapter(10);

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
            recyclerAdapter.updateList(transactions);
            recyclerView.smoothScrollToPosition(0);
        });

        //Pie Chart implementation
        PieSliceData sliceData1 = new PieSliceData(1, 10.5, "data 1", R.drawable.money, R.color.summary_income_color);
        PieSlice slice1 = new PieSlice(getContext());

        PieSliceData sliceData2 = new PieSliceData(2, 21.0, "data 1", R.drawable.money, R.color.summary_expense_color);
        PieSlice slice2 = new PieSlice(getContext());

        PieSliceData sliceData3 = new PieSliceData(3, 15, "data 3", R.drawable.money, R.color.colorAccent);
        PieSlice slice3 = new PieSlice(getContext());

        PieChart pieChart = binding.pieChart;
        slice1.setSliceData(sliceData1);
        slice2.setSliceData(sliceData2);
        slice3.setSliceData(sliceData3);
        pieChart.addSlice(slice1);
        pieChart.addSlice(slice2);
        pieChart.addSlice(slice3);
        //pieChart.build();


        return binding.getRoot();
    }

    private void updateUI() {
        if(this.getView() != null)
            this.getView().invalidate();
    }

}
