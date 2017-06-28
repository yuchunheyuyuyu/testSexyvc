package com.qtin.sexyvc.ui.main.fraghome.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.qtin.sexyvc.ui.bean.SubjectEntity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.comment.list.CommentActivity;
import com.qtin.sexyvc.ui.flash.FlashActivity;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemBannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemInvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemNewsEntity;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;
import com.qtin.sexyvc.ui.subject.list.SubjectListActivity;
import com.qtin.sexyvc.ui.widget.AutoTextView;
import com.qtin.sexyvc.ui.widget.BannerView;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.utils.CommonUtil;
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BannerHolder){
            ItemBannerEntity entity= (ItemBannerEntity) data.get(position);
            ((BannerHolder) holder).bannerView.setData(entity.getList());
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
        }else{
            holder.moreCommentContainer.setVisibility(View.GONE);
        }

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

        holder.tvFrom.setText(StringUtil.formatString(entity.getU_nickname())+" 评论了");
        holder.tvTo.setText(entity.getInvestor_name()+"@"+entity.getFund_name());
        holder.ratingScore.invalidate();
        holder.ratingScore.setRating(entity.getScore());
        holder.tvComentContent.setText(StringUtil.formatString(entity.getTitle()));
        holder.moreCommentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.gotoActivity(CommentActivity.class);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong("comment_id",entity.getComment_id());
                activity.gotoActivity(CommentDetailActivity.class,bundle);
            }
        });
    }

    /**
     * 投资人
     * @param holder
     * @param position
     */
    private void dealInvestor(InvestorHolder holder,int position){
        ItemInvestorEntity entity= (ItemInvestorEntity) data.get(position);
        holder.investorRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        holder.investorRecyclerView.removeItemDecoration(decoration);
        holder.investorRecyclerView.addItemDecoration(decoration);
        HomeInvestorAdapter adapter=new HomeInvestorAdapter(context,entity.getList());
        holder.investorRecyclerView.setAdapter(adapter);
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
        @BindView(R.id.investorRecyclerView)
        RecyclerView investorRecyclerView;

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
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.tvComentContent)
        TextView tvComentContent;
        @BindView(R.id.marginLine)
        View marginLine;
        @BindView(R.id.wholeLine)
        View wholeLine;

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
