package com.example.administrator.myshoptest.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.Base.BaseActivity;
import com.example.administrator.myshoptest.bean.JsonBean;
import com.example.administrator.myshoptest.bean.User;
import com.example.administrator.myshoptest.utils.GetJsonDataUtil;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.ClearEditText;
import com.example.administrator.myshoptest.widget.ToolbarHelper;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.activity
 * 项目日期：2017/11/1
 * 作者：liux
 * 功能：添加地址界面
 *
 * @author 75095
 */

public class AddressActivity extends BaseActivity {


    @BindView(R.id.edittxt_consignee)
    ClearEditText edittxtConsignee;
    @BindView(R.id.edittxt_phone)
    ClearEditText edittxtPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.ll_city_picker)
    LinearLayout llCityPicker;
    @BindView(R.id.edittxt_add)
    ClearEditText edittxtAdd;

    private String consignee;
    private String phone;
    private String address;
    private String addDetial;

    private User mUser;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("添加收货地址");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHelper.setMenuTitle("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGetText();
                ToastUtils.show(AddressActivity.this, "提交成功");
            }
        });
    }

    @Override
    public void initView() {
        initJsonData();
    }

    private void initGetText() {
        consignee = edittxtConsignee.getText().toString();
        phone = edittxtPhone.getText().toString();
        address = txtAddress.getText().toString();
        addDetial = edittxtAdd.getText().toString();

        mUser = new User();
        mUser.setConsignee(consignee);
        mUser.setPhone(phone);
        mUser.setAddress(address);
        mUser.setAddDetial(addDetial);
        Logger.e(mUser.toString());

        // 设置Intent回调 并设置回调内容
        Intent intent = new Intent();
        intent.putExtra("data", mUser);
        setResult(2, intent);

        //  结束当前页面(关闭当前界面)
        finish();
    }



    @OnClick(R.id.ll_city_picker)
    public void onViewClicked() {
        showPickerView();
    }

    private void showPickerView() {
        // 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                txtAddress.setText(tx);
                Toast.makeText(AddressActivity.this,tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();

    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String jsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(jsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> provinceArealist = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市

                ArrayList<String> cityArealist = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    cityArealist.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String areaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        cityArealist.add(areaName);//添加该城市所有地区数据
                    }
                }
                provinceArealist.add(cityArealist);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(provinceArealist);
        }


    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
