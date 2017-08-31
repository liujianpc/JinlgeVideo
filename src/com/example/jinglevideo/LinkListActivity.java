package com.example.jinglevideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinkListActivity extends BaseActivity {

	int[] flag = new int[5];
	String playLink = "https://api.47ks.com/webcloud/?v=";
	// viewPager�ı���
	private ViewPager viewPager = null;
	private MyPagerAdapter myPagerAdapter = null;
	private View[] views = new View[5];
	private int[] viewIds = { R.layout.view1, R.layout.view2, R.layout.view3,
			R.layout.view4, R.layout.view5 };
	private List<View> viewList = null;
	private List<String> titleList = null;
	private String[] titleStrings = { "磁力链接", "爱奇艺", "优酷", "乐视", "芒果" };
	private PagerTabStrip tab = null;
	// ������activity�ı���
	TextView titleTextView, urlTextView;
	Button rightButton;
	String title, url;
	ProgressDialog progressDialog;

	// handler����
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				// ΪlistView����������
				ListAdapter adapter = new SimpleAdapter(LinkListActivity.this,
						(List<Map<String, String>>) msg.obj,
						android.R.layout.simple_list_item_2, new String[] {
								"title", "url" }, new int[] {
								android.R.id.text1, android.R.id.text2 });

				ListView listView = null;
				switch (msg.arg1) {
				case 0:
					if (((List<Map<String, String>>) msg.obj).size() > 0) {
						flag[0] = 1;
					}

					/*
					 * rightButton.setText("    ");
					 * rightButton.setClickable(false);
					 */
					listView = (ListView) findViewById(R.id.list_view_magnet);
					listView.setAdapter(adapter);

					// ����˫���¼�
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
					// ���õ����¼�
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
									if (intent != null){
                                       // intent.putExtra("TAB_INDEX_KEY",4);
										startActivity(intent);
									}else {
										Toast.makeText(LinkListActivity.this,"请安装百度云",Toast.LENGTH_SHORT).show();
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
					break;
				case R.id.list_view_iqiyi:
					flag[1] = 1;
					listView = (ListView) findViewById(R.id.list_view_iqiyi);
					listView.setAdapter(adapter);

					// ���õ����¼�
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
					break;
				case R.id.list_view_youku:
					flag[2] = 1;
					listView = (ListView) findViewById(R.id.list_view_youku);
					listView.setAdapter(adapter);

					// ���õ����¼�
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
					break;
				case R.id.list_view_leshi:
					flag[3] = 1;
					listView = (ListView) findViewById(R.id.list_view_leshi);
					listView.setAdapter(adapter);

					// ���õ����¼�
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
					break;
				case R.id.list_view_mangguo:
					flag[4] = 1;
					listView = (ListView) findViewById(R.id.list_view_mangguo);
					listView.setAdapter(adapter);

					// ���õ����¼�
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
					break;

				default:
					break;
				}

				break;

			// �������Ĵ���
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

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.link_list_activity);
		rightButton = (Button) findViewById(R.id.title_right);

		Intent intent = getIntent();
		final String filmName = intent.getStringExtra("fileName").trim();
		rightButton.setText("    ");
		rightButton.setClickable(false);

		// viewPager������
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
		tab = (PagerTabStrip) findViewById(R.id.id_tab);
		tab.setDrawFullUnderline(false);
		tab.setBackgroundColor(Color.parseColor("#C7EA2D"));
		tab.setTextSpacing(10);
		tab.setTabIndicatorColor(Color.parseColor("#FFFF00"));

		// ΪviewList����������
		myPagerAdapter = new MyPagerAdapter(viewList, titleList);
		viewPager.setAdapter(myPagerAdapter);

		progressDialog = new ProgressDialog(LinkListActivity.this);
		progressDialog.setTitle("提示");
		progressDialog.setMessage("正在加载网络资源...");
		progressDialog.setCancelable(false);
		progressDialog.show();

		// ������������߳�
		MyThread myThread = new MyThread(filmName, handler, progressDialog, 0);
		Thread netThread = new Thread(myThread);
		netThread.start();

		// ����tab�л�
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
						Toast.makeText(LinkListActivity.this, "hhhhhhhhh",
								Toast.LENGTH_LONG).show();
						break;
					case 1:
						rightButton.setText("换源");
						rightButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										LinkListActivity.this,
										OptionActivity.class);
								startActivityForResult(intent, 1);
							}
						});
						myThread = new MyThread(filmName, handler,
								progressDialog, R.id.list_view_iqiyi);
						netThread = new Thread(myThread);
						netThread.start();
						break;
					case 2:
						rightButton.setText("换源");
						rightButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										LinkListActivity.this,
										OptionActivity.class);
								startActivityForResult(intent, 1);
							}
						});
						myThread = new MyThread(filmName, handler,
								progressDialog, R.id.list_view_youku);
						netThread = new Thread(myThread);
						netThread.start();
						break;
					case 3:
						rightButton.setText("换源");
						rightButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										LinkListActivity.this,
										OptionActivity.class);
								startActivityForResult(intent, 1);
							}
						});
						myThread = new MyThread(filmName, handler,
								progressDialog, R.id.list_view_leshi);
						netThread = new Thread(myThread);
						netThread.start();
						break;
					case 4:
						rightButton.setText("换源");
						rightButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										LinkListActivity.this,
										OptionActivity.class);
								startActivityForResult(intent, 1);
							}
						});
						myThread = new MyThread(filmName, handler,
								progressDialog, R.id.list_view_mangguo);
						netThread = new Thread(myThread);
						netThread.start();
						break;

					default:
						break;
					}

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("OnPageScroll", "onPageScrolled------>arg0��" + arg0
						+ "\nonPageScrolled------>arg1:" + arg1
						+ "\nonPageScrolled------>arg2:" + arg2);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					Log.d("OnPageScrollState",
							"onPageScrollStateChanged------>0");
				} else if (arg0 == 1) {
					Log.d("OnPageScrollState",
							"onPageScrollStateChanged------>1");
				} else if (arg0 == 2) {
					Log.d("OnPageScrollState",
							"onPageScrollStateChanged------>2");
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
		    if (data != null){
                int radioId = data.getIntExtra("resource", requestCode);
                switch (radioId) {
                    case R.id.radio1:
                        playLink = "https://api.47ks.com/webcloud/?v=";
                        break;
                    case R.id.radio2:
                        playLink = "http://aikan-tv.com/?url=";
                        break;
                    case R.id.radio3:
                        playLink = "https://jxapi.nepian.com/ckparse/?url=";
                        break;
                    case R.id.radio4:
                        playLink = "http://www.ou522.cn/t2/1.php?url=";
                        break;
                    case R.id.radio5:
                        playLink = "http://www.wmxz.wang/video.php?url=";
                        break;
                    case R.id.radio6:
                        playLink = "http://www.yydy8.com/Common/?url=";
                        break;

                    default:
                        playLink = "https://api.47ks.com/webcloud/?v=";
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

}
