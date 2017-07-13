package com.qtin.sexyvc.ui.road;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.OnSpecialClickListener;
import com.qtin.sexyvc.ui.road.bean.OnOptionClickListener;
import com.qtin.sexyvc.ui.road.bean.OptionFirstBean;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/11.
 */
public class RoadQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private int index;
    private int total;
    private OnSpecialClickListener onSpecialClickListener;
    private OnOptionClickListener onOptionClickListener;

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener;
    }

    public void setOnSpecialClickListener(OnSpecialClickListener onSpecialClickListener) {
        this.onSpecialClickListener = onSpecialClickListener;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public RoadQuestionAdapter(Context context, ArrayList<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == DataTypeInterface.TYPE_QUESTION) {
            view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
            return new QuestionHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_OPTION) {
            view = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
            return new OptionHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof QuestionHolder){
            QuestionHolder holder= (QuestionHolder) viewHolder;
            QuestionBean questionBean= (QuestionBean) data.get(position);

            setQuestion(questionBean,holder,position);
        }else if(viewHolder instanceof OptionHolder){
            OptionHolder holder= (OptionHolder) viewHolder;
            OptionFirstBean bean= (OptionFirstBean) data.get(position);

            setOption(bean,holder,position);
        }
    }

    private void setOption(final OptionFirstBean bean, OptionHolder holder, final int position){

        //选项内容
        holder.tvName.setText(StringUtil.formatString(bean.getOption_content()));
        if(bean.isSelected()){
            holder.viewDot.setVisibility(View.VISIBLE);
            holder.tvName.setSelected(true);
            holder.linkContainer.setVisibility(View.VISIBLE);
        }else{
            holder.viewDot.setVisibility(View.INVISIBLE);
            holder.tvName.setSelected(false);
            holder.linkContainer.setVisibility(View.GONE);
        }
        //添加题目
        if(bean.getAdd_question()==0){
            holder.tvAddLinkQuestion.setVisibility(View.GONE);
            holder.tvWhatQuestion.setVisibility(View.GONE);
        }else{
            holder.tvAddLinkQuestion.setVisibility(View.VISIBLE);
            holder.tvWhatQuestion.setVisibility(View.VISIBLE);
            holder.tvAddLinkQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onOptionClickListener!=null){
                        if(bean.getAddQuestions()!=null&&bean.getAddQuestions().size()>=3){
                            return;
                        }
                        onOptionClickListener.addQuestion(position);
                    }
                }
            });
        }

        //link_question
        if(bean.getLink_question()==null){
            holder.tvLinkQuestionName.setVisibility(View.GONE);
            holder.linkOptionRecyclerView.setVisibility(View.GONE);
        }else{
            holder.tvLinkQuestionName.setVisibility(View.VISIBLE);
            holder.linkOptionRecyclerView.setVisibility(View.VISIBLE);
            holder.tvLinkQuestionName.setText(StringUtil.formatString(bean.getLink_question().getTitle()));
            holder.linkOptionRecyclerView.setLayoutManager(new GridLayoutManager(context,2));

            OptionAdapter adapter=new OptionAdapter(context,bean.getLink_question().getOptions(),bean.getLink_question().getMulti_select());
            holder.linkOptionRecyclerView.setAdapter(adapter);
            //添加选项
            if(bean.getLink_question().getAdd_option()==0){
                holder.tvAddLinkOption.setVisibility(View.GONE);
            }else {
                holder.tvAddLinkOption.setVisibility(View.VISIBLE);
                holder.tvAddLinkOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onOptionClickListener!=null){
                            onOptionClickListener.addOption(position);
                        }
                    }
                });
            }
        }

        //整个的状态
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //现在只有单选
                //先取消所有的选项
                for(int i=0;i<data.size();i++){
                    if(data.get(i) instanceof OptionFirstBean){
                        ((OptionFirstBean) data.get(i)).setSelected(false);
                    }
                }
                bean.setSelected(true);
                notifyDataSetChanged();
            }
        });

        //添加的问题
        if(bean.getAddQuestions()==null||bean.getAddQuestions().isEmpty()){
            holder.addedLinkQuestionRecyclerView.setVisibility(View.GONE);
        }else{
            holder.addedLinkQuestionRecyclerView.setVisibility(View.VISIBLE);
            holder.addedLinkQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            QuestionAdapter adapter=new QuestionAdapter(context,bean.getAddQuestions(),position);
            holder.addedLinkQuestionRecyclerView.setAdapter(adapter);

            adapter.setOnOptionClickListener(onOptionClickListener);
        }


        //间隔以及分割线的处理
        if(bean.isSelected()){
            if(position==1){
                holder.topLine.setVisibility(View.VISIBLE);
                holder.bottomMarginLine.setVisibility(View.GONE);
                holder.bottomWholeLine.setVisibility(View.VISIBLE);
                holder.wideLine.setVisibility(View.VISIBLE);
                holder.wideLineTop.setVisibility(View.GONE);
            }else if(position==data.size()-1){
                holder.topLine.setVisibility(View.VISIBLE);
                holder.bottomMarginLine.setVisibility(View.GONE);
                holder.bottomWholeLine.setVisibility(View.VISIBLE);
                holder.wideLine.setVisibility(View.VISIBLE);
                holder.wideLineTop.setVisibility(View.VISIBLE);

            }else{
                holder.topLine.setVisibility(View.VISIBLE);
                holder.bottomMarginLine.setVisibility(View.GONE);
                holder.bottomWholeLine.setVisibility(View.VISIBLE);
                holder.wideLine.setVisibility(View.VISIBLE);
                holder.wideLineTop.setVisibility(View.VISIBLE);
            }
        }else{
            holder.wideLine.setVisibility(View.GONE);
            holder.wideLineTop.setVisibility(View.GONE);
            if(position==1){
                holder.topLine.setVisibility(View.VISIBLE);
                OptionFirstBean next= (OptionFirstBean) data.get(position+1);
                if(next.isSelected()){
                    holder.bottomMarginLine.setVisibility(View.GONE);
                    holder.bottomWholeLine.setVisibility(View.VISIBLE);
                }else {
                    holder.bottomMarginLine.setVisibility(View.VISIBLE);
                    holder.bottomWholeLine.setVisibility(View.GONE);
                }

            }else if(position==data.size()-1){
                holder.wideLine.setVisibility(View.VISIBLE);
                holder.bottomWholeLine.setVisibility(View.VISIBLE);
                holder.bottomMarginLine.setVisibility(View.GONE);

                OptionFirstBean last= (OptionFirstBean) data.get(position-1);
                if(last.isSelected()){
                    holder.topLine.setVisibility(View.VISIBLE);
                }else{
                    holder.topLine.setVisibility(View.GONE);
                }

            }else{

                OptionFirstBean last= (OptionFirstBean) data.get(position-1);
                OptionFirstBean next= (OptionFirstBean) data.get(position+1);


                if(last.isSelected()&&next.isSelected()){
                    holder.topLine.setVisibility(View.VISIBLE);
                    holder.bottomWholeLine.setVisibility(View.VISIBLE);
                    holder.bottomMarginLine.setVisibility(View.GONE);

                }else if(last.isSelected()&&!next.isSelected()){
                    holder.topLine.setVisibility(View.VISIBLE);
                    holder.bottomWholeLine.setVisibility(View.GONE);
                    holder.bottomMarginLine.setVisibility(View.VISIBLE);

                }else if(!last.isSelected()&&next.isSelected()){
                    holder.topLine.setVisibility(View.GONE);
                    holder.bottomWholeLine.setVisibility(View.VISIBLE);
                    holder.bottomMarginLine.setVisibility(View.GONE);

                }else{
                    holder.topLine.setVisibility(View.GONE);
                    holder.bottomWholeLine.setVisibility(View.GONE);
                    holder.bottomMarginLine.setVisibility(View.VISIBLE);

                }
            }
        }
        //关于line2间隔线

    }

    private void setQuestion(QuestionBean bean,final QuestionHolder holder, final int position){
        holder.tvIndex.setText(""+(index+1));
        holder.tvTotal.setText("/"+total);
        holder.tvQuestion.setText(StringUtil.formatString(bean.getTitle()));
        if(index==total-1){
            holder.tvNextStep.setText(context.getResources().getString(R.string.complete));
        }
        //下一步的处理
        holder.tvNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSpecialClickListener!=null&&holder.tvNextStep.isSelected()){
                    onSpecialClickListener.onSpecialItem(position);
                }
            }
        });
        //下一步的显示
        boolean isAnswered=false;
        for(int i=0;i<data.size();i++){
            if(data.get(i) instanceof OptionFirstBean){
                OptionFirstBean firstBean= (OptionFirstBean) data.get(i);
                if(firstBean.isSelected()){
                    isAnswered=true;
                    break;
                }
            }
        }
        if(isAnswered){
            holder.tvNextStep.setSelected(true);
        }else{
            holder.tvNextStep.setSelected(false);
        }

        holder.pbIndex.setProgress((index+1)*100/total);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class QuestionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIndex)
        TextView tvIndex;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvQuestion)
        TextView tvQuestion;
        @BindView(R.id.tvNextStep)
        TextView tvNextStep;
        @BindView(R.id.pbIndex)
        ProgressBar pbIndex;

        QuestionHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class OptionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topLine)
        View topLine;
        @BindView(R.id.viewDot)
        View viewDot;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvLinkQuestionName)
        TextView tvLinkQuestionName;
        @BindView(R.id.linkOptionRecyclerView)
        RecyclerView linkOptionRecyclerView;
        @BindView(R.id.tvAddLinkOption)
        TextView tvAddLinkOption;
        @BindView(R.id.tvWhatQuestion)
        TextView tvWhatQuestion;
        @BindView(R.id.addedLinkQuestionRecyclerView)
        RecyclerView addedLinkQuestionRecyclerView;
        @BindView(R.id.tvAddLinkQuestion)
        TextView tvAddLinkQuestion;
        @BindView(R.id.bottomWholeLine)
        View bottomWholeLine;
        @BindView(R.id.bottomMarginLine)
        View bottomMarginLine;
        @BindView(R.id.wideLine)
        View wideLine;
        @BindView(R.id.wideLineTop)
        View wideLineTop;
        @BindView(R.id.linkContainer)
        View linkContainer;
        @BindView(R.id.line2)
        View line2;

        OptionHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
