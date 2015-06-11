package tomcat.linewraplayout;

import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MainActivity extends BaseActivity  {

	private LineWrapLayout playerContainer;
	private List<Player> playerList;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initView();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		playerList = mProgramController.mockList();
		playerContainer = (LineWrapLayout) this
				.findViewById(R.id.player_container);
		
		if (playerList != null
				&& playerList.size() > 0) {
			playerContainer.setVisibility(View.VISIBLE);
			for (int i = 0; i < playerList
					.size(); i++) {
				Player player = playerList
						.get(i);
				// 显示参与者头像
				ImageView photo = new ImageView(
						mContext);
				LayoutParams layoutParams = new LayoutParams(
						(int) getDimens(R.dimen.dp85),
						(int) getDimens(R.dimen.dp85));
				photo.setLayoutParams(layoutParams);
				if (!TextUtils.isEmpty(player.picUrl)) {
					loadIMG(photo, player.picUrl);
				} else {
					loadIMG(photo,
							R.drawable.ic_launcher);
				}
				playerContainer.addView(photo);
			}
		} 
	}


	

	
}
