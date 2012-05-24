package sg.edu.nus.iss.yunakti.model;

import java.util.List;

public class YParentModel {
	
private String parentName;

private boolean isPackage;

public String getParentName() {
	return parentName;
}

public void setParentName(String parentName) {
	this.parentName = parentName;
}

public boolean isPackage() {
	return isPackage;
}

public void setPackage(boolean isPackage) {
	this.isPackage = isPackage;
}

public List<YModel> getClassList() {
	return classList;
}

public void setClassList(List<YModel> classList) {
	this.classList = classList;
}

private List<YModel> classList;

}
