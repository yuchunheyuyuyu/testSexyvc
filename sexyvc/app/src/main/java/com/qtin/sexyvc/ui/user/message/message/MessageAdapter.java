package com.qtin.sexyvc.ui.user.message.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.user.bean.MsgBean;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.DateUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/19.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MsgBean> data;
    private OnItemClickListener onItemClickListener;
    private boolean isHasLoadMore;

    public void setHasLoadMore(boolean hasLoadMore) {
        isHasLoadMore = hasLoadMore;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MessageAdapter(ArrayList<MsgBean> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return ConstantUtil.TYPE_NORMAL;
        }
        return ConstantUtil.TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == ConstantUtil.TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MsgHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_load_all, parent, false);
            return new FootHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof MsgHolder){
            MsgHolder holder= (MsgHolder) viewHolder;
            MsgBean bean=data.get(position);
            holder.tvMsgTitle.setText(StringUtil.formatString(bean.getTitle()));
            holder.tvMsgContent.setText(StringUtil.formatString(bean.getContent()));

            if(bean.getMessage_type()==2){
                holder.ivMsg.setImageResource(R.drawable.icon_msg_reaction);
            }else{
                holder.ivMsg.setImageResource(R.drawable.icon_msg_follow);
            }
            holder.tvTime.setText(DateUtil.getDateExpression3(bean.getCreate_time()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onClickItem(position);
                    }
                }
            });
            if(bean.getRead_time()>0){
                holder.contentContainer.setSelected(true);
            }else{
                holder.contentContainer.setSelected(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return isHasLoadMore ? data.size() + 1 : data.size();
        }
    }

    static class FootHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvHasLoadAll)
        TextView tvHasLoadAll;

        FootHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class MsgHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivMsg)
        ImageView ivMsg;
        @BindView(R.id.tvMsgTitle)
        TextView tvMsgTitle;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvMsgContent)
        TextView tvMsgContent;
        @BindView(R.id.contentContainer)
        View contentContainer;

        MsgHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
