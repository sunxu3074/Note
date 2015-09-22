


		Creating a Fragment

		//如果你的应用只需要11以上 不需要导入v4包 直接导入android.app.Fragment即可(为了兼容性能可能会差一点点?)

		Add a Fragment to an Activity at Runtime


		import android.os.Bundle;
		import android.support.v4.app.FragmentActivity;
		 
		public class MainActivity extends FragmentActivity {
		    @Override 
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.news_articles);
		 
		        // Check that the activity is using the layout version with 
		        // the fragment_container FrameLayout 
		        if (findViewById(R.id.fragment_container) != null) {
		 
		            // However, if we're being restored from a previous state, 
		            // then we don't need to do anything and should return or else 
		            // we could end up with overlapping fragments. 
		            if (savedInstanceState != null) {
		                return; 
		            } 
		 
		            // Create a new Fragment to be placed in the activity layout 
		            HeadlinesFragment firstFragment = new HeadlinesFragment();
		             
		            // In case this activity was started with special instructions from an 
		            // Intent, pass the Intent's extras to the fragment as arguments 
		            firstFragment.setArguments(getIntent().getExtras());
		             
		            // Add the fragment to the 'fragment_container' FrameLayout 
		            getSupportFragmentManager().beginTransaction()
		                    .add(R.id.fragment_container, firstFragment).commit();
		        } 
		    } 
		} 


		Replace One Fragment with Another

		// Create fragment and give it an argument specifying the article it should show 
		ArticleFragment newFragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putInt(ArticleFragment.ARG_POSITION, position);
		newFragment.setArguments(args);
		 
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); 
		 
		// Replace whatever is in the fragment_container view with this fragment, 
		// and add the transaction to the back stack so the user can navigate back 
		transaction.replace(R.id.fragment_container, newFragment);
		transaction.addToBackStack(null); 
		 
		// Commit the transaction 
		transaction.commit(); 



		Communicating with Other Fragments