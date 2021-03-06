package com.qtin.sexyvc.ui.main.fraghome.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEntity;
import com.qtin.sexyvc.ui.bean.OnBannerClickListener;
import com.qtin.sexyvc.ui.bean.OnBannerItemClickListener;
import com.qtin.sexyvc.ui.bean.OnClickMoreInvestorListener;
import com.qtin.sexyvc.ui.bean.SubjectEntity;
import com.qtin.sexyvc.ui.comment.chosen.detail.ChosenDetailActivity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.comment.list.CommentActivity;
import com.qtin.sexyvc.ui.flash.FlashActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemBannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemInvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemNewsEntity;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;
import com.qtin.sexyvc.ui.subject.list.SubjectListActivity;
import com.qtin.sexyvc.ui.widget.AutoTextView;
import com.qtin.sexyvc.ui.widget.BannerView;
import com.qtin.sexyvc.ui.widget.InvestorView;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.SpaceItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/10.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_BANNER = 0;
    public static final int ITEM_NEWS = 1;
    public static final int ITEM_INVESTOR = 2;
    public static final int ITEM_COMMENT = 3;
    public static final int ITEM_SUBJECT = 4;

    private Context context;
    private List<HomeInterface> data ;
    private SpaceItemDecoration decoration;

    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private MyBaseActivity activity;
    private OnClickMoreInvestorListener onClickMoreInvestorListener;
    private OnBannerClickListener onBannerClickItem;

    public void setOnClickMoreInvestorListener(OnClickMoreInvestorListener onClickMoreInvestorListener) {
        this.onClickMoreInvestorListener = onClickMoreInvestorListener;
    }

    public void setOnBannerClickItem(OnBannerClickListener onBannerClickItem) {
        this.onBannerClickItem = onBannerClickItem;
    }

    public HomeAdapter(Context context, List<HomeInterface> data) {
        this.context = context;
        this.data = data;
        int space= (int) DeviceUtils.dpToPixel(context,20);
        decoration=new SpaceItemDecoration(space,1);
        activity= (MyBaseActivity) context;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_BANNER) {
            view = LayoutInflater.from(context).inflate(R.layout.home_item_banner, parent, false);
            return new BannerHolder(view);
        } else if (viewType == ITEM_NEWS) {
            view = LayoutInflater.from(context).inflate(R.layout.home_item_news, parent, false);
            return new NewsHolder(view);
        } else if (viewType == ITEM_INVESTOR) {
            view = LayoutInflater.from(context).inflate(R.layout.home_item_investor, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == ITEM_COMMENT) {
            view = LayoutInflater.from(context).inflate(R.layout.home_item_comment, parent, false);
            return new CommentHolder(view);
        } else if (viewType == ITEM_SUBJECT) {
            view = LayoutInflater.from(context).inflate(R.layout.home_item_subject, parent, false);
            return new SubjectHolder(view);

        }
        return null;
    }

    public BannerView bannerView;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BannerHolder){
            final ItemBannerEntity entity= (ItemBannerEntity) data.get(position);
            ((BannerHolder) holder).bannerView.setData(entity.getList());
            bannerView=((BannerHolder) holder).bannerView;
            bannerView.startAutoPlay();
            ((BannerHolder) holder).bannerView.setOnItemClickListener(new OnBannerItemClickListener() {
                @Override
                public void onBannerClickItem(int position) {
                    onBannerClickItem.onBannerClickItem(entity.getList().get(position));
                }
            });

        }else if(holder instanceof NewsHolder){
            ItemNewsEntity entity= (ItemNewsEntity) data.get(position);
            ((NewsHolder) holder).autoTextView.setDate(entity.getList());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.gotoActivity(FlashActivity.class);
                }
            });

        }else if(holder instanceof InvestorHolder){
            dealInvestor((InvestorHolder)holder,position);
        }else if(holder instanceof CommentHolder){
            dealComment((CommentHolder)holder,position);
        }else if(holder instanceof SubjectHolder){
            dealSubject((SubjectHolder) holder,position);
        }
    }

    private void dealSubject(SubjectHolder holder,int position){
        final SubjectEntity entity= (SubjectEntity) data.get(position);
        if(entity.isFirst()){
            holder.moreSubjectContainer.setVisibility(View.VISIBLE);
        }else{
            holder.moreSubjectContainer.setVisibility(View.GONE);
        }

        if(entity.isLast()){
            holder.subjectLine.setVisibility(View.GONE);
        }else{
            holder.subjectLine.setVisibility(View.VISIBLE);
        }
        holder.tvSubjectTitle.setText(StringUtil.formatString(entity.getTitle()));
        holder.tvSubjectAuther.setText(StringUtil.formatString(entity.getSource()));

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(entity.getImg_url()))
                .imageView(holder.ivSubjectCover)
                .build());

        holder.moreSubjectContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.gotoActivity(SubjectListActivity.class);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("subject_id", entity.getSubject_id());
                bundle.putString("title", entity.getTitle());
                activity.gotoActivity(SubjectDetailActivity.class, bundle);
            }
        });
    }

    private void dealComment(CommentHolder holder,int position){
        final CommentEntity entity= (CommentEntity) data.get(position);
        if(entity.isFirst()){
            holder.moreCommentContainer.setVisibility(View.VISIBLE);
            holder.topicContainer.setVisibility(View.VISIBLE);

           mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .url(CommonUtil.getAbsolutePath(entity.getHomeInfoBean().getCover_pic()))
                    //.transformation(new RoundedCornersTransformation(context, 40, 0))
                    .imageView(holder.ivTopic)
                    .build());
            holder.tvTopicTitle.setText(StringUtil.formatString(entity.getHomeInfoBean().getTitle()));
            holder.tvTopicSummary.setText(StringUtil.formatString(entity.getHomeInfoBean().getSummary()));
            holder.ivTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putLong(ConstantUtil.INTENT_ID, entity.getHomeInfoBean().getTopic_id());
                    bundle.putBoolean("isFromChosenList",false);
                    activity.gotoActivity(ChosenDetailActivity.class, bundle);
                }
            });

        }else{
            holder.moreCommentContainer.setVisibility(View.GONE);
            holder.topicContainer.setVisibility(View.GONE);
        }

        holder.moreCommentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.gotoActivity(CommentActivity.class);
            }
        });
        if(entity.getComment_id()== ConstantUtil.DEFALUT_ID){
            holder.llCommentContent.setVisibility(View.GONE);
        }else{
            holder.llCommentContent.setVisibility(View.VISIBLE);

            if(entity.isLast()){
                holder.marginLine.setVisibility(View.GONE);
                holder.wholeLine.setVisibility(View.VISIBLE);
            }else{
                holder.marginLine.setVisibility(View.VISIBLE);
                holder.wholeLine.setVisibility(View.GONE);
            }
            if(StringUtil.isBlank(entity.getDomain_name())){
                holder.tvCommentTag.setVisibility(View.GONE);
            }else{
                holder.tvCommentTag.setVisibility(View.VISIBLE);
                holder.tvCommentTag.setText(entity.getDomain_name());
            }
            if(entity.getIs_anon()==0){
                holder.tvFrom.setSelected(true);
            }else{
                holder.tvFrom.setSelected(false);
            }
            holder.tvFrom.setText(StringUtil.formatString(entity.getU_nickname()));
            String name=entity.getInvestor_name()+"@"+entity.getFund_name();
            holder.tvTarget.setText(StringUtil.formatString(name));
            //加v的图标
           if (AppStringUtil.isShowVStatus(entity.getIs_anon(), entity.getU_auth_type(), entity.getU_auth_state())) {
                holder.ivIdentityStatus.setVisibility(View.VISIBLE);
                holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(entity.getU_auth_type()));
            } else {
                holder.ivIdentityStatus.setVisibility(View.GONE);
            }

            holder.ratingScore.setRating(entity.getScore());
            holder.tvCommentTitle.setText(StringUtil.formatString(entity.getTitle()));
            holder.tvComentContent.setText(StringUtil.formatString(entity.getContent()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putLong("comment_id",entity.getComment_id());
                    activity.gotoActivity(CommentDetailActivity.class,bundle);
                }
            });
        }
    }

    /**
     * 投资人
     * @param holder
     * @param position
     */
    private void dealInvestor(final InvestorHolder holder, int position){
        final ItemInvestorEntity entity= (ItemInvestorEntity) data.get(position);
        holder.investorView.setData(entity.getList());
        holder.tvInvestorCount.setText("/"+entity.getList().size());
        holder.investorView.setOnItemClickListener(new InvestorView.OnInvestorItemClickListener() {
            @Override
            public void onInvestorClickItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("investor_id", entity.getList().get(position).getInvestor_id());
                bundle.putBoolean("isFromFund", false);
                activity.gotoActivity(InvestorDetailActivity.class, bundle);
            }
        });
        holder.investorView.setOnInvestorChangeListener(new InvestorView.onInvestorChangeListener() {
            @Override
            public void onPageChange(int position) {
                holder.tvInvestorIndex.setText(""+(position+1));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bannerView)
        BannerView bannerView;

        BannerHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.autoTextView)
        AutoTextView autoTextView;

        NewsHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }

    static class InvestorHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.moreInvestorContainer)
        LinearLayout moreInvestorContainer;
        @BindView(R.id.investorView)
        InvestorView investorView;
        @BindView(R.id.tvInvestorIndex)
        TextView tvInvestorIndex;
        @BindView(R.id.tvInvestorCount)
        TextView tvInvestorCount;

        InvestorHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.moreCommentContainer)
        LinearLayout moreCommentContainer;
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvTarget)
        TextView tvTarget;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvCommentTitle)
        TextView tvCommentTitle;
        @BindView(R.id.tvComentContent)
        TextView tvComentContent;
        @BindView(R.id.marginLine)
        View marginLine;
        @BindView(R.id.wholeLine)
        View wholeLine;
        @BindView(R.id.topicContainer)
        View topicContainer;
        @BindView(R.id.ivTopic)
        ImageView ivTopic;
        @BindView(R.id.tvTopicTitle)
        TextView tvTopicTitle;
        @BindView(R.id.tvTopicSummary)
        TextView tvTopicSummary;
        @BindView(R.id.llCommentContent)
        View llCommentContent;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;

        CommentHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }

    static class SubjectHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.moreSubjectContainer)
        LinearLayout moreSubjectContainer;
        @BindView(R.id.tvSubjectTitle)
        TextView tvSubjectTitle;
        @BindView(R.id.tvSubjectAuther)
        TextView tvSubjectAuther;
        @BindView(R.id.subjectLine)
        View subjectLine;
        @BindView(R.id.ivSubjectCover)
        ImageView ivSubjectCover;

        SubjectHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }
}
