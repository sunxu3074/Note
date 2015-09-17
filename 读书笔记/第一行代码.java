

	第一行代码读书笔记



	1、 为什么实用Log而不实用System.out

		日志不分级别
		日志打印不可控制
		不能加过滤器

	2、 android:scheme
		android:host
		android:port
		android:path
		android:mimeType

	3、 activity通过back键回上上层Avtivity时候
		通过重写onBackPressed();

		@override
		public void onBackPressed(){

			Intent intent = new Intent();
			intnet.putExtra("data_return","Hi, this is SecondActivity`s data");
			setResult(Activity.RESULT_OK,intent);
			finish();

		}


	4、 onSaveInstanceState();
		// 当启动B Activity时候，A Activity由于系统内存不足被强行关闭，这时候点击Back键可返回A Activity .
		// 但是，A Activity不会执行 onRestart() 方法，会重新执行 onCreate()方法，
		// 尴尬的是 前面的临时数据不会被加载，这是可以再onSaveInstanceState()方法中保存数据
		// 在onCreate()方法中取出数据即可。

	5、 singleInstance

	6、 知道当前是哪一个活动

		public class BaseActivity extends Activity{

			@override
			protected void onCreate(Bundle savedInstanceState){
				super.onCreate(savedInstanceState);
				Log.d("BaseAcvtivity",getClass().getSimpleName());
			}

		}

	7、 随时随地退出程序

		新建一个ActivityCollector

		

		public class ActivityCollector{

			public static list<Activity> activities = new ArrayList<Activity>();

			public static void addActivity(Activity activity){
				activities.add(activity);
			}

			public static void removeActivity(Activity activity){
				activities.remove(activity);
			}

			public static void finishAll(){
				for (Activity activity:activities) {
					if(!activity.isFinishing()){
						activity.finish();
					}
				}
			}

		}



		public class BaseAcvtivity extends Activity{

			@override
			protected void onCreate(Bundle savedInstanceState){
				super.onCreate(savedInstanceState);
				Log.d("BaseAcvtivity",getClass().getSimpleName());
				ActivityCollector.addActivity(this);
			}

			@override
			protected void onDestroy(){
				super.onDestroy();
				ActivityCollector.removeActivity(this);
			}

		}


	8、 各类Adapter 
		ArrayAdapter etc.

	9、 单位和尺寸

		sp dpi px pt dip  
 
        得到当前屏幕的分辨率

        float xdpi = getResource().getDisplayMetrics().xdpi;


    10、 Nine-Patch

    	draw9patch.bat



    11、fragment 和 activity之间的通信
    	Fragment的生命周期


    12、 动态注册监听网络变化

    	 注册广播

    	 IntentFilter intentFilter = new IntentFilter();
    	 intentFilter.addAction("android.net.com.CONNECTIVITY_CHANGE");


    13、 注册广播监听有没有网络可用

    	NetworkInfo info = connectionManager.getActiveNetworkInfo();
    	if(info!=null&& info.isAvailable){
    		//可用
    	}else {
    		// 不可用
    	}



    14、 静态注册实现开机启动

	15、 git


	16、 File存储

		 Context.openFileInput();
		 Context.openFileOutput();
		 setSelection();

		 TextUtils.isEmpty(); // null或者等于空字符串时候 都返回true


	17、 SharedPreferences
  
         //三种方法获取sharedPreferences

	    1.Context类的getSharedPreferences(); //第一个参数做文件名

	    2.Activity类中的getPreferences();  // 当前活动名做为文件名

	    3.PreferenceManager类中的getDefaultSharedPreferences();  // 当前应用程序的包名做为文件名

	18、 SQLite

	     adb shell

	     sqlite3

	     如何升级数据库 // Important 

	     CRUD // c Create    R Retrive  U Update D Delete


	     db.rawQuery();//查询 query
	     db.execSQL();//增删改 insert update delete

    
    19、 ContentProvider // 主要用于在不同的应用程序之间实现数据共享的功能,它提供了一套完整的机制，允许一个程序访问另一个程序中的数据，同时还能保证被访数据的安全性。

    	 Uri uri = Uri.parse("content://com.example.app.provider/table1"); // 协议 content 权限(authority) com.example.app.provider 路径(path) table1
 	
 		 自定义ContentProvider // Important

 		 //重写6个方法  onCreate(); insert(); query(); delete(); update(); getType();

 		 getType(); // 

 		 1.必须以vnd开头
 		 2.如果内容URI以路径结尾，则后接 android.cursor.dir/,如果内容URI以id结尾，则后接 android.cursor.item/。
 		 3.最后接上 vnd.<authority>.<path>

 		 @override
 		 public String getType(Uri uri){
 		 	switch(uriMatcher.match(uri)){
 		 		case TABLE1_DIR:
 		 			return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
 		 		case TBALE1_ITEM:
 		 			return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";

 		 	}
 		 }


    20、 Notification  // 振动 声音 LED灯etc高级技巧

         PendingIntent // 四个值 

         SENT_SMS_ACTION //发送短信action 
         android.provider.Telephony.SMS_RECEIVED //收到短信action
         android.media.action.IMAGE_CAPTURE //启用相机
         com.android.camera.action.CROP // 启动剪裁程序
         android.intent.action.GET_CONTENT // 启动相册 ？ intent.setType("image/*");

         //TODO list 
         // 拦截短信 
         // 剪裁相片
         // 进行压缩
         // 写一个简单的播放器


    21、 Message 
         Handler
         MessageQueue
         Looper

 
    22、 Service

    	 // Service 的生命周期

         bindService();

         startService() stopService() ----> 调用 onDestroy();
         bindService() unBindService() ----> 调用 onDestroy();
         当调用startService() 又调用 bindService() 后 必须同时调用 stopService()&unBindService()后 onDestroy()才会执行;



    23、 Android的定时任务一般有两种实现方式
 
         1.Alarm  // 具有唤醒CPU的功能  (唤醒CPU跟唤醒屏幕是不同的概念 //有什么区别?)
         2.Timer  // 不适合执行需要长期运行在后期的任务

         SystemClock.elapsedRealtime(); // 返回当前设备从开机到现在的毫秒数


     24、 HTTP网络协议 

          TCP

          UDP

          ISO // 7层

          xml json Gson

          HttpClient HttpConnection

          HttpUtil // 写一个工具类 java接口回调


    25、  LBS // Location Based Service

          BaiduMap // 写一个简单demo

          GaodeMap  // 写一个简单demo


    26、  传感器


    27、  全局获取Context  // 胡凯有篇介绍单例 可以用到这

          public class MyApplication extends Application{

          	private static Context mContext;

          	@override
          	public void onCreate(){         
          		mContext = getApplicationContext(); 		
          	}

          	public static Context getContext(){
          		return mContext;
          	}

          }

          // 在manifest文件中注册application时,要写完整包名.


    28、 使用Intent传递对象

         Serializable方式  // Serializable 是序列化的意思,表示将一个对象转换成可存储可传输的状态

         Parcelable方式  // 不同于将对象进行序列化,实现原理是将一个完整对象进行分解
         				 // 重写方法 可利用android studio的插件 
         				 // 效率稍微高一些


    29、 单元测试
















































           


























 

















 








































