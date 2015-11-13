

	
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
    ------------------------------------------------------------------------------------------------------------------------------------------------------

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

    	





		
				