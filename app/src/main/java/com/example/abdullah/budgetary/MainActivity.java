package com.example.abdullah.budgetary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Transaction;
import com.example.abdullah.budgetary.ui.main.MainFragment;
import com.example.abdullah.budgetary.ui.newTransaction.NewTransactionFragment;
import com.example.abdullah.budgetary.utilities.AppExecutors;
import com.example.abdullah.budgetary.utilities.DateUtilities;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BudgetaryRepository mRepository;
    List<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = InjectorUtils.provideRepository(this);
        mRepository.getCategories().observeForever((categories1 ->  {
            categories = categories1;
            Log.d("MainActivity", "Categories updated. Number of items" + categories1.size());
        }));

        createRandomCategory();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, NewTransactionFragment.getInstance()).commit();

        findViewById(R.id.button_transaction).setOnClickListener((View) -> {
            //mRepository.addTransaction(createRandomTransaction());
        });
    }
    private Transaction createRandomTransaction(){
        Random rand = new Random();
        Long amount = rand.nextLong() * 100;
        boolean isIncome = rand.nextBoolean();
        Transaction transaction = new Transaction();
        try {
            transaction.setCategory(getRandomCategory());
        } catch (IllegalArgumentException e ){
            return null;
        }
        transaction.setNote("Transaction Note");
        transaction.setDate(DateUtilities.now());
        transaction.setIncome(isIncome);
        transaction.setAmount(amount);
        return transaction;
    }
    int temp = 0;
    int[] sampleIcons = {R.drawable.temp_hot, R.drawable.money, R.drawable.temp_hat, R.drawable.temp_vacation};
    int[] sampleColors = {R.color.summary_center_color, R.color.summary_expense_color, R.color.summary_income_color, R.color.colorAccent};
    private void createRandomCategory() {
        for(int i = 0; i < 4 ; i++) {
            Category cat = new Category();
            cat.setResId(sampleIcons[i]);
            cat.setBackgroundColor(sampleColors[3-i]);
            cat.setName("Name" + i);
            cat.setDescription("Description" + i);
            cat.setExpense(new Random().nextBoolean());
            cat.setIncome(new Random().nextBoolean());
            mRepository.addCategory(cat);
        }
    }

    private Category getRandomCategory() {
        return categories.get(new Random().nextInt(categories.size()));
    }

}
