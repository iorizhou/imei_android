package com.imei666.android.mvp.model.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class MessageListDTO{
    @Id
    private long friendId;
    private String friendAvatar;
    private String friendName;
    private String content;
    private String time;






    public MessageListDTO() {
    }




    @Generated(hash = 2029229472)
    public MessageListDTO(long friendId, String friendAvatar, String friendName,
            String content, String time) {
        this.friendId = friendId;
        this.friendAvatar = friendAvatar;
        this.friendName = friendName;
        this.content = content;
        this.time = time;
    }




    public String getFriendAvatar() {
        return friendAvatar;
    }

    public void setFriendAvatar(String friendAvatar) {
        this.friendAvatar = friendAvatar;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }
}
