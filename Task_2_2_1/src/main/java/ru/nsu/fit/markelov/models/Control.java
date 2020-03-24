package ru.nsu.fit.markelov.models;

import javafx.beans.property.SimpleStringProperty;

public class Control {

    private final SimpleStringProperty action = new SimpleStringProperty("");
    private final SimpleStringProperty primaryKey = new SimpleStringProperty("");
    private final SimpleStringProperty secondaryKey = new SimpleStringProperty("");

    public Control() {
        this("", "", "");
    }

    public Control(String action, String primaryKey, String secondaryKey) {
        setAction(action);
        setPrimaryKey(primaryKey);
        setSecondaryKey(secondaryKey);
    }

    public String getAction() {
        return action.get();
    }

    public SimpleStringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getPrimaryKey() {
        return primaryKey.get();
    }

    public SimpleStringProperty primaryKeyProperty() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey.set(primaryKey);
    }

    public String getSecondaryKey() {
        return secondaryKey.get();
    }

    public SimpleStringProperty secondaryKeyProperty() {
        return secondaryKey;
    }

    public void setSecondaryKey(String secondaryKey) {
        this.secondaryKey.set(secondaryKey);
    }
}
