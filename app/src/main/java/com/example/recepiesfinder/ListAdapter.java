package com.example.recepiesfinder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expListTitle;
    private HashMap<String, List<String>> expListDetail;
    public ListAdapter(Context context, List<String> expListTitle,
                       HashMap<String, List<String>> expListDetail) {
        this.context = context;
        this.expListTitle = expListTitle;
        this.expListDetail = expListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expListPosition) {
        return expListDetail.get(
                expListTitle.get(listPosition)
        ).get(expListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String expListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_elem, null);
        }
        TextView expListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        expListTextView.setText(expListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expListDetail.get(
                expListTitle.get(listPosition)
        ).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return expListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
