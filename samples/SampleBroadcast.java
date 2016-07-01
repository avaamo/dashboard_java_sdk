import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.avaamo.dashboard.AvaamoDashBoard;
import com.avaamo.dashboard.broadcast.BroadcastAttachment;
import com.avaamo.dashboard.broadcast.BroadcastAttachment.Type;
import com.avaamo.dashboard.broadcast.AllBroadcastCardsResponse;
import com.avaamo.dashboard.broadcast.CompanyCardManager;
import com.avaamo.dashboard.broadcast.Reply;
import com.avaamo.dashboard.broadcast.Sender;
import com.avaamo.dashboard.broadcast.card.AnnouncementCard;
import com.avaamo.dashboard.broadcast.card.BroadcastCardResponse;
import com.avaamo.dashboard.broadcast.card.CustomCard;
import com.avaamo.dashboard.broadcast.question.Aggregate;
import com.avaamo.dashboard.broadcast.question.Approval;
import com.avaamo.dashboard.broadcast.question.Checklist;
import com.avaamo.dashboard.broadcast.question.DataCapture;
import com.avaamo.dashboard.broadcast.question.FileLabel;
import com.avaamo.dashboard.broadcast.question.LabelField;
import com.avaamo.dashboard.broadcast.question.Poll;
import com.avaamo.dashboard.broadcast.question.Question;
import com.avaamo.dashboard.broadcast.question.Rating;
import com.avaamo.dashboard.broadcast.question.Signature;
import com.avaamo.dashboard.broadcast.question.SingleLineText;
import com.avaamo.dashboard.broadcast.reply.BroadcastReplyElement;
import com.avaamo.dashboard.broadcast.reply.BroadcastReplyManager;
import com.avaamo.dashboard.broadcast.user.BroadcastAudienceResponse;
import com.avaamo.dashboard.broadcast.user.BroadcastList;
import com.avaamo.dashboard.broadcast.user.BroadcastListModel;
import com.avaamo.dashboard.broadcast.user.BroadcastListUtil;
import com.avaamo.dashboard.user.User;

public class SampleBroadcast {

	static final String DASHBOARD_TOKEN = "<Replace this with dashboard token>";
	
