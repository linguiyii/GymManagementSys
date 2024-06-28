package edu.demo.gymsys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.demo.gymsys.R;
import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;
import edu.demo.gymsys.serviceImpl.UserServiceImpl;
import edu.demo.gymsys.view.SlideLayout;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> users;
    private UserService service;
    private Set<SlideLayout> sets = new HashSet();

    private static class ViewHolder {
        public TextView contentView;
        public TextView menuView;
    }

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.service = new UserServiceImpl(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_slide, null);
            viewHolder = new ViewHolder();
            viewHolder.contentView = (TextView) convertView.findViewById(R.id.content);
            viewHolder.menuView = (TextView) convertView.findViewById(R.id.menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.contentView.setText(users.get(position).getEmail());

        viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        final User user = users.get(position);
        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideLayout slideLayout = (SlideLayout) v.getParent();
                slideLayout.closeMenu(); //解决删除item后下一个item变成open状态问题
                service.deleteUser(user.getId());
                users.remove(user);
                notifyDataSetChanged();
            }
        });

        SlideLayout slideLayout = (SlideLayout) convertView;
        slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());

        return convertView;
    }

    public SlideLayout slideLayout = null;

    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener {
        /**
         * 滑动后每次手势抬起保证只有一个item是open状态，加入sets集合中
         **/
        @Override
        public void onOpen(SlideLayout layout) {
            slideLayout = layout;
            if (sets.size() > 0) {
                for (SlideLayout s : sets) {
                    s.closeMenu();
                    sets.remove(s);
                }
            }
            sets.add(layout);
        }

        @Override
        public void onMove(SlideLayout layout) {
            if (slideLayout != null && slideLayout != layout) {
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onClose(SlideLayout layout) {
            if (sets.size() > 0) {
                sets.remove(layout);
            }
            if (slideLayout == layout) {
                slideLayout = null;
            }
        }
    }

}

