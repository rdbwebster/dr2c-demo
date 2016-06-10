package demoapp.model;

import java.util.ArrayList;
import java.util.List;

public class Vdc {

	
	public Vdc() {
		super();
		name = "";
		href = "";
		description = "";
		vappRefs = new ArrayList<ReferenceType>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	String name;
	String href;
	String description;
	
	String cpuAllocated;
	String cpuUsed;
	String cpuUnits;
	
	String memoryAllocated;
	String memoryUsed;
	String memoryUnits;
	
	public String getCpuAllocated() {
		return cpuAllocated;
	}

	public void setCpuAllocated(String cpuAllocated) {
		this.cpuAllocated = cpuAllocated;
	}

	public String getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public String getCpuUnits() {
		return cpuUnits;
	}

	public void setCpuUnits(String cpuUnits) {
		this.cpuUnits = cpuUnits;
	}

	public String getMemoryAllocated() {
		return memoryAllocated;
	}

	public void setMemoryAllocated(String memoryAllocated) {
		this.memoryAllocated = memoryAllocated;
	}

	public String getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(String memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public String getMemoryUnits() {
		return memoryUnits;
	}

	public void setMemoryUnits(String memoryUnits) {
		this.memoryUnits = memoryUnits;
	}

	List<ReferenceType> vappRefs;
	
	public List<ReferenceType> getVappRefs() {
		return vappRefs;
	}

	public void setVappRefs(List<ReferenceType> vappRefs) {
		this.vappRefs = vappRefs;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	


	@Override
	public String toString() {
		return "Vdc [name=" + name + ", href=" + href + ", description=" + description + ", cpuAllocated="
				+ cpuAllocated + ", cpuUsed=" + cpuUsed + ", cpuUnits=" + cpuUnits + ", memoryAllocated="
				+ memoryAllocated + ", memoryUsed=" + memoryUsed + ", memoryUnits=" + memoryUnits + ", vappRefs="
				+ vappRefs + "]";
	}

	public Vdc(String name, String href) {
		super();
		this.name = name;
		this.href = href;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Vdc other = (Vdc) obj;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
