package cc.nexdoor.android.module;

import android.graphics.Bitmap;

public class News {
	/* NewsªºÅÜ¼Æ */
	private String _title = "";
	private String _link = "";
	private String _desc = "";
	private String _date = "";
	private String _enclosureURL = "";
	private String _enclosureTYPE = "";
	private Bitmap _bitmap = null;
	private String _thumb = "";

	public String getTitle() {
		return _title;
	}

	public String getLink() {
		return _link;
	}

	public String getDesc() {
		return _desc;
	}

	public String getDate() {
		return _date;
	}
	
	public String getEnclosureURL() {
		return _enclosureURL;
	}
	
	public String getEnclosureTYPE() {
		return _enclosureTYPE;
	}
	
	public Bitmap getBitmap() {
		return _bitmap;
	}
	
	public String getThumb() {
		return _thumb;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setLink(String link) {
		_link = link;
	}

	public void setDesc(String desc) {
		_desc = desc;
	}

	public void setDate(String date) {
		_date = date;
	}
	
	public void setEnclosureURL(String enclosureURL) {
		_enclosureURL = enclosureURL;
	}
	
	public void setEnclosureTYPE(String enclosureTYPE) {
		_enclosureTYPE = enclosureTYPE;
	}
	
	public void setBitmap(Bitmap bitmap) {
		_bitmap = bitmap;
	}
	
	public void setThumb(String thumb) {
		_thumb = thumb;
	}
}
