package com.qtin.sexyvc.ui.investor.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/7/3.
 */

public class CommentBean implements DataTypeInterface, Parcelable {

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
    private long create_time;

    private int u_auth_type;
    private int u_auth_state;
    private int is_anon;
    private int like;

    //路演列表数据
    private String u_title;
    private long u_domain;
    private long u_stage;
    private String source;

    private long id;
    private String investor_avatar;
    private String investor_title;
    private String u_domain_name;
    private String u_stage_name;

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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getU_title() {
        return u_title;
    }

    public void setU_title(String u_title) {
        this.u_title = u_title;
    }

    public long getU_domain() {
        return u_domain;
    }

    public void setU_domain(long u_domain) {
        this.u_domain = u_domain;
    }

    public long getU_stage() {
        return u_stage;
    }

    public void setU_stage(long u_stage) {
        this.u_stage = u_stage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public String getInvestor_title() {
        return investor_title;
    }

    public void setInvestor_title(String investor_title) {
        this.investor_title = investor_title;
    }

    public String getU_domain_name() {
        return u_domain_name;
    }

    public void setU_domain_name(String u_domain_name) {
        this.u_domain_name = u_domain_name;
    }

    public String getU_stage_name() {
        return u_stage_name;
    }

    public void setU_stage_name(String u_stage_name) {
        this.u_stage_name = u_stage_name;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_COMMENT;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.domain_name);
        dest.writeLong(this.domain_id);
        dest.writeString(this.u_nickname);
        dest.writeLong(this.comment_id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.fund_id);
        dest.writeString(this.fund_name);
        dest.writeLong(this.investor_id);
        dest.writeString(this.investor_name);
        dest.writeFloat(this.score);
        dest.writeInt(this.has_praise);
        dest.writeLong(this.create_time);
        dest.writeInt(this.u_auth_type);
        dest.writeInt(this.u_auth_state);
        dest.writeInt(this.is_anon);
        dest.writeInt(this.like);
        dest.writeString(this.u_title);
        dest.writeLong(this.u_domain);
        dest.writeLong(this.u_stage);
        dest.writeString(this.source);
        dest.writeLong(this.id);
        dest.writeString(this.investor_avatar);
        dest.writeString(this.investor_title);
        dest.writeString(this.u_domain_name);
        dest.writeString(this.u_stage_name);
    }

    public CommentBean() {
    }

    protected CommentBean(Parcel in) {
        this.domain_name = in.readString();
        this.domain_id = in.readLong();
        this.u_nickname = in.readString();
        this.comment_id = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
        this.fund_id = in.readLong();
        this.fund_name = in.readString();
        this.investor_id = in.readLong();
        this.investor_name = in.readString();
        this.score = in.readFloat();
        this.has_praise = in.readInt();
        this.create_time = in.readLong();
        this.u_auth_type = in.readInt();
        this.u_auth_state = in.readInt();
        this.is_anon = in.readInt();
        this.like = in.readInt();
        this.u_title = in.readString();
        this.u_domain = in.readLong();
        this.u_stage = in.readLong();
        this.source = in.readString();
        this.id = in.readLong();
        this.investor_avatar = in.readString();
        this.investor_title = in.readString();
        this.u_domain_name = in.readString();
        this.u_stage_name = in.readString();
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel source) {
            return new CommentBean(source);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };
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