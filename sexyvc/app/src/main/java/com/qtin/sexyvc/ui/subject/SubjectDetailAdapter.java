package com.qtin.sexyvc.ui.subject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.DetailClickListener;
import com.qtin.sexyvc.ui.bean.ReplyBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.subject.bean.SubjectContentEntity;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/27.
 */
public class SubjectDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private DetailClickListener clickListener;

    public void setClickListener(DetailClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public SubjectDetailAdapter(Context context, ArrayList<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DataTypeInterface.TYPE_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_subject_detail_web, parent, false);
            return new WebHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_COMMENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_reply, parent, false);
            return new CommentHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WebHolder) {
            SubjectContentEntity subjectDetailEntity = (SubjectContentEntity) data.get(position);
            final WebHolder webHolder = (WebHolder) holder;
            webHolder.webViewContent.loadData(CommonUtil.getHtmlData(subjectDetailEntity.getContent()), "text/html; charset=UTF-8", "utf-8");

            /**webHolder.webViewContent.loadUrl(Api.SUBJECT_URL+subjectDetailEntity.getSubject_id());
            webHolder.webViewContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webHolder.webViewContent.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) webHolder.webViewContent.getLayoutParams();
                    params.width=FrameLayout.LayoutParams.MATCH_PARENT;
                    params.height=FrameLayout.LayoutParams.WRAP_CONTENT;
                    webHolder.webViewContent.setLayoutParams(params);
                    webHolder.webViewContent.requestLayout();
                }
            });*/

            webHolder.itemView.requestFocus();

            TagAdapter tagAdapter = new TagAdapter<TagEntity>(subjectDetailEntity.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_filter_textview3, webHolder.flowLayout, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            if(subjectDetailEntity.getReply_count()==0){
                webHolder.tvCommentCount.setText(context.getResources().getString(R.string.has_no_comment));
            }else{
                webHolder.tvCommentCount.setText(subjectDetailEntity.getReply_count()+context.getResources().getString(R.string.comment_count));
            }

            webHolder.tvPraiseNum.setText("" + subjectDetailEntity.getPraise_count());
            webHolder.flowLayout.setMaxSelectCount(0);
            webHolder.flowLayout.setAdapter(tagAdapter);

            if (subjectDetailEntity.getHas_praise() == 0) {
                webHolder.ivPraise.setSelected(false);
            } else {
                webHolder.ivPraise.setSelected(true);
            }
            webHolder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickDetailPraise(-1);
                    }
                }
            });

            if(StringUtil.isBlank(subjectDetailEntity.getTitle())){
                webHolder.tvTitle.setVisibility(View.GONE);
            }else{
                webHolder.tvTitle.setVisibility(View.VISIBLE);
                webHolder.tvTitle.setText(subjectDetailEntity.getTitle());
            }
            StringBuilder sb=new StringBuilder();
            if(!StringUtil.isBlank(subjectDetailEntity.getSource())){
                sb.append(subjectDetailEntity.getSource());
                sb.append("  ");
            }
            if(subjectDetailEntity.getCreate_time()!=0){
                sb.append(DateUtil.getLongDate(subjectDetailEntity.getCreate_time()));
            }
            if(StringUtil.isBlank(sb.toString())){
                webHolder.tvAuthorAndTime.setVisibility(View.GONE);
            }else{
                webHolder.tvAuthorAndTime.setVisibility(View.VISIBLE);
                webHolder.tvAuthorAndTime.setText(sb.toString());
            }

        } else if (holder instanceof CommentHolder) {
            CommentHolder commentHolder = (CommentHolder) holder;
            final ReplyBean entity = (ReplyBean) data.get(position);

            //加v的图标
            if(AppStringUtil.isShowVStatus(entity.getIs_anon(),entity.getU_auth_type(),entity.getU_auth_state())){
                commentHolder.ivIdentityStatus.setVisibility(View.VISIBLE);
                commentHolder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(entity.getU_auth_type()));
            }else{
                commentHolder.ivIdentityStatus.setVisibility(View.GONE);
            }

            if (entity.getHas_praise() == 0) {
                commentHolder.ivPraise.setSelected(false);
            } else {
                commentHolder.ivPraise.setSelected(true);
            }
            commentHolder.tvContent.setText(StringUtil.formatString(entity.getReply_content()));
            commentHolder.tvNick.setText(StringUtil.formatString(entity.getU_nickname()));
            commentHolder.tvPraiseNum.setText("" + entity.getLike());

            commentHolder.tvTime.setText(DateUtil.getSpecialDate(entity.getCreate_time()));

            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.avatar_blank)
                    .errorPic(R.drawable.avatar_blank)
                    .transformation(new CropCircleTransformation(context))
                    .url(CommonUtil.getAbsolutePath(entity.getU_avatar()))
                    .imageView(commentHolder.ivAvatar)
                    .build());

            commentHolder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickItemPraise(position);
                    }
                }
            });
            commentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickItemReply(position);
                    }
                }
            });
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

    static class WebHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvAuthorAndTime)
        TextView tvAuthorAndTime;
        WebView webViewContent;
        @BindView(R.id.web_view_container)
        FrameLayout webViewContainer;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        WebHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            //创建webView
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            webViewContent = new WebView(view.getContext());
            webViewContent.setLayoutParams(lp);
            //webViewContent.setFocusable(false);
            webViewContainer.addView(webViewContent);
            initWebView(webViewContent);

            view.requestFocus();
        }

        private void initWebView(WebView webView) {
            WebSettings mSetting = webView.getSettings();
            mSetting.setJavaScriptEnabled(true);
        }
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvNick)
        TextView tvNick;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;

        CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
