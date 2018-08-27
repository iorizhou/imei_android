package com.imei666.android.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.mvp.view.activity.ItemDetailActivity;
import com.imei666.android.mvp.view.activity.OrderDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {

    private List<OrderDTO> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private Fragment mFragment;
    public OrderListAdapter(Context ctx, List<OrderDTO> datas){
        this.mContext = ctx;
        mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = datas;
        mImageLoader = ImageLoader.getInstance();
    }

    public void setDatas(List<OrderDTO> data){
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
            view = mInflater.inflate(R.layout.item_orderlist,null);
            viewHolder.orderid = (TextView)view.findViewById(R.id.orderlist_orderid);
            viewHolder.itemname = (TextView)view.findViewById(R.id.orderlist_item_name);
            viewHolder.paystatus = (TextView)view.findViewById(R.id.orderlist_paystatus);
            viewHolder.doctorinfo = (TextView)view.findViewById(R.id.orderlist_doctorinfo);
            viewHolder.discount = (TextView)view.findViewById(R.id.orderlist_item_discount);
            viewHolder.buycount = (TextView)view.findViewById(R.id.orderlist_buycount);
            viewHolder.dj = (TextView)view.findViewById(R.id.orderlist_dj);
            viewHolder.itemAvatar = (ImageView) view.findViewById(R.id.orderlist_item_cover);
            viewHolder.kefu = (Button) view.findViewById(R.id.orderlist_kefu);
            viewHolder.detail = (Button) view.findViewById(R.id.orderlist_itemdetail);
            viewHolder.pay = (Button) view.findViewById(R.id.orderlist_gopay);
            viewHolder.mMainLayout = (RelativeLayout)view.findViewById(R.id.orderlist_item_main_layout);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        final OrderDTO dto = mDatas.get(i);
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order",dto);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if (dto.getOrderStatus()==3){
            viewHolder.detail.setText("再次购买");
        }else {
            viewHolder.detail.setText("项目详情");
        }
        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id",dto.getItemId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        viewHolder.orderid.setText("订单号: "+dto.getId());
        viewHolder.itemname.setText(dto.getItemName());
        switch (dto.getOrderStatus()){
            case 0:
                viewHolder.paystatus.setText("待支付");
                viewHolder.pay.setText("去支付");

                break;
            case 1:
                viewHolder.paystatus.setText("待消费");
                viewHolder.pay.setText("查看消费码");
                break;
            case 3:
                viewHolder.paystatus.setText("退款/过期");
                viewHolder.pay.setVisibility(View.INVISIBLE);

                break;
        }
        viewHolder.doctorinfo.setText("周鹏  深圳博爱");
        viewHolder.discount.setText("￥"+dto.getTotalPrice());
        viewHolder.buycount.setText("数量: "+dto.getBuyCount()+"");
        viewHolder.dj.setText("支付订金: "+dto.getNeedPayCount()+"元");
        return view;
    }

    public class ViewHolder{
        public TextView orderid;
        public TextView itemname;
        public TextView paystatus;
        public TextView doctorinfo;
        public TextView discount;
        public TextView buycount;
        public TextView dj;
        public ImageView itemAvatar;
        public Button kefu;
        public Button detail;
        public Button pay;
        public RelativeLayout mMainLayout;
    }
}
