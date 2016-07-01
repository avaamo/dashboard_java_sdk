import com.avaamo.dashboard.AvaamoDashBoard;
import com.avaamo.dashboard.broadcast.Sender;
import com.avaamo.dashboard.broadcast.card.BroadcastCardResponse;
import com.avaamo.dashboard.broadcast.card.CustomCard;
import com.avaamo.dashboard.broadcast.question.Poll;
import com.avaamo.dashboard.broadcast.user.BroadcastList;
import com.avaamo.dashboard.broadcast.user.BroadcastListUtil;
import com.avaamo.dashboard.user.User;

public class SamplePoll {
	public static void main(String[] args) throws Exception{
		AvaamoDashBoard avaamoDashboard = AvaamoDashBoard.create("DashboardSample.DASHBOARD_TOKEN");
		
		CustomCard customCard = new CustomCard();
		customCard.setHeadline("Simple Poll");
		customCard.setSender(new Sender(1196,"Dashboard SDK Sender", null));

		// Add a poll
		Poll poll = new Poll(" What is your preference?");
		poll.addOption(0, "Yes");
		poll.addOption(1, "No");
		poll.addOption(2, "May Be");
		customCard.addQuestion(poll);

		
		BroadcastList bl = new BroadcastList("Simple User LIst");
		bl.addUser(User.findUserByEmail("madhav@avaamo.com"));
		
		// set target users
		customCard.addTargetUsersList(BroadcastListUtil.createBroadcastList(bl).getId());
		
		// set poll expiration - in hours
		customCard.setExpiresIn(2);
		
		BroadcastCardResponse res = avaamoDashboard.sendBroadcast(customCard);
		System.out.print("Broadcast card id: "+ res.getId()); // save/use this id to get the replies later on.
		
	}

}
