package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.road.show.bean.RoadDetailBean;
import com.qtin.sexyvc.ui.road.show.bean.RoadDetailBean.AnswerContent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/9/11.
 */
public class RoadView extends LinearLayout {
    public RoadView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public RoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public RoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public void setData(ArrayList<AnswerContent> data) {
        removeAllViews();
        if(data==null||data.isEmpty()){
            setVisibility(View.GONE);
            return;
        }
        for(int i=0;i<data.size();i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_road_question, this, false);
            ViewHolder holder=new ViewHolder(view);
            AnswerContent bean=data.get(i);

            holder.tvQuestionIndex.setText("Q"+(i+1));
            holder.tvQuestionTitle.setText(StringUtil.formatString(bean.getQuestion_title()));
            holder.tvOptionContent.setText("- "+StringUtil.formatString(bean.getOption_content()));

            if(bean.getSub_options()==null||bean.getSub_options().isEmpty()){
                holder.tvSubQuestionTitle.setVisibility(View.GONE);
                holder.llSubOptions.setVisibility(View.GONE);
            }else{
                holder.tvSubQuestionTitle.setVisibility(View.VISIBLE);
                holder.llSubOptions.setVisibility(View.VISIBLE);

                holder.tvSubQuestionTitle.setText(StringUtil.formatString(bean.getSub_question_title()));
                holder.llSubOptions.removeAllViews();
                for(RoadDetailBean.SubOption option:bean.getSub_options()){
                    TextView textView=new TextView(getContext());
                    textView.setText("- "+option.getOption_content());
                    textView.setTextColor(getResources().getColor(R.color.black90));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                    textView.setLineSpacing(DeviceUtils.dip2px(getContext(),6),1.0f);

                    holder.llSubOptions.addView(textView);
                }
            }

            if(bean.getAdd_questions()==null||bean.getAdd_questions().isEmpty()){
                holder.tvAddQuestionTag.setVisibility(View.GONE);
                holder.llAddQuestions.setVisibility(View.GONE);
            }else{
                holder.tvAddQuestionTag.setVisibility(View.VISIBLE);
                holder.llAddQuestions.setVisibility(View.VISIBLE);
                holder.llAddQuestions.removeAllViews();

                for(RoadDetailBean.AddQuestion option:bean.getAdd_questions()){
                    if(!StringUtil.isBlank(option.getAnswer())){
                        TextView tvTitle=new TextView(getContext());
                        tvTitle.setText("- "+option.getTitle());
                        tvTitle.setTextColor(getResources().getColor(R.color.black90));
                        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                        tvTitle.setLineSpacing(DeviceUtils.dip2px(getContext(),6),1.0f);

                        TextView tvAnswer=new TextView(getContext());
                        tvAnswer.setText("答："+(StringUtil.isBlank(option.getAnswer())?"无":option.getAnswer()));
                        tvAnswer.setTextColor(getResources().getColor(R.color.black90));
                        tvAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                        tvAnswer.setLineSpacing(DeviceUtils.dip2px(getContext(),6),1.0f);

                        holder.llAddQuestions.addView(tvTitle);
                        holder.llAddQuestions.addView(tvAnswer);
                    }
                }
            }
            addView(view);
        }
    }

    static class ViewHolder {
        @BindView(R.id.tvQuestionIndex)
        TextView tvQuestionIndex;
        @BindView(R.id.tvQuestionTitle)
        TextView tvQuestionTitle;
        @BindView(R.id.tvOptionContent)
        TextView tvOptionContent;
        @BindView(R.id.tvSubQuestionTitle)
        TextView tvSubQuestionTitle;
        @BindView(R.id.llSubOptions)
        LinearLayout llSubOptions;
        @BindView(R.id.tvAddQuestionTag)
        TextView tvAddQuestionTag;
        @BindView(R.id.llAddQuestions)
        LinearLayout llAddQuestions;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
