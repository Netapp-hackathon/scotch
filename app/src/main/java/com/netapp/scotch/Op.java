package com.netapp.scotch;

import java.util.List;

/**
 * Created by shivamk on 09-Dec-15.
 */
public class Op {
    String opName;
    String opDescription;
    int opId;

    public Op(String opName, String opDescription, int opId) {
        this.opName = opName;
        this.opDescription = opDescription;
        this.opId = opId;
    }

    public String toString() {
        return "Op: " + opName + ", Description: " + opDescription + ", Id: " + opId;
    }
}