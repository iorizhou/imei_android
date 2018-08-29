package com.imei666.android.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.db.DBUtil;
import com.imei666.android.mvp.model.dto.MessageDTO;
import com.imei666.android.mvp.model.dto.MessageDTODao;
import com.imei666.android.mvp.model.dto.MessageListDTO;
import com.imei666.android.mvp.model.dto.MessageListDTODao;
import com.imei666.android.mvp.view.activity.ChatDetailActivity;
import com.imei666.android.mvp.view.activity.MainActivity;
import com.imei666.android.utils.Constants;
import com.imei666.android.utils.MessageNotificationDispatcher;
import com.imei666.android.utils.adapter.MsgListAdapter;

import org.kymjs.chat.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MsgpageFragment extends BaseFragment {

    @BindView(R.id.msg_listview)
    ListView mListView;

    private List<MessageListDTO> mDatas;

    private MsgListAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageNotificationDispatcher.getInstance().regMessageNotification("msg_fragment", new MessageNotificationDispatcher.MessageListener() {
            @Override
            public void onMessage(String content) {
                try{
                    mDatas = DBUtil.getInstance(getActivity()).getDaoSession().getMessageListDTODao().queryBuilder().orderDesc(MessageListDTODao.Properties.Time).list();
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_msg,null);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas = DBUtil.getInstance(getActivity()).getDaoSession().getMessageListDTODao().queryBuilder().orderDesc(MessageListDTODao.Properties.Time).list();
        mAdapter = new MsgListAdapter(getActivity(),mDatas);
        mListView.setAdapter(mAdapter);
        String token  = Constants.TENCENT_XINGE_TOKEN;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("friendId",mDatas.get(i).getFriendId());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageNotificationDispatcher.getInstance().unregMessageNotification("msg_fragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatas = DBUtil.getInstance(getActivity()).getDaoSession().getMessageListDTODao().queryBuilder().orderDesc(MessageListDTODao.Properties.Time).list();
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
    }
}
