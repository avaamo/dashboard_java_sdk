import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.avaamo.dashboard.AvaamoDashBoard;
import com.avaamo.dashboard.group.Admin;
import com.avaamo.dashboard.group.Group;
import com.avaamo.dashboard.group.GroupListResponse;
import com.avaamo.dashboard.group.GroupResponse;
import com.avaamo.dashboard.group.NewGroup;
import com.avaamo.dashboard.user.User;

public class SampleGroup {

	public static void main(String[] args) {
		AvaamoDashBoard avaamoDashboard = AvaamoDashBoard.create(SampleBroadcast.DASHBOARD_TOKEN);

		NewGroup newgroup = new NewGroup();
		newgroup.setName("Group Name");
		newgroup.setDescription("Group Description");
		newgroup.addUser(User.findUserByEmail("<email of the user>").getAvaamoId());//User to be added to the group

		Admin admin = new Admin(User.findUserByEmail("<email of the admin>").getAvaamoId());
		newgroup.setAdmin(admin);

		try {
			GroupResponse response = avaamoDashboard.getGroupManager().createGroup(newgroup);
			//System.out.println("Group id: "+response.getId());//852

			//Add member to group
			ArrayList<Integer> user_ids = new ArrayList<>();
			User user1 = User.findUserByEmail("<email of the user>");
			user_ids.add(user1.getId());
			avaamoDashboard.getGroupManager().addMemberToGroup(852, user_ids);

			avaamoDashboard.getGroupManager().removeMemberFromGroup(852, 35545);

			avaamoDashboard.getGroupManager().changeAdmin(852, 35676);

			Group group = new Group(853);
			group.setDescription("Descr");
			group.setAvatarImage(new File("test_image.jpg"));

			GroupResponse groupResponse = avaamoDashboard.getGroupManager().updateGroup(group);
			System.out.println("Updated Group id: "+groupResponse.getId());//852

			//avaamoDashboard.getGroupManager().getGroup(852);	

			GroupListResponse listResponse= new GroupListResponse();
			do{	
				ArrayList<Group> users = listResponse.getEntries();
			}while(listResponse.hasEntries());


		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
