package application.db;


public class DBException extends Exception{
	private static final long serialVersionUID = 1L;
	private String header;
	
	public DBException(String message) {
		super(message);
		this.setHeader(header);
	}
	
	public DBException(String message, String header) {
		super(message);
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
