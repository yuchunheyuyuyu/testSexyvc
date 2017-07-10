package com.qtin.sexyvc.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ls on 17/7/6.
 */
@Entity
public class KeyWordBean {

    private String keyWord;
    @Id
    private long createTime;

    @Generated(hash = 255788455)
    public KeyWordBean(String keyWord, long createTime) {
        this.keyWord = keyWord;
        this.createTime = createTime;
    }

    @Generated(hash = 673900408)
    public KeyWordBean() {
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
