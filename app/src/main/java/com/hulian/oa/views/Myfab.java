package com.hulian.oa.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hulian.oa.R;

import java.util.ArrayList;
import java.util.List;

public class Myfab extends View {

    private boolean isShow=false;
    private int rect=0;
    private List<Integer> icon=new ArrayList();
    private MenuListener menuListener;

    private int color;
    private int realheight=0;

    public void setColor(int color) {
        this.color = color;
    }

    public Myfab(Context context) {
        super(context);
    }

    public Myfab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isClickable()) {
            setClickable(true);
        }
        color= ContextCompat.getColor(context, R.color.colorPrimary);
    }

    public void collapse()
    {
        rect=0;
        invalidate();//不加动画直接缩回去
    }
    public void setIcon(List<Integer> list)
    {
        this.icon=list;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result=false;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                result=TouchMethod((int)event.getX(), (int)event.getY(),false);
                break;
            }
            case MotionEvent.ACTION_DOWN:
            {
                result=TouchMethod((int)event.getX(), (int)event.getY(),true);//
                break;
            }
        }
        if(result)
            return true; //已消费事件
        else
            return false;//未消费事件
    }

    private boolean TouchMethod(int x,int y ,boolean isDown)
    {
        if(y>getMeasuredHeight()-getMeasuredWidth()&&y<getMeasuredHeight()) //如果点在底部按钮上
        {
            if(!isDown)
                startAnimation();
            return true;
        }
        else if(y>0&&y<getMeasuredHeight()-getMeasuredWidth()&&isShow)//如果点在选项上，并且按钮在展开状态
        {
            if(!isDown) {
                for (int i = icon.size(); i > 0; i--) //计算并判断点在了哪个位置（view的宽度为Width，高度为icon.size*Width,相当于每个图标所占的区域都是正方形）
                {
                    if (y > (i - 1) * getMeasuredWidth() && y < i * getMeasuredWidth()) {
                        if (menuListener != null)
                            menuListener.click(icon.size() - i + 1);//调用接口
                    }
                }
            }
            return true;
        }
        else
            return false;//按钮未展开
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        int px=getMeasuredWidth()/2;

        Paint mPaint=new Paint();
        mPaint.setColor(color);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);//设置画笔宽度为10px
        mPaint.setAntiAlias(true);

        Path path=new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.translate(px, getMeasuredHeight()-px);//移动坐标中心

        canvas.drawArc(-px,-px,px,px,0,180,true,mPaint);//画出底部的半圆
        canvas.drawArc(-px, -px - rect, px, -rect + px, 180, 180, true, mPaint); //画出上部分的半圆
        canvas.drawRect(-px, -rect, px, 0, mPaint);//画出两个半圆中间的矩形

        Bitmap bitmap= BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_add);
        canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,mPaint); //获取并绘制按钮没有展开时的图标

        for(int i=0;i<icon.size();i++)
        {
            if(rect>=2*px*(i+1))//2*px=getMeasuredWidth(),i+1=当前的图标个数(-y)，如果上升高度足够显示下一个图标，就绘制
            {
                Bitmap bitmap1= BitmapFactory.decodeResource(getContext().getResources(),icon.get(i).intValue());
                canvas.drawBitmap(bitmap1,-bitmap.getWidth()/2,-bitmap.getHeight()/2-(i+1)*2*px,mPaint);//在相应位置绘制图标
            }
        }

        if(rect==realheight) {//完全展开
            isShow=true;
        }
        else if(rect==0) {    //完全闭合
            isShow=false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;

        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);

        width=width>height?height:width;//取较小的
        height=height>width?width:height;

        height+=icon.size()*width;     //根据加入图标个数累加view高度

        realheight=height-width;//加入图标的总高度

        setMeasuredDimension(width,height);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    private class ami extends Animation
    {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            rect=(int)(interpolatedTime*(realheight));
            invalidate();
        }

    }
    private class ami2 extends Animation
    {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            rect=(int)((1-interpolatedTime)*(realheight));
            invalidate();
        }

    }
    public void startAnimation() {
        if(!isShow) {
            ami move = new ami();
            move.setDuration(300);
            move.setInterpolator(new AccelerateDecelerateInterpolator());
            startAnimation(move);
        }
        else
        {
            ami2 move = new ami2();
            move.setDuration(300);
            move.setInterpolator(new AccelerateDecelerateInterpolator());
            startAnimation(move);
        }
    }
    public interface MenuListener//需实现此接口以便接受点击事件
    {
        void click(int i);
    }
}
