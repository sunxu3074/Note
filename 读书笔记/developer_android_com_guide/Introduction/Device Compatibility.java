

		Device features

		<manifest ... >
		    <uses-feature android:name="android.hardware.sensor.compass"
		                  android:required="true" />
		    ...
		</manifest>

		//每个机器上的硬件可能不一样,例如没有罗盘,则关闭指南针的功能
		//should set the required attribute to "false" and check for the device feature at runtime.

		PackageManager pm = getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
		    // This device does not have a compass, turn off the compass feature 
		    disableCompassFeature(); 
		} 

		=======================================================================================================================

		Platform version

		targetSdkVersion 要跟新到最新

		android:targetSdkVersion属性所指定的则是经过我们优化的最高版本。这个属性并不能保证我们我们的App不能运行在更高的版本上，而是用来说明我们的App是否应该继承在更高版本上发生的行为改变。如果我们没有更新targetSdkVersion到最新的版本，那么在最新的版本上运行的时候，那么系统就假设我们的App是需要向后兼容的。

		举个例子，在4.4版本中，AlarmManager的行为发生了改变，为了节省电量，系统会将时间相差不多的Alarm放在一起执行，这样就不能保证你的Alarm准时运行。所以说，如果你的targetSdkVersion大于等于19，那么运行时间就是不确定的，但是如果是小于19，那么就会使用之前老的API，保证Alarm能够准时运行。

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
		    // Running on something older than API level 11, so disable 
		    // the drag/drop features that use ClipboardManager APIs 
		    // 如果API小于11,则不能使用剪切板
		    disableDragAndDrop(); 
		} 

