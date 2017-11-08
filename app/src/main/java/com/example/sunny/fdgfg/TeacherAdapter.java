package com.example.sunny.fdgfg;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.example.sunny.Model.Teacher;

/**
 * Created by sunny on 2017/6/20.
 */

public class TeacherAdapter extends ArrayAdapter<Teacher> {

    public TeacherAdapter(Context context, int resource, List<Teacher> objects) {
        super(context, resource, objects);
    }

    static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
        TextView textView1;
    }

    // 类似 tableViewCell return cell
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取老师的数据
        final Teacher teacher = getItem(position);

        // 创建布局  convertView 复用
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.teacher_list_item, parent, false);
            holder = new ViewHolder();

            // 获取cell
            holder.imageView = (ImageView) view.findViewById(R.id.tcImage);
            holder.textView1 = (TextView) view.findViewById(R.id.tcName);
            holder.textView = (TextView) view.findViewById(R.id.tcDesc);
            view.setTag(holder); // 将控件缓存在convertView中
        }
        else {
            view = convertView;
            holder = (ViewHolder) view.getTag(); // 缓存池取出cell
        }

        // 赋值
        holder.imageView.setImageResource(teacher.getImageId());
        holder.textView1.setText(teacher.getName());
        holder.textView.setText(teacher.getDesc());

        return view;
    }
}
