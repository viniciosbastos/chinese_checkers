package ifce.ppd.models;

public class Movement {

	private Cell from;
	
	private Cell to;

	public Movement(Cell from, Cell to) {
		this.from = from;
		this.to = to;
	}

	public Cell getFrom() {
		return from;
	}

	public void setFrom(Cell from) {
		this.from = from;
	}

	public Cell getTo() {
		return to;
	}

	public void setTo(Cell to) {
		this.to = to;
	}
	
	
}
