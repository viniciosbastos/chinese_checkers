package ifce.ppd.models;

import java.io.Serializable;

public class MessageCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7629091522213707953L;
	private String text;
	
	public MessageCommand(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	
}
