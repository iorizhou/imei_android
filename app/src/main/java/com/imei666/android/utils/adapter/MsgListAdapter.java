package com.imei666.android.utils.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.model.dto.MessageDTO;
import com.imei666.android.mvp.model.dto.MessageListDTO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MsgListAdapter extends BaseAdapter {

    private List<MessageListDTO> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private Fragment mFragment;
    public MsgListAdapter(Context ctx, List<MessageListDTO> datas){
        this.mContext = ctx;
        mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = datas;
        mImageLoader = ImageLoader.getInstance();
    }

    public void setDatas(List<MessageListDTO> data){
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
            view = mInflater.inflate(R.layout.item_msg,null);
            viewHolder.name = (TextView)view.findViewById(R.id.msg_user_name);
            viewHolder.cover = (ImageView)view.findViewById(R.id.msg_user_cover);
            viewHolder.content = (TextView)view.findViewById(R.id.msg_content);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        MessageListDTO dto = mDatas.get(i);
        viewHolder.name.setText(dto.getFriendName());
        viewHolder.content.setText(dto.getContent());
        ImageLoader.getInstance().displayImage(dto.getFriendAvatar(),viewHolder.cover);
        return view;
    }

    public class ViewHolder{
        public ImageView cover;
        public TextView name;
        public TextView content;
    }
}
