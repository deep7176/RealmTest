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
                loadFromRealm();
                break;
            case R.id.fab:
                logToRealm();
                break;
        }
    }

    private void loadFromRealm() {
        RealmResults<Exercise> result = realm.where(Exercise.class).findAll();

        if(result.size() != 0){
            Toast.makeText(this, "" + result.size(), Toast.LENGTH_SHORT).show();
            for (Exercise e : result) {
                String message = "Name: " + e.getName() + "\n" +
                        "Group: " + e.getMuscleGroup() + "\n";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show();
        }
    }

    private void logToRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Exercise e = realm.createObject(Exercise.class);
                WorkoutPlan p = realm.createObject(WorkoutPlan.class);
                WorkoutSet s = realm.createObject(WorkoutSet.class);
                e.setName("Curls");
                e.setMuscleGroup("Bicep");

                p.setName("Daily Log");
                p.setDescription("Logging daily workouts");
                p.getExercises().add(e);

                s.setExercise(e);
                s.setReps((long) 10);
                s.setWeight((long) 150);

                e.getSets().add(s);
            }
        });
    }
}
