package com.qtin.sexyvc.ui.investor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.OnClickFundListener;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.investor.bean.InvestorBean;
import com.qtin.sexyvc.ui.investor.bean.RoadShowItemBean;
import com.qtin.sexyvc.ui.more.MoreCaseActivity;
import com.qtin.sexyvc.ui.more.object.activity.ObjectCommentActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.jess.arms.utils.UiUtils.getString;

/**
 * Created by ls on 17/7/3.
 */

public class InvestorDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;
    private int auth_state;

    private OnClickFundListener onClickFundListener;

    public void setOnClickFundListener(OnClickFundListener onClickFundListener) {
        this.onClickFundListener = onClickFundListener;
    }

    public InvestorDetailAdapter(Context context, ArrayList<DataTypeInterface> data,int auth_state) {
        this.context = context;
        this.data = data;
        this.auth_state=auth_state;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_text_comment, parent, false);
            return new CommentHolder(view);
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
            dealComment(bean, holder);
        }
    }

    private void dealComment(final CommentBean bean, CommentHolder holder) {

        holder.praiseContainer.setVisibility(View.GONE);
        holder.ratingScore.setRating(bean.getScore());
        if (StringUtil.isBlank(bean.getDomain_name())) {
            holder.tvCommentTag.setVisibility(View.GONE);
        } else {
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(bean.getDomain_name());
        }
        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));


        holder.tvTitle.setText(StringUtil.formatString(bean.getTitle()));
        holder.tvContent.setText(StringUtil.formatString(bean.getContent()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("comment_id", bean.getComment_id());
                activity.gotoActivity(CommentDetailActivity.class, bundle);
            }
        });
        //加v的图标
        if(AppStringUtil.isShowVStatus(bean.getIs_anon(),bean.getU_auth_type(),bean.getU_auth_state())){
            holder.ivIdentityStatus.setVisibility(View.VISIBLE);
            holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
        }else{
            holder.ivIdentityStatus.setVisibility(View.GONE);
        }
    }

    private void dealContent(final InvestorBean bean, final ContentHolder holder) {
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .transformation(new CropCircleTransformation(context))
                .url(CommonUtil.getAbsolutePath(bean.getInvestor_avatar()))
                .imageView(holder.ivAvatar)
                .build());

        holder.tvName.setText(StringUtil.formatString(bean.getInvestor_name()));

        holder.tvCompany.setText(AppStringUtil.formatNoKnown(context, bean.getFund_name()));
        holder.tvPosition.setText(AppStringUtil.formatNoKnown(context, bean.getInvestor_title()));
        //标签
        if (bean.getTags() == null || bean.getTags().isEmpty()) {
            holder.flowLayout.setVisibility(View.GONE);
        } else {
            holder.flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(bean.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_filter_textview5_ll, parent, false);
                    TextView tv= (TextView) view.findViewById(R.id.tvContent);
                    tv.setText(o.getTag_name());
                    return view;
                }
            };
            holder.flowLayout.setAdapter(tagAdapter);
        }

        holder.tvIntroduce.setText(AppStringUtil.formatNoData(context, bean.getInvestor_intro()));

        if(bean.getInvestor_uid()==0){
            holder.ivAnthStatus.setVisibility(View.GONE);
        }else{
            holder.ivAnthStatus.setVisibility(View.VISIBLE);
        }

        //评分
        holder.tvRateNum.setText(bean.getScore_count() + " 人");

        if(bean.getScore_count()==0){
            holder.tvRating.setText(getString(R.string.now_no_person_rate));
            holder.tvRating.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }else{
            holder.tvRating.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            holder.tvRating.setText("" + bean.getScore());
        }
        holder.ratingScore.setRating(bean.getScore());
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
        if (bean.getCase_list() == null||bean.getCase_list().isEmpty()) {
            holder.ivArrowCase.setVisibility(View.GONE);
            holder.tvCaseNum.setText(context.getResources().getString(R.string.no_data));
        } else {
            holder.ivArrowCase.setVisibility(View.VISIBLE);
            String caseFormat = context.getResources().getString(R.string.format_more_case);
            holder.tvCaseNum.setText(String.format(caseFormat, "" + bean.getCase_list().size()));
        }
        if (bean.getCase_list() == null || bean.getCase_list().isEmpty()) {
            holder.recyclerViewCase.setVisibility(View.GONE);
        } else {
            holder.recyclerViewCase.setVisibility(View.VISIBLE);
            holder.recyclerViewCase.clearFocus();

            List<CaseBean> tem= bean.getCase_list().size()>4? bean.getCase_list().subList(0, 4) :bean.getCase_list();
            ArrayList<CaseBean> cases=new ArrayList<CaseBean>();
            cases.addAll(tem);
            CaseAdapter caseAdapter = new CaseAdapter(context, cases);

            holder.recyclerViewCase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerViewCase.setAdapter(caseAdapter);

            holder.caseContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY,bean.getCase_list());
                    String format=context.getResources().getString(R.string.who_case);
                    String title=String.format(format,StringUtil.formatString(bean.getInvestor_name()));
                    bundle.putString(ConstantUtil.INTENT_TITLE,title);
                    activity.gotoActivity(MoreCaseActivity.class,bundle);
                }
            });
        }
        //评论
        if (bean.getComment_number() == 0) {
            holder.ivArrowComment.setVisibility(View.GONE);
            holder.tvCommentNum.setText(context.getResources().getString(R.string.no_data));
        } else {
            holder.ivArrowComment.setVisibility(View.VISIBLE);
            String commentFormat = context.getResources().getString(R.string.format_more_commnet);
            holder.tvCommentNum.setText(String.format(commentFormat, "" + bean.getComment_number()));

            holder.commentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,ConstantUtil.TYPE_INVESTOR);
                    bundle.putLong(ConstantUtil.INTENT_ID,bean.getInvestor_id());
                    bundle.putString(ConstantUtil.INTENT_TITLE,"对"+bean.getInvestor_name()+"的评论");
                    bundle.putInt("auth_state",auth_state);
                    activity.gotoActivity(ObjectCommentActivity.class,bundle);
                }
            });
        }

        if(bean.getFund_id()!=0){
            holder.companyContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickFundListener!=null){
                        onClickFundListener.onClick();
                    }
                }
            });
        }
    }

    private int countRoadPercent(RoadShowItemBean itemBean) {
        if(itemBean==null){
            itemBean=new RoadShowItemBean();
        }

        itemBean.setAgainst(itemBean.getAgainst()+5);
        itemBean.setAgree(itemBean.getAgree()+5);

        int totle = itemBean.getAgree() + itemBean.getAgainst();
        if (totle == 0) {
            return 0;
        } else {
            return itemBean.getAgree() * 100 / totle;
        }
    }


    static class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.lineBottom)
        View lineBottom;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;
        @BindView(R.id.praiseContainer)
        View praiseContainer;

        CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
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
        RatingBar ratingScore;
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
        @BindView(R.id.tvCaseNum)
        TextView tvCaseNum;
        @BindView(R.id.recyclerViewCase)
        RecyclerView recyclerViewCase;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.ivArrowCase)
        ImageView ivArrowCase;
        @BindView(R.id.ivArrowComment)
        ImageView ivArrowComment;
        @BindView(R.id.caseContainer)
        LinearLayout caseContainer;
        @BindView(R.id.commentContainer)
        LinearLayout commentContainer;
        @BindView(R.id.companyContainer)
        View companyContainer;

        ContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
