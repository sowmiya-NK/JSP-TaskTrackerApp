package com.codewithsowmiya.Model;

public class TaskApp {
    private int id;
    private String task;
    private String description;

    private boolean completeFlag;

    public TaskApp(int id, String task, String description, boolean completeFlag) {
        this.id = id;
        this.task = task;
        this.description = description;
        this.completeFlag = completeFlag;
    }

    public TaskApp() {
    }

    public boolean isCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(boolean completeFlag) {
        this.completeFlag = completeFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