	public static void main(String[] args) {

		try {
			AvaamoDashBoard avaamoDashboard = AvaamoDashBoard.create(DASHBOARD_TOKEN);
			SampleBroadcast testApp = new SampleBroadcast();

			testApp.createAndSendAnnouncementCard(avaamoDashboard);

			testApp.createAndSendCustomCard(avaamoDashboard);

			int poll_id = testApp.createAndSendPollCard(avaamoDashboard);
			testApp.fetchPollResponse(poll_id);

			//testApp.fetchBroadcastReply();
			
			BroadcastAudienceResponse audienceResponse = new BroadcastAudienceResponse(4260);
			do{	
				ArrayList<User> audiences = audienceResponse.getEntries();
			}while(audienceResponse.hasEntries());
			
			
			AllBroadcastCardsResponse allCardsResponse = new AllBroadcastCardsResponse(75);
			do{	
				ArrayList<BroadcastCardResponse> broadcast_cards = allCardsResponse.getEntries();
			}while(allCardsResponse.hasEntries());
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

	}

	public void createAndSendAnnouncementCard(AvaamoDashBoard avaamoDashboard) throws Exception {

		AnnouncementCard ac = new AnnouncementCard();

		// set header line
		ac.setHeadline("Annoucement Heading: Check this out@@@");
		ac.setShowcaseImage(new File("test_image.jpg"));
		ac.setBody("This is the test body. It can be minimal rich text too. " + "Like <b>Bold</b> <i>Italic</i>.");

		// set sender name and avatar
		ac.setSender(new Sender(1, "Sender Name", null));

		//Received User can reply to these users
		ac.addReplyTo(35545);//Passing user ids now.
		ac.addReplyTo(36284);

		//Add attachemnts like simple link, video link and files 
		ArrayList<BroadcastAttachment> attachments = new ArrayList<>();
		attachments.add(new BroadcastAttachment(Type.LINK,"Attachment 1","https://www.google.com"));
		ac.setAttachments(attachments);

		ac.addTargetUsersList(createSampleBroadcastList().getId());

		// send the broadcast
		avaamoDashboard.sendBroadcast(ac);
	}

	public void createAndSendCustomCard(AvaamoDashBoard avaamoDashboard) {
		CustomCard customCard = new CustomCard();

		customCard.setHeadline("MultiField Card");
		customCard.setSender(new Sender(1196,"SDK Sender", null));

		// Add Questions
		customCard.addQuestion(new Signature("Sign here for sdk"));

		// Add a poll
		Poll poll = new Poll("Is sdk usefull for you?");
		poll.addOption(0, "Yes");
		poll.addOption(0, "No");
		customCard.addQuestion(poll);

		// Add a rating question
		Rating rating = new Rating("Rate this sdk");
		rating.setStarCount(5);
		rating.setStartCount(2);// These values don't reflect
		customCard.addQuestion(rating);


		// single line text field
		customCard.addQuestion(new SingleLineText("Single line text field"));

		// multiline text field
		customCard.addQuestion(new DataCapture("Data capture field"));

		// Display only label. This can take minimal HTML as well
		customCard.addQuestion(new LabelField("Label  field"));

		// yes or no question
		customCard.addQuestion(new Approval("Yes/No  field"));

		// Number field
		customCard.addQuestion(new Aggregate("Aggregate  field"));

		// To Do items field
		Checklist checklist = new Checklist("CheckList  field");
		checklist.addOption(0, "Option 1");
		checklist.addOption(0, "Option 2");
		customCard.addQuestion(checklist);

		//File attachment field
		File file = new File("test_image.jpg");
		customCard.addQuestion(new FileLabel("File sending..", file));

		//Set expiry in hours(Without expiry also servers accepts the card..should we allow that?)
		customCard.setExpiresIn(2);

		customCard.addTargetUsersList(245);//Target user list id. If you don't have one create using "createSampleBroadcastList()"
		try {
			avaamoDashboard.sendBroadcast(customCard);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public int createAndSendPollCard(AvaamoDashBoard avaamoDashboard) throws Exception{

		CustomCard customCard = new CustomCard();
		customCard.setHeadline("Only Poll Card");
		customCard.setSender(new Sender(1196,"SDK Sender", null));

		// Add a poll
		Poll poll = new Poll("I am polling for your response?");
		poll.addOption(0, "ok");
		poll.addOption(0, "okayyyyy");

		customCard.addQuestion(poll);

		int res = CompanyCardManager.sendCard(avaamoDashboard, customCard,"<Enter company e-mail contact>");//ex: madhav@avaamo.com

		return res;
	}

	public int createAndSendDataCaptureCard(AvaamoDashBoard avaamoDashboard) throws Exception{

		CustomCard customCard = new CustomCard();
		customCard.setHeadline("Only Data capture Card");
		customCard.setSender(new Sender(1196,"SDK Sender", null));//1196 is sender id. To create a new sender pass 0


		// multiline text field
		customCard.addQuestion(new DataCapture("Data capture field"));

		int res  = CompanyCardManager.sendCard(avaamoDashboard, customCard,"<Enter company e-mail contact>");//ex: madhav@avaamo.com
		return res;
	}

	public BroadcastListModel createSampleBroadcastList() throws Exception{
		// Every time creates new list
		BroadcastList broadcastListRequest = new BroadcastList("Simple User LIst");
		User user2 = User.findUserByEmail("<email>");
		broadcastListRequest.addUser(user2);

		//broadcastListRequest.addUser(User.findUserByEmail("<add email>"));

		BroadcastListModel broadcastListResponse = BroadcastListUtil.createBroadcastList(broadcastListRequest);
		return broadcastListResponse;
	}

	public void fetchBroadcastReply() throws IOException{

		BroadcastReplyManager replyManager = new BroadcastReplyManager(4200);//Broadcast Id

		ArrayList<Question> questions = replyManager.getBroadcastReply().getQuestions();
		for (Question question : questions) {
			System.out.print(" "+question);
		}
		System.out.println();

		ArrayList<BroadcastReplyElement> replyElements = replyManager.getBroadcastReply().getResponses();
		for (BroadcastReplyElement broadcastReplyElement : replyElements) {
			System.out.println("----------------");
			System.out.println(""+broadcastReplyElement.getUser());
			ArrayList<Reply> replies  = broadcastReplyElement.getReplies();
			for(Reply reply:replies){
				System.out.print(" "+reply);
			}
			System.out.println();
			System.out.println("----------------");
		}
	}

	public void fetchPollResponse(int id) throws IOException{
		BroadcastReplyManager replyManager = new BroadcastReplyManager(id);//Broadcast Id

		ArrayList<Question> questions = replyManager.getBroadcastReply().getQuestions();
		for (Question question : questions) {
			System.out.print(" "+question);
		}
		System.out.println();

		ArrayList<BroadcastReplyElement> replyElements = replyManager.getBroadcastReply().getResponses();
		for (BroadcastReplyElement broadcastReplyElement : replyElements) {
			System.out.println("----------------");
			System.out.println(""+broadcastReplyElement.getUser());
			ArrayList<Reply> replies  = broadcastReplyElement.getReplies();
			for(Reply reply:replies){
				System.out.print(" "+reply);
			}
			System.out.println();
			System.out.println("----------------");
		}
	}
}
