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
import java.util.Random;

public class GridViewAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    private List<TypeDTO> mDatas;
    private ImageLoader mImageLoader;


    public void setDatas(List<TypeDTO> datas){
        this.mDatas = datas;
    }



    public GridViewAdapter(Context ctx)
    {
        mInflater = LayoutInflater.from(ctx);
        mImageLoader = ImageLoader.getInstance();
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
            view = mInflater.inflate(R.layout.element_item_small,null);
            viewHolder.imgView = (ImageView) view.findViewById(R.id.imageView1);
            viewHolder.textView = (TextView)view.findViewById(R.id.textView1);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        TypeDTO dto = mDatas.get(position);
        viewHolder.textView.setText(dto.getName());
        mImageLoader.displayImage(dto.getPicUrl(),viewHolder.imgView);
        return view;
    }

    class ViewHolder{
        ImageView imgView;
        TextView textView;
    }
}
