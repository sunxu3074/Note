


		Using Permissions

		<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		    package="com.android.app.myapp" >
		    <uses-permission android:name="android.permission.RECEIVE_SMS" />
		    ...
		</manifest>

		manifest 中的permissions 
		1.没有用户涉及到用户隐私或者设备操作的 默认安装
		2.设计到的 会询问用户是否安装


		If the device is running Android 6.0 (API level 23) or higher, and the app's targetSdkVersion is 23 or higher
		用户需要每次启动时,都要检查权限,因为用户可以随时随地的收回权限

		If the device is running Android 5.1 (API level 22) or lower, or the app's targetSdkVersion is 22 or lower
		在安装时询问用户是否同意权限,只有卸载才会撤回权限.

		============================================================================================================================================

		User IDs and File Access

		You can use the sharedUserId attribute in the AndroidManifest.xml‘s manifest tag of each package to have them assigned the same user ID.  By doing this, for purposes of security the two packages are then treated as being the same application, with the same user ID and file permissions. Note that in order to retain security, only two applications signed with the same signature (and requesting the same sharedUserId) will be given the same user ID.
		//因为安全的强制实施是在进程级别上的，任何两个软件包的代码不能运行在同一进程上，因为它们需要以不同的Linux用户身份来运行(不同的UID).
		//你可以通过在每个软件包里的AndroidManifest.xml 文件中设置sharedUserId 标签的属性值来请求系统赋予相同的UID. 如果这样做，安全机制会把这样两个app会被当做是同一个app，拥有相同的UID和文件权限. 请注意为了保持(记住)它们的安全权限，这两个app必须是使用相同的签名才能这么做

