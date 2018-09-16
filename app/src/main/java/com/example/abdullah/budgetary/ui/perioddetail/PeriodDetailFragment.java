package com.example.abdullah.budgetary.ui.perioddetail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Period;
import com.example.abdullah.budgetary.databinding.FragmentPeriodDetailsBinding;
import com.example.abdullah.budgetary.ui.CustomDialogFragment;
import com.example.abdullah.budgetary.ui.detailtransaction.TransactionDetailFragment;
import com.example.abdullah.budgetary.ui.utils.TransactionRecyclerAdapter;
import com.example.abdullah.budgetary.utilities.DateUtilities;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

import java.util.List;

public class PeriodDetailFragment extends Fragment {
    FragmentPeriodDetailsBinding binding;
    TransactionRecyclerAdapter adapter;
    PeriodDetailViewModel model;
    List<Period> periods;

    public static Fragment getInstance() {
        return new PeriodDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PeriodDetailFragmentViewModelFactory factory = InjectorUtils.providePeriodDetailFragmentViewModelFactory(getContext());
        model = ViewModelProviders.of(this, factory).get(PeriodDetailViewModel.class);

        adapter = new TransactionRecyclerAdapter();

        Period period = new Period(DateUtilities.periodStart(), DateUtilities.now());
        model.setPeriod(period);
        model.getTransactions().observe(this, (transactions -> {
            adapter.updateList(transactions);
        }));

    }
    ArrayAdapter<String> stringAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_period_details, container, false);

        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.setAdapter(adapter);

        stringAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(stringAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          model.setPeriod(periods.get(position));
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  });

        model.getPeriods().observe(this, (periods -> {
            this.periods = periods;
            String[] per = getPeriodStrings(periods);
            stringAdapter.addAll(per);

        }));

        adapter.setOnItemClickListener((view, id) -> {
            inflateDialogFragment(TransactionDetailFragment.getInstance(id));
        });


        return binding.getRoot();

    }

    private void inflateDialogFragment(Fragment instance) {
        CustomDialogFragment d = new CustomDialogFragment();
        d.setButtons(null, null);
        d.setTitle(null);
        d.setFragment(instance);
        d.show(getChildFragmentManager(), "dialog");
    }

    private String[] getPeriodStrings(List<Period> periods) {
        if(periods == null)
            return new String [0];
        String[] strings = new String[periods.size()];

        int i = 0;
        for(Period p: periods){
            strings[i] = DateUtilities.getPeriodInfo(p);
            i++;
        }
        return strings;
    }
}
