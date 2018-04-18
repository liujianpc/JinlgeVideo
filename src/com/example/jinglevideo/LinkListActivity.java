package com.example.jinglevideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author XP-PC-XXX
 */
public class LinkListActivity extends BaseActivity {

    int[] flag = new int[5];
    String playLink = "http://jqaaa.com/jx.php?url=";
    private ViewPager viewPager = null;
    private MyPagerAdapter myPagerAdapter = null;
    private View[] views = new View[5];
    private static final int[] viewIds = {R.layout.view1, R.layout.view2, R.layout.view3,
            R.layout.view4, R.layout.view5};
    private List<View> viewList = null;
    private List<String> titleList = null;
    private static final String[] titleStrings = {"磁力", "爱奇艺", "优酷", "腾讯", "芒果", "乐视"};
    //private PagerTabStrip tab = null;
    TextView titleTextView, urlTextView;
    Button rightButton;
    String title, url;
    ProgressDialog progressDialog;
    // handler
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ListAdapter adapter = new SimpleAdapter(LinkListActivity.this,
                            (List<Map<String, String>>) msg.obj,
                            android.R.layout.simple_list_item_2, new String[]{
                            "title", "url"}, new int[]{
                            android.R.id.text1, android.R.id.text2});

                    ListView listView = null;
                    switch (msg.arg1) {
                        case 0:
                            if (((List<Map<String, String>>) msg.obj).size() > 0) {
                                flag[0] = 1;
                            }


                            listView = (ListView) findViewById(R.id.list_view_magnet);
                            listView.setAdapter(adapter);

                            listView.setOnItemLongClickListener(new OnItemLongClickListener() {

                                @SuppressWarnings("deprecation")
                                @Override
                                public boolean onItemLongClick(AdapterView<?> arg0,
                                                               View arg1, int arg2, long arg3) {
                                    // TODO Auto-generated method stub
                                    titleTextView = (TextView) arg1
                                            .findViewById(android.R.id.text1);
                                    urlTextView = (TextView) arg1
                                            .findViewById(android.R.id.text2);
                                    title = titleTextView.getText().toString();
                                    url = urlTextView.getText().toString();
                                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    cm.setText(url);
                                    Toast.makeText(LinkListActivity.this,
                                            "磁力链接已经复制到剪切板", Toast.LENGTH_LONG).show();
                                    return true;

                                }
                            });
                            setMagnetPageListener(listView);
                            break;
                        case R.id.list_view_iqiyi:
                            flag[1] = 1;
                            listView = (ListView) findViewById(R.id.list_view_iqiyi);
                            listView.setAdapter(adapter);

                            setListViewListener(listView);
                            break;
                        case R.id.list_view_youku:
                            flag[2] = 1;
                            listView = (ListView) findViewById(R.id.list_view_youku);
                            listView.setAdapter(adapter);

                            setListViewListener(listView);
                            break;
                        case R.id.list_view_tencent:
                            flag[3] = 1;
                            listView = (ListView) findViewById(R.id.list_view_tencent);
                            listView.setAdapter(adapter);

                            setListViewListener(listView);
                            break;
                        case R.id.list_view_mangguo:
                            flag[4] = 1;
                            listView = (ListView) findViewById(R.id.list_view_mangguo);
                            listView.setAdapter(adapter);

                            setListViewListener(listView);
                            break;
                        case R.id.list_view_leshi:
                            flag[4] = 1;
                            listView = (ListView) findViewById(R.id.list_view_leshi);
                            listView.setAdapter(adapter);

                            setListViewListener(listView);
                            break;

                        default:
                            break;
                    }

                    break;

