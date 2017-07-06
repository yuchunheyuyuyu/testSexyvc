package com.qtin.sexyvc.ui.fund.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBean;
import com.qtin.sexyvc.ui.investor.CaseAdapter;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.investor.bean.RoadShowItemBean;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeInvestorAdapter;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.SpaceItemDecoration;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ls on 17/7/6.
 */

public class FundDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;

    private SpaceItemDecoration decoration;

    public FundDetailAdapter(Context context, ArrayList<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        activity = (MyBaseActivity) context;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();

        int space= (int) DeviceUtils.dpToPixel(context,20);
        decoration=new SpaceItemDecoration(space,1);
    }


    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DataTypeInterface.TYPE_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_top, parent, false);
            return new ContentHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_comment, parent, false);
            return new CommentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ContentHolder){
            ContentHolder holder= (ContentHolder) viewHolder;
            FundDetailBean bean= (FundDetailBean) data.get(position);
            dealContent(bean,holder);
        }else{
            CommentHolder holder= (CommentHolder) viewHolder;
            CommentBean bean= (CommentBean) data.get(position);
            dealComment(bean,holder);
        }
    }

    private void dealComment(final CommentBean bean,CommentHolder holder){
        holder.ratingScore.setRating(bean.getScore());
        if(StringUtil.isBlank(bean.getDomain_name())){
            holder.tvCommentTag.setVisibility(View.GONE);
        }else{
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(bean.getDomain_name());
        }
        holder.tvTo.setText(StringUtil.formatString(bean.getInvestor_name()));
        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
        holder.tvTitle.setText(StringUtil.formatString(bean.getTitle()));
        holder.tvContent.setText(StringUtil.formatString(bean.getContent()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong("comment_id",bean.getComment_id());
                activity.gotoActivity(CommentDetailActivity.class,bundle);
            }
        });
    }

    private void dealContent(FundDetailBean bean,ContentHolder holder){
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .transformation(new RoundedCornersTransformation(context,0,0))
                .placeholder(R.drawable.logo_blank)
                .errorPic(R.drawable.logo_blank)
                .url(CommonUtil.getAbsolutePath(bean.getFund_logo()))
                .imageView(holder.ivLogo)
                .build());

        holder.tvName.setText(StringUtil.formatString(bean.getFund_name()));
        holder.ratingScore.setRating(bean.getScore());
        holder.tvRating.setText(""+bean.getScore());
        //holder.tvRateNum.setText(+"人");
        //holder.tvLocation.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(context,bean.get));

        //路演评价
        if (bean.getRoad_show() != null) {
            holder.pbProfessionalQualities.setProgress(countRoadPercent(bean.getRoad_show().getProfessional()));
            holder.pbRoadEfficiency.setProgress(countRoadPercent(bean.getRoad_show().getEfficiency()));
            holder.pbFeedbackSpeed.setProgress(countRoadPercent(bean.getRoad_show().getFeedback()));
            holder.pbExperienceShare.setProgress(countRoadPercent(bean.getRoad_show().getExperience()));
        } else {
            holder.pbProfessionalQualities.setProgress(0);
            holder.pbRoadEfficiency.setProgress(0);
            holder.pbFeedbackSpeed.setProgress(0);
            holder.pbExperienceShare.setProgress(0);
        }
        //基本介绍
        holder.tvIntroduce.setText(com.qtin.sexyvc.utils.StringUtil.formatNoData(context,bean.getFund_intro()));
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

        //投资案例
        if (bean.getCase_number() == 0) {
            holder.ivArrowCase.setVisibility(View.GONE);
            holder.tvCaseNum.setText(context.getResources().getString(R.string.no_data));
        } else {
            holder.ivArrowCase.setVisibility(View.VISIBLE);
            String caseFormat = context.getResources().getString(R.string.format_more_case);
            holder.tvCaseNum.setText(String.format(caseFormat, "" + bean.getCase_number()));
        }
        if (bean.getCase_list() == null || bean.getCase_list().isEmpty()) {
            holder.recyclerViewCase.setVisibility(View.GONE);
        } else {
            holder.recyclerViewCase.setVisibility(View.VISIBLE);
            holder.recyclerViewCase.clearFocus();
            CaseAdapter caseAdapter = new CaseAdapter(context, bean.getCase_list());
            holder.recyclerViewCase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerViewCase.setAdapter(caseAdapter);
        }

        //评论
        if(bean.getComment_number()==0){
            holder.ivArrowComment.setVisibility(View.GONE);
            holder.tvCommentNum.setText(context.getResources().getString(R.string.no_data));
        }else{
            holder.ivArrowComment.setVisibility(View.VISIBLE);
            String commentFormat = context.getResources().getString(R.string.format_more_commnet);
            holder.tvCommentNum.setText(String.format(commentFormat, "" + bean.getComment_number()));
        }

        //投资人列表
        holder.recyclerViewInvestor.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerViewInvestor.removeItemDecoration(decoration);
        holder.recyclerViewInvestor.addItemDecoration(decoration);
        HomeInvestorAdapter adapter=new HomeInvestorAdapter(context,bean.getInvestor_list());
        holder.recyclerViewInvestor.setAdapter(adapter);
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

    static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvLocation)
        TextView tvLocation;
        @BindView(R.id.tvRateNum)
        TextView tvRateNum;
        @BindView(R.id.tvRating)
        TextView tvRating;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.pbProfessionalQualities)
        ProgressBar pbProfessionalQualities;
        @BindView(R.id.pbFeedbackSpeed)
        ProgressBar pbFeedbackSpeed;
        @BindView(R.id.pbRoadEfficiency)
        ProgressBar pbRoadEfficiency;
        @BindView(R.id.pbExperienceShare)
        ProgressBar pbExperienceShare;
        @BindView(R.id.tvIntroduce)
        TextView tvIntroduce;
        @BindView(R.id.domainFlowLayout)
        TagFlowLayout domainFlowLayout;
        @BindView(R.id.stageFlowLayout)
        TagFlowLayout stageFlowLayout;
        @BindView(R.id.tvInvestorNum)
        TextView tvInvestorNum;
        @BindView(R.id.ivInvestorCase)
        ImageView ivInvestorCase;
        @BindView(R.id.recyclerViewInvestor)
        RecyclerView recyclerViewInvestor;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCaseNum)
        TextView tvCaseNum;
        @BindView(R.id.ivArrowCase)
        ImageView ivArrowCase;
        @BindView(R.id.recyclerViewCase)
        RecyclerView recyclerViewCase;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.ivArrowComment)
        ImageView ivArrowComment;

        ContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CommentHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvTo)
        TextView tvTo;
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
}
