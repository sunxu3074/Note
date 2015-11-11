


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

	--------------------------------------------------------------------------------------------------------------------------------------

		Communicating with Other Fragments

		//发送广播
		//构建一个interface
		//定义一个接口 fragment->activity

		public class HeadlinesFragment extends ListFragment {
		    OnHeadlineSelectedListener mCallback;

		    // Container Activity must implement this interface
		    public interface OnHeadlineSelectedListener {
		        public void onArticleSelected(int position);
		    }

		    @Override
		    public void onAttach(Activity activity) {
		        super.onAttach(activity);

		        // This makes sure that the container activity has implemented
		        // the callback interface. If not, it throws an exception
		        try {
		            mCallback = (OnHeadlineSelectedListener) activity;
		        } catch (ClassCastException e) {
		            throw new ClassCastException(activity.toString()
		                    + " must implement OnHeadlineSelectedListener");
		        }
		    }

		    ...
		}

		@Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
	        // Send the event to the host activity
	        mCallback.onArticleSelected(position);
	    }

	    //实现接口
	    public static class MainActivity extends Activity
		        implements HeadlinesFragment.OnHeadlineSelectedListener{
		    ...

		    public void onArticleSelected(int position) {
		        // The user selected the headline of an article from the HeadlinesFragment
		        // Do something here to display that article
		    }
		}

		//activity->fragment

		public static class MainActivity extends Activity
		        implements HeadlinesFragment.OnHeadlineSelectedListener{
		    ...

		    public void onArticleSelected(int position) {
		        // The user selected the headline of an article from the HeadlinesFragment
		        // Do something here to display that article

		        ArticleFragment articleFrag = (ArticleFragment)
		                getSupportFragmentManager().findFragmentById(R.id.article_fragment);
		                //宿主activity通过findFragmentById()方法获取fragment的实例，然后直接调用Fragment的public方法来向fragment传递消息。

		        if (articleFrag != null) {
		            // If article frag is available, we're in two-pane layout...

		            // Call a method in the ArticleFragment to update its content
		            articleFrag.updateArticleView(position);
		        } else {
		            // Otherwise, we're in the one-pane layout and must swap frags...

		            // Create fragment and give it an argument for the selected article
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
		        }
		    }
		}

		//待续 http://developer.android.com/training/basics/fragments/communicating.html