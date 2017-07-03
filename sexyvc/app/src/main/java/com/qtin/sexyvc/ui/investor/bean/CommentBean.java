package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/7/3.
 */

public class CommentBean implements DataTypeInterface{

    private String domain_name;
    private long domain_id;
    private String u_nickname;
    private long comment_id;

    private String title;
    private String content;
    private long fund_id;
    private String fund_name;

    private long investor_id;
    private String investor_name;
    private float score;
    private int has_praise;

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public long getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(long domain_id) {
        this.domain_id = domain_id;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getHas_praise() {
        return has_praise;
    }

    public void setHas_praise(int has_praise) {
        this.has_praise = has_praise;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_COMMENT;
    }
}
/**


 "domain_name": "教育",
 "domain_id": 1,
 "u_nickname": "王昱斌",
 "comment_id": 1,

 "title": "我不能说我足够了解他们，但是我强烈推荐他们！",
 "content": "我和他们合作了不止一次了。",
 "fund_id": 40,
 "fund_name": "寒武创投",

 "investor_id": 388,
 "investor_name": "韩冰",
 "score": "3.0",
 "has_praise": 0
 */