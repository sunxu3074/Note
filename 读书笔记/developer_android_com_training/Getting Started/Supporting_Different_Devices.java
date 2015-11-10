

		Supporting Different Languages

		MyProject/
		    res/
		       values/
		           strings.xml
		       values-es/
		           strings.xml
		       values-fr/
		           strings.xml
		       values-zh-rCN
		           strings.xml

		------------------------------------------------------------------------------------------------------

		Supporting Different Screens

		MyProject/
		    res/
		        layout/              # default (portrait)
		            main.xml
		        layout-land/         # landscape
		            main.xml
		        layout-large/        # large (portrait)
		            main.xml
		        layout-large-land/   # large landscape
		            main.xml

		更多:  https://developer.android.com/guide/topics/resources/providing-resources.html#BestMatch

		Create Different Bitmaps

			xhdpi: 2.0
			hdpi: 1.5
			mdpi: 1.0 (baseline)
			ldpi: 0.75

			//这意味着，如果针对xhdpi的设备生成了一张200x200的图像，那么应该为hdpi生成150x150,为mdpi生成100x100, 和为ldpi生成75x75的图片资源。

			//Note:低密度(ldpi)资源是非必要的，当提供了hdpi的图像，系统会把hdpi的图像按比例缩小一半，去适配ldpi的屏幕。

		-------------------------------------------------------------------------------------------------------

		Supporting Different Platform Versions

		Check System Version at Runtime

			private void setUpActionBar() { 
			    // Make sure we're running on Honeycomb or higher to use ActionBar APIs 
			    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			        ActionBar actionBar = getActionBar();
			        actionBar.setDisplayHomeAsUpEnabled(true);
			    } 
			} 

			//Note:当解析XML资源时，Android会忽略当前设备不支持的XML属性。所以我们可以安全地使用较新版本的XML属性，而不需要担心旧版本Android遇到这些代码时会崩溃。例如如果我们设置targetSdkVersion="11"，app会在Android 3.0或更高时默认包含ActionBar。然后添加menu items到action bar时，我们需要在自己的menu XML资源中设置android:showAsAction="ifRoom"。在跨版本的XML文件中这么做是安全的，因为旧版本的Android会简单地忽略showAsAction属性(就是这样，你并不需要用到res/menu-v11/中单独版本的文件)。

	    Use Platform Styles and Themes

	    //使activity看起来像对话框:

		<activity android:theme="@android:style/Theme.Dialog">
		//使activity有一个透明背景:

		<activity android:theme="@android:style/Theme.Translucent">



		



		