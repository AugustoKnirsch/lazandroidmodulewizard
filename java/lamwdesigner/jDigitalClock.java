package com.example.appdemo1;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.DigitalClock;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.Gravity;

/*Draft java code by "Lazarus Android Module Wizard" [5/9/2015]*/
/*https://github.com/jmpessoa/lazandroidmodulewizard*/
/*jVisualControl template*/

public class jDigitalClock extends DigitalClock /*TextClock*/ { //please, fix what GUI object will be extended!

	private long       pascalObj = 0;    // Pascal Object
	private Controls   controls  = null; // Control Class for events

	private Context context = null;
	private ViewGroup parent   = null;         // parent view
	private ViewGroup.MarginLayoutParams lparams = null;              // layout XYWH

	private OnClickListener onClickListener;   // click event

	private Boolean enabled  = true;           // click-touch enabled!

	private int lparamsAnchorRule[] = new int[30];
	private int countAnchorRule = 0;
	private int lparamsParentRule[] = new int[30];
	private int countParentRule = 0;

	//private int lparamH = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
	//private int lparamW = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
	private int lparamH = 100;
	private int lparamW = 100;
	private int marginLeft = 0;
	private int marginTop = 0;
	private int marginRight = 0;
	private int marginBottom = 0;
	private int lgravity = Gravity.TOP | Gravity.START;
	private float lweight = 0;

	private boolean mRemovedFromParent = false;

	float mTextSize = 0; //default
	int mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_SP; //default

	//GUIDELINE: please, preferentially, init all yours params names with "_", ex: int _flag, String _hello ...

	public jDigitalClock(Controls _ctrls, long _Self) { //Add more others news "_xxx"p arams if needed!
		super(_ctrls.activity);
		context   = _ctrls.activity;
		pascalObj = _Self;
		controls  = _ctrls;

		lparams = new ViewGroup.MarginLayoutParams(lparamW, lparamH);     // W,H
		lparams.setMargins(marginLeft,marginTop,marginRight,marginBottom); // L,T,R,B

		onClickListener = new OnClickListener(){
			/*.*/public void onClick(View view){  //please, do not remove /*.*/ mask for parse invisibility!
				if (enabled) {
					controls.pOnClick(pascalObj, Const.Click_Default); //JNI event onClick!
				}
			};
		};
		setOnClickListener(onClickListener);
	} //end constructor

	public void jFree() {
		if (parent != null) { parent.removeView(this); }
		//free local objects...
		lparams = null;
		setOnClickListener(null);
	}

	private static MarginLayoutParams newLayoutParams(ViewGroup _aparent, ViewGroup.MarginLayoutParams _baseparams) {
		if (_aparent instanceof FrameLayout) {
			return new FrameLayout.LayoutParams(_baseparams);
		} else if (_aparent instanceof RelativeLayout) {
			return new RelativeLayout.LayoutParams(_baseparams);
		} else if (_aparent instanceof LinearLayout) {
			return new LinearLayout.LayoutParams(_baseparams);
		} else if (_aparent == null) {
			throw new NullPointerException("Parent is null");
		} else {
			throw new IllegalArgumentException("Parent is neither FrameLayout or RelativeLayout or LinearLayout: "
					+ _aparent.getClass().getName());
		}
	}

	public void SetViewParent(ViewGroup _viewgroup) {
		if (parent != null) { parent.removeView(this); }
		parent = _viewgroup;

		parent.addView(this,newLayoutParams(parent,(ViewGroup.MarginLayoutParams)lparams));
		lparams = null;
		lparams = (ViewGroup.MarginLayoutParams)this.getLayoutParams();

		mRemovedFromParent = false;
	}

	public void RemoveFromViewParent() {
		if (!mRemovedFromParent) {
			this.setVisibility(android.view.View.INVISIBLE);
			if (parent != null)
				parent.removeView(this);
			mRemovedFromParent = true;
		}
	}

	public View GetView() {
		return this;
	}

	public void SetLParamWidth(int _w) {
		lparamW = _w;
	}

	public void SetLParamHeight(int _h) {
		lparamH = _h;
	}

	public void setLGravity(int _g) {
		lgravity = _g;
	}

	public void setLWeight(float _w) {
		lweight = _w;
	}

	public void SetLeftTopRightBottomWidthHeight(int _left, int _top, int _right, int _bottom, int _w, int _h) {
		marginLeft = _left;
		marginTop = _top;
		marginRight = _right;
		marginBottom = _bottom;
		lparamH = _h;
		lparamW = _w;
	}

	public void AddLParamsAnchorRule(int _rule) {
		lparamsAnchorRule[countAnchorRule] = _rule;
		countAnchorRule = countAnchorRule + 1;
	}

	public void AddLParamsParentRule(int _rule) {
		lparamsParentRule[countParentRule] = _rule;
		countParentRule = countParentRule + 1;
	}

	public void SetLayoutAll(int _idAnchor) {
		lparams.width  = lparamW;
		lparams.height = lparamH;
		lparams.setMargins(marginLeft,marginTop,marginRight,marginBottom);

		if (lparams instanceof RelativeLayout.LayoutParams) {
			if (_idAnchor > 0) {
				for (int i = 0; i < countAnchorRule; i++) {
					((RelativeLayout.LayoutParams)lparams).addRule(lparamsAnchorRule[i], _idAnchor);
				}
			}
			for (int j = 0; j < countParentRule; j++) {
				((RelativeLayout.LayoutParams)lparams).addRule(lparamsParentRule[j]);
			}
		}
		if (lparams instanceof FrameLayout.LayoutParams) {
			((FrameLayout.LayoutParams)lparams).gravity = lgravity;
		}
		if (lparams instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams)lparams).weight = lweight;
		}
		//
		this.setLayoutParams(lparams);
	}

	public void ClearLayoutAll() {
		if (lparams instanceof RelativeLayout.LayoutParams) {
			for (int i = 0; i < countAnchorRule; i++) {
				((RelativeLayout.LayoutParams)lparams).removeRule(lparamsAnchorRule[i]);
			}

			for (int j = 0; j < countParentRule; j++) {
				((RelativeLayout.LayoutParams) lparams).removeRule(lparamsParentRule[j]);
			}
		}
		countAnchorRule = 0;
		countParentRule = 0;
	}

	public void SetId(int _id) { //wrapper method pattern ...
		this.setId(_id);
	}

	//write others [public] methods code here......
	//GUIDELINE: please, preferentially, init all yours params names with "_", ex: int _flag, String _hello ...

	public void SetTextSize(float size) {
		mTextSize = size;
		CharSequence t = this.getText();
		this.setTextSize(mTextSizeTypedValue, mTextSize);
		this.setText(t);
	}

	//TTextSizeTypedValue =(tsDefault, tsPixels, tsDIP, tsInches, tsMillimeters, tsPoints, tsScaledPixel);
	public void SetFontSizeUnit(int _unit) {
		switch (_unit) {
			case 0: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_SP; break; //default
			case 1: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_PX; break; //default
			case 2: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_DIP; break; //default
			case 3: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_IN; break; //default
			case 4: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_MM; break; //default
			case 5: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_PT; break; //default
			case 6: mTextSizeTypedValue = TypedValue.COMPLEX_UNIT_SP; break; //default
		}
		String t = this.getText().toString();
		this.setTextSize(mTextSizeTypedValue, mTextSize);
		this.setText(t);
	}

} //end class

