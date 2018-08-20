package com.imei666.android.utils.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.DiaryDTO;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.utils.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {

    private List<ItemDTO> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private Fragment mFragment;
    public ItemListAdapter(Context ctx, List<ItemDTO> datas){
        this.mContext = ctx;
        mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = datas;
        mImageLoader = ImageLoader.getInstance();
    }

    public void setDatas(List<ItemDTO> data){
        this.mDatas = data;
    }

    @Override
    public int getCount() {
        return this.mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_item,null);
            viewHolder.content = (TextView)view.findViewById(R.id.content);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ItemDTO dto = mDatas.get(i);
        viewHolder.content.setText(dto.getName());
        return view;
    }

    public class ViewHolder{

        public TextView content;
    }
}
