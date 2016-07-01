import java.io.IOException;
import java.util.ArrayList;

import com.avaamo.dashboard.AvaamoDashBoard;
import com.avaamo.dashboard.user.InviteUserTemplate;
import com.avaamo.dashboard.user.NewUser;
import com.avaamo.dashboard.user.User;
import com.avaamo.dashboard.user.UserListResponse;

public class SampleUser {

	public static void main(String[] args) {

		AvaamoDashBoard avaamoDashboard = AvaamoDashBoard.create(SampleBroadcast.DASHBOARD_TOKEN);

		NewUser newuser = new NewUser();
		newuser.setEmail("<email of the user>");
		newuser.setPhoneNo("<phone no>");
		newuser.setFirstName("SDK");
		newuser.setLastName("user9");
		try {
			int user_id = avaamoDashboard.getUserManager().createUser(newuser);
			
			User user = avaamoDashboard.getUserManager().getUser(user_id);
			
			//User user = new User(user_id);
			user.setFirstName("SDK first name");
			
			User updatedUser = avaamoDashboard.getUserManager().updateUser(user);
			System.out.println("updated user phone: "+updatedUser.getPhone());
			
			InviteUserTemplate template = new InviteUserTemplate();
			template.setFrom_name("Benn");
			template.setSms_body("sms body goes here");
			template.setSubject("Invite from sdk dashboard");
			template.setTemplate("Template of the invite message");
			avaamoDashboard.getUserManager().inviteUser(user_id, template);
			
			UserListResponse response= new UserListResponse();
			do{	
				ArrayList<User> users = response.getEntries();
			}while(response.hasEntries());
			
			
			avaamoDashboard.getUserManager().deleteUser(user_id);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

}
