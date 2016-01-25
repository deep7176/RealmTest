package com.asdf.app.realmtest.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Deep on 1/16/16.
 * for FitX
 */
public class Exercise extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private Date date;
    private String muscleGroup;
    private WorkoutPlan plan;
    private RealmList<WorkoutSet> sets;

    public WorkoutPlan getPlan() {
        return plan;
    }

    public void setPlan(WorkoutPlan plan) {
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<WorkoutSet> getSets() {
        return sets;
    }

    public void setSets(RealmList<WorkoutSet> sets) {
        this.sets = sets;
    }
}
