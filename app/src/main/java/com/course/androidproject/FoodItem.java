package com.course.androidproject;

public class FoodItem {
    private int resourceId,cal;

    public FoodItem(int resourceId, int cal) {
        this.resourceId = resourceId;
        this.cal = cal;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getName() {
        return cal;
    }

    public void setName(int cal) {
        this.cal = cal;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}