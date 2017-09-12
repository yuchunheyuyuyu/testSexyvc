package com.qtin.sexyvc.ui.road.action.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/13.
 */

public class RoadRequest {
    private String token;
    private long fund_id;
    private long investor_id;
    private String answers;
    private long stage_id;
    private int is_anon;

    public long getStage_id() {
        return stage_id;
    }

    public void setStage_id(long stage_id) {
        this.stage_id = stage_id;
    }

    public int getIs_anon() {
        return is_anon;
    }

    public void setIs_anon(int is_anon) {
        this.is_anon = is_anon;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public static class AnswerItem{
        private long question_id;
        private long option_id;
        private ArrayList<AddQuestionBean> add_questions;
        private long sub_question_id;
        private ArrayList<OptionSecondBean> sub_options;

        public long getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(long question_id) {
            this.question_id = question_id;
        }

        public long getOption_id() {
            return option_id;
        }

        public void setOption_id(long option_id) {
            this.option_id = option_id;
        }

        public ArrayList<AddQuestionBean> getAdd_questions() {
            return add_questions;
        }

        public void setAdd_questions(ArrayList<AddQuestionBean> add_questions) {
            this.add_questions = add_questions;
        }

        public long getSub_questionid() {
            return sub_question_id;
        }

        public void setSub_questionid(long sub_questionid) {
            this.sub_question_id = sub_questionid;
        }

        public ArrayList<OptionSecondBean> getSub_options() {
            return sub_options;
        }

        public void setSub_options(ArrayList<OptionSecondBean> sub_options) {
            this.sub_options = sub_options;
        }
    }
}
