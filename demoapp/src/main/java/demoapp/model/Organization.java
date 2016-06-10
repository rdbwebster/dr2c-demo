package demoapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {

	
	String name = "";
	String href = "";
	String replHref = "";
	List<ReferenceType> vdcRefs  = new ArrayList<ReferenceType>();;
	
	public List<ReferenceType> getVdcRefs() {
		return vdcRefs;
	}

	public void setVdcRefs(List<ReferenceType> vdcRefs) {
		this.vdcRefs = vdcRefs;
	}

	public Organization() {
		super();
	
	}

	public String getReplHref() {
		return replHref;
	}

	public void setReplHref(String replHref) {
		this.replHref = replHref;
	}


	
	@Override
	public String toString() {
		return "Organization [name=" + name + ", href=" + href + ", replHref=" + replHref + ", vdcRefs=" + vdcRefs
				+ "]";
	}

	public Organization(String name, String orgHref) {
		super();
		this.name = name;
		this.href = orgHref;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		return true;
	}

	public String getHref() {
		return this.href;
	}
	public void setHref(String orgHref) {
		this.href = orgHref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
