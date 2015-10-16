

	
		Taking Photos Simply
			
			Request Camera Permission

			<manifest ... >
			    <uses-feature android:name="android.hardware.camera"
			                  android:required="true" />
			    ...
			</manifest>
			// 如果不是强制需要 在某个activity要用camera的话,单独判断hasSystemFeature(PackageManager.FEATURE_CAMERA)
			// 如果false 执行disableCarema();

			-------------------------------------------------------------------------------------------------------------------

			Take a Photo with the Camera App

			static final int REQUEST_IMAGE_CAPTURE = 1;
 
			private void dispatchTakePictureIntent() { 
			    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) { // for that safe to use the intent. 
			        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			    } 
			}//如果直接startActivityForResult using an intent that no app can handle, your app will crash

			--------------------------------------------------------------------------------------------------------------------

			Get the Thumbnail

			@Override 
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			        Bundle extras = data.getExtras();
			        Bitmap imageBitmap = (Bitmap) extras.get("data");
			        // the small bitmap in the extras 
			        // 可能只适合一个图标 icon
			        // 实际大小的图像需要更多的工作.
			        mImageView.setImageBitmap(imageBitmap);
			    } 
			} 

			---------------------------------------------------------------------------------------------------------------------

			Save the Full-size Photo

			<manifest ...>
			    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
			                     android:maxSdkVersion="18" />
			    ...
			</manifest>
			//However, if you'd like the photos to remain private to your app only, you can instead use the directory provided by getExternalFilesDir(). On Android 4.3 and lower, writing to this directory also requires the WRITE_EXTERNAL_STORAGE permission. Beginning with Android 4.4, the permission is no longer required because the directory is not accessible by other apps, so you can declare the permission should be requested only on the lower versions of Android by adding the maxSdkVersion attribute.

			//create a collision-resistant file name:
			//↓↓↓

			String mCurrentPhotoPath;
 
			private File createImageFile() throws IOException {
			    // Create an image file name 
			    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			    String imageFileName = "JPEG_" + timeStamp + "_";
			    File storageDir = Environment.getExternalStoragePublicDirectory(
			            Environment.DIRECTORY_PICTURES);
			    File image = File.createTempFile(
			        imageFileName,  /* prefix */
			        ".jpg",         /* suffix */ 
			        storageDir      /* directory */
			    ); 
			 
			    // Save a file: path for use with ACTION_VIEW intents 
			    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
			    return image;
			} 

			//With this method available to create a file for the photo, you can now create and invoke the Intent like this:

			static final int REQUEST_TAKE_PHOTO = 1;
 
			private void dispatchTakePictureIntent() { 
			    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    // Ensure that there's a camera activity to handle the intent 
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			        // Create the File where the photo should go 
			        File photoFile = null;
			        try { 
			            photoFile = createImageFile();
			        } catch (IOException ex) {
			            // Error occurred while creating the File 
			            ... 
			        } 
			        // Continue only if the File was successfully created 
			        if (photoFile != null) {
			            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
			                    Uri.fromFile(photoFile));
			            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			        } 
			    } 
			} 

			-------------------------------------------------------------------------------------------------------------------

			Add the Photo to a Gallery

			// If you saved your photo to the directory provided by getExternalFilesDir(), the media scanner cannot access the files because they are private to your app.
			private void galleryAddPic() { 
			    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			    File f = new File(mCurrentPhotoPath);
			    Uri contentUri = Uri.fromFile(f);
			    mediaScanIntent.setData(contentUri);
			    this.sendBroadcast(mediaScanIntent);
			} 

			--------------------------------------------------------------------------------------------------------------------

			Decode a Scaled Image

			private void setPic() { 
			    // Get the dimensions of the View 
			    int targetW = mImageView.getWidth();
			    int targetH = mImageView.getHeight();
			 
			    // Get the dimensions of the bitmap 
			    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			    bmOptions.inJustDecodeBounds = true;
			    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			    int photoW = bmOptions.outWidth;
			    int photoH = bmOptions.outHeight;
			 
			    // Determine how much to scale down the image 
			    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
			 
			    // Decode the image file into a Bitmap sized to fill the View 
			    bmOptions.inJustDecodeBounds = false;
			    bmOptions.inSampleSize = scaleFactor;
			    bmOptions.inPurgeable = true;
			 
			    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			    mImageView.setImageBitmap(bitmap);
			} 