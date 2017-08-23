package com.qtin.sexyvc.ui.recommend;

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
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.recommend.bean.RecommendBean;
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
 * Created by ls on 17/8/17.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RecommendBean> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecommendAdapter(Context context, ArrayList<RecommendBean> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        RecommendBean bean=data.get(position);

        if(position==data.size()-1){
            holder.bottomLine.setVisibility(View.VISIBLE);
        }else{
            holder.bottomLine.setVisibility(View.GONE);
        }

        if(bean.isSelected()){
            holder.ivChoose.setSelected(true);
        }else{
            holder.ivChoose.setSelected(false);
        }
        holder.tvName.setText(StringUtil.formatString(bean.getInvestor_name()));

        StringBuilder sb=new StringBuilder();
        sb.append("@");
        sb.append(StringUtil.formatString(bean.getFund_name()));

        holder.tvPosition.setText(sb.toString());

        holder.tvScore.setText("" + bean.getScore());

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(bean.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());

        //评分
        holder.ratingScore.setRating(bean.getScore());
        //标签

        if (bean.getTags() == null || bean.getTags().isEmpty()) {
            holder.flowLayout.setVisibility(View.GONE);
        } else {
            holder.flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(bean.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_filter_textview4, parent, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            holder.flowLayout.setAdapter(tagAdapter);
        }

        if (bean.getInvestor_uid() > 0) {
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
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivChoose)
        ImageView ivChoose;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.bottomLine)
        View bottomLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
