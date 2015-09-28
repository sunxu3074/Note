

	
		Sending Simple Data to Other Apps

			Send Text Content

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND); 
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."); 
			sendIntent.setType("text/plain"); 
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			// Intent.createChooser has some advantages:

			Even if the user has previously selected a default action for this intent, the chooser will still be displayed.
			If no applications match, Android displays a system message.
			You can specify a title for the chooser dialog.


			Send Binary Content

			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND); 
			shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage); 
			shareIntent.setType("image/jpeg"); // You can use a MIME type of "*/*", but this will only match activities that are able to handle generic data streams.
			startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));


			 create a ContentProvider like this is to use the FileProvider helper class.

			 Use the system MediaStore. The MediaStore is primarily aimed at video, audio and image MIME types, however beginning with Android 3.0 (API level 11) it can also store non-media types 


			Send Multiple Pieces of Content

			ArrayList<Uri> imageUris = new ArrayList<Uri>();
			imageUris.add(imageUri1); // Add your image URIs here 
			imageUris.add(imageUri2); 
			 
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE); 
			shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris); 
			shareIntent.setType("image/*"); 
			startActivity(Intent.createChooser(shareIntent, "Share images to.."));



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















