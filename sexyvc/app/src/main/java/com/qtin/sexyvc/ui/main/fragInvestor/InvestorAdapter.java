package com.qtin.sexyvc.ui.main.fragInvestor;

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
import com.qtin.sexyvc.ui.bean.FundEntity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/9.
 */
public class InvestorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public InvestorAdapter(Context context, ArrayList<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DataTypeInterface.TYPE_INVESTOR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investor, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_TO_ENTERING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_entering, parent, false);
            return new EnteringHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_TO_IMPROVE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_improve, parent, false);
            return new ImproveHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund, parent, false);
            return new FundHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof FundHolder) {
            FundHolder holder = (FundHolder) viewHolder;
            FundEntity entity = (FundEntity) data.get(position);

            dealFund(position, entity, holder);
        } else if (viewHolder instanceof EnteringHolder) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onClickItem(position);
                    }
                }
            });
        } else if(viewHolder instanceof ImproveHolder){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onClickItem(position);
                    }
                }
            });
        } else {
            InvestorHolder holder = (InvestorHolder) viewHolder;
            InvestorEntity entity = (InvestorEntity) data.get(position);

            dealInvestor(position, entity, holder);
        }
    }

    private void dealFund(final int position, FundEntity entity, FundHolder holder) {
        holder.tvName.setText(StringUtil.formatString(entity.getFund_name()));
        String format = context.getResources().getString(R.string.format_fund);
        holder.tvInvestorAndCommentNumber.setText(String.format(format, entity.getInvestor_number(), entity.getComment_number()));

        //评分
        holder.ratingScore.setRating(entity.getScore());
        holder.tvScore.setText("" + entity.getScore());
        //标签
        if (entity.getTags() == null || entity.getTags().isEmpty()) {
            holder.flowLayout.setVisibility(View.GONE);
        } else {
            holder.flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(entity.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_filter_textview4, parent, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            holder.flowLayout.setAdapter(tagAdapter);
        }
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                //.transformation(new RoundedCornersTransformation(context, 0, 0))
                .isFitCenter(true)
                .placeholder(R.drawable.logo_blank)
                .errorPic(R.drawable.logo_blank)
                .url(CommonUtil.getAbsolutePath(entity.getFund_logo()))
                .imageView(holder.ivLogo)
                .build());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClickItem(position);
                }
            }
        });
    }


    private void dealInvestor(final int position, InvestorEntity entity, InvestorHolder holder) {
        holder.tvName.setText(StringUtil.formatString(entity.getInvestor_name()));

        StringBuilder sb = new StringBuilder();
        if (!StringUtil.isBlank(entity.getFund_name())) {
            sb.append(StringUtil.formatString(entity.getFund_name()));
            sb.append("  ");
        }


        sb.append(StringUtil.formatString(entity.getTitle()));
        sb.append("  ");
        sb.append("|");
        sb.append("  ");
        sb.append("评论");
        sb.append(entity.getComment_number());
        holder.tvPosition.setText(sb.toString());
        holder.tvScore.setText("" + entity.getScore());

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());

        //评分
        holder.ratingScore.setRating(entity.getScore());
        //标签

        if (entity.getTags() == null || entity.getTags().isEmpty()) {
            holder.tagContainer.setVisibility(View.GONE);
        } else {
            holder.tagContainer.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(entity.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_filter_textview4, parent, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            holder.tagContainer.setAdapter(tagAdapter);
        }

        if (entity.getU_id() > 0) {
            holder.ivAnthStatus.setVisibility(View.VISIBLE);
        } else {
            holder.ivAnthStatus.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class InvestorHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.flowLayout)
        TagFlowLayout tagContainer;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;

        InvestorHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FundHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvInvestorAndCommentNumber)
        TextView tvInvestorAndCommentNumber;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;

        FundHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class EnteringHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        EnteringHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ImproveHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;

        ImproveHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
