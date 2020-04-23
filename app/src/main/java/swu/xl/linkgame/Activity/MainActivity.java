package swu.xl.linkgame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import swu.xl.linkgame.Constant.Constant;
import swu.xl.linkgame.Model.XLLevel;
import swu.xl.linkgame.Model.XLProp;
import swu.xl.linkgame.Model.XLUser;
import swu.xl.linkgame.R;
import swu.xl.linkgame.Util.PxUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //简单模式
    Button mode_easy;
    //普通模式
    Button mode_normal;
    //困难模式
    Button mode_hard;

    //设置按钮
    Button btn_setting;
    //帮助按钮
    Button btn_help;
    //商店按钮
    Button btn_store;

    //根布局
    RelativeLayout root_main;

    //帮助布局
    LinearLayout inflate_help;

    //是否加载帮助布局完成标志
    boolean flag_help = false;

    @Xml(layouts = "activity_main")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        X2C.setContentView(this, R.layout.activity_main);

        //沉浸式状态栏
        ImmersionBar.with(this).init();

        //数据库 LitePal
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();

        //向数据库装入数据
        initSQLite3();

        //初始化数据
        initView();

        //设置模式按钮的drawableLeft
        setDrawableLeft(mode_easy,R.drawable.main_mode_easy);
        setDrawableLeft(mode_normal,R.drawable.main_mode_normal);
        setDrawableLeft(mode_hard,R.drawable.main_mode_hard);

        //加载布局
        initInflate();
    }

    /**
     * 初始化数据库
     */
    private void initSQLite3() {
        //查找当前数据库的内容
        List<XLUser> users = LitePal.findAll(XLUser.class);
        List<XLLevel> levels = LitePal.findAll(XLLevel.class);
        List<XLProp> props = LitePal.findAll(XLProp.class);

        //如果用户数据为空，装入数据
        if (users.size() == 0){
            XLUser user = new XLUser();
            user.setU_money(1000);
            user.setU_background(0);
            user.save();
        }

        //如果关卡数据为空，装入数据
        if (levels.size() == 0){
            //简单模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('1');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }

            //普通模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('2');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }

            //困难模式
            for(int i = 1; i <= 40; i++){
                XLLevel level = new XLLevel();
                //设置关卡号
                level.setL_id(i);
                //设置关卡模式
                level.setL_mode('3');
                //设置关卡的闯关状态
                if (i == 1){
                    level.setL_new('4');
                }else {
                    level.setL_new('0');
                }
                //设置关卡的闯关时间
                level.setL_time(0);

                //插入
                level.save();
            }
        }

        //如果道具数据为空，装入数据
        if (props.size() == 0){
            //1.装入拳头道具
            XLProp prop_fight = new XLProp();
            prop_fight.setP_kind('1');
            prop_fight.setP_number(9);
            prop_fight.setP_price(10);
            prop_fight.save();

            //2.装入炸弹道具
            XLProp prop_bomb = new XLProp();
            prop_bomb.setP_kind('2');
            prop_bomb.setP_number(9);
            prop_bomb.setP_price(10);
            prop_bomb.save();

            //3.装入刷新道具
            XLProp prop_refresh = new XLProp();
            prop_refresh.setP_kind('3');
            prop_refresh.setP_number(9);
            prop_refresh.setP_price(10);
            prop_refresh.save();
        }
    }

    /**
     * 数据的初始化
     */
    private void initView() {
        mode_easy = findViewById(R.id.main_mode_easy);
        mode_easy.setOnClickListener(this);
        mode_normal = findViewById(R.id.main_mode_normal);
        mode_normal.setOnClickListener(this);
        mode_hard = findViewById(R.id.main_mode_hard);
        mode_hard.setOnClickListener(this);
        btn_setting = findViewById(R.id.main_setting);
        btn_setting.setOnClickListener(this);
        btn_help = findViewById(R.id.main_help);
        btn_help.setOnClickListener(this);
        btn_store = findViewById(R.id.main_store);
        btn_store.setOnClickListener(this);
        root_main = findViewById(R.id.root_main);
    }

    /**
     * 用给定资源设置指定按钮的drawableLeft
     */
    private void setDrawableLeft(Button btn, int main_mode_resource) {
        //获取指定的drawable
        Drawable drawable = getResources().getDrawable(main_mode_resource);
        //设置其drawable的左上右下
        drawable.setBounds(PxUtil.dpToPx(20,this),PxUtil.dpToPx(2,this), PxUtil.dpToPx(60,this),PxUtil.dpToPx(42,this));
        //设置放在控件的左上右下
        btn.setCompoundDrawables(drawable,null,null,null);
    }

    /**
     * 加载布局
     */
    @Xml(layouts = "help_view")
    private void initInflate() {
        //加载帮助布局
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载布局
                inflate_help = (LinearLayout) X2C.inflate(MainActivity.this, R.layout.help_view, null);

                //改变标志
                flag_help = true;
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_mode_easy:
                Log.d(Constant.TAG,"简单模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels1 = LitePal.where("l_mode == ?", "1").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels1.size()+"");

                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels1) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                //跳转界面
                Intent intent_easy = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_easy = new Bundle();
                //加入关卡模式数据
                bundle_easy.putString("mode","简单");
                //加入关卡数据
                bundle_easy.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels1);
                intent_easy.putExtras(bundle_easy);
                //跳转
                startActivity(intent_easy);

                break;
            case R.id.main_mode_normal:
                Log.d(Constant.TAG,"普通模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels2 = LitePal.where("l_mode == ?", "2").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels2.size()+"");

                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels2) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                //跳转界面
                Intent intent_normal = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_normal = new Bundle();
                //加入关卡模式数据
                bundle_normal.putString("mode","简单");
                //加入关卡数据
                bundle_normal.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels2);
                intent_normal.putExtras(bundle_normal);
                //跳转
                startActivity(intent_normal);

                break;
            case R.id.main_mode_hard:
                Log.d(Constant.TAG,"困难模式按钮");

                //查询简单模式的数据
                List<XLLevel> XLLevels3 = LitePal.where("l_mode == ?", "3").find(XLLevel.class);
                Log.d(Constant.TAG,XLLevels3.size()+"");
                
                //依次查询每一个内容
                for (XLLevel xlLevel : XLLevels3) {
                    Log.d(Constant.TAG,xlLevel.toString());
                }

                //跳转界面
                Intent intent_hard = new Intent(this, LevelActivity.class);
                //加入数据
                Bundle bundle_hard = new Bundle();
                //加入关卡模式数据
                bundle_hard.putString("mode","简单");
                //加入关卡数据
                bundle_hard.putParcelableArrayList("levels", (ArrayList<? extends Parcelable>) XLLevels3);
                intent_hard.putExtras(bundle_hard);
                //跳转
                startActivity(intent_hard);

                break;
            case R.id.main_setting:
                Log.d(Constant.TAG,"设置按钮");
                break;
            case R.id.main_help:
                Log.d(Constant.TAG,"帮助按钮");

                if (flag_help){
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //layoutParams.setMargins(0, ScreenUtil.getScreenHeight(getApplicationContext()),0,0);
                    root_main.addView(inflate_help,layoutParams);

                    inflate_help.findViewById(R.id.main_know).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root_main.removeViewInLayout(inflate_help);
                        }
                    });
                }

                break;
            case R.id.main_store:
                Log.d(Constant.TAG,"商店按钮");
                break;
        }
    }
}
