package com.cm.fm.mall.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.fm.mall.R;
import com.cm.fm.mall.base.BaseMVPActivity;
import com.cm.fm.mall.contract.activity.RegisterContract;
import com.cm.fm.mall.common.MallConstant;
import com.cm.fm.mall.presenter.activity.RegisterPresenter;
import com.cm.fm.mall.common.util.LogUtil;
import com.cm.fm.mall.common.util.Utils;
/**
 * 注册页面
 *  1、activityId : 3
 *  2、本页所有请求码以 300 开始
 */
public class RegisterActivity extends BaseMVPActivity<RegisterPresenter> implements RegisterContract.View,View.OnClickListener  {
    private Activity context;
    private ProgressBar pb_progress;
    private EditText et_register_account;
    private EditText et_register_password;
    private TextView tv_register_back,tv_register_login;
    private Button bt_register_btn;
    private ImageView iv_register_imageView_lock;

    private boolean TypeIsPassword = true;
    private final String TAG = "FM_RegisterActivity";
    @Override
    protected void activityAnim() {
        //告知页面，使用动画
        Utils.actUseAnim(context,R.transition.fade);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {
        et_register_account = findViewById(R.id.et_register_account);
        et_register_password = findViewById(R.id.et_register_password);
        tv_register_back = findViewById(R.id.tv_register_back);
        tv_register_login = findViewById(R.id.tv_register_login);
        pb_progress = findViewById(R.id.pb_progress_reg);
        //TODO 密码框默认是密文类型，密文需与 TYPE_CLASS_TEXT 一起设置才生效
        et_register_password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        bt_register_btn = findViewById(R.id.bt_register_btn);
        iv_register_imageView_lock = findViewById(R.id.iv_register_imageView_lock);

        //密文明文背景图片切换
        iv_register_imageView_lock.setOnClickListener(this);
        //点击注册
        bt_register_btn.setOnClickListener(this);
        tv_register_back.setOnClickListener(this);
        //直接登陆
        tv_register_login.setOnClickListener(this);
    }

    @Override
    protected int initLayout() {
        context = this;
        return R.layout.activity_register;
    }

    @Override
    public void OnRegisterResult(int code, String msg) {
        switch (code){
            case MallConstant.SUCCESS:
//                Utils.startActivityClose(context,LoginActivity.class);
                //注册成功，直接回传 UserFragment 状态
                setResult(RESULT_OK);
                this.finish();
                break;
            case MallConstant.FAIL:
                Utils.tips(context,msg);
                break;
        }

    }

    @Override
    public void showLoading() {
        super.showLoading();
        pb_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        pb_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_imageView_lock:
                if(TypeIsPassword){
                    //如果当前 密码框 是密文类型，就替换为 文本明文 类型，并替换背景图片
                    et_register_password.setInputType( InputType.TYPE_CLASS_TEXT);
                    iv_register_imageView_lock.setBackground(getResources().getDrawable(R.mipmap.ic_partial_secure));
                    TypeIsPassword = false;
                }else {
                    //设置为文本密码类型，并替换背景图片
                    et_register_password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_register_imageView_lock.setBackground(getResources().getDrawable(R.mipmap.ic_secure));
                    TypeIsPassword = true;
                }
                break;
            case R.id.bt_register_btn:
                //点击了注册按钮
                LogUtil.d(TAG,"onClick");
                String account = et_register_account.getText().toString();
                String password = et_register_password.getText().toString();
//                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                if(account.isEmpty() || password.isEmpty()){
                    Utils.tips (context,"提示：账号密码不能为空！");
                    return;
                }
                mPresenter.registerP(account,password);
                break;
            case R.id.tv_register_login:
                //点击直接登陆
                /**先将结果返回给 UserFragment ，让fragment自行拉起 loginActivity。
                 * 否则直接在注册页拉起登录页，暂时没法将结果通知给fragment
                 */
                Intent intent = new Intent();
                intent.putExtra("type","login");
                setResult(RESULT_OK,intent);
                context.finish();
            case R.id.tv_register_back:
                context.finish();
                break;
        }
    }

}
