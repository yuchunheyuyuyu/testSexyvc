package com.qtin.sexyvc.ui.investor;

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
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/5.
 */

public class CaseAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CaseBean> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;
    private OnDeleteCaseListener onDeleteCaseListener;
    private OnAddProjectListener onAddProjectListener;

    public void setOnAddProjectListener(OnAddProjectListener onAddProjectListener) {
        this.onAddProjectListener = onAddProjectListener;
    }

    public void setOnDeleteCaseListener(OnDeleteCaseListener onDeleteCaseListener) {
        this.onDeleteCaseListener = onDeleteCaseListener;
    }

    public static interface OnAddProjectListener{
        void onClick();
    }

    public static interface OnDeleteCaseListener{
        void onClickDelete(int position);
    }

    private boolean isShowDelete;//是否显示删除

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
        notifyDataSetChanged();
    }

    public CaseAdapter2(Context context, ArrayList<CaseBean> data) {
        this.context = context;
        this.data = data;
        activity = (MyBaseActivity) context;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_case2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        CaseBean bean=data.get(position);
        ViewHolder holder= (ViewHolder) viewHolder;
        if(bean.getCase_id()== ConstantUtil.DEFALUT_ID){
            holder.ivAddCase.setVisibility(View.VISIBLE);
            holder.ivLogo.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
            holder.tvName.setText(context.getString(R.string.add_case));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black50));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onAddProjectListener!=null){
                        onAddProjectListener.onClick();
                    }
                }
            });

        }else{
            holder.ivAddCase.setVisibility(View.GONE);
            holder.ivLogo.setVisibility(View.VISIBLE);
            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    //.transformation(new RoundedCornersTransformation(context,0,0))
                    .isFitCenter(true)
                    .placeholder(R.drawable.logo_blank)
                    .errorPic(R.drawable.logo_blank)
                    .url(CommonUtil.getAbsolutePath(bean.getCase_logo()))
                    .imageView(holder.ivLogo)
                    .build());
            holder.tvName.setText(StringUtil.formatString(bean.getCase_name()));

            if(isShowDelete){
                holder.ivDelete.setVisibility(View.VISIBLE);
                holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onDeleteCaseListener!=null){
                            onDeleteCaseListener.onClickDelete(position);
                        }
                    }
                });
            }else{
                holder.ivDelete.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black90));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.ivAddCase)
        ImageView ivAddCase;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
