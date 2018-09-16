package com.example.abdullah.budgetary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abdullah.budgetary.data.BudgetaryRepository;
import com.example.abdullah.budgetary.data.Category;
import com.example.abdullah.budgetary.data.Period;
import com.example.abdullah.budgetary.ui.CustomDialogFragment;
import com.example.abdullah.budgetary.ui.main.MainFragment;
import com.example.abdullah.budgetary.ui.newTransaction.NewTransactionFragment;
import com.example.abdullah.budgetary.ui.perioddetail.PeriodDetailFragment;
import com.example.abdullah.budgetary.ui.utils.IconJsonParser;
import com.example.abdullah.budgetary.utilities.BindingUtils;
import com.example.abdullah.budgetary.utilities.InjectorUtils;
import com.example.abdullah.budgetary.utilities.PreferenceHandler;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BudgetaryRepository mRepository;
    List<Category> categories;
    int drawableVersion;
    int periodStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        mRepository = InjectorUtils.provideRepository(this);

        checkSharedPrefs();
        parseIcons();

        BindingUtils.loadIcons(this, mRepository);

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

        mRepository.getAllPeriods().observe(this, (periods -> {
            if(periods != null)
                if(periods.size() < 4) {
                    Period last = periods.get(periods.size() - 1);
                    Period ne = Period.createNewPeriod(last.getEnd());

                    mRepository.addPeriod(ne);
                }
        }));

    }

    private void parseIcons() {
        String json = loadDrawableJson();
        IconJsonParser parser = new IconJsonParser(json, drawableVersion);
        parser.getVersionStatus().observe(this, (Boolean isRepoOld)->{
            if(isRepoOld != null && isRepoOld){
                Log.d(TAG, "Icon repository is old loading new one");
                parser.getIcons().observe(this,  (icons)-> {
                   mRepository.addIcons(icons);
                   //parser.getIcons().removeObservers((AppCompatActivity)this.getBaseContext());
                });
                new PreferenceHandler(this).setIconsVersion(parser.getVersion());
            } else {
                Log.d(TAG, "Icon repository is up to date.");
            }
            //parser.getVersionStatus().removeObservers((AppCompatActivity)getApplicationContext());
        });
    }

    private String loadDrawableJson() {

        String s = null;
        try {
            InputStream stream = this.getAssets().open("drawables.json");
            s = convert(stream, Charset.defaultCharset());
            //Log.d("Test", "checkDrawableStatus: " + s);
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return s;
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
        if(handler.isFirstRun()){
            onFirstRun();
        } else{
            Log.d(TAG, "checkSharedPrefs: Not on first run");
        }

    }

    private void onFirstRun() {
        createFirstPeriod();
    }

    private void createFirstPeriod(){
        Period p = Period.createFirstPeriod(this.periodStart);
        mRepository.addPeriod(p);
    }

}
