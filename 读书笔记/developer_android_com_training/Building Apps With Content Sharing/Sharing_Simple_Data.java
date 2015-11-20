

	
		Sending Simple Data to Other Apps

			Send Text Content//分享文本内容

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND); 
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."); 
			sendIntent.setType("text/plain"); 
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			
			// Intent.createChooser has some advantages:
			Even if the user has previously selected a default action for this intent, the chooser will still be displayed.
			//即使用户之前为这个intent设置了默认的action,选择界面还是会被显示
			If no applications match, Android displays a system message.
			//如果没有匹配的程序,Android会显示系统信息
			You can specify a title for the chooser dialog.
			//我们可以指定选择界面的标题

			Here''s the updated code:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND); 
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."); 
			sendIntent.setType("text/plain"); 
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			

			Send Binary Content

			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND); 
			shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage); 
			shareIntent.setType("image/jpeg"); // You can use a MIME type of "*/*", but this will only match activities that are able to handle generic data streams.
			startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

			Note the following:
			 create a ContentProvider like this is to use the FileProvider helper class.

			Use the system MediaStore. The MediaStore is primarily aimed at video, audio and image MIME types, however beginning with Android 3.0 (API level 11) it can also store non-media types (see MediaStore.Files for more info). Files can be inserted into the MediaStore using scanFile() after which a content:  style Uri suitable for sharing is passed to the provided onScanCompleted() callback. Note that once added to the system MediaStore the content is accessible to any app on the device.


			Send Multiple Pieces of Content

			ArrayList<Uri> imageUris = new ArrayList<Uri>();
			imageUris.add(imageUri1); // Add your image URIs here 
			imageUris.add(imageUri2); 
			 
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE); 
			shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris); 
			shareIntent.setType("image/*"); 
			startActivity(Intent.createChooser(shareIntent, "Share images to.."));

			//请确保指定到数据的URIs能够被接收程序所访问(添加访问权限)

---------------------------------------------------------------------------------------------------------------------------------------------------------

	Receiving Simple Data from Other Apps

		Update Your Manifest

		<activity android:name=".ui.MyActivity" >
		    <intent-filter>
		        <action android:name="android.intent.action.SEND" />
		        <category android:name="android.intent.category.DEFAULT" />
		        <data android:mimeType="image/*" />
		    </intent-filter>
		    <intent-filter>
		        <action android:name="android.intent.action.SEND" />
		        <category android:name="android.intent.category.DEFAULT" />
		        <data android:mimeType="text/plain" />
		    </intent-filter>
		    <intent-filter>
		        <action android:name="android.intent.action.SEND_MULTIPLE" />
		        <category android:name="android.intent.category.DEFAULT" />
		        <data android:mimeType="image/*" />
		    </intent-filter>
		</activity>


		Handle the Incoming Content


		void onCreate (Bundle savedInstanceState) {
		    ... 
		    // Get intent, action and MIME type 
		    Intent intent = getIntent();
		    String action = intent.getAction();
		    String type = intent.getType();
		 
		    if (Intent.ACTION_SEND.equals(action) && type != null) {
		        if ("text/plain".equals(type)) {
		            handleSendText(intent); // Handle text being sent
		        } else if (type.startsWith("image/")) {
		            handleSendImage(intent); // Handle single image being sent
		        } 
		    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
		        if (type.startsWith("image/")) {
		            handleSendMultipleImages(intent); // Handle multiple images being sent
		        } 
		    } else { 
		        // Handle other intents, such as being started from the home screen 
		    } 
		    ... 
		} 
		 
		void handleSendText(Intent intent) {
		    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		    if (sharedText != null) {
		        // Update UI to reflect text being shared 
		    } 
		} 
		 
		void handleSendImage(Intent intent) {
		    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		    if (imageUri != null) {
		        // Update UI to reflect image being shared 
		    } 
		} 
		 
		void handleSendMultipleImages(Intent intent) {
		    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		    if (imageUris != null) {
		        // Update UI to reflect multiple images being shared 
		    } 
		} 
		//remember to process binary data in a separate thread rather than the main ("UI") thread.
		//由于无法知道其他程序发送过来的数据内容是文本还是其他类型的数据，若数据量巨大，则需要大量处理时间，因此我们应避免在UI线程里面去处理那些获取到的数据。

---------------------------------------------------------------------------------------------------------------------------------------------------------

	Adding an Easy Share Action

		 ShareActionProvider is available starting with API Level 14 and higher.

		 Update Menu Declarations

		 <menu xmlns:android="http://schemas.android.com/apk/res/android">
		    <item
		            android:id="@+id/menu_item_share"
		            android:showAsAction="ifRoom"
		            android:title="Share"
		            android:actionProviderClass=
		                "android.widget.ShareActionProvider" />
		    ...
		</menu>

		Set the Share Intent

		private ShareActionProvider mShareActionProvider;
		... 
		 
		@Override 
		public boolean onCreateOptionsMenu(Menu menu) {
		    // Inflate menu resource file. 
		    getMenuInflater().inflate(R.menu.share_menu, menu);
		 
		    // Locate MenuItem with ShareActionProvider 
		    MenuItem item = menu.findItem(R.id.menu_item_share);
		 
		    // Fetch and store ShareActionProvider 
		    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
		 
		    // Return true to display menu 
		    return true; 
		} 
		 
		// Call to update the share intent 
		private void setShareIntent(Intent shareIntent) {
		    if (mShareActionProvider != null) {
		        mShareActionProvider.setShareIntent(shareIntent);
		    } 
		} 















