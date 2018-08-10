package com.example.abdullah.budgetary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.data.database.TransactionType;
import com.example.abdullah.budgetary.ui.main.MainFragment;
import com.example.abdullah.budgetary.utilities.DateUtilities;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity {
    BudgetaryRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = InjectorUtils.provideRepository(this);
        findViewById(R.id.frame);
        getSupportFragmentManager().beginTransaction().add(R.id.frame, MainFragment.getInstance()).commit();

        findViewById(R.id.button).setOnClickListener((View) -> {
            Transaction t = new Transaction();
            t.setAmount(10.12);
            t.setDate(DateUtilities.now());
            t.setIncome(true);
            t.setNote("N/A");
            t.setType(TransactionType.FOOD);

            mRepository.addTransaction(t);
        });
    }
}