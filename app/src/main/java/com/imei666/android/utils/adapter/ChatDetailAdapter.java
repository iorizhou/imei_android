/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.imei666.android.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imei666.android.mvp.model.dto.MessageDTO;

import org.kymjs.chat.ChatActivity.OnChatItemClickListener;
import org.kymjs.chat.R;
import org.kymjs.chat.UrlUtils;
import org.kymjs.chat.bean.Message;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class ChatDetailAdapter extends BaseAdapter {

    private final Context cxt;
    private List<MessageDTO> datas = null;
    private KJBitmap kjb;
    private OnChatItemClickListener listener;

    public ChatDetailAdapter(Context cxt, List<MessageDTO> datas, OnChatItemClickListener listener) {
        this.cxt = cxt;
        if (datas == null) {
            datas = new ArrayList<MessageDTO>(0);
        }
        this.datas = datas;
        kjb = new KJBitmap();
        this.listener = listener;
    }

    public void refresh(List<MessageDTO> datas) {
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).isOwnSend() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final ViewHolder holder;
        final MessageDTO data = datas.get(position);
        if (v == null) {
            holder = new ViewHolder();
            if (data.isOwnSend()) {
                v = View.inflate(cxt, R.layout.chat_item_list_right, null);
            } else {
                v = View.inflate(cxt, R.layout.chat_item_list_left, null);
            }
            holder.layout_content = (RelativeLayout) v.findViewById(R.id.chat_item_layout_content);
            holder.img_avatar = (ImageView) v.findViewById(R.id.chat_item_avatar);
            holder.img_chatimage = (ImageView) v.findViewById(R.id.chat_item_content_image);
            holder.img_sendfail = (ImageView) v.findViewById(R.id.chat_item_fail);
            holder.progress = (ProgressBar) v.findViewById(R.id.chat_item_progress);
            holder.tv_chatcontent = (TextView) v.findViewById(R.id.chat_item_content_text);
            holder.tv_date = (TextView) v.findViewById(R.id.chat_item_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tv_date.setText(StringUtils.friendlyTime(StringUtils.getDataTime("yyyy-MM-dd " +
                "HH:mm:ss")));
        holder.tv_date.setVisibility(View.VISIBLE);
        //如果是文本类型，则隐藏图片，如果是图片则隐藏文本
        if (data.getMessageType() == 0) {
            holder.img_chatimage.setVisibility(View.GONE);
            holder.tv_chatcontent.setVisibility(View.VISIBLE);
            if (data.getContent().contains("href")) {
                holder.tv_chatcontent = UrlUtils.handleHtmlText(holder.tv_chatcontent, data
                        .getContent());
            } else {
                holder.tv_chatcontent = UrlUtils.handleText(holder.tv_chatcontent, data
                        .getContent());
            }
        } else {
            holder.tv_chatcontent.setVisibility(View.GONE);
            holder.img_chatimage.setVisibility(View.VISIBLE);

            //如果内存缓存中有要显示的图片，且要显示的图片不是holder复用的图片，则什么也不做，否则显示一张加载中的图片
            if (kjb.getMemoryCache(data.getContent()) != null && data.getContent() != null &&
                    data.getContent().equals(holder.img_chatimage.getTag())) {
            } else {
                holder.img_chatimage.setImageResource(R.drawable.default_head);
            }
            kjb.display(holder.img_chatimage, data.getContent(), 300, 300);
        }

        //如果是表情或图片，则不显示气泡，如果是图片则显示气泡
        if (data.getMessageType() != 0) {
            holder.layout_content.setBackgroundResource(android.R.color.transparent);
        } else {
            if (data.isOwnSend()) {
                holder.layout_content.setBackgroundResource(R.drawable.chat_to_bg_selector);
            } else {
                holder.layout_content.setBackgroundResource(R.drawable.chat_from_bg_selector);
            }
        }

        //显示头像
        kjb.display(holder.img_avatar, data.getSenderAvatar());

        if (listener != null) {
            holder.tv_chatcontent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTextClick(position);
                }
            });
            holder.img_chatimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    switch (data.getType()) {
//                        case Message.MSG_TYPE_PHOTO:
//                            listener.onPhotoClick(position);
//                            break;
//                        case Message.MSG_TYPE_FACE:
//                            listener.onFaceClick(position);
//                            break;
//                    }
                }
            });
        }

        //消息发送的状态
        switch (data.getSendStatus()) {
            case 2:
                //失败
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.VISIBLE);
                break;
            case 1:
                //发送成功
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
            case 0:
                //发送中
                holder.progress.setVisibility(View.VISIBLE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
        }

        return v;
    }

    static class ViewHolder {
        TextView tv_date;
        ImageView img_avatar;
        TextView tv_chatcontent;
        ImageView img_chatimage;
        ImageView img_sendfail;
        ProgressBar progress;
        RelativeLayout layout_content;
    }
}
