package com.qtin.sexyvc.ui.fund.detail.bean;

import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.investor.bean.RoadShowBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/5.
 */

public class FundDetailBean implements DataTypeInterface{
    private long fund_id;
    private String fund_name;
    private String fund_intro;
    private String fund_logo;

    private int comment_number;
    private int case_number;
    private int investor_number;
    private float score;

    private ArrayList<FilterEntity> domain_list;
    private ArrayList<FilterEntity> stage_list;
    private ArrayList<CaseBean> case_list;
    private ArrayList<InvestorEntity> investor_list;

    private RoadShowBean road_show;
    private ArrayList<TagEntity> tags;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getFund_intro() {
        return fund_intro;
    }

    public void setFund_intro(String fund_intro) {
        this.fund_intro = fund_intro;
    }

    public String getFund_logo() {
        return fund_logo;
    }

    public void setFund_logo(String fund_logo) {
        this.fund_logo = fund_logo;
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

    public int getInvestor_number() {
        return investor_number;
    }

    public void setInvestor_number(int investor_number) {
        this.investor_number = investor_number;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public ArrayList<InvestorEntity> getInvestor_list() {
        return investor_list;
    }

    public void setInvestor_list(ArrayList<InvestorEntity> investor_list) {
        this.investor_list = investor_list;
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
/**


 "fund_id": 1,
 "fund_name": "IDG资本",
 "fund_intro": "IDG资本专注于与中国市场有关的VC/PE投资项目s",
 "fund_logo": "image/2016/12/09/c76427cff9a48f4ef8e3ce3b0249c2d6.jpg",

 "comment_number": 3,
 "case_number": 0,
 "investor_number": 479,
 "score": "6.4",
 "link_list": [],

 */