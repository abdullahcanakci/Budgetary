package com.example.abdullah.budgetary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.ui.CustomDialogFragment;
import com.example.abdullah.budgetary.ui.main.MainFragment;
import com.example.abdullah.budgetary.ui.newTransaction.NewTransactionFragment;
import com.example.abdullah.budgetary.utilities.InjectorUtils;

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

        getSupportFragmentManager().beginTransaction().add(R.id.frame, MainFragment.getInstance()).commit();

        findViewById(R.id.button_transaction).setOnClickListener((View) -> {
            //mRepository.addTransaction(createRandomTransaction());
            CustomDialogFragment d = new CustomDialogFragment();
            d.setButtons(R.string.confirm_button_text, null);
            d.setTitle(R.string.dialog_title_new);
            d.setFragment(NewTransactionFragment.getInstance());
            d.show(getSupportFragmentManager(), "dialog");
        });
    }

    int temp = 0;
    int[] sampleIcons = {R.drawable.temp_hot, R.drawable.money, R.drawable.temp_hat, R.drawable.temp_vacation};
    int[] sampleColors = {R.color.summary_center_color, R.color.summary_expense_color, R.color.summary_income_color, R.color.colorAccent};
    private void createRandomCategory() {
        for(int i = 0; i < 4 ; i++) {
            Category cat = new Category();
            Icon icon = new Icon(
                    0,
                    getResources().getColor(sampleColors[3-i]),
                    getResources().getResourceName(sampleIcons[i]),
                    "Description" + i
            );
            cat.setIcon(icon);
            cat.setName("Name" + i);
            boolean x = new Random().nextBoolean();
            cat.setExpense(x);
            cat.setIncome(!x);
            mRepository.addCategory(cat);
        }
    }

}
