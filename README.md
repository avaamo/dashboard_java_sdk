# dashboard_java_sdk

##### [Setup](#setup)
##### [Create a Group](#create)

#### Setup
Create a instance of AvaamoDashBoard
```java
AvaamoDashBoard avaamoDashboard = AvaamoDashBoard.create("<Provide your dashborad access token>");
```

#### Create a Group
```java
NewGroup newgroup = new NewGroup();
newgroup.setName("Group Name");
newgroup.setDescription("Group Description");
newgroup.addUser(User.findUserByEmail("<email of the user>").getId());//User to be added to the group
Admin admin = new Admin(User.findUserByEmail("<email of the admin>").getId());
newgroup.setAdmin(admin);
try {
GroupResponse response = avaamoDashboard.getGroupManager().createGroup(newgroup);
} catch (IOException e) {
e.printStackTrace();
}
```
#### Add member to group
```java
//Add member to group
ArrayList<Integer> user_ids = new ArrayList<>();
User user1 = User.findUserByEmail("<email of the user>");
user_ids.add(user1.getId());
avaamoDashboard.getGroupManager().addMemberToGroup(<group_id>, user_ids);
```
#### Remove member from group
```java
avaamoDashboard.getGroupManager().removeMemberFromGroup(<group_id>, <user_id>);
```

#### Change admin of the group
```java
avaamoDashboard.getGroupManager().changeAdmin(<group_id>, <user_id>);
```

#### Update group details
```java
Group group = new Group(853);
group.setDescription("Description”);
group.setAvatarImage(new File("test_image.jpg"));
GroupResponse groupResponse = avaamoDashboard.getGroupManager().updateGroup(group);
```

#### Get list of members in the group
```java
GroupListResponse listResponse= new GroupListResponse();
do{	
	ArrayList<Group> users = listResponse.getEntries();
}while(listResponse.hasEntries());
```

