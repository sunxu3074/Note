


     Managing_the_Activity_LifeCycle



     Starting an Activity

	     the activity can exist in one of only three states for an extended period of time:
	     //其中只有三个状态是静态的，这三个状态下activity可以存在一段比较长的时间。(其它几个状态会很快就切换掉，停留的时间比较短暂)

	     Resumed(visible)
			In this state, the activity is in the foreground and the user can interact with it. (Also sometimes referred to as the "running" state.)
			//该状态下，activity处在前台，用户可以与它进行交互。(通常也被理解为"running" 状态)

		 Paused(partially visible)
			In this state, the activity is partially obscured by another activity—the other activity that's in the foreground is semi-transparent or doesn't cover the entire screen. The paused activity does not receive user input and cannot execute any code.
			//该状态下，activity的部分被另外一个activity所遮盖：另外的activity来到前台，但是半透明的，不会覆盖整个屏幕。被暂停的activity不再接受用户的输入且不再执行任何代码。

		 Stopped(hidden)
			In this state, the activity is completely hidden and not visible to the user; it is considered to be in the background. While stopped, the activity instance and all its state information such as member variables is retained, but it cannot execute any code.
			//该状态下, activity完全被隐藏，对用户不可见。可以认为是在后台。当stopped, activity实例与它的所有状态信息（如成员变量等）都会被保留，但activity不能执行任何代码。

		//当屏幕熄灭时(the activity is completely hidden and not visible to the user(Stoped state))
	    onPause();onStop();
	    //再次亮起
	    onRestart();onStart();onResume();



	    If your activity includes background threads that you created during onCreate() or other long-running resources that could potentially leak memory if not properly closed, you should kill them during onDestroy().
	    //如果activity含有在onCreate调用时创建的后台线程，或者是其他有可能导致内存泄漏的资源，则应该在OnDestroy()时进行资源清理，杀死后台线程。

	    @Override 
		public void onDestroy() { 
		    super.onDestroy();  // Always call the superclass 
		     
		    // Stop method tracing that the activity started during onCreate() 
		    android.os.Debug.stopMethodTracing();
		} 
		//Note: 除非程序在onCreate()方法里面就调用了finish()方法，系统通常是在执行了onPause()与onStop() 之后再调用onDestroy() 。在某些情况下，例如我们的activity只是做了一个临时的逻辑跳转的功能，它只是用来决定跳转到哪一个activity，这样的话，需要在onCreate里面调用finish方法，这样系统会直接调用onDestory，跳过生命周期中的其他方法。

    -----------------------------------------------------------------------------------------------------------------------------------------------------
   
     Pausing and Resuming an Activity

     		Pause Your Activity

	     	    1>use the onPause() callback to:
	     	    //通常应该在onPause()回调方法里面做以下事情:

				Stop animations or other ongoing actions that could consume CPU.
				//停止动画或者是其他正在运行的操作，那些都会导致CPU的浪费.

				Commit unsaved changes, but only if users expect such changes to be permanently saved when they leave (such as a draft email).
				//提交在用户离开时期待保存的内容(例如邮件草稿).

				Release system resources, such as broadcast receivers, handles to sensors (like GPS), or any resources that may affect battery life while your activity is paused and the user does not need them.
				//释放系统资源，例如broadcast receivers, sensors (比如GPS), 或者是其他任何会影响到电量的资源。

				//如果程序使用Camera,onPause()会是一个比较好的地方去做那些释放资源的操作。
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

				//Note:应该把那些heavy-load的工作放到onStop()去做

			Resume Your Activity
				
				@Override
				public void onResume() {
				    super.onResume();  // Always call the superclass method first

				    // Get the Camera instance as the activity achieves full user focus
				    if (mCamera == null) {
				        initializeCamera(); // Local method to handle camera init
				    }
				}

	-----------------------------------------------------------------------------------------------------------------------------------------------------
  
    Stopping and Restarting an Activity

    	The user opens the Recent Apps window and switches from your app to another app. The activity in your app that's' currently in the foreground is stopped. If the user returns to your app from the Home screen launcher icon or the Recent Apps window, the activity restarts.
    	//用户打开最近使用app的菜单并从我们的app切换到另外一个app，这个时候我们的app是被停止的。如果用户通过手机主界面的启动程序图标或者最近使用程序的窗口回到我们的app，那么我们的activity会重启。
    	//用户打开最近使用app的菜单并从我们的app切换到另外一个app，这个时候我们的app是被停止的。不执行生命周期


    	Stop Your Activity

    	//在极端情况下,系统不会回调onDestroy()方法而直接杀死应用进程,在onStop()方法中释放会导致内存泄露的资源是非常重要的。

    	should use onStop() to perform larger, more CPU intensive shut-down operations, such as writing information to a database.

    	Start/Restart Your Activity

    	// 在onStart()方法中做一些回复工作.(即使avtivity的实例被销毁了,onRestart()做不到)  
    	// onRestart()这个方法如何使用没有任何的guidelines
    	// 跟onStop()对应
    	//一般来说onStop()就释放了大部分的资源,OnDestroy()是清除可能会导致内存泄露的最后一次机会.( be sure that additional threads are destroyed and other long-running actions like method tracing are also stopped.)

	-----------------------------------------------------------------------------------------------------------------------------------------------------
    	
    Recreating an Activity

    	// 每个view最好加上id 尤其是能编辑的内容 便于毁容 给用户更好的体验
    	In order for the Android system to restore the state of the views in your activity, each view must have a unique ID, supplied by the android:id attribute.

    	//通常来说，跳转到其他的activity或者是点击Home都会导致当前的activity执行onSaveInstanceState，因为这种情况下的activity都是有可能会被destory并且是需要保存状态以便后续恢复使用的，而从跳转的activity点击back回到前一个activity，那么跳转前的activity是执行退栈的操作，所以这种情况下是不会执行onSaveInstanceState的，因为这个activity不可能存在需要重建的操作

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


		//当Activity从Destory中重建，我们可以从系统传递的Activity的Bundle中恢复保存的状态。 onCreate() 与 onRestoreInstanceState() 回调方法都接收到了同样的Bundle，里面包含了同样的实例状态信息。

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


		// 在onStart()方法之后执行,系统仅仅会在存在需要恢复的状态信息时才会调用 onRestoreInstanceState() ，因此不需要检查 Bundle 是否为null。
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy 
		    super.onRestoreInstanceState(savedInstanceState);
		    
		    // Restore state members from saved instance 
		    mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
		    mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
		} 