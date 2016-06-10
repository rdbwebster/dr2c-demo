package demoapp.model;

public class VMInfo {

	
	  String status;
	  String name;
	  String owner;
	  String cpu;
	  String memory;
	  String os;
	  String recoveryStatus;
	  String recoveryTime;
	  String vApp;
	  String href;
	  ReferenceType taskLinkRef = null;   // task may not exist
	  String powerOnHref = null;   //  may not exist
	  String powerOffHref = null;   //  may not exist
	  String suspendHref = null;   //  may not exist



	public String getSuspendHref() {
		return suspendHref;
	}


	public void setSuspendHref(String suspendOffHref) {
		this.suspendHref = suspendOffHref;
	}


	public String getPowerOnHref() {
		return powerOnHref;
	}


	public void setPowerOnHref(String powerOnHRef) {
		this.powerOnHref = powerOnHRef;
	}


	public String getPowerOffhref() {
		return powerOffHref;
	}


	public void setPowerOffHref(String powerOffHref) {
		this.powerOffHref = powerOffHref;
	}


	public VMInfo() {
		status = "";
		name = "";
		owner = "";
		cpu = "";
		memory = "";
		os = "";
		recoveryStatus = "";
		recoveryTime = "";
		vApp = "";
		href="";
	}
	  
	  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((os == null) ? 0 : os.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((recoveryStatus == null) ? 0 : recoveryStatus.hashCode());
		result = prime * result + ((recoveryTime == null) ? 0 : recoveryTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vApp == null) ? 0 : vApp.hashCode());
		return result;
	}
	public VMInfo(String name) {
		super();
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VMInfo other = (VMInfo) obj;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (memory == null) {
			if (other.memory != null)
				return false;
		} else if (!memory.equals(other.memory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (os == null) {
			if (other.os != null)
				return false;
		} else if (!os.equals(other.os))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (recoveryStatus == null) {
			if (other.recoveryStatus != null)
				return false;
		} else if (!recoveryStatus.equals(other.recoveryStatus))
			return false;
		if (recoveryTime == null) {
			if (other.recoveryTime != null)
				return false;
		} else if (!recoveryTime.equals(other.recoveryTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (vApp == null) {
			if (other.vApp != null)
				return false;
		} else if (!vApp.equals(other.vApp))
			return false;
		return true;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getRecoveryStatus() {
		return recoveryStatus;
	}
	public void setRecoveryStatus(String recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}
	public String getRecoveryTime() {
		return recoveryTime;
	}
	public void setRecoveryTime(String recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	public String getvApp() {
		return vApp;
	}
	public void setvApp(String vApp) {
		this.vApp = vApp;
	}
	  public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}





	@Override
	public String toString() {
		return "VMInfo [status=" + status + ", name=" + name + ", owner=" + owner + ", cpu=" + cpu + ", memory="
				+ memory + ", os=" + os + ", recoveryStatus=" + recoveryStatus + ", recoveryTime=" + recoveryTime
				+ ", vApp=" + vApp + ", href=" + href + ", taskLinkRef=" + taskLinkRef + ", powerOnHref=" + powerOnHref
				+ ", powerOffHref=" + powerOffHref + ", suspendOffHref=" + suspendHref + "]";
	}


	public ReferenceType getTaskLinkRef() {
		return taskLinkRef;
	}


	public void setTaskLinkRef(ReferenceType taskLinkRef) {
		this.taskLinkRef = taskLinkRef;
	}





	  
}
