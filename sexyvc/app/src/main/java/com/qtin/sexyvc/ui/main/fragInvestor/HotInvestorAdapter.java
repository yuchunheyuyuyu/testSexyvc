package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.HotSearchBean;
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
public class HotInvestorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener itemClickListener;
    private OnClickSwitchListener onClickSwitchListener;
    private onClickHotListener onClickHotListener;

    public void setOnClickHotListener(HotInvestorAdapter.onClickHotListener onClickHotListener) {
        this.onClickHotListener = onClickHotListener;
    }

    public static interface OnClickSwitchListener{
        void onClickSwitch();
    }

    public static interface onClickHotListener{
        void onClick(int position);
    }

    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        this.onClickSwitchListener = onClickSwitchListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public HotInvestorAdapter(Context context, ArrayList<DataTypeInterface> data) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investor_with_switch, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_HOT_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_search, parent, false);
            return new HotSearchViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof HotSearchViewHolder) {
            HotSearchBean bean= (HotSearchBean) data.get(position);
            HotSearchViewHolder holder= (HotSearchViewHolder) viewHolder;
            if(bean.getList()==null||bean.getList().isEmpty()){
                holder.itemView.setVisibility(View.GONE);
            }else{
                holder.itemView.setVisibility(View.VISIBLE);
            }
            holder.hotRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
            HotAdapter adapter=new HotAdapter(bean.getList());
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClickItem(int position) {
                    if(onClickHotListener!=null){
                        onClickHotListener.onClick(position);
                    }
                }
            });
            holder.hotRecyclerView.setAdapter(adapter);

        } else {
            InvestorHolder holder = (InvestorHolder) viewHolder;
            InvestorEntity entity = (InvestorEntity) data.get(position);

            dealInvestor(position, entity, holder);
        }
    }


    private void dealInvestor(final int position, InvestorEntity entity, InvestorHolder holder) {

        if(entity.isFirst()){
            holder.llInterestedInvestor.setVisibility(View.VISIBLE);
            holder.llSwitchInvestor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickSwitchListener!=null){
                        onClickSwitchListener.onClickSwitch();
                    }
                }
            });
        }else{
            holder.llInterestedInvestor.setVisibility(View.GONE);
        }

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
        @BindView(R.id.llInterestedInvestor)
        LinearLayout llInterestedInvestor;
        @BindView(R.id.llSwitchInvestor)
        LinearLayout llSwitchInvestor;

        InvestorHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HotSearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hotRecyclerView)
        RecyclerView hotRecyclerView;

        HotSearchViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
