package com.helpfooter.steve.amklovebaby.DataObjs;

/**
 * Created by Steve on 2015/12/17.
 */
public class DoctorSearchLableObj {
    int count;
    String name;
    String search;

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getSearch() {
        return search;
    }

    public DoctorSearchLableObj(int count, String name, String search) {

        this.count = count;
        this.name = name;
        this.search = search;
    }
}
