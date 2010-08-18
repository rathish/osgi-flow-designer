package com.yobny.opensource.osgi.karafview.bean;

public class ListCmdPojo {
	
	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleState() {
		return bundleState;
	}

	public void setBundleState(String bundleState) {
		this.bundleState = bundleState;
	}

	public String getBundleBluePrint() {
		return bundleBluePrint;
	}

	public void setBundleBluePrint(String bundleBluePrint) {
		this.bundleBluePrint = bundleBluePrint;
	}

	public String getBundleLevel() {
		return bundleLevel;
	}

	public void setBundleLevel(String bundleLevel) {
		this.bundleLevel = bundleLevel;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
	
	public String getBundleVersion() {
		return bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}
	
	private String bundleVersion;
	
	private String bundleId;
	
	private String bundleState;
	
	private String bundleBluePrint;
	
	private String bundleLevel;
	
	private String bundleName;
	
	
}
