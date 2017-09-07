package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.qtin.sexyvc.mvp.model.entity.DaoSession;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import java.util.List;

/**
 * Created by ls on 17/6/15.
 */
@Entity
public class UserInfoEntity implements Parcelable {
    @Id
    private String  token;
    private int has_project;
    private String business_card;
    private String u_nickname;

    private int u_gender;
    private String u_avatar;
    private String u_signature;
    private String u_phone;

    private String u_email;
    private String u_backup_phone;
    private String u_backup_email;
    private String u_company;

    private String u_title;
    private int u_auth_state;
    private int u_auth_type;//0未填写，1投资人，2创始人，3FA

    private int has_comment;
    private int has_roadshow;

    private long investor_id;
    private String investor_name;

    @ToMany(referencedJoinProperty = "type_id")
    private List<FilterEntity> domain_list;
    @ToMany(referencedJoinProperty = "type_id")
    private List<FilterEntity> stage_list;
    private int case_number;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getHas_project() {
        return has_project;
    }

    public void setHas_project(int has_project) {
        this.has_project = has_project;
    }

    public String getBusiness_card() {
        return business_card;
    }

    public void setBusiness_card(String business_card) {
        this.business_card = business_card;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public int getU_gender() {
        return u_gender;
    }

    public void setU_gender(int u_gender) {
        this.u_gender = u_gender;
    }

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getU_signature() {
        return u_signature;
    }

    public void setU_signature(String u_signature) {
        this.u_signature = u_signature;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_backup_phone() {
        return u_backup_phone;
    }

    public void setU_backup_phone(String u_backup_phone) {
        this.u_backup_phone = u_backup_phone;
    }

    public String getU_backup_email() {
        return u_backup_email;
    }

    public void setU_backup_email(String u_backup_email) {
        this.u_backup_email = u_backup_email;
    }

    public String getU_company() {
        return u_company;
    }

    public void setU_company(String u_company) {
        this.u_company = u_company;
    }

    public String getU_title() {
        return u_title;
    }

    public void setU_title(String u_title) {
        this.u_title = u_title;
    }

    public int getU_auth_state() {
        return u_auth_state;
    }

    public void setU_auth_state(int u_auth_state) {
        this.u_auth_state = u_auth_state;
    }

    public int getU_auth_type() {
        return u_auth_type;
    }

    public void setU_auth_type(int u_auth_type) {
        this.u_auth_type = u_auth_type;
    }

    public int getHas_comment() {
        return has_comment;
    }

    public void setHas_comment(int has_comment) {
        this.has_comment = has_comment;
    }

    public int getHas_roadshow() {
        return has_roadshow;
    }

    public void setHas_roadshow(int has_roadshow) {
        this.has_roadshow = has_roadshow;
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
    @Keep
    public List<FilterEntity> getDomain_list() {
        return domain_list;
    }

    public void setDomain_list(List<FilterEntity> domain_list) {
        this.domain_list = domain_list;
    }
    @Keep
    public List<FilterEntity> getStage_list() {
        return stage_list;
    }

    public void setStage_list(List<FilterEntity> stage_list) {
        this.stage_list = stage_list;
    }

    public int getCase_number() {
        return case_number;
    }

    public void setCase_number(int case_number) {
        this.case_number = case_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.has_project);
        dest.writeString(this.business_card);
        dest.writeString(this.u_nickname);
        dest.writeInt(this.u_gender);
        dest.writeString(this.u_avatar);
        dest.writeString(this.u_signature);
        dest.writeString(this.u_phone);
        dest.writeString(this.u_email);
        dest.writeString(this.u_backup_phone);
        dest.writeString(this.u_backup_email);
        dest.writeString(this.u_company);
        dest.writeString(this.u_title);
        dest.writeInt(this.u_auth_state);
        dest.writeInt(this.u_auth_type);
        dest.writeInt(this.has_comment);
        dest.writeInt(this.has_roadshow);
        dest.writeLong(this.investor_id);
        dest.writeString(this.investor_name);
        dest.writeTypedList(this.domain_list);
        dest.writeTypedList(this.stage_list);
        dest.writeInt(this.case_number);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1806225593)
    public synchronized void resetDomain_list() {
        domain_list = null;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1927749801)
    public synchronized void resetStage_list() {
        stage_list = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 750861190)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserInfoEntityDao() : null;
    }

    public UserInfoEntity() {
    }

    protected UserInfoEntity(Parcel in) {
        this.token = in.readString();
        this.has_project = in.readInt();
        this.business_card = in.readString();
        this.u_nickname = in.readString();
        this.u_gender = in.readInt();
        this.u_avatar = in.readString();
        this.u_signature = in.readString();
        this.u_phone = in.readString();
        this.u_email = in.readString();
        this.u_backup_phone = in.readString();
        this.u_backup_email = in.readString();
        this.u_company = in.readString();
        this.u_title = in.readString();
        this.u_auth_state = in.readInt();
        this.u_auth_type = in.readInt();
        this.has_comment = in.readInt();
        this.has_roadshow = in.readInt();
        this.investor_id = in.readLong();
        this.investor_name = in.readString();
        this.domain_list = in.createTypedArrayList(FilterEntity.CREATOR);
        this.stage_list = in.createTypedArrayList(FilterEntity.CREATOR);
        this.case_number = in.readInt();
    }

    @Generated(hash = 1648464340)
    public UserInfoEntity(String token, int has_project, String business_card, String u_nickname, int u_gender, String u_avatar, String u_signature,
            String u_phone, String u_email, String u_backup_phone, String u_backup_email, String u_company, String u_title, int u_auth_state,
            int u_auth_type, int has_comment, int has_roadshow, long investor_id, String investor_name, int case_number) {
        this.token = token;
        this.has_project = has_project;
        this.business_card = business_card;
        this.u_nickname = u_nickname;
        this.u_gender = u_gender;
        this.u_avatar = u_avatar;
        this.u_signature = u_signature;
        this.u_phone = u_phone;
        this.u_email = u_email;
        this.u_backup_phone = u_backup_phone;
        this.u_backup_email = u_backup_email;
        this.u_company = u_company;
        this.u_title = u_title;
        this.u_auth_state = u_auth_state;
        this.u_auth_type = u_auth_type;
        this.has_comment = has_comment;
        this.has_roadshow = has_roadshow;
        this.investor_id = investor_id;
        this.investor_name = investor_name;
        this.case_number = case_number;
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel source) {
            return new UserInfoEntity(source);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 681261568)
    private transient UserInfoEntityDao myDao;
}
/**
 "u_nickname": "王昱斌昵称",
 "u_gender": "",
 "u_avatar": "https://wx.qlogo.cn/mmopen/H7TrngfktapWXKOz5R2QOAkKw0qKR7DicpBmiaiaGNJuicxRyE8zsv6ynOEibKHOYSOgGh4vPhXJb6hYemobhdLibQMt6zI2yeUZLf/0",
 "u_signature": "",
 "u_company": "",
 "u_title": "",
 "u_phone": "15221014211",
 "u_email": "test@qq.com",
 "u_backup_phone": "",
 "u_backup_email": "",
 "u_auth_state": "",
 "u_auth_type": ""

 */