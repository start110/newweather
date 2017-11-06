package com.newweather.app;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.newweather.app.utils.Constant;
import com.newweather.app.utils.HttpUtil;
import com.newweather.app.utils.PrefUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 闪屏
 */
public class SplashActivity extends Activity {

    private RelativeLayout activity_splash;
    private ImageView mImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        initAnimation();
    }

    private void initData() {
        SharedPreferences  sp = PreferenceManager.getDefaultSharedPreferences(this);
        //获取必应每日一图
        String bingPic =sp.getString("bing_pic",null);
        if(bingPic != null){    //加载缓存的
            Glide.with(this).load(bingPic).into(mImg);
        }else{      //加载服务器的
            loadBingPic();
        }
    }

    private void initView() {
        activity_splash= (RelativeLayout) findViewById(R.id.activity_splash);
        mImg = (ImageView)findViewById(R.id.iv_img);
    }

    private void initAnimation() {

        //旋转动画
        //以自身为中心旋转360度
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画时间为2秒
        rotateAnimation.setDuration(1000);
        //显示动画完成后的视图
        rotateAnimation.setFillAfter(true);

        //缩放动画
        //以自身为中心从0倍放大到1倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        //声明动画集
        AnimationSet set = new AnimationSet(false);
        //添加动画
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                 boolean isflag = PrefUtils.getBoolean(SplashActivity.this,"is_user_guide_showed",false);


                if(!isflag){
                Intent  intent = new Intent(SplashActivity.this,BoundActivity.class);
                startActivity(intent);
                }else{
                    Intent  intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //开始动画
        activity_splash.startAnimation(set);


    }
    private void loadBingPic() {
        HttpUtil.sendOkHttpRequest(Constant.BINGPIC, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(SplashActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(SplashActivity.this).load(bingPic).into(mImg);
                    }
                });
            }



        });

    }

}
