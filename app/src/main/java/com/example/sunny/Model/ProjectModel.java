package com.example.sunny.Model;

/**
 * Created by sunny on 2017/11/7.
 */

public class ProjectModel {

    private int project_id;
    private String name;

    public ProjectModel(String name, int id)
    {
        this.project_id = id;
        this.name = name;
    }

    public void setProject_id(int id){
        this.project_id = id;
    }

    public int getProject_id(){
        return this.project_id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
