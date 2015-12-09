package com.netapp.scotch;

/**
 * Created by shivamk on 09-Dec-15.
 */
public class Workspace {
    String workspaceName;
    int workspaceId;

    public Workspace(String workspaceName, int workspaceId) {
        this.workspaceName = workspaceName;
        this.workspaceId = workspaceId;
    }

    public String toString() {
        return "Workspace: " + workspaceName + ", Id: " + workspaceId;
    }
}