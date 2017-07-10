package com.qtin.sexyvc.ui.follow.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/20.
 */
public class ConcernListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyBaseActivity activity;
    private Context context;
    private List<ConcernListEntity> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ConcernListAdapter(Context context, List<ConcernListEntity> data) {
        this.context = context;
        this.data = data;
        activity = (MyBaseActivity) context;

        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ConstantUtil.TYPE_NORMAL) {
            view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
            return new ItemHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_local_search, parent, false);
            return new HistoryHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getContact_id()== ConstantUtil.DEFALUT_ID){
            return ConstantUtil.TYPE_UNNORMAL;
        }
        return ConstantUtil.TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder instanceof ItemHolder){
            final ConcernListEntity entity=data.get(position);

            ItemHolder holder = (ItemHolder) viewHolder;
            if (position == data.size() - 1) {
                holder.wholeLine.setVisibility(View.VISIBLE);
                holder.marginLine.setVisibility(View.GONE);
            } else {
                holder.wholeLine.setVisibility(View.GONE);
                holder.marginLine.setVisibility(View.VISIBLE);
            }

            holder.clickContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onClickItem(position);
                    }
                }
            });

            if(entity.getInvestor_uid()==0){
                holder.ivAnthStatus.setVisibility(View.GONE);
            }else{
                holder.ivAnthStatus.setVisibility(View.VISIBLE);
            }

            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .errorPic(R.drawable.avatar_blank)
                    .placeholder(R.drawable.avatar_blank)
                    .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                    .transformation(new CropCircleTransformation(context))
                    .imageView(holder.ivAvatar)
                    .build());
            holder.tvName.setText(StringUtil.formatString(entity.getInvestor_name()));

            String title=StringUtil.isBlank(entity.getFund_name())?entity.getTitle():(entity.getFund_name()+" "+entity.getTitle());
            holder.tvPosition.setText(title);


        }else{
            HistoryHolder holder= (HistoryHolder) viewHolder;
            holder.tvClearHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickItem(ConstantUtil.ACTION_CLEAR_HISTORY);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.marginLine)
        View marginLine;
        @BindView(R.id.wholeLine)
        View wholeLine;
        @BindView(R.id.clickContainer)
        View clickContainer;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;

        ItemHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HistoryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvClearHistory)
        TextView tvClearHistory;

        HistoryHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }
}
