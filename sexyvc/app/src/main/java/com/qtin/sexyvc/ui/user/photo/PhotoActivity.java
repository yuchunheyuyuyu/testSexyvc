package com.qtin.sexyvc.ui.user.photo;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.UploadPhotoUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.photo.di.DaggerPhotoComponent;
import com.qtin.sexyvc.ui.user.photo.di.PhotoModule;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class PhotoActivity extends MyBaseActivity<PhotoPresent> implements PhotoContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.photoView)
    ImageView photoView;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvStatusHint)
    TextView tvStatusHint;

    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private String url;
    private int u_auth_state;

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    // 拍照地址
    private String path;

    private Dialog warnDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoComponent.builder().appComponent(appComponent).photoModule(new PhotoModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.photo_activity;
    }

    @Override
    protected void initData() {
        url = getIntent().getExtras().getString(ConstantUtil.INTENT_URL);
        u_auth_state = getIntent().getExtras().getInt("u_auth_state");
        tvTitle.setText(getResources().getString(R.string.title_vertify_position));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.cancle_identity));
        mImageLoader = customApplication.getAppComponent().imageLoader();
        setText();
    }

    private void setText() {
        boolean isBold = false;
        if (u_auth_state == ConstantUtil.AUTH_STATE_PASS) {
            tvStatus.setText(getString(R.string.status_pass));
            tvStatus.setTextColor(getResources().getColor(R.color.dusk90));
            tvStatusHint.setText("");
            tvRight.setVisibility(View.VISIBLE);

            //加载图片
            mImageLoader.loadImage(customApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.add_business_card)
                    .errorPic(R.drawable.add_business_card)
                    .url(CommonUtil.getAbsolutePath(url))
                    .imageView(photoView)
                    .build());

        } else if (u_auth_state == ConstantUtil.AUTH_STATE_COMMITING) {
            isBold = true;
            tvStatus.setText(getString(R.string.status_commiting_pass));
            tvStatusHint.setText(getString(R.string.status_commiting_hint));
            tvStatus.setTextColor(getResources().getColor(R.color.dusk));
            tvRight.setVisibility(View.VISIBLE);

            //加载图片
            mImageLoader.loadImage(customApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.add_business_card)
                    .errorPic(R.drawable.add_business_card)
                    .url(CommonUtil.getAbsolutePath(url))
                    .imageView(photoView)
                    .build());

        } else {
            tvStatus.setText(getString(R.string.status_no_pass));
            tvStatusHint.setText(getString(R.string.status_no_pass_hint));
            tvStatus.setTextColor(getResources().getColor(R.color.dusk90));
            tvRight.setVisibility(View.GONE);

            photoView.setImageResource(R.drawable.add_business_card);
        }

        TextPaint paint = tvStatus.getPaint();
        paint.setFakeBoldText(isBold);
    }

    @Override
    public void showLoading() {
        showDialog("正在提交");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    private void setResultBack(){
        Intent intent = new Intent();
        intent.putExtra("u_auth_state", u_auth_state);
        intent.putExtra("url", url);
        setResult(0, intent);
    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.photoView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if (u_auth_state == ConstantUtil.AUTH_STATE_PASS) {
                    showCancleDialog(getString(R.string.warn_pass));
                } else if (u_auth_state == ConstantUtil.AUTH_STATE_COMMITING) {
                    showCancleDialog(getString(R.string.warn_commiting));
                }
                break;
            case R.id.photoView:
                if (u_auth_state == ConstantUtil.AUTH_STATE_UNPASS) {
                    showPhotoDialog();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALBUM_REQUEST_CODE:
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    File file = new File(picturePath);
                    if (file != null && file.exists()) {
                        Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                picturePath, BaseApplication.screenSize.x,
                                BaseApplication.screenSize.y, true);
                        String newPath = picturePath + "r.png";
                        OutputStream stream = new FileOutputStream(newPath);
                        bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        DeviceUtils.recycle(bitmapOriginal);
                        mPresenter.getQiNiuToken(newPath);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CAMERA_REQUEST_CODE:
                try {
                    if (path != null) {
                        String newPath = path;
                        int angle = DeviceUtils.readPictureDegree(path);

                        newPath = path + "r.png";
                        Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                path, BaseApplication.screenSize.x,
                                BaseApplication.screenSize.y, true);

                        OutputStream stream = new FileOutputStream(newPath);
                        if (angle != 0) {
                            Bitmap bm = DeviceUtils.rotaingImageView(angle, bitmapOriginal);
                            bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                            DeviceUtils.recycle(bm);
                        } else {
                            bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                        }

                        File file = new File(newPath);
                        if (file != null && file.exists()) {
                            mPresenter.getQiNiuToken(newPath);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    /**
     * 相册弹出框
     */
    private void showPhotoDialog() {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        View btn_report = view.findViewById(R.id.btn_report);
        View btn_error = view.findViewById(R.id.btn_error);
        View cancleSelected = view.findViewById(R.id.cancleSelected);
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


    @Override
    public void uploadSuccess(String url) {
        u_auth_state = ConstantUtil.AUTH_STATE_COMMITING;
        this.url=url;
        setResultBack();
        setText();
        /**showComfirmDialog("已完成提交", "我们的工作人员将在 3 个工作日内审核", "好", new ComfirmListerner() {
            @Override
            public void onComfirm() {
                dismissComfirmDialog();
                finish();
            }
        });*/
    }

    @Override
    public void cancleAuthSuccess() {
        u_auth_state = ConstantUtil.AUTH_STATE_UNPASS;
        url = "";
        setResultBack();
        setText();
    }

    @Override
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }

    /**
     * 取消认证diglog
     */
    protected void showCancleDialog(String content) {

        View view = View.inflate(this, R.layout.warn_dialog, null);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(content);

        view.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warnDialog != null && warnDialog.isShowing()) {
                    warnDialog.dismiss();
                }
            }
        });

        view.findViewById(R.id.tvComfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warnDialog != null && warnDialog.isShowing()) {
                    warnDialog.dismiss();
                }
                mPresenter.cancelAuth();
            }
        });
        warnDialog = new Dialog(this);
        warnDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        warnDialog.setContentView(view);
        Window regionWindow = warnDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warnDialog.setCanceledOnTouchOutside(true);
        warnDialog.show();
    }

}
