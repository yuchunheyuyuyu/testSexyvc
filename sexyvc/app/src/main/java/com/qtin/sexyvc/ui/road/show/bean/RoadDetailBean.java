package com.qtin.sexyvc.ui.road.show.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/8.
 */

public class RoadDetailBean {
    private long id;
    private long u_id;
    private long investor_id;
    private long fund_id;

    private int status;
    private long create_time;
    private String source;
    private String investor_name;
    private String investor_avatar;

    private String fund_name;
    private String investor_title;
    private float investor_score;
    private int score;

    private long u_domain;
    private long u_stage;
    private String u_nickname;
    private String u_title;

    private String u_domain_name;
    private String u_stage_name;
    private int like;
    private ArrayList<AnswerContent> answer_content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getInvestor_title() {
        return investor_title;
    }

    public void setInvestor_title(String investor_title) {
        this.investor_title = investor_title;
    }

    public float getInvestor_score() {
        return investor_score;
    }

    public void setInvestor_score(float investor_score) {
        this.investor_score = investor_score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getU_title() {
        return u_title;
    }

    public void setU_title(String u_title) {
        this.u_title = u_title;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public ArrayList<AnswerContent> getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(ArrayList<AnswerContent> answer_content) {
        this.answer_content = answer_content;
    }

    public static class AnswerContent{
        private String question_title;
        private String option_content;

        private String sub_question_title;
        private ArrayList<SubOption> sub_options;
        private ArrayList<AddQuestion> add_questions;

        public String getQuestion_title() {
            return question_title;
        }

        public void setQuestion_title(String question_title) {
            this.question_title = question_title;
        }

        public String getOption_content() {
            return option_content;
        }

        public void setOption_content(String option_content) {
            this.option_content = option_content;
        }

        public String getSub_question_title() {
            return sub_question_title;
        }

        public void setSub_question_title(String sub_question_title) {
            this.sub_question_title = sub_question_title;
        }

        public ArrayList<SubOption> getSub_options() {
            return sub_options;
        }

        public void setSub_options(ArrayList<SubOption> sub_options) {
            this.sub_options = sub_options;
        }

        public ArrayList<AddQuestion> getAdd_questions() {
            return add_questions;
        }

        public void setAdd_questions(ArrayList<AddQuestion> add_questions) {
            this.add_questions = add_questions;
        }
    }

    public static class SubOption{
        private String option_content;

        public String getOption_content() {
            return option_content;
        }

        public void setOption_content(String option_content) {
            this.option_content = option_content;
        }
    }
    public static class AddQuestion{
        private String answer;
        private String title;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
