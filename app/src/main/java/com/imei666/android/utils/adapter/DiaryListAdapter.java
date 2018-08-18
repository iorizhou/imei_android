package com.imei666.android.utils.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.DiaryDTO;
import com.imei666.android.utils.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryListAdapter extends BaseAdapter {

    private List<DiaryDTO> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private Fragment mFragment;
    public DiaryListAdapter(Context ctx, List<DiaryDTO> datas){
        this.mContext = ctx;
        mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = datas;
        mImageLoader = ImageLoader.getInstance();
    }

    public void setDatas(List<DiaryDTO> data){
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
            view = mInflater.inflate(R.layout.item_diary,null);
            viewHolder.authorAvatar = (ImageView) view.findViewById(R.id.author_avatar);
            viewHolder.author = (TextView)view.findViewById(R.id.author);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            viewHolder.beforeCover = (ImageView)view.findViewById(R.id.before_cover);
            viewHolder.content = (TextView)view.findViewById(R.id.content);
            viewHolder.tag = (TextView)view.findViewById(R.id.tag);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        DiaryDTO dto = mDatas.get(i);
        viewHolder.tag.setText("#"+dto.getTag());
        viewHolder.author.setText(dto.getAuthor());
        viewHolder.time.setText(DateUtil.formatDate(dto.getPublishTime()));
        viewHolder.content.setText(dto.getSimpleContent());
        mImageLoader.displayImage(dto.getAuthorAvatar(),viewHolder.authorAvatar);
        mImageLoader.displayImage(dto.getCoverImg(),viewHolder.beforeCover);
        return view;
    }

    public class ViewHolder{
        public ImageView authorAvatar;
        public TextView author;
        public TextView time;
        public ImageView beforeCover;
        public TextView content;
        public TextView tag;
    }
}
