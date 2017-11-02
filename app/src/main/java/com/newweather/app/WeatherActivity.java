package com.newweather.app;

import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newweather.app.gson.Forecast;
import com.newweather.app.gson.Weather;
import com.newweather.app.service.AutoUpdateService;
import com.newweather.app.utils.Constant;
import com.newweather.app.utils.HttpUtil;
import com.newweather.app.utils.JsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 天气信息的Activity
 */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    private ScrollView mScrollView;

    private TextView mTitleCity;

    private TextView mTitleUpTime;

    private  TextView mDegreeText;

    private  TextView mWeatherInfoText;

    private LinearLayout mForecastLayout;

    private TextView mAqiText;

    private TextView mPm25Text;

    private  TextView mComfortText;

    private  TextView mCarWashText;

    private  TextView mSportText;

    private ImageView mPicImg;

    //污染程度
    private  TextView mQltyText;

    private TextView mAirText;

    private TextView mClothingText;

    private TextView mTravelText;

    //下拉刷新
    public SwipeRefreshLayout mSwipeRefreshLayout;

    public DrawerLayout mDrawerLayout;
    private Button mBtMenu;
    private  Button mBtHome;
    public  static boolean flag;
    private SharedPreferences sp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*if(Build.VERSION.SDK_INT >= 21){
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
          getWindow().setStatusBarColor(Color.TRANSPARENT);

        }*/
        setContentView(R.layout.activity_weather);
        Log.d("开始",flag+"");
        initView();
        initListener();
        initData();


    }

    private void initListener() {
        mBtMenu.setOnClickListener(this);
        mBtHome.setOnClickListener(this);
    }


    /**
     * 初始化控件
     */
    private void initView() {

        mScrollView = (ScrollView) findViewById(R.id.weather_layout);
        mTitleCity = (TextView) findViewById(R.id.title_city);
        mTitleUpTime = (TextView) findViewById(R.id.title_update_time);
        mDegreeText = (TextView) findViewById(R.id.degree_text);
        mWeatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        mForecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        mAqiText = (TextView) findViewById(R.id.aqi_text);
        mPm25Text = (TextView) findViewById(R.id.pm25_text);
        mComfortText = (TextView) findViewById(R.id.comfort_text);
        mCarWashText = (TextView) findViewById(R.id.car_wash_text);
        mSportText = (TextView) findViewById(R.id.sport_text);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //mPicImg = (ImageView) findViewById(R.id.iv_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mBtMenu = (Button) findViewById(R.id.bt_menu);
        mQltyText = (TextView) findViewById(R.id.qlty_text);
        mAirText = (TextView) findViewById(R.id.air_text);
        mClothingText = (TextView) findViewById(R.id.clothing_text);
        mTravelText = (TextView) findViewById(R.id.travel_text);
        mBtHome = (Button) findViewById(R.id.bt_home);


    }


    private void initData() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
         sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather",null);

        final String weatherId;
        if(weatherString != null){  //有缓存的数据,则直接解析
           Weather weather = JsonUtil.jsonWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            Log.i("此城的天气情况11111111",weatherId);
            showWeatherInfo(weather);
        }else{                  //加载服务器的数据
             weatherId = getIntent().getStringExtra("weather_id");
            Log.i("此城的天气情况222222222",weatherId);
            mScrollView.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        //手动刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             Log.i("此城的天气情况",weatherId);
                requestWeather(weatherId);
            }
        });
       /* //获取必应每日一图
        String bingPic =sp.getString("bing_pic",null);
        if(bingPic != null){    //加载缓存的
            Glide.with(this).load(bingPic).into(mPicImg);
        }else{      //加载服务器的
            loadBingPic();
        }*/
    }
/*
    private void loadBingPic() {
        HttpUtil.sendOkHttpRequest(Constant.BINGPIC, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(mPicImg);
                    }
                });

            }
        });

    }*/

    /**
     * 根据天气id请求天气信息
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = Constant.GUOLIN+"weather?cityid="+
                weatherId+"&key=4a9a4e7b24c744299d28f7e7a44f3bf0";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"天气信息可能被黑洞吸走，请稍后再尝试...",Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final  String responseText = response.body().string();
                final Weather weather = JsonUtil.jsonWeatherResponse(responseText);

                Log.d("json数据",responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(weather != null && "ok".equals(weather.status)){
                            showWeatherInfo(weather);
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();

                        }else{
                            Toast.makeText(WeatherActivity.this,"天气信息正在路上",Toast.LENGTH_SHORT).show();

                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        });
        //loadBingPic();
    }

    /**
     * 处理并展示weather实体类中的信息
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updtaTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        mTitleCity.setText(cityName);
        mTitleUpTime.setText(updtaTime);
        mDegreeText.setText(degree);
        mWeatherInfoText.setText(weatherInfo);
        mForecastLayout.removeAllViews();

        for(Forecast forcast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,mForecastLayout,false);
            TextView date =view.findViewById(R.id.date_text);
            TextView info =view.findViewById(R.id.info_text);
            TextView max =view.findViewById(R.id.max_text);
            TextView min =view.findViewById(R.id.min_text);
            date.setText(forcast.date);
            info.setText(forcast.more.info);
            max.setText(forcast.temperature.max+"℃");
            min.setText(forcast.temperature.min+"℃");
            mForecastLayout.addView(view);

        }
        if(weather.aqi != null){
            mQltyText.setText(weather.aqi.city.qlty);
            mAqiText.setText(weather.aqi.city.aqi);
            mPm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = weather.suggestion.comfort.info;
        String carWash = weather.suggestion.carWash.info;
        String sport = weather.suggestion.sport.info;
        String air = weather.suggestion.air.info;
        String clothing = weather.suggestion.clothing.info;
        String travel = weather.suggestion.travel.info;
        mComfortText.setText(comfort);
        mCarWashText.setText(carWash);
        mSportText.setText(sport);
        mAirText.setText(air);
        mClothingText.setText(clothing);
        mTravelText.setText(travel);
        mScrollView.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_menu:  //打开滑动菜单
                flag =true;
                mDrawerLayout.openDrawer(GravityCompat.START);
                Log.d("menu按钮",flag+"");
                break;
            case R.id.bt_home:
                flag=false;
                Intent intent = new Intent(this,BoundActivity.class);
                startActivity(intent);
                Log.d("home按钮",flag+"");
                sp.edit().clear().commit();

                break;
        }
    }
}
