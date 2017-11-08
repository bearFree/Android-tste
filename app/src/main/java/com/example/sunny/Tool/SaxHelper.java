package com.example.sunny.Tool;

import android.util.Log;
import android.util.Xml;
import android.widget.ProgressBar;

import com.example.sunny.Model.ProjectModel;


import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

/**
 * Created by sunny on 2017/11/8.
 */

public class SaxHelper {

    private ProjectModel project;
    private ArrayList<ProjectModel> projects;

    private String tagName = null;

    /**
     * 用pull解析器解析xml文件，存入List<Person> 集合中
     *
     * @param in 读取的Person.xml文件对应的输入流
     * @return 将读取到的List<Person>集合返回
     * @throws Exception
     */
//    public static List<ProjectModel> xmlParser(InputStream in) throws Exception {
//        List<ProjectModel> projects = null;
//        ProjectModel project = null;
//        XmlPullParser parser = Xml.newPullParser();
//        parser.setInput(in, "utf-8");
//
//        int event = parser.getEventType();
//        while (XmlPullParser.END_DOCUMENT != event) {
//            switch (event) {
//                case XmlPullParser.START_DOCUMENT:
//                    projects = new ArrayList<ProjectModel>();
//                    break;
//                case XmlPullParser.START_TAG:
//                    if ("person".equals(parser.getName())) {
//                        project = new ProjectModel();
//                        int id = new Integer(parser.getAttributeValue(0));
//                        project.setId(id);
//                    }
//                    if ("name".equals(parser.getName())) {
//                        String name = parser.nextText();
//                        if (null != name) {
//                            person.setName(name);
//                        }
//                    }
//                    if ("age".equals(parser.getName())) {
//                        String text = parser.nextText();
//                        if (null != text) {
//                            int age = new Integer(text);
//                            person.setAge(age);
//                        }
//                    }
//                    break;
//                case XmlPullParser.END_TAG:
//                    if ("person".equals(parser.getName()) && (null != persons && null != person)) {
//                        persons.add(person);
//                        person = null;
//                    }
//                    break;
//            }
//
//            event = parser.next();
//        }
//        return persons;
//    }
//
//    /**
//     * 用 XmlSerializer 将List<Person> 集合放入XML文件中
//     * @param persons  要写入XML的List<Person>集合
//     * @param os   写入到哪里
//     * @throws Exception
//     */
//    public static void createXML(List<Person> persons, OutputStream os) throws Exception {
//        XmlSerializer serializer = Xml.newSerializer();
//        serializer.setOutput(os, "utf-8");
//        serializer.startDocument("utf-8", true);
//        serializer.startTag(null, "persons");
//        for(Person person : persons) {
//            serializer.startTag(null, "person");
//
//            serializer.attribute(null, "id", person.getId() + "");
//
//            serializer.startTag(null, "name");
//            serializer.text(person.getName());
//            serializer.endTag(null, "name");
//
//            serializer.startTag(null, "age");
//            serializer.text(person.getAge()+"");
//            serializer.endTag(null, "age");
//
//            serializer.endTag(null, "person");
//        }
//        serializer.endTag(null, "persons");
//        serializer.endDocument();
//        os.flush();
//        os.close();
//    }

}
