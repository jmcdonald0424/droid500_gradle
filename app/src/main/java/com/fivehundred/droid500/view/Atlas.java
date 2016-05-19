package com.fivehundred.droid500.view;

public enum Atlas {

    MAIN_ATLAS("drawable/mainatlas", 100, 10);

    private final String location;
    private final int size;
    private final int columnCount;

    private Atlas(String location, int size, int columnCount) {
        this.location = location;
        this.size = size;
        this.columnCount = columnCount;
    }

    public String getLocation() {
        return location;
    }

    public int getSize() {
        return size;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
