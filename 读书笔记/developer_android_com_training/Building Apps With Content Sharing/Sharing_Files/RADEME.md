


		# Setting Up File Sharing
		====
		Android的FileProvider组件会基于在XML文件中的具体配置为文件创建Content URI。

		## 指定FileProvider
		

		```java
		<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		    package="com.example.myapp">
		    <application
		        ...>
		        <provider
		            android:name="android.support.v4.content.FileProvider"
		            android:authorities="com.example.myapp.fileprovider"//使用自己的包名
		            android:grantUriPermissions="true"
		            android:exported="false">
		            <meta-data
		                android:name="android.support.FILE_PROVIDER_PATHS"
		                android:resource="@xml/filepaths" />  //指定了我们希望共享的目录路径 无.xml后缀
		        </provider>
		        ...
		    </application>
		</manifest>
		```
		----

		##指定可共享目录路径
		下面的是一个“res/xml/filepaths.xml”的内容样例
		```java
		<paths>
		    <files-path path="images/" name="myimages" />
		</paths>
		```

		----

		