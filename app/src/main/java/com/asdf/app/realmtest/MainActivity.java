package com.asdf.app.realmtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asdf.app.realmtest.model.Exercise;
import com.asdf.app.realmtest.model.WorkoutPlan;
import com.asdf.app.realmtest.model.WorkoutSet;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextView;
    private Button mLoadButton;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.clear(Exercise.class);
        realm.clear(WorkoutPlan.class);
        realm.clear(WorkoutSet.class);
        WorkoutPlan p = realm.copyToRealm(new WorkoutPlan("Log"));
        realm.commitTransaction();

        mTextView = (TextView) findViewById(R.id.textview);
        mLoadButton = (Button) findViewById(R.id.load);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mLoadButton.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.load:
                //loadFromRealm();
                loadPlans();
                break;
            case R.id.fab:
                logToRealm();
                break;
        }
    }

    private void loadPlans() {
        RealmResults<WorkoutPlan> result = realm.where(WorkoutPlan.class).findAll();

        if(result.size() != 0){
            Toast.makeText(this, "" + result.size(), Toast.LENGTH_SHORT).show();
            mTextView.setText("");
            for (WorkoutPlan p : result) {
                String message = "Name: " + p.getName() + "\n";
                if(p.getExercises().size() != 0){
                    for (Exercise exercise : p.getExercises()) {
                        message += " Exercise name: " + exercise.getName() + "\n";
                    }
                }
                mTextView.append(message);
            }
        }else{
            Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromRealm() {
        RealmResults<Exercise> result = realm.where(Exercise.class).findAll();

        if(result.size() != 0){
            Toast.makeText(this, "" + result.size(), Toast.LENGTH_SHORT).show();
            mTextView.setText("");
            for (Exercise e : result) {
                String message = "Name: " + e.getName() + "\n" +
                        "Group: " + e.getMuscleGroup()+ "\n" +
                        ((e.getPlan() != null) ? "Plan: " + e.getPlan().getName()+ "\n" : "");
                mTextView.append(message);
            }
        }else{
            Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show();
        }
    }

    private void logToRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                WorkoutPlan p = realm.where(WorkoutPlan.class).findFirst();
                p.setDescription("Logging daily workouts");

                Exercise e = realm.copyToRealm(new Exercise("Curls", getTodaysDate()));
                e.setMuscleGroup("Bicep");
                e.setPlan(p);
                p.getExercises().add(e);

                WorkoutSet s = realm.copyToRealm(new WorkoutSet((long) 150, (long) 10));
                s.setExercise(e);

                e.getSets().add(s);
            }
        });
    }

    private Date getTodaysDate() {
        Calendar c = Calendar.getInstance();
        return new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }
}
