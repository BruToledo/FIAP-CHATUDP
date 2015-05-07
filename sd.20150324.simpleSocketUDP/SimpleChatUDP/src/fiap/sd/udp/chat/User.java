package fiap.sd.udp.chat;

public class User {
	private String name;
	private String userIp;
	private boolean isRegistred = false;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public boolean isRegistred() {
		return isRegistred;
	}

	public void setRegistred(boolean isRegistred) {
		this.isRegistred = isRegistred;
	}

	@Override
	public boolean equals(Object obj) {
		User comp = (User) obj;
		return userIp.equals(comp.userIp);
	}
}
