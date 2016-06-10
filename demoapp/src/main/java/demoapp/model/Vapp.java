package demoapp.model;

import java.util.ArrayList;
import java.util.List;

public class Vapp {


	  	  
    public Vapp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
    
	public Vapp(String name, String href) {
		super();
		this.name = name;
		this.href = href;
	}
	
	@Override
	public String toString() {
		return "Vapp [name=" + name + ", href=" + href + ", owner=" + owner + ", vms=" + vms + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((vms == null) ? 0 : vms.hashCode());
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
		Vapp other = (Vapp) obj;
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
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (vms == null) {
			if (other.vms != null)
				return false;
		} else if (!vms.equals(other.vms))
			return false;
		return true;
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
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public List<ReferenceType> getVms() {
		return vms;
	}
	
	public void setVms(List<ReferenceType> vms) {
		this.vms = vms;
	}
	
	

	  	
	  	
	  	public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDeployed() {
			return deployed;
		}

		public void setDeployed(String deployed) {
			this.deployed = deployed;
		}

		
		String name;
	  	String href;
	  	String owner;
	  	String status;
	  	String deployed;
		List<ReferenceType> vms = new ArrayList<ReferenceType>();
	  	  
}
