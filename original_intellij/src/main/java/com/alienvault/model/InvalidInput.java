package com.alienvault.model;

public class InvalidInput {
    private String repoName;
    private String reason;

    public InvalidInput(String repoName,String reason){
        this.repoName = repoName;
        this.reason = reason;
    }

    public String getRepoName(){
        return repoName;
    }

    public void setRepoName(String repoName){
        this.repoName = repoName;
    }

    public String getReason(){
        return reason;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String toString(){
        return "Repository: " + repoName + "\nReason for Failure: " + reason;
    }
}
