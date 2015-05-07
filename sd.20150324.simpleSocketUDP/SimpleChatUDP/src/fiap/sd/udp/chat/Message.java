package fiap.sd.udp.chat;

public class Message {
	private String destinationIp;
	private Commands command;
	private String message;
	
	public Message()
	{		
	}

	public Message(String destinationIp, Commands command, String message)
	{
		this.destinationIp = destinationIp;
		this.command = command;
		this.message = message;
	}

	public String getDestinationIp() {
		return destinationIp;
	}

	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}

	public Commands getCommand() {
		return command;
	}

	public void setCommand(Commands command) {
		this.command = command;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}		
}
