package com.qtin.sexyvc.ui.user.message.notice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.user.bean.MsgBean;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/19.
 */

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MsgBean> data;
    private boolean isHasLoadMore;

    public void setHasLoadMore(boolean hasLoadMore) {
        isHasLoadMore = hasLoadMore;
    }

    public NoticeAdapter(ArrayList<MsgBean> data) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
            return new NoticeHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_load_all, parent, false);
            return new FootHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof NoticeHolder) {
            NoticeHolder holder = (NoticeHolder) viewHolder;
            MsgBean bean = data.get(position);
            holder.tvContent.setText(StringUtil.formatString(bean.getContent()));
            holder.tvTime.setText(DateUtil.getDateExpression3(bean.getCreate_time()));
            if (bean.getRead_time() > 0) {
                holder.contentContainer.setSelected(true);
            } else {
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
    static class NoticeHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.contentContainer)
        LinearLayout contentContainer;

        NoticeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
