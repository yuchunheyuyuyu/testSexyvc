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
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.list.bean.FollowedFundBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.CommonUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/20.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ListAdapter(Context context, List<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DataTypeInterface.TYPE_INVESTOR) {
            view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_FUND) {
            view = LayoutInflater.from(context).inflate(R.layout.item_followed_fund, parent, false);
            return new FundHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_FOOTER){
            view = LayoutInflater.from(context).inflate(R.layout.item_has_load_all, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof InvestorHolder) {
            final ConcernListEntity entity = (ConcernListEntity) data.get(position);
            InvestorHolder holder = (InvestorHolder) viewHolder;
            if (position == data.size() - 1) {
                holder.wholeLine.setVisibility(View.GONE);
                holder.marginLine.setVisibility(View.VISIBLE);
            } else {
                holder.wholeLine.setVisibility(View.GONE);
                holder.marginLine.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(position);
                    }
                }
            });

            if (entity.getInvestor_uid() == 0) {
                holder.ivAnthStatus.setVisibility(View.GONE);
            } else {
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

            String title = StringUtil.isBlank(entity.getFund_name()) ? entity.getTitle() : (entity.getFund_name() + " " + entity.getTitle());
            holder.tvPosition.setText(title);


        } else if (viewHolder instanceof FundHolder) {
            FundHolder holder = (FundHolder) viewHolder;
            FollowedFundBean bean= (FollowedFundBean) data.get(position);

            holder.tvInvestorNumber.setText("收录投资人 "+bean.getInvestor_number());
            holder.tvName.setText(StringUtil.formatString(bean.getFund_name()));
            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .errorPic(R.drawable.avatar_blank)
                    .placeholder(R.drawable.avatar_blank)
                    .url(CommonUtil.getAbsolutePath(bean.getFund_logo()))
                    .imageView(holder.ivLogo)
                    .isFitCenter(true)
                    .build());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class InvestorHolder extends RecyclerView.ViewHolder {
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

        InvestorHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FundHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvInvestorNumber)
        TextView tvInvestorNumber;

        FundHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvHasLoadAll)
        TextView tvHasLoadAll;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
