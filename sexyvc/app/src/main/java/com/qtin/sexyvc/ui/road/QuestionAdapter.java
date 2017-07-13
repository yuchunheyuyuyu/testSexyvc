package com.qtin.sexyvc.ui.road;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.road.bean.AddQuestionBean;
import com.qtin.sexyvc.ui.road.bean.OnOptionClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/12.
 */

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<AddQuestionBean> data;
    private OnOptionClickListener onOptionClickListener;
    private int optionPosition;

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener;
    }

    public QuestionAdapter(Context context, ArrayList<AddQuestionBean> data,int optionPosition) {
        this.context = context;
        this.data = data;
        this.optionPosition=optionPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_added, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        AddQuestionBean bean=data.get(position);

        holder.tvTitle.setText("-"+StringUtil.formatString(bean.getTitle()));
        if(StringUtil.isBlank(bean.getAnswer())){
            holder.tvAnswer.setText(context.getResources().getString(R.string.hint_answer));
        }else{
            holder.tvAnswer.setText(bean.getAnswer());
        }
        holder.tvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionClickListener.addAnswer(optionPosition,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvAnswer)
        TextView tvAnswer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
