

	
		Sending the User to Another App

		
		Verify There is an App to Receive the Intent//是否有正确的应用响应Intent

			// Build the intent 
			Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
			 
			// Verify it resolves 
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
			boolean isIntentSafe = activities.size() > 0;
			 
			// Start an activity if it's safe 
			if (isIntentSafe) {
			    startActivity(mapIntent);
			} 

		Show an App Chooser//显示应用程序选择器

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


		//Android 2.3以后  The temporary permission applies only to the specific contact requested, so you cannot query a contact other than the one specified by the intent's Uri, unless you do declare the READ_CONTACTS permission.


		Allowing Other Apps to Start Your Activity

			//当你的应用程序安装到手机上,系统会识别你应用的intent filters到被支持于所有安装程序的内部目录.

		