package com.netapp.scotch;

import java.util.List;

/**
 * Created by shivamk on 09-Dec-15.
 */
public class Op {
    String mOpName;
    String mOpDescription;
    int mOpId;

    public Op(String opName, String opDescription, int opId) {
        this.mOpName = opName;
        this.mOpDescription = opDescription;
        this.mOpId = opId;
    }

    public Op() {

    }

    public String toString() {
        return "Op: " + mOpName + ", Description: " + mOpDescription + ", Id: " + mOpId;
    }

    public String getopName() {
        return mOpName;
    }

    public void setopName(String opName) {
        this.mOpName = opName;
    }

    public String getopDescription() {
        return mOpDescription;
    }

    public void setopDescription(String opDescription) {
        this.mOpDescription = opDescription;
    }

    public int getopId() {
        return mOpId;
    }

    public void setopId(int opId) {
        this.mOpId = opId;
    }
}