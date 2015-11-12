

	
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

    	





		
				