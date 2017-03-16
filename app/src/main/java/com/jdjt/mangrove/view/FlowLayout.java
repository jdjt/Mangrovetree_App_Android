package com.jdjt.mangrove.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * @author wmy
 * @Description: tag标签
 * @FileName:FlowLayout
 * @Package
 * @Date 2016/9/8 11:58
 */
public class FlowLayout extends ViewGroup {
    private float mVerticalSpacing; //每个item纵向间距
    private float mHorizontalSpacing; //每个item横向间距
    private ListAdapter adapter;//tag标签适配器

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHorizontalSpacing(float pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(float pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    /**
     * 声明一个适配器，用于数据更新后 更新页面绘制
     * @param adapter
     */
    public void setAdapter(@NonNull ListAdapter adapter) {
        if (adapter == null) throw new NullPointerException("FlowLayout.setAdapter不能传空参数");
        this.adapter = adapter;
        changeViews(); //这个是用来给Flowlayout加view的逻辑，先忽略
        adapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {
                changeViews();
            }

            @Override
            public void onInvalidated() {
                changeViews();
            }
        });
    }

    /**
     * view 配置器
     */
    private void changeViews() {
        final int count = adapter.getCount();  //先拿到要显示的view的数量
        if (count > 0) {
            //在即将显示的数量大于0的情况下 再lookup一下flowlayout当前子view的数量
            int childCount = getChildCount();
            if (childCount > count) {
                //如果说flowlayout当前的子view数量多过要显示的view数量，那就删除几个，让其数量保持一致
                removeViews(count, childCount - count);
            }
            //上面就是我所说的‘能复用就复用，不能复用再创建‘

            for (int i = 0; i < count; i++) {
                // 这个就比较简单了， 向adapter要view。
                //getChildAt(i) 就是adapter内getView中的第二个参数 convertView,
                //如果有就传过去 ，没有就是null,这时按正常来说，我们会重新创建一个view。
                final View view = adapter.getView(i, getChildAt(i), this);
                if (view.getLayoutParams() == null) {
                    //好了，这时呢得到了一个view,看看有没有 布局参数，
                    //没有就给一个，免得FlowLayout自动生成一个的话
                    //会match_parent,这样就不合我们的要求了，
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    //这些就是加加右和下margin，让view 不会粘一起，好看一点，
                    //可以做成一个自定义属性嘛，从xml里面读，这个就不讨论了，
//                    lp.setMargins(4, 4, 4, 4);
                    view.setLayoutParams(lp);

                }

                if (getChildAt(i) != view) {
                    //这里，为啥要判断下呢？
                    //这要说到前面的蹩脚的复用了，
                    //前面把getChildAt(i)传给了getView，如果FlowLayout本身就有view,
                    //那么在getView里面，就只是改变一下text,image等等的数据，这时
                    //getChildAt(i) 和adater返回的view肯定还是同一个view，所以不用重复加
                    //----
                    //但是如果不一样，那就是说，getChildAt(i)就是null , 跟本就没有，
                    addView(view);
                }
            }
        } else {
            removeAllViews();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //通过计算每一个子控件的高度，得到自己的高度
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > selfWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(selfWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        //根据子控件的宽高，计算子控件应该出现的位置。
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }
}