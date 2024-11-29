package edu.demo.gymsys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import edu.demo.gymsys.R;

public class SlideLayout extends FrameLayout {

    private View contentView; // 主内容视图
    private View menuView; // 菜单视图
    private int viewHeight; // 视图的高度
    private int contentWidth; // 主内容视图的宽度
    private int menuWidth; // 菜单视图的宽度

    // 滑动器，用于平滑滚动
    private Scroller scroller;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化滑动器
        scroller = new Scroller(context);
    }

    /**
     * 布局文件加载完成时被调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 查找子视图
        contentView = findViewById(R.id.content);
        menuView = findViewById(R.id.delete);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取视图的高度
        viewHeight = getMeasuredHeight();

        // 获取子视图的宽度
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 布局菜单视图，将其放置在主内容视图的右侧
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    private float startX; // 记录触摸起始点X坐标
    private float startY; // 记录触摸起始点Y坐标

    private float downX; // 记录按下点的X坐标
    private float downY; // 记录按下点的Y坐标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下点的坐标
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取当前触摸点的坐标
                float endX = event.getX();
                float endY = event.getY();

                // 计算偏移量
                float distanceX = endX - startX;

                // 计算将要滚动到的位置
                int toScrollX = (int) (getScrollX() - distanceX);
                // 屏蔽非法值
                if (toScrollX < 0) {
                    toScrollX = 0;
                }
                if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                // 滚动到计算后的位置
                scrollTo(toScrollX, getScrollY());

                // 更新起始点
                startX = event.getX();

                // 计算水平和垂直方向的移动距离
                float dx = Math.abs(event.getX() - downX);
                float dy = Math.abs(event.getY() - downY);
                if (dx > dy && dx > 6) {
                    // 事件反拦截，使父视图的事件传递到自身
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                // 根据当前滚动位置决定是打开还是关闭菜单
                if (getScrollX() > menuWidth / 2) {
                    openMenu(); // 打开菜单
                } else {
                    closeMenu(); // 关闭菜单
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下点的坐标
                downX = startX = event.getX();
                downY = startY = event.getY();
                if (onStateChangeListener != null) {
                    onStateChangeListener.onMove(this); // 通知监听器移动事件
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算水平和垂直方向的移动距离
                float dx = Math.abs(event.getX() - downX);
                float dy = Math.abs(event.getY() - downY);
                if (dx > dy && dx > 6) {
                    // 拦截事件
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        int dx = menuWidth - getScrollX(); // 计算需要滚动的距离
        scroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY());
        invalidate(); // 重绘视图，触发computeScroll()
        if (onStateChangeListener != null) {
            onStateChangeListener.onOpen(this); // 通知监听器菜单打开事件
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        int dx = -getScrollX(); // 计算需要滚动的距离
        scroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY());
        invalidate(); // 重绘视图，触发computeScroll()
        if (onStateChangeListener != null) {
            onStateChangeListener.onClose(this); // 通知监听器菜单关闭事件
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            // 滚动到当前滑动器的位置
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate(); // 重绘视图
        }
    }

    /**
     * 状态改变监听器接口
     */
    public interface OnStateChangeListener {
        void onOpen(SlideLayout slideLayout); // 当菜单打开时调用
        void onMove(SlideLayout slideLayout); // 当视图移动时调用
        void onClose(SlideLayout slideLayout); // 当菜单关闭时调用
    }

    private OnStateChangeListener onStateChangeListener;

    /**
     * 设置状态改变监听器
     * @param onStateChangeListener 状态改变监听器
     */
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
