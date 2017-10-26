package com.example.administrator.myshoptest.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myshoptest.MainActivity;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.LoginActivity;
import com.example.administrator.myshoptest.eventbus.DataSynEvent;
import com.example.administrator.myshoptest.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 我的界面
 * Created by Ivan on 15/9/22.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.img_head)
    CircleImageView mImgHead;
    @BindView(R.id.text_username)
    TextView mTextUsername;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    @BindView(R.id.txt_my_orders)
    TextView mTxtMyOrders;
    @BindView(R.id.txt_my_favorite)
    TextView mTxtMyFavorite;
    @BindView(R.id.txt_my_address)
    TextView mTxtMyAddress;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.img_head, R.id.text_username, R.id.txt_my_orders, R.id.txt_my_favorite, R.id.txt_my_address, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                Intent intent_img_head = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_img_head);
                break;
            case R.id.text_username:

                Intent intent_txt_username = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_txt_username);
                break;
            case R.id.txt_my_orders:
                ToastUtils.show(getContext(),"点击我的订单");
                break;
            case R.id.txt_my_favorite:
                ToastUtils.show(getContext(),"点击我的收藏");
                MainActivity activity = new MainActivity();
                EventBus.getDefault().post(new DataSynEvent(1));
                break;
            case R.id.txt_my_address:
                ToastUtils.show(getContext(),"点击我的收货地址");
                break;
            case R.id.btn_logout:
                Intent intent_btn = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_btn);
                break;
        }
    }
}
