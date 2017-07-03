package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/3.
 */
public class InvestorBean implements DataTypeInterface{
    private long investor_id;
    private String investor_name;
    private String investor_intro;
    private String investor_avatar;

    private String investor_title;
    private String fund_name;
    private int comment_number;
    private int case_number;

    private ArrayList<FilterEntity> domain_list;
    private ArrayList<FilterEntity> stage_list;
    private ArrayList<CaseBean> case_list;

    private int has_follow;
    private RoadShowBean road_show;
    private ArrayList<TagEntity> tags;

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

    public String getInvestor_intro() {
        return investor_intro;
    }

    public void setInvestor_intro(String investor_intro) {
        this.investor_intro = investor_intro;
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

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public int getCase_number() {
        return case_number;
    }

    public void setCase_number(int case_number) {
        this.case_number = case_number;
    }

    public ArrayList<FilterEntity> getDomain_list() {
        return domain_list;
    }

    public void setDomain_list(ArrayList<FilterEntity> domain_list) {
        this.domain_list = domain_list;
    }

    public ArrayList<FilterEntity> getStage_list() {
        return stage_list;
    }

    public void setStage_list(ArrayList<FilterEntity> stage_list) {
        this.stage_list = stage_list;
    }

    public ArrayList<CaseBean> getCase_list() {
        return case_list;
    }

    public void setCase_list(ArrayList<CaseBean> case_list) {
        this.case_list = case_list;
    }

    public int getHas_follow() {
        return has_follow;
    }

    public void setHas_follow(int has_follow) {
        this.has_follow = has_follow;
    }

    public RoadShowBean getRoad_show() {
        return road_show;
    }

    public void setRoad_show(RoadShowBean road_show) {
        this.road_show = road_show;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_CONTENT;
    }
}