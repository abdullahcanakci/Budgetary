package com.example.abdullah.budgetary.ui.detailtransaction;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.databinding.FragmentTransactionDetailBinding;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

public class TransactionDetailFragment extends Fragment {
    private static final String TRANSACTION_ID = "transaction_id";
    private TransactionDetailFragmentViewModel model = null;
    FragmentTransactionDetailBinding binding;

    private long id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(TRANSACTION_ID, 0);
        TransactionDetailFragmentViewModelFactory factory = InjectorUtils.provideTransactionDetailFragmentViewModelFactory(getContext(), id);
        model = ViewModelProviders.of(this, factory).get(TransactionDetailFragmentViewModel.class);

    }

    public static TransactionDetailFragment getInstance(long transctionId){
        Bundle bundle = new Bundle();
        bundle.putLong(TRANSACTION_ID, transctionId);
        TransactionDetailFragment f = new TransactionDetailFragment();
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_detail, container, false);
        model.getTransaction().observe(this, (transaction) -> {
            if(transaction != null)
                binding.setTransaction(transaction);
            else {
                Toast.makeText(this.getContext(),R.string.transaction_detail_load_fail, Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}
