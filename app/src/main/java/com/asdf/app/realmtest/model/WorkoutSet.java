package com.asdf.app.realmtest.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Deep on 1/17/16.
 * for FitX
 */
public class WorkoutSet extends RealmObject {
    @PrimaryKey private int id;
    private Exercise exercise;
    private long weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReps() {
        return reps;
    }

    public void setReps(long reps) {
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    private long reps;
}
