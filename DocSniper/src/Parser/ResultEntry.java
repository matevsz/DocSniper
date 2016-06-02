package Parser;

public class ResultEntry {
	private final String text;
	private final String file;
	private final int page;
	
	ResultEntry(String text, String file, int page) {
		this.text = text;
		this.file = file;
		this.page = page;
	}
	
	public String getText() {
		return text;
	}

	public String getFile() {
		return file;
	}

	public int getPage() {
		return page;
	}

	@Override
	public String toString() {
		return file+":"+page+":\n"+text;
	}
}
