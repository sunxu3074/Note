



		Saving data

		Saving Key-Value Sets

		Get a Handle to a SharedPreferences

			getSharedPreferences()
			 — Use this if you need multiple shared preference files identified by name, which you specify with the first parameter. You can call this from any Context in your app.//如果需要多个shared preference,指定它的第一个参数

			getPreferences() 
			— Use this from an Activity if you need to use only one shared preference file for the activity. Because this retrieves a default shared preference file that belongs to the activity, you don't need to supply a name.'//如果在一个activity中只需要一个shared preference文件,就用这个类(它的文件名就是这个activity)

			Context context = getActivity();
			SharedPreferences sharedPref = context.getSharedPreferences(
			        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			        //string-->such as "com.example.myapp.PREFERENCE_FILE_KEY"

			//当activity仅需要一个shared preference文件时
			SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

			//Caution: 如果创建了一个MODE_WORLD_READABLE或者MODE_WORLD_WRITEABLE 模式的shared preference文件，则其他任何app均可通过文件名访问该文件。

	--------------------------------------------------------------------------------------------------------------------------------------		
		Saving Files

		Internal storage:
			It is always available.//总是可用的
			Files saved here are accessible by only your app by default.//这里的文件默认只能被我们的app所访问。
			When the user uninstalls your app, the system removes all your app's' files from internal storage.
			//当用户卸载app的时候，系统会把internal内该app相关的文件都清除干净。
			Internal storage is best when you want to be sure that neither the user nor other apps can access your files.
			//Internal是我们在想确保不被用户与其他app所访问的最佳存储区域。
		External storage:
			It is not always available, because the user can mount the external storage as USB storage and in some cases remove it from the device.//并不总是可用的，因为用户有时会通过USB存储模式挂载外部存储器，当取下挂载的这部分后，就无法对其进行访问了。
			It is world-readable, so files saved here may be read outside of your control.
			//是大家都可以访问的，因此保存在这里的文件可能被其他程序访问
			When the user uninstalls your app, the system removes your app's' files from here only if you save them in the directory from getExternalFilesDir().//当用户卸载我们的app时，系统仅仅会删除external根目录（getExternalFilesDir()）下的相关文件。
			External storage is the best place for files that don't' require access restrictions and for files that you want to share with other apps or allow the user to access with a computer.
			//External是在不需要严格的访问权限并且希望这些文件能够被其他app所共享或者是允许用户通过电脑访问时的最佳存储区域。

		android:installLocation//如果应用安装程序很大,可以选择安装在external storage


		Obtain Permissions for External Storage

		//写权限声明
		//读权限在当前版本中是可以直接获得的,但是在将来的版本中将会改变.所以还是声明上为好.

		Save a File on Internal Storage

			You don’t need any permissions to save files on the internal storage. Your application always has permission to read and write files in its internal storage directory.

			getFilesDir();
				//Returns a File representing an internal directory for your app.
			getCacheDir();
				//返回一个File，代表了我们app的internal缓存目录。请确保这个目录下的文件能够在一旦不再需要的时候马上被删除，并对其大小进行合理限制，例如1MB 。系统的内部存储空间不够时，会自行选择删除缓存文件。

			1>File file = new File(context.getFilesDir(), filename);

			2>String filename = "myfile";
			  String string = "Hello world!";
			  FileOutputStream outputStream;
			 
			  try { 
			    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			    outputStream.write(string.getBytes());
			    outputStream.close();
			  } catch (Exception e) {
			    e.printStackTrace();
			  } 


			chche some files

			public File getTempFile(Context context, String url) {
			    File file;
			    try { 
			        String fileName = Uri.parse(url).getLastPathSegment();
			        file = File.createTempFile(fileName, null, context.getCacheDir());
			    catch (IOException e) {
			        // Error while creating file 
			    } 
			    return file;
			} 

			//Note: Your app's internal storage directory is specified by your app's package name in a special location of the Android file system. Technically, another app can read your internal files if you set the file mode to be readable. However, the other app would also need to know your app package name and file names. Other apps cannot browse your internal directories and do not have read or write access unless you explicitly set the files to be readable or writable. So as long as you use MODE_PRIVATE for your files on the internal storage, they are never accessible to other apps.
			
			//Note: 我们的app的internal storage 目录以app的包名作为标识存放在Android文件系统的特定目录下[data/data/com.example.xx]。 从技术上讲，如果文件被设置为可读的，那么其他app就可以读取该internal文件。然而，其他app需要知道包名与文件名。若没有设置为可读或者可写，其他app是没有办法读写的。因此我们只要使用了MODE_PRIVATE ，那么这些文件就不可能被其他app所访问。

		Save a File on External Storage

			 /* Checks if external storage is available for read and write */ 
				public boolean isExternalStorageWritable() { 
				    String state = Environment.getExternalStorageState();
				    if (Environment.MEDIA_MOUNTED.equals(state)) {
				        return true; 
				    } 
				    return false; 
				} 
				 
			/* Checks if external storage is available to at least read */ 
				public boolean isExternalStorageReadable() { 
				    String state = Environment.getExternalStorageState();
				    if (Environment.MEDIA_MOUNTED.equals(state) ||
				        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				        return true; 
				    } 
				    return false; 
				} 	

			 Public files
			 	//卸载app后这些文件依然可用
			 	For example, photos captured by your app or other downloaded files.//那些被我们的app拍摄的图片或者下载的文件。
			 	getExternalStoragePublicDirectory();// return public file

			 Private files
			 	//卸载app后会删除
			 	For example, additional resources downloaded by your app or temporary media files.
			 	//那些文件从技术上而言可以被用户与其他app所访问,那些被我们的app下载的缓存文件。
			 	getExternalFilesDir();// return private file 

			 create a directory for an individual photo album:
			 public File getAlbumStorageDir(Context context, String albumName) {
			    // Get the directory for the app's private pictures directory.  
			    File file = new File(context.getExternalFilesDir(
			            Environment.DIRECTORY_PICTURES), albumName);
			            //provided by API constants .These directory names ensure that the files are treated properly by the system
			    		//DIRECTORY_MUSIC or DIRECTORY_PICTURES etc.
			    if (!file.mkdirs()) {
			        Log.e(LOG_TAG, "Directory not created");
			    } 
			    return file;
			} 

			//private
			public File getAlbumStorageDir(Context context, String albumName) {
			    // Get the directory for the app's private pictures directory.
			    File file = new File(context.getExternalFilesDir(
			            Environment.DIRECTORY_PICTURES), albumName);
			    if (!file.mkdirs()) {
			        Log.e(LOG_TAG, "Directory not created");
			    }
			    return file;
			}
			//如果刚开始的时候，没有预定义的子目录存放我们的文件，可以在 getExternalFilesDir()方法中传递null. 它会返回app在external storage下的private的根目录。


			Query Free Space

			getFreeSpace();// 如果返回的mb只比你想存的数据多一点或者the file system is less than 90% full ---> 不能去存储
			getTotalSpace();

			IOException中做一些操作,没必要去申请freespace


			Delete a File

			myFile.delete();
			myContext.deleteFile(fileName);// the file is saved on internal storage

			When the user uninstalls your app, the Android system deletes the following:
				//当卸载应用时,系统会删除以下文件:
				All files you saved on internal storage
				All files you saved on external storage using getExternalFilesDir().
				//通常来说，我们应该定期地手动删除所有通过 getCacheDir() 方式创建的缓存文件，以及那些不会再用到的文件。



	    Saving Data in SQL Databases

	    //存取一些重复或结构化数据 例如联系人

	    //TODO update database ? 

	    //Because they can be long-running, be sure that you call getWritableDatabase() or getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.