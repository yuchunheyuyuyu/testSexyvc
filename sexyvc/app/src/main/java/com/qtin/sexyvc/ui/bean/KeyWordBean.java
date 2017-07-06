package com.qtin.sexyvc.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ls on 17/7/6.
 */
@Entity
public class KeyWordBean {

    private String keyWord;

    @Generated(hash = 529484218)
    public KeyWordBean(String keyWord) {
        this.keyWord = keyWord;
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
}
