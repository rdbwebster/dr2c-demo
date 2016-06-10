package demoapp.model;

public class Task {

	String name="";
	String href="";
	String status="";
	String operation="";
	String operationName="";
	String startTime="";
	String expiryTime="";
	String ownerName="";
	String ownerHref="";
	String userName="";
	String userHref="";
	String orgName="";
	String orgHref="";
	String errorDescription="";
	
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiryTime == null) ? 0 : expiryTime.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((operationName == null) ? 0 : operationName.hashCode());
		result = prime * result + ((orgHref == null) ? 0 : orgHref.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((ownerHref == null) ? 0 : ownerHref.hashCode());
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((userHref == null) ? 0 : userHref.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Task other = (Task) obj;
		if (expiryTime == null) {
			if (other.expiryTime != null)
				return false;
		} else if (!expiryTime.equals(other.expiryTime))
			return false;
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
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (operationName == null) {
			if (other.operationName != null)
				return false;
		} else if (!operationName.equals(other.operationName))
			return false;
		if (orgHref == null) {
			if (other.orgHref != null)
				return false;
		} else if (!orgHref.equals(other.orgHref))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (ownerHref == null) {
			if (other.ownerHref != null)
				return false;
		} else if (!ownerHref.equals(other.ownerHref))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userHref == null) {
			if (other.userHref != null)
				return false;
		} else if (!userHref.equals(other.userHref))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Task [name=" + name + ", href=" + href + ", status=" + status + ", operation=" + operation
				+ ", operationName=" + operationName + ", startTime=" + startTime + ", expiryTime=" + expiryTime
				+ ", ownerName=" + ownerName + ", ownerHref=" + ownerHref + ", userName=" + userName + ", userHref="
				+ userHref + ", orgName=" + orgName + ", orgHref=" + orgHref + "]";
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerHref() {
		return ownerHref;
	}
	public void setOwnerHref(String ownerHref) {
		this.ownerHref = ownerHref;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHref() {
		return userHref;
	}
	public void setUserHref(String userHref) {
		this.userHref = userHref;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgHref() {
		return orgHref;
	}
	public void setOrgHref(String orgHref) {
		this.orgHref = orgHref;
	}

}
