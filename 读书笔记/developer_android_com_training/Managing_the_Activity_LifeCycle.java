


     Supporting Differenf Decices

	     1. Languages
	     2. Sereens
	     3. Platform Versions




     Starting an Activity

	     the activity can exist in one of only three states for an extended period of time:

	     Resumed(visible)
			In this state, the activity is in the foreground and the user can interact with it. (Also sometimes referred to as the "running" state.)

		 Paused(partially visible)
			In this state, the activity is partially obscured by another activity—the other activity that's in the foreground is semi-transparent or doesn't cover the entire screen. The paused activity does not receive user input and cannot execute any code.

		 Stopped(hidden)
			In this state, the activity is completely hidden and not visible to the user; it is considered to be in the background. While stopped, the activity instance and all its state information such as member variables is retained, but it cannot execute any code.

		//当屏幕熄灭时(the activity is completely hidden and not visible to the user(Stoped state))
	    onPause();onStop();
	    //再次亮起
	    onRestart();onStart();onResume();

	    If your activity includes background threads that you created during onCreate() or other long-running resources that could potentially leak memory if not properly closed, you should kill them during onDestroy().
	    @Override 
		public void onDestroy() { 
		    super.onDestroy();  // Always call the superclass 
		     
		    // Stop method tracing that the activity started during onCreate() 
		    android.os.Debug.stopMethodTracing();
		} 

   
     Pausing and Resuming an Activity
     		Pause Your Activity

	     	    1>use the onPause() callback to:

				Stop animations or other ongoing actions that could consume CPU.

				Commit unsaved changes, but only if users expect such changes to be permanently saved when they leave (such as a draft email).

				Release system resources, such as broadcast receivers, handles to sensors (like GPS), or any resources that may affect battery life while your activity is paused and the user does not need them.



				@Override
				public void onPause() {
				    super.onPause();  // Always call the superclass method first

				    // Release the Camera because we don't need it when paused
				    // and other activities might need to use it.
				    if (mCamera != null) {
				        mCamera.release();
				        mCamera = null;
				    }
				}

			Resume Your Activity
				
				@Override
				public void onResume() {
				    super.onResume();  // Always call the superclass method first

				    // Get the Camera instance as the activity achieves full user focus
				    if (mCamera == null) {
				        initializeCamera(); // Local method to handle camera init
				    }
				}