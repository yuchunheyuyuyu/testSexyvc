package com.qtin.sexyvc.ui.subject;

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
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.subject.bean.SubjectBannerEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectListEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectListInterface;
import com.qtin.sexyvc.ui.widget.BannerView;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/23.
 */
public class SujectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SubjectListInterface> data;

    public static final int ITEM_BANNER = 0;
    public static final int ITEM_NORMAL = 1;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;

    public SujectListAdapter(Context context, ArrayList<SubjectListInterface> data) {
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
        if (viewType == ITEM_BANNER) {
            view = LayoutInflater.from(context).inflate(R.layout.subject_item_banner, parent, false);
            return new BannerHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.subject_item_normal, parent, false);
            return new SubjectHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof BannerHolder){
            BannerHolder holder= (BannerHolder) viewHolder;
            SubjectBannerEntity bannerEntity= (SubjectBannerEntity) data.get(position);
            holder.bannerView.setData(bannerEntity.getBanners());
        }else{
            SubjectHolder holder= (SubjectHolder) viewHolder;
            SubjectListEntity entity= (SubjectListEntity) data.get(position);

            holder.tvSubjectTitle.setText(StringUtil.formatString(entity.getTitle()));
            holder.tvSubjectAuther.setText(StringUtil.formatString(entity.getSource()));

            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .url(CommonUtil.getAbsolutePath(entity.getImg_url()))
                    .imageView(holder.ivSubjectCover)
                    .build());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onClickItem(position);
                    }
                }
            });
            long lastStamp=0;
            if(position>0){
                SubjectListInterface last=data.get(position-1);
                if(last instanceof SubjectListEntity){
                    lastStamp=((SubjectListEntity)last).getCreate_time();
                }
            }
            //和上一条是同一天
            if(DateUtil.isNeedShow(entity.getCreate_time(),lastStamp)){
                holder.tvDate.setVisibility(View.GONE);
            }else{
                holder.tvDate.setVisibility(View.VISIBLE);
                holder.tvDate.setText("| "+DateUtil.getDateExpression(entity.getCreate_time()));
            }
        }
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
            ButterKnife.bind(this, view);
        }
    }

    static class SubjectHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.ivSubjectCover)
        ImageView ivSubjectCover;
        @BindView(R.id.tvSubjectTitle)
        TextView tvSubjectTitle;
        @BindView(R.id.tvSubjectAuther)
        TextView tvSubjectAuther;

        SubjectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
