


 	http://developer.android.com/training 


 	1.<application ... >
   		 ...
   		 <activity
      	  android:name="com.mycompany.myfirstapp.DisplayMessageActivity"
      	  android:label="@string/title_activity_display_message"
      	  android:parentActivityName="com.mycompany.myfirstapp.MyActivity" >
      	  <meta-data
           	 android:name="android.support.PARENT_ACTIVITY"
             android:value="com.mycompany.myfirstapp.MyActivity" />
    	 </activity>
	  </application>

	  The android:parentActivityName attribute declares the name of this activity's parent activity within the app's logical hierarchy. The system uses this value to implement default navigation behaviors, such as Up navigation on Android 4.1 (API level 16) and higher. //4.1及以上系统使用

	  You can provide the same navigation behaviors for older versions of Android by using the Support Library and adding the <meta-data> element as shown here.//为了向下兼容 加入<meta-data>元素


	2.ActionBar  //Support Android 3.0 and Above Only  (API 11)

	  1>theme Theme.Holo或是子类 3.0显示

	  2>为了兼容3.0以前 Android2.1(API 7) 
	  需要继承ActionBarActivity
	  导入 v7 appcompat library
	  //public class MainActivity extends ActionBarActivity { ... }
	  并且theme Theme.AppCompat或子类



    3. android:showAsAction="ifRoom"  // should appear as action button
       android:showAsAction="never"   // should always be in the overflow (By default, all actions appear in the overflow 但是写出来更明确)

       If your app is using the Support Library

       需要自定义xml的命名空间
       <menu xmlns:android="http://schemas.android.com/apk/res/android"
      		xmlns:yourapp="http://schemas.android.com/apk/res-auto" >
    			<!-- Search, should appear as action button -->
    			<item android:id="@+id/action_search"
          			  android:icon="@drawable/ic_action_search"
                      android:title="@string/action_search"
                      yourapp:showAsAction="ifRoom"  />
             ...
        </menu>