package tomcat.linewraplayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * wrap layout
 * @author tomcat
 *
 */
public class LineWrapLayout extends ViewGroup {

	private int line_height;
	private int hSpacing = 1;//子View之间的横向间隔
	private int vSpacing = 1;  //子View之间的纵向间隔

	public LineWrapLayout(Context context) {
		super(context);
	}

	public LineWrapLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.LineWrapLayout);
        //
		hSpacing = a.getDimensionPixelSize(R.styleable.LineWrapLayout_horizontal_spacing, 15);
		vSpacing = a.getDimensionPixelSize(R.styleable.LineWrapLayout_vertical_spacing, 15);
		a.recycle();
	}

	public LineWrapLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.LineWrapLayout, defStyle, 0);
		//得到横向间隔
		hSpacing = a.getDimensionPixelSize(R.styleable.LineWrapLayout_horizontal_spacing, 15);
		//得到纵向间隔
		vSpacing = a.getDimensionPixelSize(R.styleable.LineWrapLayout_vertical_spacing, 15);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if( MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED ){
			throw new RuntimeException("LineWrapLayout: child MeasureSpec mode undefined!");
		}

		//得到ViewGroup的初始宽高
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec)
				+ getPaddingBottom()+getPaddingTop();
		
		final int count = getChildCount();
		int line_height = 0;

		//获取第一个子View的起始点位置
		int xpos = getPaddingLeft();
		int ypos = getPaddingTop();

		//计算每一个子View的尺寸,并算出ViewGroup的高度
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				final LayoutParams lp = child.getLayoutParams();
				//算出子View宽的MeasureSpec值
				int wSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
				//算出子View高的MeasureSpec值
				int hSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
				//让子View记住自己宽高的MeasureSpec值,子View的
	            //onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	            //函数传入的就是这里算出来的这两个值
				child.measure(wSpec, hSpec);
				//设置完MeasureSpec值后调用View.getMeasuredWidth()函数算出View的宽度
				final int childw = child.getMeasuredWidth();
				//记录最大行高(子View的高度有可能不一样,行高取最大高度)
				line_height = Math.max(line_height, child.getMeasuredHeight() + vSpacing);
				
				if (xpos + childw > width) {
					//初始坐标的x偏移值+子View宽度>ViewGroup宽度 就换行
					xpos = getPaddingLeft();//坐标x偏移值归零
					ypos += line_height;    //坐标y偏移值再加上本行的行高也就是换行
				}
				//算出下一个子View的起始点x偏移值
				xpos += childw + hSpacing;
			}
		}
		this.line_height = line_height;

		if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			//对高度期望值没有限制
			height = ypos + line_height;

		} else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
			//达不到指定高度则缩小高度
			if (ypos + line_height < height) {
				height = ypos + line_height;
			}
		} else {
			height = ypos + line_height;
		}
		//设置ViewGroup宽高值
		setMeasuredDimension(width, height);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		if (p instanceof LayoutParams) {
			return true;
		}
		return false;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = getChildCount();
		final int width = r - l;
		int xpos = getPaddingLeft();
		int ypos = getPaddingTop();
        //设置每一个子View的位置,左上角xy坐标与右下角xy坐标确定View的位置
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				final int childw = child.getMeasuredWidth();
				final int childh = child.getMeasuredHeight();
				if (xpos + childw > width) {
					xpos = getPaddingLeft();
					ypos += line_height;
				}
				child.layout(xpos, ypos, xpos + childw, ypos + childh);
				xpos += childw + hSpacing;
				
			}
		}
	}
}