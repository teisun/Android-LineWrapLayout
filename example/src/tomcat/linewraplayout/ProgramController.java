package tomcat.linewraplayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ProgramController {

	private static ProgramController instance;
	private Context mContext;

	private ProgramController(Context context) {
		mContext = context;
	}

	public static ProgramController getInstance(Context context) {
		if (instance == null) {
			instance = new ProgramController(context);
		}
		return instance;
	}

	public List<Player> mockData() {
		List<Player> result = mockList();
		return result;
	}

	public List<Player> mockList() {
		List<Player> playerList = new ArrayList<Player>();
		for( int j=0;j<18;j++ ){
			Player player = new Player();
			if( j%2==0 ){
				player.uId = "1342";
				player.name = "小泽玛利亚";
				player.phone = "18600976166";
				player.playerNum = 3;
				player.picUrl = "http://lifeixphoto.qiniudn.com/00/1363842946673_z1wts1.jpg!bigger";
			}else{
				player.uId = "1343";
				player.name = "苍老师";
				player.phone = "18600976167";
				player.playerNum = 2;
				player.picUrl = "http://www.sj88.com/attachments/201412/21/14/4939o2rti.jpg";
			}
			playerList.add(player);
		
		}
		return playerList;
	}

	

}
