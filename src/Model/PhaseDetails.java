package Model;

import java.util.ArrayList;
import java.util.List;

public class PhaseDetails {
	private int lg;
	private int bzm;
	private Boolean tg;
	private String ezType;
	private List<Integer> ez = new ArrayList<>();

	public int getLg() {
		return lg;
	}

	public void setLg(int lg) {
		this.lg = lg;
	}

	public int getBzm() {
		return bzm;
	}

	public void setBzm(int bzm) {
		this.bzm = bzm;
	}

	public List<Integer> getEz() {
		return ez;
	}

	public void setEz(List<Integer> ez) {
		this.ez = ez;
	}
	
	public void addToEz(int ez) {
		this.ez.add(ez);
	}

	public Boolean getTg() {
		return tg;
	}

	public void setTg(Boolean tg) {
		this.tg = tg;
	}

	public String getEzType() {
		return ezType;
	}

	public void setEzType(String ezType) {
		this.ezType = ezType;
	}
}