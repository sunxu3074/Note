

	
		Managing Audio Playback

			//PS:Quora is a knowledge-sharing community that depends on everyone being able to pitch in when they know something.

			Identify Which Audio Stream to Use

				
				Android maintains a separate audio stream for playing music, alarms, notifications, the incoming call ringer, system sounds, in-call volume, and DTMF tones.

				Most of these streams are restricted to system events, so unless your app is a replacement alarm clock, you’ll almost certainly be playing your audio using the STREAM_MUSIC stream.

			

			Use Hardware Volume Keys to Control Your App’s Audio Volume

				should typically call it within the onCreate() method (of the Activity or Fragment that controls your media).

				setVolumeControlStream(AudioManager.STREAM_MUSIC);//From this point onwards, pressing the volume keys on the device affect the audio stream you specify (in this case “music”) whenever the target activity or fragment is visible.


			Use Hardware Playback Control Keys to Control Your App’s Audio Playback

				Media playback buttons such as play, pause, stop, skip, and previous are available on some handsets and many connected or wireless headsets.Whenever a user presses one of these hardware keys, the system broadcasts an intent with the ACTION_MEDIA_BUTTON action.

				<receiver android:name=".RemoteControlReceiver">
				    <intent-filter>
				        <action android:name="android.intent.action.MEDIA_BUTTON" />
				    </intent-filter>
				</receiver>

				public class RemoteControlReceiver extends BroadcastReceiver {
				    @Override 
				    public void onReceive(Context context, Intent intent) {
				        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
				            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
				            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {//KeyEvent.KEYCODE_MEDIA_*
				                // Handle key press. 
				            } 
				        } 
				    } 
				} 


				AudioManager am = mContext.getSystemService(Context.AUDIO_SERVICE);
				... 
				 
				// Start listening for button presses 
				am.registerMediaButtonEventReceiver(RemoteControlReceiver);
				... 
				 
				// Stop listening for button presses 
				am.unregisterMediaButtonEventReceiver(RemoteControlReceiver);//Typically, apps should unregister most of their receivers whenever they become inactive or invisible (such as during the onStop() callback).  responding to media playback buttons is most important when your application isn’t visible and therefore can’t be controlled by the on-screen UI.
				//A better approach is to register and unregister the media button event receiver when your application gains and loses the audio focus. !!!
				// 在取得音频焦点时注册 失去焦点时注销



		Managing Audio Focus


				Request the Audio Focus

					AudioManager am = mContext.getSystemService(Context.AUDIO_SERVICE);
					... 
					 
					// Request audio focus for playback 
					int result = am.requestAudioFocus(afChangeListener,
					                                 // Use the music stream. 
					                                 AudioManager.STREAM_MUSIC,
					                                 // Request permanent focus. 
					                                 AudioManager.AUDIOFOCUS_GAIN);
					    
					if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
					    am.registerMediaButtonEventReceiver(RemoteControlReceiver);
					    // Start playback. 
					} 


					// Abandon audio focus when playback complete    
					am.abandonAudioFocus(afChangeListener);


					// Request audio focus for playback 
					int result = am.requestAudioFocus(afChangeListener,
					                             // Use the music stream. 
					                             AudioManager.STREAM_MUSIC,
					                             // Request permanent focus. 
					                             AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
					    
					if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
					    // Start playback. 
					} 

					