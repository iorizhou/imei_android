package com.imei666.android.mvp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imei666.android.R;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDetailFragment extends BaseFragment {

    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x1;
    @BindView(R.id.chat_msg_input_box)
    KJChatKeyboard box;
    @BindView(R.id.chat_listview)
    ListView mRealListView;


    List<Message> datas = new ArrayList<Message>();
    private ChatAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chatdetail,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRealListView.setSelector(android.R.color.transparent);
        initMessageInputToolBox();
        initListView();
    }


    private void initListView() {
        byte[] emoji = new byte[]{
                (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81
        };
        Message message = new Message(Message.MSG_TYPE_TEXT,
                Message.MSG_STATE_SUCCESS, "\ue415", "avatar", "Jerry", "avatar",
                new String(emoji), false, true, new Date(System.currentTimeMillis()
                - (1000 * 60 * 60 * 24) * 8));
        Message message1 = new Message(Message.MSG_TYPE_TEXT,
                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
                "以后的版本支持链接高亮喔:http://www.kymjs.com支持http、https、svn、ftp开头的链接",
                true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8));
        Message message2 = new Message(Message.MSG_TYPE_PHOTO,
                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
                "http://static.oschina.net/uploads/space/2015/0611/103706_rpPc_1157342.png",
                false, true, new Date(
                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
        Message message6 = new Message(Message.MSG_TYPE_TEXT,
                Message.MSG_STATE_FAIL, "Tom", "avatar", "Jerry", "avatar",
                "test send fail", true, false, new Date(
                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
        Message message7 = new Message(Message.MSG_TYPE_TEXT,
                Message.MSG_STATE_SENDING, "Tom", "avatar", "Jerry", "avatar",
                "<a href=\"http://kymjs.com\">自定义链接</a>也是支持的", true, true, new Date(System.currentTimeMillis()
                - (1000 * 60 * 60 * 24) * 6));

        datas.add(message);
        datas.add(message1);
        datas.add(message2);
        datas.add(message6);
        datas.add(message7);

        adapter = new ChatAdapter(getActivity(), datas, getOnChatItemClickListener());
        mRealListView.setAdapter(adapter);
    }
    private void initMessageInputToolBox() {
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                Message message = new Message(Message.MSG_TYPE_TEXT, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry",
                        "avatar", content, true, true, new Date());
                datas.add(message);
                adapter.refresh(datas);
//                createReplayMsg(message);
            }

            @Override
            public void selectedFace(Faceicon content) {
                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry", "avatar", content.getPath(), true, true, new
                        Date());
                datas.add(message);
                adapter.refresh(datas);
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
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
            Uri dataUri = data.getData();
            if (dataUri != null) {
                File file = FileUtils.uri2File(getActivity(), dataUri);
                Message message = new Message(Message.MSG_TYPE_PHOTO, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry",
                        "avatar", file.getAbsolutePath(), true, true, new Date());
                datas.add(message);
                adapter.refresh(datas);
            }
        }
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
