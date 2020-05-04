package com.example.recepiesfinder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expListTitle;
    private List<String> expListTitleFull;
    private HashMap<String, List<String>> expListDetail;
    private HashMap<String, List<String>> expListDetailFull;
    private HashMap<String, CheckBox> SwitchCheck = new HashMap<>();

    public ListAdapter(Context context, List<String> expListTitle,
                       HashMap<String, List<String>> expListDetail) {
        this.context = context;
        this.expListTitle = expListTitle;
        expListTitleFull = new ArrayList<>(expListTitle);
        this.expListDetail = expListDetail;
        expListDetailFull = new HashMap<>(expListDetail);
        for(Map.Entry e: expListDetail.entrySet()){
            for (String str: (List<String>)e.getValue()){
                SwitchCheck.put(str, new CheckBox(context));
                SwitchCheck.get(str).setChecked(false);
            }
        }
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
        CheckBox check = (CheckBox) convertView.findViewById(R.id.checkIng);
        check.setChecked(SwitchCheck.get(expListText).isChecked());
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
    public void setMyCheck(String name, boolean value){
        SwitchCheck.get(name).setChecked(value);
    }
    public boolean getMyCheck(String name){
        return SwitchCheck.get(name).isChecked();
    }


    public void filter(String text) {
        expListDetail.clear();
        expListTitle.clear();
        if (text.isEmpty()) {
            expListDetail.putAll(expListDetailFull);
            expListTitle.addAll(expListTitleFull);
        } else {
            String filPat = text.toLowerCase().trim();

            for (Map.Entry e: expListDetailFull.entrySet()){
                for (String str: (List<String>)e.getValue()){
                    if (str.toLowerCase().startsWith(filPat)){
                        if (expListDetail.get(str.substring(0, 1)) != null){
                            expListDetail.get(str.substring(0, 1)).add(str);
                        }
                        else {
                            expListDetail.put(str.substring(0, 1), new ArrayList<String>(Collections.singleton(str)));
                        }

                    }
                }
            }
            expListTitle.addAll(expListDetail.keySet());
        }
        notifyDataSetChanged();
    }

}
