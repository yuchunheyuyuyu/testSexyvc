package com.qtin.sexyvc.ui.user.project.my.bean;

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
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.user.project.my.MyProjectActivity;
import com.qtin.sexyvc.utils.CommonUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ls on 17/7/10.
 */

public class ProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ProjectBean> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener itemClickListener;
    private MyProjectActivity activity;

    public ProjectAdapter(Context context, ArrayList<ProjectBean> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
        activity= (MyProjectActivity) context;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        ProjectBean bean=data.get(position);

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .transformation(new RoundedCornersTransformation(context,0,0))
                .placeholder(R.drawable.logo_blank)
                .errorPic(R.drawable.logo_blank)
                .url(CommonUtil.getAbsolutePath(bean.getLogo()))
                .imageView(holder.ivLogo)
                .build());

        holder.tvName.setText(StringUtil.formatString(bean.getProject_name()));
        holder.tvIntroduce.setText(StringUtil.formatString(bean.getShort_intro()));
        holder.tvDomainFinance.setText(activity.getProjectInfo(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data==null||data.isEmpty()){
            return 0;
        }
        return 1 ;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDomainFinance)
        TextView tvDomainFinance;
        @BindView(R.id.tvIntroduce)
        TextView tvIntroduce;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
