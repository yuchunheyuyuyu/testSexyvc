package com.qtin.sexyvc.ui.investor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.investor.bean.InvestorBean;
import com.qtin.sexyvc.ui.investor.bean.ItemCaseBean;
import com.qtin.sexyvc.ui.investor.bean.ItemBaseBean;
import com.qtin.sexyvc.ui.investor.bean.ItemTagBean;
import com.qtin.sexyvc.ui.investor.bean.RoadShowBean;
import com.qtin.sexyvc.ui.investor.bean.RoadShowItemBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/7/3.
 */

public class InvestorDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;

    public InvestorDetailAdapter(Context context, ArrayList<DataTypeInterface> data) {
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
        if (viewType == DataTypeInterface.TYPE_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_top, parent, false);
            return new ContentHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_COMMENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_comment, parent, false);
            return new CommentHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_ROAD) {
            view = LayoutInflater.from(context).inflate(R.layout.item_invertor_road, parent, false);
            return new RoadHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_BASE_INFO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_base_info, parent, false);
            return new BaseInfoHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_TAG) {
            view = LayoutInflater.from(context).inflate(R.layout.item_inverstor_tags, parent, false);
            return new TagHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_CASE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_case, parent, false);
            return new CaseHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ContentHolder) {
            ContentHolder holder = (ContentHolder) viewHolder;
            InvestorBean bean = (InvestorBean) data.get(position);

            dealContent(bean, holder);
        } else if (viewHolder instanceof CommentHolder) {
            CommentHolder holder = (CommentHolder) viewHolder;
            CommentBean bean = (CommentBean) data.get(position);
        } else if (viewHolder instanceof RoadHolder) {
            dealRoad((RoadHolder) viewHolder, (RoadShowBean) data.get(position));
        } else if (viewHolder instanceof BaseInfoHolder) {
            BaseInfoHolder holder = (BaseInfoHolder) viewHolder;
            ItemBaseBean bean = (ItemBaseBean) data.get(position);
            holder.tvIntroduce.setText(StringUtil.formatString(bean.getInvestor_intro()));
        } else if (viewHolder instanceof TagHolder) {
            dealTag((ItemTagBean) data.get(position), (TagHolder) viewHolder);
        } else if (viewHolder instanceof CaseHolder) {
            dealCase((CaseHolder) viewHolder, (ItemCaseBean) data.get(position));
        }
    }

    private void dealCase(CaseHolder holder, ItemCaseBean bean) {
        String caseFormat = context.getResources().getString(R.string.format_more_case);
        holder.tvCaseNum.setText(String.format(caseFormat, "" + bean.getCase_number()));
        String commentFormat = context.getResources().getString(R.string.format_more_commnet);
        holder.tvCommentNum.setText(String.format(commentFormat, "" + bean.getComment_number()));
    }

    private void dealTag(ItemTagBean bean, TagHolder holder) {
        //行业标签
        if (bean.getDomain_list() == null || bean.getDomain_list().isEmpty()) {
            holder.domainFlowLayout.setVisibility(View.GONE);
        } else {
            holder.domainFlowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<FilterEntity>(bean.getDomain_list()) {
                @Override
                public View getView(FlowLayout parent, int position, FilterEntity o) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_filter_icon, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tvName);
                    ImageView iv = (ImageView) view.findViewById(R.id.ivIcon);
                    if (position == 0) {
                        iv.setVisibility(View.VISIBLE);
                    } else {
                        iv.setVisibility(View.GONE);
                    }
                    tv.setText(o.getType_name());
                    return view;
                }
            };
            holder.domainFlowLayout.setAdapter(tagAdapter);
        }

        //阶段标签
        if (bean.getStage_list() == null || bean.getStage_list().isEmpty()) {
            holder.stageFlowLayout.setVisibility(View.GONE);
        } else {
            holder.stageFlowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<FilterEntity>(bean.getStage_list()) {
                @Override
                public View getView(FlowLayout parent, int position, FilterEntity o) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_filter_icon, parent, false);
                    TextView tv = (TextView) view.findViewById(R.id.tvName);
                    ImageView iv = (ImageView) view.findViewById(R.id.ivIcon);
                    if (position == 0) {
                        iv.setVisibility(View.VISIBLE);
                        iv.setImageResource(R.drawable.icon_row);
                    } else {
                        iv.setVisibility(View.GONE);
                    }
                    tv.setText(o.getType_name());
                    return view;
                }
            };
            holder.stageFlowLayout.setAdapter(tagAdapter);
        }
    }

    private void dealRoad(RoadHolder holder, RoadShowBean bean) {
        //路演评价
        if (bean != null) {
            holder.pbProfessionalQualities.setProgress(countRoadPercent(bean.getProfessional()));
            holder.pbRoadEfficiency.setProgress(countRoadPercent(bean.getEfficiency()));
            holder.pbFeedbackSpeed.setProgress(countRoadPercent(bean.getFeedback()));
            holder.pbExperienceShare.setProgress(countRoadPercent(bean.getExperience()));
        } else {
            holder.pbProfessionalQualities.setProgress(0);
            holder.pbRoadEfficiency.setProgress(0);
            holder.pbFeedbackSpeed.setProgress(0);
            holder.pbExperienceShare.setProgress(0);
        }
    }

    private void dealContent(InvestorBean bean, final ContentHolder holder) {
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .transformation(new CropCircleTransformation(context))
                .url(CommonUtil.getAbsolutePath(bean.getInvestor_avatar()))
                .imageView(holder.ivAvatar)
                .build());

        holder.tvName.setText(StringUtil.formatString(bean.getInvestor_name()));
        holder.tvCompany.setText(StringUtil.formatString(bean.getFund_name()));
        holder.tvPosition.setText(StringUtil.formatString(bean.getInvestor_title()));
        //标签
        if (bean.getTags() == null || bean.getTags().isEmpty()) {
            holder.flowLayout.setVisibility(View.GONE);
        } else {
            holder.flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(bean.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_filter_textview2, parent, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            holder.flowLayout.setAdapter(tagAdapter);
        }

        //评分
        holder.tvRateNum.setText(bean.getComment_number() + " 人");
        //holder.tvRating.setText();
        //holder.ratingScore.setRating();
    }

    private int countRoadPercent(RoadShowItemBean itemBean) {
        if (itemBean != null) {
            int totle = itemBean.getAgree() + itemBean.getAgainst();
            if (totle == 0) {
                return 0;
            } else {
                return itemBean.getAgree() * 100 / totle;
            }
        }
        return 0;
    }


    static class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.lineBottom)
        View lineBottom;

        CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class RoadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pbProfessionalQualities)
        ProgressBar pbProfessionalQualities;
        @BindView(R.id.pbFeedbackSpeed)
        ProgressBar pbFeedbackSpeed;
        @BindView(R.id.pbRoadEfficiency)
        ProgressBar pbRoadEfficiency;
        @BindView(R.id.pbExperienceShare)
        ProgressBar pbExperienceShare;

        RoadHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class BaseInfoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIntroduce)
        TextView tvIntroduce;

        BaseInfoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TagHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.domainFlowLayout)
        TagFlowLayout domainFlowLayout;
        @BindView(R.id.stageFlowLayout)
        TagFlowLayout stageFlowLayout;

        TagHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CaseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCaseNum)
        TextView tvCaseNum;
        @BindView(R.id.recyclerViewCase)
        RecyclerView recyclerViewCase;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;

        CaseHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContentHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvCompany)
        TextView tvCompany;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvRateNum)
        TextView tvRateNum;
        @BindView(R.id.tvRating)
        TextView tvRating;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;

        ContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
