package com.alienvault.model;

public class Issue{
    private Long id;
    private String state;
    private String title;
    private String repository;
    private String createdOn;

    public Issue(Long id,String state,String title,String repository,String created_on) {
        this.id = id;
        this.state = state;
        this.title = title;
        this.repository = repository;
        this.createdOn = created_on;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getRepository(){
        return repository;
    }

    public void setRepository(String repository){
        this.repository = repository;
    }

    public String getCreatedOn(){
        return createdOn;
    }

    public void setCreatedOn(String createdOn){
        this.createdOn = createdOn;
    }

    public String toString(){
        return "{ id: " + id + ", state: " + state + ", title: " + title + ": repository: " + repository + "created_at: " + createdOn + "";
    }
}
