package Model;

import java.util.ArrayList;

public class ZA {
	
	private int code;
	private String type;
	private String desc_bg;
	private String desc_eng;
	
	public ZA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZA(ArrayList<String> temp) {
		this.code = TryParseInt(temp.get(1));
		this.type = temp.get(2);
		this.desc_bg = temp.get(3);
		this.desc_eng = temp.get(4);
	}
	
	private int TryParseInt(String value) {
		try {
			double d = Double.parseDouble(value);
			return (int) d;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc_bg() {
		return desc_bg;
	}

	public void setDesc_bg(String desc_bg) {
		this.desc_bg = desc_bg;
	}

	public String getDesc_eng() {
		return desc_eng;
	}

	public void setDesc_eng(String desc_eng) {
		this.desc_eng = desc_eng;
	}
	
	
}
