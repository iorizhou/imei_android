package com.imei666.android.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.TypeDTO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    private List<String> mDatas;


    public void setDatas(List<String> datas){
        this.mDatas = datas;
    }



    public SearchHistoryAdapter(Context ctx)
    {
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_search_history,null);
            viewHolder.textView = (TextView)view.findViewById(R.id.textView1);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.textView.setText(mDatas.get(position));
        return view;
    }

    class ViewHolder{
        TextView textView;
    }
}
