package com.qtin.sexyvc.ui.user.project.add;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.utils.UploadPhotoUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.user.project.add.di.AddProjectModule;
import com.qtin.sexyvc.ui.user.project.add.di.DaggerAddProjectComponent;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CashierInputFilter;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ls on 17/4/26.
 */
public class AddProjectActivity extends MyBaseActivity<AddProjectPresent> implements AddProjectContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvIntroduce)
    TextView tvIntroduce;
    @BindView(R.id.tvDomain)
    TextView tvDomain;
    @BindView(R.id.tvFinance)
    TextView tvFinance;


    private Dialog domainDialog;
    private ArrayList<FilterEntity> domainData = new ArrayList<>();

    private Dialog stageDialog;
    private ArrayList<FilterEntity> stageData = new ArrayList<>();

    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    private final int CROP_REQUEST_CODE = 0x006;
    // 裁减过后的照片地址
    private String cropedPhoto;
    // 拍照地址
    private String path;

    private boolean isEdit;//是否是编辑
    private ProjectBean projectBean;

    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAddProjectComponent.builder().appComponent(appComponent).addProjectModule(new AddProjectModule(this)).build().inject(this);
    }


    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.add_project_activity;
    }

    @Override
    protected void initData() {
        mImageLoader = customApplication.getAppComponent().imageLoader();
        isEdit=getIntent().getExtras().getBoolean(ConstantUtil.INTENT_IS_EDIT);

        if(isEdit){
            projectBean=getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);

            tvTitle.setText(getResources().getString(R.string.title_edit_project));


        }else{
            projectBean=new ProjectBean();
            projectBean.setLast_currency(1);
            projectBean.setLast_stage_id(ConstantUtil.SPECIAL_ID);
            tvTitle.setText(getResources().getString(R.string.title_add_project));
        }
        setValue();

        tvRight.setVisibility(View.VISIBLE);

        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);

        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .transformation(new RoundedCornersTransformation(this, com.qtin.sexyvc.ui.widget.DeviceUtils.dip2px(this,2),0))
                .errorPic(R.drawable.logo_blank)
                .placeholder(R.drawable.logo_blank)
                .url(CommonUtil.getAbsolutePath(projectBean.getLogo()))
                .imageView(ivLogo)
                .build());
    }

    private void setValue(){
        tvName.setText(StringUtil.formatString(projectBean.getProject_name()));
        tvIntroduce.setText(StringUtil.formatString(projectBean.getShort_intro()));
    }

    @Override
    public void showLoading() {
        showDialog(getResources().getString(R.string.dealing));
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this,StringUtil.formatString(message));
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.logoContainer, R.id.nameContainer, R.id.introduceContainer, R.id.domainContainer, R.id.financeContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(StringUtil.isBlank(projectBean.getProject_name())){
                    showMessage("项目名称不能为空");
                    return;
                }

                if(StringUtil.isBlank(projectBean.getShort_intro())){
                    showMessage("一句话简介不能为空");
                    return;
                }

                if(projectBean.getDomain_id()==0){
                    showMessage("行业不能为空");
                    return;
                }

                if(projectBean.getLast_stage_id()==ConstantUtil.SPECIAL_ID){
                    showMessage("请选择轮次标签");
                    return;
                }


                if(cropedPhoto==null){
                    if(isEdit){
                        mPresenter.editProject(projectBean);
                    }else{
                        mPresenter.createProject(projectBean);
                    }
                }else{
                    mPresenter.getQiNiuToken(cropedPhoto);
                }

                break;
            case R.id.logoContainer:
                showPhotoDialog(true);
                break;
            case R.id.nameContainer:
                Bundle nameBundle = new Bundle();
                nameBundle.putString(ModifyActivity.MODIFY_INTENT_VALUE1, StringUtil.formatString(projectBean.getProject_name()));
                nameBundle.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_PROJECT_NAME);
                gotoActivityForResult(ModifyActivity.class, nameBundle, ModifyActivity.MODIFY_PROJECT_NAME);
                break;
            case R.id.introduceContainer:
                Bundle introduceBundle = new Bundle();
                introduceBundle.putString(ModifyActivity.MODIFY_INTENT_VALUE1, StringUtil.formatString(projectBean.getShort_intro()));
                introduceBundle.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_PROJECT_INTRODUCE);
                gotoActivityForResult(ModifyActivity.class, introduceBundle, ModifyActivity.MODIFY_PROJECT_INTRODUCE);
                break;
            case R.id.domainContainer:
                showDomainDialog();
                break;
            case R.id.financeContainer:
                showStageDialog();
                break;
        }
    }

    /**
     * 相册弹出框
     */
    private void showPhotoDialog(boolean isAvatar) {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        if(isAvatar){
            view.findViewById(R.id.tvHint).setVisibility(View.GONE);
            view.findViewById(R.id.lineHint).setVisibility(View.GONE);
        }

        View btn_report =view.findViewById(R.id.btn_report);
        View btn_error =view.findViewById(R.id.btn_error);
        View cancleSelected =view.findViewById(R.id.cancleSelected);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                takePhoto();
            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                goToAlbum();
            }
        });
        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
            }
        });
        selectPhotoDialog = new Dialog(this);
        selectPhotoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPhotoDialog.setContentView(view);
        Window regionWindow = selectPhotoDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectPhotoDialog.setCanceledOnTouchOutside(true);
        selectPhotoDialog.show();
    }


    /**
     * 显示行业的dialog
     */
    private void showDomainDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.domain_dialog, null);
        final DomainHolder holder = new DomainHolder(view);
        holder.tvCancleDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDomainDialog();
            }
        });

        holder.tvComfirmDomain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissDomainDialog();
                long domain_id=0;
                for(FilterEntity entity:domainData){
                    if(entity.isSelected()){
                        domain_id=entity.getType_id();
                        break;
                    }
                }
                projectBean.setDomain_id(domain_id);
                setDomainText();
            }
        });
        domainAdapter = new TagAdapter<FilterEntity>(domainData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(AddProjectActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
                //AutoUtils.auto(tv);
                TextView tv= (TextView) view.findViewById(R.id.tvFilter);
                if(o.isSelected()){
                    tv.setSelected(true);
                }else{
                    tv.setSelected(false);
                }
                tv.setText(o.getType_name());
                return view;
            }
        };
        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(view instanceof LinearLayout){
                    LinearLayout layout= (LinearLayout) view;
                    TextView tv= (TextView) layout.getChildAt(0);
                    if(!domainData.get(position).isSelected()){
                        for(FilterEntity entity:domainData){
                            entity.setSelected(false);
                        }
                        domainData.get(position).setSelected(true);
                        tv.setSelected(true);
                        domainAdapter.notifyDataChanged();
                    }
                }

                return false;
            }
        });

        setDomainStatus();

        holder.flowLayout.setAdapter(domainAdapter);
        holder.flowLayout.setMaxSelectCount(1);
        domainDialog = new Dialog(this);
        domainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        domainDialog.setContentView(view);
        Window regionWindow = domainDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        domainDialog.setCanceledOnTouchOutside(true);
        domainDialog.show();
    }

    private void dismissDomainDialog() {
        if (domainDialog != null && domainDialog.isShowing()) {
            domainDialog.dismiss();
        }
    }

    /**
     * 显示阶段的dialog
     */
    public void showStageDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.stage_dialog, null);

        final StageHolder holder=new StageHolder(view);
        stageAdapter=new TagAdapter<FilterEntity>(stageData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(AddProjectActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
                //AutoUtils.auto(tv);
                TextView tv= (TextView) view.findViewById(R.id.tvFilter);
                tv.setText(o.getType_name());
                if(o.isSelected()){
                    tv.setSelected(true);
                }else{
                    tv.setSelected(false);
                }
                return view;
            }
        };
        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(view instanceof LinearLayout){
                    LinearLayout layout= (LinearLayout) view;
                    TextView tv= (TextView) layout.getChildAt(0);

                    if(!stageData.get(position).isSelected()){
                        for(FilterEntity entity:stageData){
                            entity.setSelected(false);
                        }
                        stageData.get(position).setSelected(true);
                        tv.setSelected(true);
                        stageAdapter.notifyDataChanged();
                    }
                }

                return false;
            }
        });

        setStageStatus();
        holder.flowLayout.setAdapter(stageAdapter);
        holder.flowLayout.setMaxSelectCount(1);

        holder.changeMoneyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectBean.getLast_currency()==0){
                    projectBean.setLast_currency(1);
                    holder.tvMoneyType.setText(getResources().getString(R.string.renminbi));
                }else{
                    projectBean.setLast_currency(0);
                    holder.tvMoneyType.setText(getResources().getString(R.string.dollar));
                }
            }
        });

        InputFilter[] filters={new CashierInputFilter()};
        holder.etMoney.setFilters(filters);
        String money=""+projectBean.getLast_financial_amount();
        if(projectBean.getLast_financial_amount()==0){
            holder.etMoney.setText("");
        }else{
            holder.etMoney.setText(money);
            holder.etMoney.setSelection(money.length());
        }

        if(projectBean.getLast_currency()==0){
            holder.tvMoneyType.setText(getResources().getString(R.string.dollar));
        }else{
            holder.tvMoneyType.setText(getResources().getString(R.string.renminbi));
        }

        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
            }
        });
        holder.tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
                long stage_id=ConstantUtil.SPECIAL_ID;
                for(FilterEntity entity:stageData){
                    if(entity.isSelected()){
                        stage_id=entity.getType_id();
                        break;
                    }
                }
                if(stage_id==ConstantUtil.SPECIAL_ID){
                    showMessage("请选择轮次标签");
                    return;
                }

                projectBean.setLast_stage_id(stage_id);
                if(stage_id==0){
                    projectBean.setLast_financial_amount(0);
                }else{
                    try{
                        projectBean.setLast_financial_amount(Long.parseLong(holder.etMoney.getText().toString()));
                    }catch(Exception e){
                        e.printStackTrace();
                        projectBean.setLast_financial_amount(0);
                    }
                }
                setStageText();
            }
        });

        stageDialog = new Dialog(this);
        stageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stageDialog.setContentView(view);
        Window inputWindow = stageDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        stageDialog.setCanceledOnTouchOutside(true);
        stageDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.etMoney.setFocusable(true);
                InputMethodManager inputMethodManager = (InputMethodManager) holder.etMoney.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    public void dismissStageDialog() {
        if (stageDialog != null && stageDialog.isShowing()) {
            stageDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ModifyActivity.MODIFY_PROJECT_NAME:
                if (data != null) {
                    String name = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                    tvName.setText(StringUtil.formatString(name));
                    projectBean.setProject_name(name);
                }
                break;
            case ModifyActivity.MODIFY_PROJECT_INTRODUCE:
                if (data != null) {
                    String introduce = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                    tvIntroduce.setText(StringUtil.formatString(introduce));
                    projectBean.setShort_intro(introduce);
                }
                break;
            case ALBUM_REQUEST_CODE:
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Uri uri = Uri.fromFile(new File(picturePath));
                    cropPhoto(uri);
                    // 获取图片并显示
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CAMERA_REQUEST_CODE:
                try {
                    if (path != null) {
                        String newPath = path;
                        int angle = DeviceUtils.readPictureDegree(path);
                        if (angle != 0) {
                            newPath = path + "r.png";
                            Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                    path, BaseApplication.screenSize.x,
                                    BaseApplication.screenSize.y, true);
                            Bitmap bm = DeviceUtils.rotaingImageView(angle, bitmapOriginal);
                            OutputStream stream = new FileOutputStream(newPath);
                            bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                            DeviceUtils.recycle(bm);
                        } else {
                            newPath = path;
                        }

                        File file = new File(newPath);
                        if (file != null && file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            cropPhoto(uri);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

                break;
            case CROP_REQUEST_CODE: // 裁剪
                if(data!=null){
                    //mPresenter.getQiNiuToken(cropedPhoto);
                    Bitmap bitmap= BitmapFactory.decodeFile(cropedPhoto);
                    ivLogo.setImageBitmap(bitmap);
                }

                break;
        }
    }

    private void setDomainText(){
        if(domainData!=null){
            for(int i=0;i<domainData.size();i++){
                if(domainData.get(i).getType_id()==projectBean.getDomain_id()){
                    tvDomain.setText(domainData.get(i).getType_name());
                    domainData.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    private void setDomainStatus(){
        if(domainData!=null){
            for(int i=0;i<domainData.size();i++){
                if(domainData.get(i).getType_id()==projectBean.getDomain_id()){
                    domainAdapter.setSelectedList(i);
                    break;
                }
            }
        }
    }

    private void setStageText(){
        if(stageData!=null){
            for(int i=0;i<stageData.size();i++){
                if(stageData.get(i).getType_id()==projectBean.getLast_stage_id()){
                    setStageValue(stageData.get(i).getType_name());
                    stageData.get(i).setSelected(true);
                    break;
                }
            }

        }
    }

    private void setStageValue(String stage){
        long money=projectBean.getLast_financial_amount();
        if(money<=0){
            tvFinance.setText(StringUtil.formatString(stage));
        }else{
            StringBuilder sb=new StringBuilder();
            sb.append(stage);
            sb.append("  ");
            String moneyValue=money/10000f+"";
            sb.append(moneyValue);
            sb.append("万");
            if(projectBean.getLast_currency()==0){
                sb.append(getResources().getString(R.string.dollar));
            }else{
                sb.append(getResources().getString(R.string.renminbi));
            }
            tvFinance.setText(sb.toString());
        }
    }

    private void setStageStatus(){
        if(stageData!=null){
            for(int i=0;i<stageData.size();i++){
                if(stageData.get(i).getType_id()==projectBean.getLast_stage_id()){
                    stageAdapter.setSelectedList(i);
                    break;
                }
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_DOMAIN:
                domainData.clear();
                domainData.addAll(list);

                setDomainText();
                break;
            case TYPE_STAGE:
                stageData.clear();

                FilterEntity entity=new FilterEntity();
                entity.setType_id(0);
                entity.setType_name("未融资");

                stageData.add(entity);
                stageData.addAll(list);

                setStageText();
                break;
        }
    }

    @Override
    public void onEditSuccess(ProjectBean bean) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,bean);
        intent.putExtras(bundle);
        setResult(0,intent);
        finish();
    }

    @Override
    public void onCreateSuccess(ProjectBean bean) {
        mPresenter.updateProjectState();
        Intent intent=new Intent();
        intent.putExtra("has_project",1);
        setResult(0,intent);
        finish();
    }

    @Override
    public void uploadPhotoSuccess(String imageUrl) {
        projectBean.setLogo(imageUrl);
        if(isEdit){
            mPresenter.editProject(projectBean);
        }else{
            mPresenter.createProject(projectBean);
        }
    }

    @Override
    public void showProgress(String msg) {
        showDialog(msg);
    }

    @Override
    public void hideProgress() {
        dialogDismiss();
    }

    public void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA);
            String fileName = sdf.format(new Date());
            File file = new File(DataHelper.getCacheFile(getApplicationContext()), fileName);
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
            path = DataHelper.getCacheFile(getApplicationContext()).getAbsolutePath() + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToAlbum() {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, ALBUM_REQUEST_CODE);
    }

    // 裁减
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        cropedPhoto = DataHelper.getCacheFile(getApplicationContext()) + File.separator
                + System.currentTimeMillis() + ".png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(cropedPhoto)));
        intent.putExtra("return-data", false);// 若为false则表示不返回数据
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    static class DomainHolder {
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCancleDomain)
        TextView tvCancleDomain;
        @BindView(R.id.tvComfirmDomain)
        TextView tvComfirmDomain;

        DomainHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class StageHolder {
        @BindView(R.id.etMoney)
        EditText etMoney;
        @BindView(R.id.tvMoneyType)
        TextView tvMoneyType;
        @BindView(R.id.changeMoneyContainer)
        LinearLayout changeMoneyContainer;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCancle)
        TextView tvCancle;
        @BindView(R.id.tvComfirm)
        TextView tvComfirm;

        StageHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
