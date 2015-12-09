package com.netapp.scotch;

/**
 * Created by shivamk on 09-Dec-15.
 */
public class Vsim {
    String vsimName;
    int vsimId;

    public Vsim(String vsimName, int vsimId) {
        this.vsimName = vsimName;
        this.vsimId = vsimId;
    }

    public String toString() {
        return "Vsim: " + vsimName + ", Id: " + vsimId;
    }
}