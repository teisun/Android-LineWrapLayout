package tomcat.linewraplayout;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public abstract class BaseActivity extends Activity implements OnClickListener {

	ThreadPoolManager mThreadPoolManager;


	protected Context mContext;

	protected ProgressDialog progress;


	protected final String INFO_NAME = "info";

	protected ProgramController mProgramController;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mThreadPoolManager = ThreadPoolManager.getInstance();
		mContext = this;
		progress = getProgressDialog("正在加载,请稍后...");
		progress.setCancelable(true);

		mProgramController = ProgramController.getInstance(mContext
				.getApplicationContext());

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public ProgressDialog getProgressDialog(String msg) {
		ProgressDialog progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	public void showToast(final String msg) {
		runOnUI(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

	/**
	 * UI线程执行一个任务
	 * 
	 * @param run
	 */
	public void runOnUI(Runnable run) {
		runOnUiThread(run);
	}


	/**
	 * 
	 * @param run
	 */
	public void executeTask(Runnable run) {
		mThreadPoolManager.executeTask(run);
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param context
	 * @param achor
	 */
	public  void hideSoftInput(Context context, View achor) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(achor.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void dismissProgressDialog() {
		runOnUI(new Runnable() {

			@Override
			public void run() {
				if (progress != null) {
					progress.dismiss();
				}

			}
		});

	}

	public void showProgressDialog() {
		runOnUI(new Runnable() {

			@Override
			public void run() {
				if (progress == null) {
					progress = new ProgressDialog(mContext);
					progress.setMessage("正在加载,请稍后...");
					progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				}
				progress.setCancelable(true);
				try {
					progress.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void onDestroy() {
		if (progress != null) {
			progress.dismiss();
		}
		super.onDestroy();
	}




	/**
	 * 加载图片
	 * 
	 * @param tuContainer
	 * @param item
	 */
	public void loadIMG(ImageView img, String url) {
		Picasso.with(mContext).load(url).error(R.drawable.ic_launcher).into(img);
	}
	
	public void loadIMG(ImageView img, String url, int errorId) {
		Picasso.with(this).load(url).error(errorId).into(img);
	}
	
	public void loadIMG(ImageView img, String url, int placeholder, int errorId) {
		Picasso.with(mContext).load(url).error(errorId)
				.placeholder(placeholder).into(img);
	}

	/**
	 * 加载图片
	 * 
	 * @param tuContainer
	 * @param item
	 */
	public void loadIMG(ImageView img, File file) {
		Picasso.with(mContext).load(file).error(R.drawable.ic_launcher).into(img);
	}

	/**
	 * 加载本地图片
	 * 
	 * @param tuContainer
	 * @param item
	 */
	public void loadIMG(ImageView img, int id) {
		Picasso.with(mContext).load(id).into(img);
	}

	/**
	 * 获得配置文件参数
	 * 
	 * @param id
	 * @return
	 */
	public float getDimens(int id) {
		return getResources().getDimension(id);
	}

	
	public Context getContext() {
		return this;
	}
	
	public OnClickListener getClickListener() {
		return this;
	} 
	
	@Override
	public void onClick(View v) {
		
	}
	

}
