


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

	// 如果主动调用finish()方法 会直接回调onDestroy(); 不执行之间的一些方法



    Stopping and Restarting an Activity

    	Stop Your Activity

    	//在极端情况下,系统不会回调onDestroy()方法而直接杀死应用进程,在onStop()方法中释放会导致内存泄露的资源是非常重要的。

    	should use onStop() to perform larger, more CPU intensive shut-down operations, such as writing information to a database.

    	Start/Restart Your Activity

    	// 在onStart()方法中做一些回复工作.(即使avtivity的实例被销毁了,onRestart()做不到)  
    	// 跟onStop()对应
    	//一般来说onStop()就释放了大部分的资源,OnDestroy()是清除可能会导致内存泄露的最后一次机会.( be sure that additional threads are destroyed and other long-running actions like method tracing are also stopped.)


    Recreating an Activity

    	// 每个view最好加上id 尤其是能编辑的内容 便于毁容 给用户更好的体验
    	In order for the Android system to restore the state of the views in your activity, each view must have a unique ID, supplied by the android:id attribute.



    	static final String STATE_SCORE = "playerScore";
		static final String STATE_LEVEL = "playerLevel";
		... 
		 
		@Override 
		public void onSaveInstanceState(Bundle savedInstanceState) {
		    // Save the user's current game state 
		    savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
		    savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);
		     
		    // Always call the superclass so it can save the view hierarchy state 
		    super.onSaveInstanceState(savedInstanceState);
		} 


		@Override 
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState); // Always call the superclass first
		    
		    // Check whether we're recreating a previously destroyed instance 
		    if (savedInstanceState != null) {
		        // Restore value of members from saved state 
		        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
		        mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
		    } else { 
		        // Probably initialize members with default values for a new instance 
		    } 
		    ... 
		} 


		// 在onStart()方法之后执行,且不用验证Bundle是否为空,只有Bundle不为空时才会调用该方法
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy 
		    super.onRestoreInstanceState(savedInstanceState);
		    
		    // Restore state members from saved instance 
		    mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
		    mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
		} 