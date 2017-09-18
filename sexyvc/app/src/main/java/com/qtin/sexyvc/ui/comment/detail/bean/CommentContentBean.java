package com.qtin.sexyvc.ui.comment.detail.bean;

import com.qtin.sexyvc.ui.bean.AdditionalBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/28.
 */

public class CommentContentBean implements DataTypeInterface{

    private String u_nickname;
    private String u_avatar;
    private String type_name;
    private long type_id;
    private long comment_id;

    private String title;
    private String content;
    private String fund_name;

    private long investor_id;
    private String investor_name;
    private float score;
    private int reply_count;

    private int praise_count;
    private int has_praise;
    private String create_time;

    private String investor_avatar;
    private float investor_score;
    private int whether_author;
    private long investor_uid;
    private long fund_id;

    private int u_auth_type;
    private int u_auth_state;
    private int is_anon;

    public int getU_auth_type() {
        return u_auth_type;
    }

    public void setU_auth_type(int u_auth_type) {
        this.u_auth_type = u_auth_type;
    }

    public int getU_auth_state() {
        return u_auth_state;
    }

    public void setU_auth_state(int u_auth_state) {
        this.u_auth_state = u_auth_state;
    }

    public int getIs_anon() {
        return is_anon;
    }

    public void setIs_anon(int is_anon) {
        this.is_anon = is_anon;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public float getInvestor_score() {
        return investor_score;
    }

    public void setInvestor_score(float investor_score) {
        this.investor_score = investor_score;
    }

    public int getWhether_author() {
        return whether_author;
    }

    public void setWhether_author(int whether_author) {
        this.whether_author = whether_author;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private ArrayList<AdditionalBean> additional;

    public ArrayList<AdditionalBean> getAdditional() {
        return additional;
    }

    public void setAdditional(ArrayList<AdditionalBean> additional) {
        this.additional = additional;
    }

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
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

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public int getHas_praise() {
        return has_praise;
    }

    public void setHas_praise(int has_praise) {
        this.has_praise = has_praise;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_CONTENT;
    }
}
/**
 "u_avatar": "https://wx.qlogo.cn/mmopen/H7TrngfktapWXKOz5R2QOAkKw0qKR7DicpBmiaiaGNJuicxRyE8zsv6ynOEibKHOYSOgGh4vPhXJb6hYemobhdLibQMt6zI2yeUZLf/0",
 "type_name": "教育",
 "type_id": 1,
 "comment_id": 1,

 "title": "我不能说我足够了解他们，但是我强烈推荐他们！",
 "content": "我和他们合作了不止一次了，他们一直是我们非常棒的投资伙伴！记得我第一次签TS的时候，和他们谈判之后他们给的条款十分的公平，而且迅速地就完成了签署。一开始还是有点担心，毕竟听说很多大的投资机构喜欢先签TS，然后再慢慢挑选，不满意的最后就不投了，毕竟签了TS之后有一段时间的封锁期，万一最后什么钱都没拿到，时间也耽误了就麻烦了。但是他们很快就开始DD，整个过程迅速又严谨，一点都不拖泥带水。最令人印象深刻的是他们给我们的客户体验以及产品前景提供了很多的介绍和帮助，甚至给我们介绍了其他的投资者。在接下来的交流中，他们也积极地和我们讨论战略和操作问题，我每周都会和他们一次见面，谈话总是以“我们怎么能帮助你”开始，我不能说我足够了解他们，但是我强烈推荐他们，尤其是创业者在初期或者早期融资的时候，你会得到比钱更多更珍贵的东西。",
 "fund_id": 40,
 "fund_name": "寒武创投",

 "investor_id": 388,
 "investor_name": "韩冰",
 "score": "3.0",
 "reply_count": 6,

 "praise_count": 2,
 "has_praise": 0
 */