                case 2:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(
                            LinkListActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("网络错误！重新设置网络？");
                    dialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(
                                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivity(intent);

                                }
                            });
                    dialog.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    arg0.dismiss();

                                }
                            });
                    dialog.show();

                    break;

                default:
                    break;
            }

        }

        ;
    };

    /**
     * 磁力頁面的監聽器設置
     *
     * @param listView
     */
    private void setMagnetPageListener(ListView listView) {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                titleTextView = (TextView) arg1
                        .findViewById(android.R.id.text1);
                urlTextView = (TextView) arg1
                        .findViewById(android.R.id.text2);
                title = titleTextView.getText().toString();
                url = urlTextView.getText().toString();
                final String api = "http://api.kltvv.top/api/ap.php?u=";
                AlertDialog.Builder builder = new AlertDialog.Builder(LinkListActivity.this);
                builder.setCancelable(true);
                builder.setMessage("去浏览器打开，还是百度云离线下载？");
                builder.setTitle("选择打开方式");
                builder.setNegativeButton("百度云", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PackageManager packageManager = getPackageManager();
                        Intent intent = packageManager.getLaunchIntentForPackage("com.baidu.netdisk");
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(LinkListActivity.this, "请安装百度云", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setPositiveButton("浏览器", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(LinkListActivity.this, "前往浏览器打开视频",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse(api + url));
                        startActivity(intent);

                    }
                });
                builder.create();
                builder.show();

            }
        });
    }

    /**
     * 设置listView的监听器
     *
     * @param listView
     */

    private void setListViewListener(ListView listView) {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                titleTextView = (TextView) arg1
                        .findViewById(android.R.id.text1);
                urlTextView = (TextView) arg1
                        .findViewById(android.R.id.text2);
                title = titleTextView.getText().toString();
                url = urlTextView.getText().toString();
                String api = playLink + url;
                Toast.makeText(LinkListActivity.this, "前往浏览器打开视频",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(api));
                startActivity(intent);

            }
        });
    }

    /**
     * 使用videoPlayer发开
     *
     * @param listView
     */
    private void gotoVideoPlayer(ListView listView) {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                titleTextView = (TextView) arg1
                        .findViewById(android.R.id.text1);
                urlTextView = (TextView) arg1
                        .findViewById(android.R.id.text2);
                title = titleTextView.getText().toString();
                url = urlTextView.getText().toString();
                String api = playLink + url;
                Toast.makeText(LinkListActivity.this, "前往videoPlayer打开视频",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LinkListActivity.this, PlayActivity.class);
                intent.putExtra("url", api);
                intent.putExtra("title", title);
                startActivity(intent);

            }
        });
    }

    private com.viewpagerindicator.TabPageIndicator indicator;
    //  private PagerTabStrip idtab;
    private ViewPager idviewpager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.link_list_activity);
        this.idviewpager = (ViewPager) findViewById(R.id.id_viewpager);
        //  this.idtab = (PagerTabStrip) findViewById(R.id.id_tab);
        this.indicator = (TabPageIndicator) findViewById(R.id.indicator);
        rightButton = (Button) findViewById(R.id.title_right);

        Intent intent = getIntent();
        final String filmName = intent.getStringExtra("fileName").trim();
        rightButton.setText("    ");
        rightButton.setClickable(false);

        viewList = new ArrayList<>();
        for (int i = 0; i < viewIds.length; i++) {
            views[i] = View.inflate(LinkListActivity.this, viewIds[i], null);
            viewList.add(views[i]);
        }
        titleList = new ArrayList<>();
        for (int i = 0; i < titleStrings.length; i++) {
            titleList.add(titleStrings[i]);
        }
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
       /* tab = (PagerTabStrip) findViewById(R.id.id_tab);
        tab.setDrawFullUnderline(false);
        tab.setBackgroundColor(Color.parseColor("#C7EA2D"));
        tab.setTextSpacing(10);
        tab.setTabIndicatorColor(Color.parseColor("#FFFF00"));*/
        indicator.setBackgroundColor(Color.parseColor("#C7EA2D"));

        // viewList
        myPagerAdapter = new MyPagerAdapter(viewList, titleList);
        viewPager.setAdapter(myPagerAdapter);
        indicator.setViewPager(viewPager);

        progressDialog = new ProgressDialog(LinkListActivity.this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在加载网络资源...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        MyThread myThread = new MyThread(filmName, handler, progressDialog, 0);
        Thread netThread = new Thread(myThread);
        netThread.start();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int id) {
                // TODO Auto-generated method stub

                if (flag[id] != 1) {

                    MyThread myThread;
                    Thread netThread;
                    progressDialog.setTitle("提示");
                    progressDialog.setMessage("正在加载网络资源...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    switch (id) {
                        case 0:
                            rightButton.setText("    ");
                            rightButton.setClickable(false);

                            break;
                        case 1:
                            rightButton.setText("换源");
                            rightButton.setOnClickListener(new Listener());
                            myThread = new MyThread(filmName, handler,
                                    progressDialog, R.id.list_view_iqiyi);
                            netThread = new Thread(myThread);
                            netThread.start();
                            break;
                        case 2:
                            rightButton.setText("换源");
                            rightButton.setOnClickListener(new Listener());
                            myThread = new MyThread(filmName, handler,
                                    progressDialog, R.id.list_view_youku);
                            netThread = new Thread(myThread);
                            netThread.start();
                            break;
                        case 3:
                            rightButton.setText("换源");
                            rightButton.setOnClickListener(new Listener());
                            myThread = new MyThread(filmName, handler,
                                    progressDialog, R.id.list_view_tencent);
                            netThread = new Thread(myThread);
                            netThread.start();
                            break;
                        case 4:
                            rightButton.setText("换源");
                            rightButton.setOnClickListener(new Listener());
                            myThread = new MyThread(filmName, handler,
                                    progressDialog, R.id.list_view_mangguo);
                            netThread = new Thread(myThread);
                            netThread.start();
                            break;
                        case 5:
                            rightButton.setText("换源");
                            rightButton.setOnClickListener(new Listener());
                            myThread = new MyThread(filmName, handler,
                                    progressDialog, R.id.list_view_leshi);
                            netThread = new Thread(myThread);
                            netThread.start();
                            break;

                        default:
                            break;
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 1:
                if (data != null) {
                    int radioId = data.getIntExtra("resource", requestCode);

                    SharedPreferences.Editor editor = this.getSharedPreferences("playlink", MODE_PRIVATE).edit();
                    editor.putInt("playlink", radioId);
                    editor.apply();

                    switch (radioId) {
                        case R.id.radio1:
                            playLink = ""https://www.jqaaa.com/jq3/?url=http://www.iqiyi.com/v_19rrifh28s.html?vfm=2008_aldbd;//"http://jqaaa.com/jx.php?url=";
                            break;
                        case R.id.radio2:
                            playLink = "http://y.mt2t.com/lines?url=";
                            break;
                        case R.id.radio3:
                            playLink = "http://api.baiyug.cn/vip/index.php?url=";
                            break;
                        case R.id.radio4:
                            playLink = "http://api.xfsub.com/index.php?url=";
                            break;
                        case R.id.radio5:
                            playLink = "https://api.flvsp.com/?url=";
                            break;
                        case R.id.radio6:
                            playLink = "http://www.tutule4d.com/djx/index.php?url=";
                            break;

                        case R.id.radio7:
                            playLink = "http://www.wmxz.wang/video.php?url=";
                            break;
                        default:
                            break;

                    }
                    break;
                }


            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.link_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about_menu) {
            Intent intent = new Intent(LinkListActivity.this,
                    AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.exit_menu) {
            ActivityCollector.finishAllActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    class Listener implements OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LinkListActivity.this,
                    OptionActivity.class);
            startActivityForResult(intent, 1);
        }
    }

}
