package com.newweather.app.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newweather.app.MainActivity;
import com.newweather.app.R;
import com.newweather.app.WeatherActivity;
import com.newweather.app.entity.City;
import com.newweather.app.entity.County;
import com.newweather.app.entity.Province;
import com.newweather.app.utils.Constant;
import com.newweather.app.utils.HttpUtil;
import com.newweather.app.utils.JsonUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Qing on 2017/10/28.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private Button backBt;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backBt = (Button) view.findViewById(R.id.bt_back);
        listView =(ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("选择了该城市","");
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {

                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(i);
                    queryCounties();
                }else if(currentLevel == LEVEL_COUNTY){
                    String weatherId = countyList.get(i).getWeatherId();
                    if(getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        Log.d("选择了该城市111111",weatherId+"dddd");
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof  WeatherActivity){
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.mDrawerLayout.closeDrawers();
                        activity.mSwipeRefreshLayout.setRefreshing(true);
                        activity.requestWeather(weatherId);
                        Log.d("选择了该城市",weatherId+"dddd");

                    }
                }
            }
        });
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }

            }
        });

        queryProvinces();
    }
    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryProvinces() {

        titleText.setText("非帆天气");
        backBt.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);

        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());

            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String url = Constant.CHINA;
            queryFromServer(url, "province");

        }
    }


    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCities() {
       titleText.setText(selectedProvince.getProvinceName());
        backBt.setVisibility(View.VISIBLE);
        cityList =DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);

            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode =selectedProvince.getProvinceCode();
            String url = Constant.CHINA+"/"+provinceCode;
            Log.d("地址",url);
            queryFromServer(url, "city");

        }
    }


    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backBt.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String url = Constant.CHINA+"/"+provinceCode+"/"+cityCode;
            queryFromServer(url, "county");
        }
    }


    /**
     * 根据传入的代号和类型从服务器上查询省市县数据
     */
    private void queryFromServer( String url, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(),"服务器连接失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String responseText = response.body().string();
                Log.d("数据",responseText);
                boolean result = false;
                if ("province".equals(type)) {

                    result = JsonUtil.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = JsonUtil.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    Log.d("数据",result+"");
                    result = JsonUtil.handleCountyResponse(responseText, selectedCity.getId());

                }

                if (result) {

                    // 通过runOnUiThread()方法回到主线程处理逻辑
                  getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                        });
                }

            }
                    });

            }


            /**
             * 显示进度对话框
             */
            private void showProgressDialog() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("正在加载...");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.show();
            }
            /**
             * 关闭进度对话框
             */
            private void closeProgressDialog() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }


}
