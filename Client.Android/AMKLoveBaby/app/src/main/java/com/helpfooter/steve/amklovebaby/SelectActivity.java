package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.CustomControlView.MyTextView;
import com.helpfooter.steve.amklovebaby.CustomObject.VerifyCodeButtonDisable;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.CommentObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.ISelectObj;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;


public abstract class SelectActivity extends Activity implements View.OnClickListener {

    LinearLayout sv;
    protected ArrayList<ISelectObj> lst=new ArrayList<>();
    ISelectObj currentSelect=null;

    boolean haveload=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if(haveload==false){
            InitData();
            InitUI();
            haveload=true;
        }
    }

    abstract public void InitData();

    protected void InitUI() {
        ((TextView)findViewById(R.id.btnOK)).setOnClickListener(this);
        ((TextView)findViewById(R.id.btnCancel)).setOnClickListener(this);
        sv=(LinearLayout)findViewById(R.id.svBody);

        if(lst!=null&&lst.size()>0){
            DiaplayList();
        }
    }

    private void DiaplayList() {
        for(ISelectObj obj:lst) {

            PercentLinearLayout layout=new PercentLinearLayout(this);
            PercentLinearLayout.LayoutParams layoutparam=ToolsUtil.getLayoutParamHeightWrap();
            layout.setLayoutParams(layoutparam);

            if(obj.ShowLogo()) {
                ImageView img = new ImageView(this);
                PercentLinearLayout.LayoutParams imglayoutparam = ToolsUtil.getLayoutParamHeightWrap();
                imglayoutparam.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f, true);
                imglayoutparam.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f, true);
                imglayoutparam.mPercentLayoutInfo.topMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f, true);
                img.setAdjustViewBounds(true);
                img.setLayoutParams(imglayoutparam);
                obj.LoadImage(img);

                layout.addView(img);
            }

            TextView txtName=new MyTextView(this);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
            param.setMargins(20, 15, 15, 15);
            txtName.setTextSize(15);
            txtName.setLayoutParams(param);
            txtName.setText(obj.DisplayName());

            layout.setTag(obj);
            layout.setOnClickListener(this);

            layout.addView(txtName);
            sv.addView(layout);

            LinearLayout line=ToolsUtil.GenPLine(this);
            sv.addView(line);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnOK:
                if(currentSelect==null){
                    Toast.makeText(this,"请选择一个选项",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("return", currentSelect.SelectedValue());
                intent.putExtra("returnName", currentSelect.DisplayName());
                setResult(RESULT_OK, intent);
                this.finish();
                return;
            case R.id.btnCancel:
                this.finish();
                return;
            default:

                int count=sv.getChildCount();
                for(int i=0;i<count;i++){
                    View selectView=sv.getChildAt(i);
                    if(selectView.getTag()!=null){
                        if(selectView!=v){
                            selectView.setBackgroundColor(0);
                        }else {
                            selectView.setBackgroundColor(Color.parseColor("#81CAE6"));
                        }
                    }
                }
                currentSelect=(ISelectObj)v.getTag();
        }
    }
    protected android.os.Handler DiaplayListHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
           DiaplayList();
        }
    };
}
