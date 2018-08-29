package com.imei666.android.mvp.view.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.db.DBUtil;
import com.imei666.android.mvp.model.dto.MessageDTO;
import com.imei666.android.mvp.model.dto.MessageDTODao;
import com.imei666.android.mvp.model.dto.MessageListDTO;
import com.imei666.android.mvp.model.dto.MessageListDTODao;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.mvp.view.activity.MainActivity;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.service.MessageService;
import com.imei666.android.utils.MessageNotificationDispatcher;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.ChatDetailAdapter;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.greendao.DbUtils;
import org.greenrobot.greendao.query.QueryBuilder;
import org.kymjs.chat.ChatActivity;
import org.kymjs.chat.OnOperationListener;
import org.kymjs.chat.adapter.ChatAdapter;
import org.kymjs.chat.bean.Emojicon;
import org.kymjs.chat.bean.Faceicon;
import org.kymjs.chat.bean.Message;
import org.kymjs.chat.emoji.DisplayRules;
import org.kymjs.chat.widget.KJChatKeyboard;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ChatDetailFragment extends BaseFragment {

    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x1;
    @BindView(R.id.chat_msg_input_box)
    KJChatKeyboard box;
    @BindView(R.id.chat_listview)
    ListView mRealListView;
    private long mFriendId;

    List<MessageDTO> datas = new ArrayList<MessageDTO>();
    private ChatDetailAdapter adapter;
    private MessageService mMsgService;



    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MessageService.MyBinder binder = (MessageService.MyBinder)iBinder;
            Toasty.info(getActivity(),"service call "+binder.getService().getHello(),Toast.LENGTH_SHORT).show();
            mMsgService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private void bindMessageService(){
        Intent intent = new Intent(getActivity(), MessageService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFriendId = getArguments().getLong("friendId");
        bindMessageService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chatdetail,null);
        ButterKnife.bind(this,view);
        return view;
    }

    private void loadMsg(){
        QueryBuilder queryBuilder = DBUtil.getInstance(getActivity()).getDaoSession().getMessageDTODao().queryBuilder();
        queryBuilder.or(MessageDTODao.Properties.SenderId.eq(mFriendId),MessageDTODao.Properties.RecverId.eq(mFriendId));
        datas = queryBuilder.list();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRealListView.setSelector(android.R.color.transparent);
        loadMsg();
        initMessageInputToolBox();
        initListView();
        mRealListView.setSelection(datas.size()-1);
        MessageNotificationDispatcher.getInstance().regMessageNotification("chat_detail", new MessageNotificationDispatcher.MessageListener() {
            @Override
            public void onMessage(String content) {
                adapter.refresh(datas);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageNotificationDispatcher.getInstance().unregMessageNotification("chat_detail");

    }



    private void initListView() {


        adapter = new ChatDetailAdapter(getActivity(), datas, getOnChatItemClickListener());
        mRealListView.setAdapter(adapter);
    }
    private void initMessageInputToolBox() {
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                final MessageDTO tmpMsg = new MessageDTO();
                tmpMsg.setSendStatus(0);
                tmpMsg.setOwnSend(true);
                tmpMsg.setContent(content);
                tmpMsg.setMessageType(0);
                tmpMsg.setRecverId(mFriendId);
                tmpMsg.setSenderId(1);
                datas.add(tmpMsg);
                adapter.refresh(datas);
                if (mMsgService!=null){
                    mMsgService.sendMsg(content, tmpMsg, mFriendId, new MessageService.SendMsgListener() {
                        @Override
                        public void onSendSuccess(MessageDTO messageDTO) {
                            loadMsg();
                            adapter.refresh(datas);
                        }

                        @Override
                        public void onSendFailed(int msgCode, String msg) {
                            //找出该tmpMsg在datas队列里的index,更新它
                            tmpMsg.setSendStatus(2);
                            adapter.refresh(datas);
                        }
                    });
                }
            }

            @Override
            public void selectedFace(Faceicon content) {
//                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS,
//                        "Tom", "avatar", "Jerry", "avatar", content.getPath(), true, true, new
//                        Date());
//                datas.add(message);
//                adapter.refresh(datas);
//                createReplayMsg(message);
            }

            @Override
            public void selectedEmoji(Emojicon emoji) {
                box.getEditTextBox().append(emoji.getValue());
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
                DisplayRules.backspace(box.getEditTextBox());
            }

            @Override
            public void selectedFunction(int index) {
                switch (index) {
                    case 0:
                        goToAlbum();
                        break;
                    case 1:
                        ViewInject.toast("跳转相机");
                        break;
                }
            }
        });

        List<String> faceCagegory = new ArrayList<>();
//        File faceList = FileUtils.getSaveFolder("chat");
        File faceList = new File("");
        if (faceList.isDirectory()) {
            File[] faceFolderArray = faceList.listFiles();
            for (File folder : faceFolderArray) {
                if (!folder.isHidden()) {
                    faceCagegory.add(folder.getAbsolutePath());
                }
            }
        }

        box.setFaceData(faceCagegory);
        mRealListView.setOnTouchListener(getOnTouchListener());
    }

    /**
     * 跳转到选择相册界面
     */
    private void goToAlbum() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
//            Uri dataUri = data.getData();
//            if (dataUri != null) {
//                File file = FileUtils.uri2File(getActivity(), dataUri);
//                Message message = new Message(Message.MSG_TYPE_PHOTO, Message.MSG_STATE_SUCCESS,
//                        "Tom", "avatar", "Jerry",
//                        "avatar", file.getAbsolutePath(), true, true, new Date());
//                datas.add(message);
//                adapter.refresh(datas);
//            }
//        }
    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                box.hideLayout();
                box.hideKeyboard(getActivity());
                return false;
            }
        };
    }
    public static ChatDetailFragment newInStance(long friendId){
        ChatDetailFragment fragment = new ChatDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("friendId",friendId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ChatActivity.OnChatItemClickListener getOnChatItemClickListener() {
        return new ChatActivity.OnChatItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                KJLoger.debug(datas.get(position).getContent() + "点击图片的");
            }

            @Override
            public void onTextClick(int position) {
            }

            @Override
            public void onFaceClick(int position) {
            }
        };
    }
}
