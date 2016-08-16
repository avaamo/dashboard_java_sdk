import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.avaamo.dashboard.AvaamoDashBoard;
import com.avaamo.dashboard.group.Admin;
import com.avaamo.dashboard.group.Group;
import com.avaamo.dashboard.group.GroupListResponse;
import com.avaamo.dashboard.group.GroupMember;
import com.avaamo.dashboard.group.GroupMembersResponse;
import com.avaamo.dashboard.group.GroupMessagesResponse;
import com.avaamo.dashboard.group.GroupResponse;
import com.avaamo.dashboard.group.NewGroup;
import com.avaamo.dashboard.message.Message;
import com.avaamo.dashboard.message.Message.AttachedFile;
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
            
            
            GroupMembersResponse membersResponse= new GroupMembersResponse(888);
            do{
                ArrayList<GroupMember> group_members = membersResponse.getEntries();
            }while(membersResponse.hasEntries());
            
            
            AttachedFile file = null;
            GroupMessagesResponse messagesResponse= new GroupMessagesResponse(854);
            do{
                ArrayList<Message> group_messages = messagesResponse.getEntries();
                for (Iterator<Message> iterator = group_messages.iterator(); iterator.hasNext();) {
                    Message message = (Message) iterator.next();
                    System.out.println(""+message);
                    if(message.hasAttachments()){
                        List<AttachedFile> files = message.getAttachments().get(0).files;
                        for (ListIterator<AttachedFile> iterator2 = files.listIterator(); iterator2.hasNext();) {
                            file = (AttachedFile) iterator2.next();
                            System.out.println(""+file);
                        }
                    }
                }
            }while(messagesResponse.hasEntries());

            //Provide folder name to which the file should be downloaded
            boolean fileDownloaded = AvaamoDashBoard.getInstance().getAttachmentUtil().downloadAttachment(file, "downloads");
            
            boolean fileDownloaded2 = AvaamoDashBoard.getInstance().getAttachmentUtil().downloadAttachment("68488",
                                                                            "Temp_file.jpeg", "downloads");

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
