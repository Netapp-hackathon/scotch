package com.netapp.scotch;

/**
 * Created by jay3 on 12/10/2015.
 */
public class Result {

    String mresultOpName;
    String mresult;
    String mresultCreated;
    int mresultId;

    public Result(String resultOpName, String result, String resultCreated, int resultId) {
        this.mresultOpName = resultOpName;
        this.mresult = result;
        this.mresultCreated = resultCreated;
        this.mresultId = resultId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "mresultOpName='" + mresultOpName + '\'' +
                ", mresult=" + mresult +
                ", mresultCreated='" + mresultCreated + '\'' +
                ", mresultId=" + mresultId +
                '}';
    }
}
