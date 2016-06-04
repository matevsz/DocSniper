package Parser;

import Main.CDocument;

public class ResultEntry {
	private final String text;
	private final CDocument file;
	private final int page;
	
	ResultEntry(String text, CDocument file, int page) {
		this.text = text;
		this.file = file;
		this.page = page;
	}
	
	public String getText() {
		return text;
	}

	public CDocument getFile() {
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
