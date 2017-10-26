package com.example.administrator.myshoptest.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.bean.Campaign;
import com.example.administrator.myshoptest.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/13.
 */

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<HomeCampaign> homeCampaignList;

    private static int VIEW_TYPE_L = 0;
    private static int VIER_TYPE_R = 1;

    private OnCampaignClickListener mOnCampaignClickListener;

    public HomeCatgoryAdapter(Context context, List<HomeCampaign> homeCampaignList) {
        mContext = context;
        this.homeCampaignList = homeCampaignList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_L) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.template_home_cardview, parent, false));
        } else {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.template_home_cardview2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampaign campaign = homeCampaignList.get(position);
        holder.mTextTitle.setText(campaign.getTitle());
        //从网络上获取图片数据，并进行缓存
        Picasso.with(mContext).load(campaign.getCpOne().getImgUrl()).into(holder.mImgviewBig);
        Picasso.with(mContext).load(campaign.getCpTwo().getImgUrl()).into(holder.mImgviewSmallTop);
        Picasso.with(mContext).load(campaign.getCpThree().getImgUrl()).into(holder.mImgviewSmallBottom);
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_L;
        } else {
            return VIER_TYPE_R;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.imgview_big)
        ImageView mImgviewBig;
        @BindView(R.id.imgview_small_top)
        ImageView mImgviewSmallTop;
        @BindView(R.id.imgview_small_bottom)
        ImageView mImgviewSmallBottom;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        @OnClick({ R.id.imgview_big, R.id.imgview_small_top, R.id.imgview_small_bottom})
        public void onClick(View view) {
            /**
             * 这里记住前面的class ViewHolder 不要设置成 static
             * 不然会找不到mOnCampaignClickListener
             */
            if (mOnCampaignClickListener != null){
                anim(view);
            }

        }

        /**
         * 这里是设置首页商品点击的动画效果
         * @param view
         */
        private void anim(final View view) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotationX",0.0F,360.0F)
                    .setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    HomeCampaign campaign = homeCampaignList.get(getLayoutPosition());
                    switch (view.getId()) {
                        case R.id.imgview_big:
                            mOnCampaignClickListener.Onclick(view,campaign.getCpOne());
                            break;
                        case R.id.imgview_small_top:
                            mOnCampaignClickListener.Onclick(view,campaign.getCpTwo());
                            break;
                        case R.id.imgview_small_bottom:
                            mOnCampaignClickListener.Onclick(view,campaign.getCpThree());
                            break;
                    }
                }
            });
            animator.start();
        }
    }

    public interface OnCampaignClickListener {
        void Onclick(View view, Campaign campaign);
    }

    public void setOnCampaignClickListener(OnCampaignClickListener campaignClickListener) {
        this.mOnCampaignClickListener = campaignClickListener;
    }

}
