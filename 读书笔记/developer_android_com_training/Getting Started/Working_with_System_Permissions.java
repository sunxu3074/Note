

	
		Working with System Permissions

		Declaring Permissions

		see Normal and Dangerous Permissions:  
		http://developer.android.com/guide/topics/security/permissions.html#normal-dangerous
		The system's' behavior after you declare a permission depends on how sensitive the permission is. If the permission does not affect user privacy, the system grants the permission automatically. If the permission might grant access to sensitive user information, the system asks the user to approve the request.
		//如果申请的权限没有涉及到用户隐私,系统会自动的赋予权限.如果申请的权限涉及到用户的隐私信息,那么系统将会询问用户是否同意这个请求.

		Your app only needs permissions for actions that it performs directly.
		// 你的应用程序只需要直接执行的权限(如果在app中读取联系人则需要读取联系人的权限,如果是启动别的app,如系统自带的联系人app从而获得联系人信息,则不需要任何权限,但是系统自带的app需要权限)
		For more information, see Consider Using an Intent
		http://developer.android.com/training/permissions/best-practices.html#perms-vs-intents
    -----------------------------------------------------------------------------------------------------------------------------------------------------

    	Requesting Permissions at Run Time

    	Beginning in Android 6.0 (API level 23), users grant permissions to apps while the app is running, not when they install the app. This approach streamlines the app install process, since the user does not need to grant permissions when they install or update the app. It also gives the user more control over the app's functionality; for example, a user could choose to give a camera app access to the camera but not to the device location. The user can revoke the permissions at any time, by going to the app's Settings screen.
    	// 从android6.0开始,当程序运行时会被用户赋予权限,而不是安装程序时.给了用户更能够控制程序的功能.用户在设置里面可随时收回权限.

    	1.如果申请的权限没有涉及到用户隐私,系统会自动的赋予权限.
    	2.如果申请的权限涉及到用户的隐私信息,那么系统将会明确询问用户是否同意这个请求.
    	
    	If the device is running Android 5.1 or lower, or your app's' target SDK is 22 or lower: If you list a dangerous permission in your manifest, the user has to grant the permission when they install the app; if they do not grant the permission, the system does not install the app at all.
    	//如果是Android5.1或者更低的版本,有涉及到用户隐私信息的权限,用户将会在安装程序时不得不赋予权限,如果不同意将不能安装这个程序.
    	If the device is running Android 6.0 or higher, and your app's' target SDK is 23 or higher: The app has to list the permissions in the manifest, and it must request each dangerous permission it needs while the app is running. The user can grant or deny each permission, and the app can continue to run with limited capabilities even if the user denies a permission request.
    	//如果是Android6.0或者更高的版本,将会在程序运行时询问每个涉及到用户隐私的权限.用户可以拒绝这些权限,程序将会继续运行在因为权限被拒的限制范围内.

    	Check For Permissions

    	//因为用户可随时收回权限,所以在需要一个dangerous permission时,每次都要检查

    	// Assume thisActivity is the current activity 
		int permissionCheck = ContextCompat.checkSelfPermission(thisActivity,
		        Manifest.permission.WRITE_CALENDAR); 

		return PackageManager.PERMISSION_GRANTED --> 可操作
		return PackageManager.PERMISSION_DENIED --> 显示地向用户询问权限


		Explain why the app needs permissions

		//弹出一个dialog解释为什么需要这个权限(简短有力的解释)

		shouldShowRequestPermissionRationale()
		return true; --> 之前请求过但是被拒绝请求了
		return false; --> 之前被拒绝还被点击了 Don't' ask again   或者  设备不支持


		Request the permissions you need

		// Here, thisActivity is the current activity 
		if (ContextCompat.checkSelfPermission(thisActivity,
		                Manifest.permission.READ_CONTACTS) 
		        != PackageManager.PERMISSION_GRANTED) {
		 
		    // Should we show an explanation? 
		    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
		            Manifest.permission.READ_CONTACTS)) { 
		 
		        // Show an expanation to the user *asynchronously* -- don't block 
		        // this thread waiting for the user's response! After the user 
		        // sees the explanation, try again to request the permission. 
		 
		    } else { 
		 
		        // No explanation needed, we can request the permission. 
		 
		        ActivityCompat.requestPermissions(thisActivity,
		                new String[]{Manifest.permission.READ_CONTACTS},
		                MY_PERMISSIONS_REQUEST_READ_CONTACTS); 
		 
		        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an 
		        // app-defined int constant. The callback method gets the 
		        // result of the request. 
		    } 
		} 


		//The callback is passed the same request code you passed to requestPermissions()
		@Override 
		public void onRequestPermissionsResult(int requestCode,
		        String permissions[], int[] grantResults) {
		    switch (requestCode) {
		        case MY_PERMISSIONS_REQUEST_READ_CONTACTS: { 
		            // If request is cancelled, the result arrays are empty. 
		            if (grantResults.length > 0
		                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
		 
		                // permission was granted, yay! Do the 
		                // contacts-related task you need to do. 
		 
		            } else { 
		 
		                // permission denied, boo! Disable the 
		                // functionality that depends on this permission. 
		            } 
		            return; 
		        } 
		 
		        // other 'case' lines to check for other 
		        // permissions this app might request 
		    } 
		} 

	-----------------------------------------------------------------------------------------------------------------------------------------------------

		Permissions Best Practices

		Consider Using an Intent

		执行一个任务有两种方式:
		1.use permissions.
		2.use an intent.

		各自的优缺点:
		If you use permissions: for example CAMERA permission
		Your app has full control over the user experience when you perform the operation. However, such broad control adds to the complexity of your task, since you need to design an appropriate UI.
		//执行操作时有全面的控制体验,然而你需要设计适当的UI会增加复杂性.
		The user is prompted to give permission once, either at run time or at install time (depending on the user''s Android version). After that, your app can perform the operation without requiring additional interaction from the user. However, if the user doesn''t grant the permission (or revokes it later on), your app becomes unable to perform the operation at all.
		//在运行时或者安装时用户会被提示权限(根据版本而定).接着,你的应用程序会执行操作不需要额外的用户操作.然而,如果用户没有授予这个权限,或者在之后收回权限,那么你的应用程序将会一直不能执行操作.
		If you use an intent: for example ACTION_IMAGE_CAPTURE
		You do not have to design the UI for the operation. The app that handles the intent provides the UI. However, this means you have no control over the user experience. The user could be interacting with an app you''ve never seen.
		//不必设计UI.
		If the user does not have a default app for the operation, the system prompts the user to choose an app. If the user does not designate a default handler, they may have to go through an extra dialog every time they perform the operation.
		//如果没有默认程序执行操作,系统会提示用户选择app.如果用户没有指定一个默认的,则在每次执行这个操作时都会弹出dialog.


		Only Ask for Permissions You Need
		You should minimize the number of permissions your app needs.

		Quite often your app can avoid requesting a permission by using an intent instead. If a feature is not a core part of your app''s functionality, you should consider handing the work over to another app, as described in Consider Using An Intent.
		//很多时候你的应用程序通过用一个intent代替能避免获取权限操作.如果这个特性不是你程序的核心部分,你应该考虑让另外一个应用程序来处理.


		Don''t Overwhelm the User

		you should ask for permissions as you need them.


		Explain Why You Need Permissions
		a photography app might want to use location services so it can geotag the photos. A typical user might not understand that a photo can contain location information, and would be puzzled why their photography app wants to know the location. So in this case, it''s a good idea for the app to tell the user about this feature before calling requestPermissions().

		One way to inform the user is to incorporate these requests into an app tutorial. The tutorial can show each of the app''s features in turn, and as it does this, it can explain what permissions are needed. For example, the photography app''s tutorial could demonstrate its "share photos with your contacts" feature, then tell the user that they need to give permission for the app to see the user''s contacts. The app could then call requestPermissions() to ask the user for that access. Of course, not every user is going to follow the tutorial, so you still need to check for and request permissions during the app''s normal operation.


		Test for Both Permissions Models

		Beginning with Android 6.0 (API level 23), users grant and revoke app permissions at run time, instead of doing so when they install the app. As a result, you''ll have to test your app under a wider range of conditions. Prior to Android 6.0, you could reasonably assume that if your app is running at all, it has all the permissions it declares in the app manifest. Under the new_permissions model, you can no longer make that assumption..
		//自从Android6.0以后,用户在运行时授予和收回权限,不再是安装程序的时候.因此,你必须在更广泛的条件下测试您的应用程序.

		The following tips will help you identify permissions-related code problems on devices running API level 23 or higher:

		Identify your app’s current permissions and the related code paths.
		//确定你的应用程序的当前的权限和相关的代码路径。
		Test user flows across permission-protected services and data.
		//测试用户跨权限保护的服务和数据流.
		Test with various combinations of granted or revoked permissions. For example, a camera app might list CAMERA, READ_CONTACTS, and ACCESS_FINE_LOCATION in its manifest. You should test the app with each of these permissions turned on and off, to make sure the app can handle all permission configurations gracefully.
		//在各种收回或者赋予权限之间的组合之间进行测试.确保你的应用程序能处理所有的权限配置.
		Use the adb tool to manage permissions from the command line://用adb在命令行管理权限
			List permissions and status by group://列出所有的权限状态
				$ adb shell pm list permissions -d -g
			Grant or revoke one or more permissions://赋予或者收回一个或更多权限
				$ adb shell pm [grant|revoke] <permission-name> ...
			Analyze your app for services that use permissions //分析应用中使用权限的服务




			
				