package com.example.abdullah.budgetary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.data.Period;
import com.example.abdullah.budgetary.ui.CustomDialogFragment;
import com.example.abdullah.budgetary.ui.main.MainFragment;
import com.example.abdullah.budgetary.ui.newTransaction.NewTransactionFragment;
import com.example.abdullah.budgetary.ui.perioddetail.PeriodDetailFragment;
import com.example.abdullah.budgetary.utilities.BindingUtils;
import com.example.abdullah.budgetary.utilities.InjectorUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    BudgetaryRepository mRepository;
    List<Category> categories;
    int drawableVersion;
    int periodStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JodaTimeAndroid.init(getApplicationContext());

        mRepository = InjectorUtils.provideRepository(this);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, MainFragment.getInstance()).commit();

        findViewById(R.id.button_transaction).setOnClickListener((View) -> {
            //mRepository.addTransaction(createRandomTransaction());
            CustomDialogFragment d = new CustomDialogFragment();
            d.setButtons(R.string.confirm_button_text, null);
            d.setTitle(R.string.dialog_title_new);
            d.setFragment(NewTransactionFragment.getInstance());
            d.show(getSupportFragmentManager(), "dialog");
        });


        findViewById(R.id.button_details).setOnClickListener((view) -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, PeriodDetailFragment.getInstance()).addToBackStack("details").commit();
        });

        checkSharedPrefs();
        //checkDrawableStatus();

        mRepository.removeAllPeriods();

        Period period = new Period(new Date(0), new Date());
        Period period1 = new Period(new Date(), new Date(Long.MAX_VALUE));
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(period);
        periods.add(period1);
        mRepository.addPeriod(periods);


        BindingUtils.loadIcons(this, mRepository);


    }

    private void checkDrawableStatus() {

        String s = null;
        try {
            InputStream stream = this.getAssets().open("drawables.json");
            s = convert(stream, Charset.defaultCharset());
            Log.d("Test", "checkDrawableStatus: " + s);
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        Gson g = new Gson();
        Type type = new TypeToken<List<Icon>>(){}.getType();
        List<Icon> icons = g.fromJson(s, type);
        Log.d("Test", "checkDrawableStatus: ");
        mRepository.addIcons(icons);

    }

    public String convert(InputStream inputStream, Charset charset){
        try (Scanner scanner = new Scanner(inputStream, charset.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    private void checkSharedPrefs(){
        PreferenceHandler handler = new PreferenceHandler(this);

        drawableVersion = handler.getDrawablesVersion();
        periodStart = handler.getPeriodStart();

    }

}
