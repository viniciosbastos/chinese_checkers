package ifce.ppd.bindings;

import javafx.beans.binding.StringBinding;

public class ObservableStringBufferBiding extends StringBinding{
	
	private final StringBuffer buffer = new StringBuffer();

	@Override
	protected String computeValue() {
		return this.buffer.toString();	
	}
	
	public void append(String text) {
		this.buffer.append(text);
		this.buffer.append("\n");
		invalidate();
	}

}
