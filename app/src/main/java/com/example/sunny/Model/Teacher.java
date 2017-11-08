package com.example.sunny.Model;

/**
 * Created by sunny on 2017/6/19.
 */

import com.example.sunny.fdgfg.R;

import java.util.ArrayList;
import java.util.List;


public class Teacher {

    // 私有属性，只能被class自己访问，修改
    private int imageId;
    private String name;
    private String desc;

    // 可以被所有类访问
    public boolean isTeacher;

    // 只能被自己，子类及同一个包中的类访问
    protected String sex;


    // 构造函数
    public Teacher(int imageId, String name, String desc) {
        this.imageId = imageId;
        this.name = name;
        this.desc = desc;
        isTeacher = false;
    }

    // IO 返回dataSource
    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(R.mipmap.mode," 老王","要想生活很开心，头上必须顶点绿"));
        teachers.add(new Teacher(R.mipmap.phon," 隔壁","调起电话，6.0之后动态获取权限"));
        teachers.add(new Teacher(R.mipmap.ldc," 翠花","内容提供器---ContentResolver"));
        teachers.add(new Teacher(R.mipmap.preview," 通知","Nofification"));
        teachers.add(new Teacher(R.mipmap.preview," 网络","GET POST XML解析"));
        teachers.add(new Teacher(R.mipmap.preview," 服务","Service"));
        teachers.add(new Teacher(R.mipmap.preview," 第三个","Nofification"));

        return teachers;
    }

    // 提供外部属性访问接口
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
