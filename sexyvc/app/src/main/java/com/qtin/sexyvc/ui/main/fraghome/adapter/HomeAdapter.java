package com.qtin.sexyvc.ui.main.fraghome.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemBannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemInvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemNewsEntity;
import com.qtin.sexyvc.ui.widget.AutoTextView;
import com.qtin.sexyvc.ui.widget.BannerView;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
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

    public HomeAdapter(Context context, List<HomeInterface> data) {
        this.context = context;
        this.data = data;
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
        }else if(holder instanceof InvestorHolder){
            dealInvestor((InvestorHolder)holder,position);
        }else if(holder instanceof CommentHolder){

        }else if(holder instanceof SubjectHolder){

        }
    }

    private void dealInvestor(InvestorHolder holder,int position){
        ItemInvestorEntity entity= (ItemInvestorEntity) data.get(position);
        holder.investorRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.investorRecyclerView.addItemDecoration(new SpaceItemDecoration);
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

        SubjectHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }
}
