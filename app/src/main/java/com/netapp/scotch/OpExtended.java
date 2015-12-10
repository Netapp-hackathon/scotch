package com.netapp.scotch;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by jay3 on 12/10/2015.
 */
public class OpExtended extends Op {

    private Integer mEId;
    private Integer mResult;
    private String mCreated;

    public OpExtended(int opId, String opName, Integer eId, Integer result) {
        super(opName, "", opId);

        this.mEId = eId;
        this.mResult = result;
        this.mCreated = "";
    }

    public OpExtended(int opId, String opName, Integer eId, Integer result, String created) {
        super(opName, "", opId);

        this.mEId = eId;
        this.mResult = result;
        this.mCreated = created;
    }

    public OpExtended() {
        super();
    }

    public Integer geteId() {
        return mEId;
    }

    public void seteId(int eId) {
        this.mEId = eId;
    }

    public String getresult() {

        String res = new String();

        if(mResult == Utils.constant.PASSED) {
            res = "Passed";
        } else if(mResult == Utils.constant.IN_PROGRSS) {
            res = "In Progress";
        } else {
            res = "Failed";
        }

        return res;
    }

    public void setresult(Integer result) {
        this.mResult = result;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCretaed(String cretaed) {
        this.mCreated = cretaed;
    }

    @Override
    public String toString() {
        return "OpExtended{" +
                super.toString() +
                ", mEId=" + mEId +
                ", mResult=" + mResult +
                ", mCreated=" + mCreated +
                '}';
    }
}
