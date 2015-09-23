



		Saving data

		Saving Key-Value Sets

		Get a Handle to a SharedPreferences

			getSharedPreferences()
			 — Use this if you need multiple shared preference files identified by name, which you specify with the first parameter. You can call this from any Context in your app.//如果需要多个shared preference,指定它的第一个参数

			getPreferences() 
			— Use this from an Activity if you need to use only one shared preference file for the activity. Because this retrieves a default shared preference file that belongs to the activity, you don't need to supply a name.'//如果在一个activity中只需要一个shared preference文件,就用这个类(它的文件名就是这个activity)

			Context context = getActivity();
			SharedPreferences sharedPref = context.getSharedPreferences(
			        getString(R.string.preference_file_key), Context.MODE_PRIVATE);//string-->such as "com.example.myapp.PREFERENCE_FILE_KEY"




		Saving Files

		Internal storage:
			It's always available.
			Files saved here are accessible by only your app by default.
			When the user uninstalls your app, the system removes all your app's files from internal storage.
			Internal storage is best when you want to be sure that neither the user nor other apps can access your files.

		External storage:
			It's not always available, because the user can mount the external storage as USB storage and in some cases remove it from the device.
			It's world-readable, so files saved here may be read outside of your control.
			When the user uninstalls your app, the system removes your app's files from here only if you save them in the directory from getExternalFilesDir().
			External storage is the best place for files that don't require access restrictions and for files that you want to share with other apps or allow the user to access with a computer.

		android:installLocation//如果应用安装程序很大,可以选择安装在external storage



		Save a File on Internal Storage

			You don’t need any permissions to save files on the internal storage. Your application always has permission to read and write files in its internal storage directory.

			getFilesDir();
				//Returns a File representing an internal directory for your app.
			getCacheDir();

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

			
			 So as long as you use MODE_PRIVATE for your files on the internal storage, they are never accessible to other apps.


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
			 	For example, photos captured by your app or other downloaded files.
			 	getExternalStoragePublicDirectory();// return public file

			 Private files
			 	//卸载app后会删除
			 	For example, additional resources downloaded by your app or temporary media files.
			 	getExternalFilesDir();// return private file 

			  create a directory for an individual photo album:
			 public File getAlbumStorageDir(Context context, String albumName) {
			    // Get the directory for the app's private pictures directory.  
			    File file = new File(context.getExternalFilesDir(
			            Environment.DIRECTORY_PICTURES), albumName);//provided by API constants .These directory names ensure that the files are treated properly by the system
			    if (!file.mkdirs()) {
			        Log.e(LOG_TAG, "Directory not created");
			    } 
			    return file;
			} 


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