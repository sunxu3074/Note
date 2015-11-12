

	
		Interacting with Other Apps

		Sending the User to Another App
		//当想要唤起不同的app来执行某个动作（比如查看地图），则必须使用隐式（implicit）的intent
		
		Verify There is an App to Receive the Intent//是否有正确的应用响应Intent

			// Build the intent 
			Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
			//ction指定了我们想做的事情，例如查看，编辑，发送或者是获取一些东西。通常还会附带一些数据.请尽可能的将Intent定义的更加确切。例如，如果想要使用ACTION_VIEW 的intent来显示一张图片，则还应该指定 MIME type 为image/*
			 
			// Verify it resolves (如果触发了一个intent，而且没有任何一个app会去接收这个intent，则app会crash)
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
			boolean isIntentSafe = activities.size() > 0;
			 
			// Start an activity if it's safe 
			if (isIntentSafe) {
			    startActivity(mapIntent);
			} 

		Show an App Chooser//显示应用程序选择器
		//如果用户希望每次都弹出选择界面，而且每次都不确定会选择哪个app启动，例如分享功能，用户选择分享到哪个app都是不确定的，这个时候，需要强制弹出选择的对话框

			Intent intent = new Intent(Intent.ACTION_SEND);
			... 
			 
			// Always use string resources for UI text. 
			// This says something like "Share this photo with" 
			String title = getResources().getString(R.string.chooser_title);
			// Create intent to show chooser 
			Intent chooser = Intent.createChooser(intent, title);
			 
			// Verify the intent will resolve to at least one activity 
			if (intent.resolveActivity(getPackageManager()) != null) {
			    startActivity(chooser);
			} 

	---------------------------------------------------------------------------------------------------------------------------------------

		Getting a Result from an Activity

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    // Check which request we're responding to
		    if (requestCode == PICK_CONTACT_REQUEST) {
		        // Make sure the request was successful
		        if (resultCode == RESULT_OK) {
		            // The user picked a contact.
		            // The Intent's data Uri identifies which contact was selected.

		            // Do something with the contact here (bigger example below)
		        }
		    }
		}
		//为正确处理这些result，我们必须了解那些result intent的格式。对于自己程序里面的返回result是比较简单的。Apps都会有一些自己的api来指定特定的数据。例如，People app (Contacts app on some older versions) 总是返回一个URI来指定选择的contact，Camera app 则是在data数据区返回一个 Bitmap （see the class about Capturing Photos).

		//Android 2.3以后  The temporary permission applies only to the specific contact requested, so you cannot query a contact other than the one specified by the intent's Uri, unless you do declare the READ_CONTACTS permission.
		//在Android 2.3 (API level 9)之前对Contacts Provider的请求(比如上面的代码)，需要声明READ_CONTACTS权限(更多详见Security and Permissions)。但如果是Android 2.3以上的系统就不需要这么做。但这种临时权限也仅限于特定的请求，所以仍无法获取除返回的Intent以外的联系人信息，除非声明了READ_CONTACTS权限。

    ---------------------------------------------------------------------------------------------------------------------------------------

		Allowing Other Apps to Start Your Activity

		//当app被安装到设备上时,系统可以识别intent filter并把这些信息记录下来.当其他app使用implicit intent执行 startActivity() 或者 startActivityForResult()时,系统会自动查找出那些可以响应该intent的activity.

		//假设我们的activity可以handle 文本与图片，无论是ACTION_SEND还是ACTION_SENDTO 的intent。在这种情况下，就必须为两个action定义两个不同的intent filter。因为ACTION_SENDTO intent 必须使用 Uri 类型来指定接收者使用 send 或 sendto 的地址。例如:
		<activity android:name="ShareActivity">
		    <!-- filter for sending text; accepts SENDTO action with sms URI schemes -->
		    <intent-filter>
		        <action android:name="android.intent.action.SENDTO"/>
		        <category android:name="android.intent.category.DEFAULT"/>
		        <data android:scheme="sms" />
		        <data android:scheme="smsto" />
		    </intent-filter>
		    <!-- filter for sending text or images; accepts SEND action and text or image data -->
		    <intent-filter>
		        <action android:name="android.intent.action.SEND"/>
		        <category android:name="android.intent.category.DEFAULT"/>
		        <data android:mimeType="image/*"/>
		        <data android:mimeType="text/plain"/>
		    </intent-filter>
		</activity>

		Handle the Intent in Your Activity  ↑↑↑↑↑

		@Override 
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		 
		    setContentView(R.layout.main);
		 
		    // Get the intent that started this activity 
		    Intent intent = getIntent();
		    Uri data = intent.getData();
		 
		    // Figure out what to do based on the intent type 
		    if (intent.getType().indexOf("image/") != -1) {
		        // Handle intents with image data ... 
		    } else if (intent.getType().equals("text/plain")) {
		        // Handle intents with text ... 
		    } 
		} 

		Return a Result

		// Create intent to deliver some kind of result data 
		Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri");
		setResult(Activity.RESULT_OK, result);
		finish();
		//如果只是纯粹想要返回一个int来表示某些返回的result数据之一，则可以设置result code为任何大于0的数值。如果我们返回的result只是一个int，那么连intent都可以不需要返回了，可以调用setResult()然后只传递result code如下：
		setResult(RESULT_COLOR_RED);
		finish();






		
				