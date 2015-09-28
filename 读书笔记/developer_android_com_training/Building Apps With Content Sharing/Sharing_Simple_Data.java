

	
		Sending Simple Data to Other Apps

			Send Text Content

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND); 
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send."); 
			sendIntent.setType("text/plain"); 
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			// Intent.createChooser has some advantages:

			Even if the user has previously selected a default action for this intent, the chooser will still be displayed.
			If no applications match, Android displays a system message.
			You can specify a title for the chooser dialog.


			Send Binary Content

			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND); 
			shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage); 
			shareIntent.setType("image/jpeg"); // You can use a MIME type of "*/*", but this will only match activities that are able to handle generic data streams.
			startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));


			 create a ContentProvider like this is to use the FileProvider helper class.

			 Use the system MediaStore. The MediaStore is primarily aimed at video, audio and image MIME types, however beginning with Android 3.0 (API level 11) it can also store non-media types 


			Send Multiple Pieces of Content

			ArrayList<Uri> imageUris = new ArrayList<Uri>();
			imageUris.add(imageUri1); // Add your image URIs here 
			imageUris.add(imageUri2); 
			 
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE); 
			shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris); 
			shareIntent.setType("image/*"); 
			startActivity(Intent.createChooser(shareIntent, "Share images to.."));
