package com.example.administrator.myshoptest.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.CartAdapter;
import com.example.administrator.myshoptest.adapter.decoration.DividerItemDecoration;
import com.example.administrator.myshoptest.bean.ShoppingCart;
import com.example.administrator.myshoptest.utils.CartProvider;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.CnToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 购物车界面
 * Created by Ivan on 15/9/22.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;
    private static final String TAG = "CartFragment";

    @BindView(R.id.cart_toolbar)
    CnToolbar mCartToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @BindView(R.id.txt_total)
    TextView mTxtTotal;
    @BindView(R.id.btn_order)
    Button mBtnOrder;
    @BindView(R.id.btn_del)
    Button mBtnDel;


    private CartAdapter mCartAdapter;
    private CartProvider mCartProvider;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_cart, null);
        ButterKnife.bind(this, view);
        showToolbar();
        return view;
    }

    private void showToolbar() {
        mCartToolbar.hideSearchView();
        mCartToolbar.showTitleView();
        mCartToolbar.setTitle("购物车");
        mCartToolbar.getRightButton().setVisibility(View.VISIBLE);
        mCartToolbar.setRightButtonText("编辑");
        mCartToolbar.getRightButton().setOnClickListener(this);
        mCartToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    @Override
    protected void initData() {
        mCartProvider = new CartProvider(mContext);
        showData();
    }

    private void showData() {
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCartAdapter = new CartAdapter(mContext,carts,mCheckboxAll,mTxtTotal);
        mRecyclerView.setAdapter(mCartAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
    }


    @OnClick({R.id.btn_order, R.id.btn_del})
    public void onButtonClick(View view) {

        switch (view.getId()) {
            case R.id.btn_order:
                ToastUtils.show(getContext(), "以点击结算");
                /*Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                startActivity(intent,true);*/
                break;
            case R.id.btn_del:
                mCartAdapter.delcart();
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View view) {
        int action = (int) view.getTag();
        if (ACTION_EDIT == action) {
            showDelControl();
            ToastUtils.show(getContext(), "以点击编辑");
        }else if (ACTION_CAMPLATE == action){
            hideDelControl();
        }
    }


    /**
     * 隐藏删除按钮界面
     */
    private void hideDelControl() {
        mCartToolbar.getRightButton().setText("编辑");
        mTxtTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mCartToolbar.getRightButton().setTag(ACTION_EDIT);
        mCartAdapter.checkAll_None(true);
        mCheckboxAll.setChecked(true);
        mCartAdapter.showTotalPrice();
    }

    /**
     * 显示删除按钮界面
     */
    private void showDelControl() {
        mCartToolbar.getRightButton().setText("完成");
        mTxtTotal.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.GONE);
        mCartToolbar.getRightButton().setTag(ACTION_CAMPLATE);
        mCartAdapter.checkAll_None(false);
        mCheckboxAll.setChecked(false);
    }


    /**
     * 每次点击购物车按钮，进行本地数据的刷新
     */

    public void refData(){
        mCartAdapter.clearData();
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCartAdapter.addData(carts);
        mCartAdapter.showTotalPrice();
    }
}
