package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/6.
 */
public class FundEntity implements DataTypeInterface{
    private long fund_id;
    private String fund_name;
    private String fund_logo;

    private int investor_number;
    private int comment_number;
    private float score;
    private ArrayList<TagEntity> tags;

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getFund_logo() {
        return fund_logo;
    }

    public void setFund_logo(String fund_logo) {
        this.fund_logo = fund_logo;
    }

    public int getInvestor_number() {
        return investor_number;
    }

    public void setInvestor_number(int investor_number) {
        this.investor_number = investor_number;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_FUND;
    }
}
/**

 "fund_id": 1,
 "fund_name": "IDG资本",
 "fund_logo": "image/2016/12/09/c76427cff9a48f4ef8e3ce3b0249c2d6.jpg",
 "investor_number": 479,
 "comment_number": 3,
 "score": "6.4",
 "tags": [
 {
 "tag_count": 2,
 "tag_id": 2,
 "tag_name": "医疗"
 }
 ]

 */