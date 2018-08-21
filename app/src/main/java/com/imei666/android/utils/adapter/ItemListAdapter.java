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
            viewHolder.name = (TextView)view.findViewById(R.id.item_name);
            viewHolder.cover = (ImageView)view.findViewById(R.id.item_cover);
            viewHolder.doctor = (TextView)view.findViewById(R.id.item_doctor_name);
            viewHolder.hospital = (TextView)view.findViewById(R.id.item_hospital);
            viewHolder.discount = (TextView)view.findViewById(R.id.item_discount);
            viewHolder.oriPrice = (TextView)view.findViewById(R.id.item_orig_price);
            viewHolder.oriPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ItemDTO dto = mDatas.get(i);
        viewHolder.name.setText(dto.getName());
        viewHolder.doctor.setText(dto.getDoctorName());
        viewHolder.hospital.setText(dto.getHospitalName());
        viewHolder.discount.setText(dto.getDiscountPrice()+"");
        viewHolder.oriPrice.setText(dto.getOrigPrice()+"");
        ImageLoader.getInstance().displayImage(dto.getCover(),viewHolder.cover);
        return view;
    }

    public class ViewHolder{
        public ImageView cover;
        public TextView name;
        public TextView doctor;
        public TextView hospital;
        public TextView discount;
        public TextView oriPrice;
    }
}
