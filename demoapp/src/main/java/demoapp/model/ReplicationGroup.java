package demoapp.model;

public class ReplicationGroup {

	String name = "";
	String href= "";
	String rpo= "";
	String quiesceGuestEnabled= "";
	String networkCompressionEnabled= "";
	String placeholderVappId= "";
	String eventPartitionId= "";
	String replicationState= "";
	String vrServerInfo= "";
	String paused= "";
	String currentRpoViolation= "";
	String testRecoveryState= "";
	String recoveryState= "";
	String recoveryCompletionTime= "";
	String transferStartTime= "";
	String transferSeconds= "";
	String transferBytes= "";
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentRpoViolation == null) ? 0 : currentRpoViolation.hashCode());
		result = prime * result + ((eventPartitionId == null) ? 0 : eventPartitionId.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((networkCompressionEnabled == null) ? 0 : networkCompressionEnabled.hashCode());
		result = prime * result + ((paused == null) ? 0 : paused.hashCode());
		result = prime * result + ((placeholderVappId == null) ? 0 : placeholderVappId.hashCode());
		result = prime * result + ((quiesceGuestEnabled == null) ? 0 : quiesceGuestEnabled.hashCode());
		result = prime * result + ((recoveryCompletionTime == null) ? 0 : recoveryCompletionTime.hashCode());
		result = prime * result + ((recoveryState == null) ? 0 : recoveryState.hashCode());
		result = prime * result + ((replicationState == null) ? 0 : replicationState.hashCode());
		result = prime * result + ((rpo == null) ? 0 : rpo.hashCode());
		result = prime * result + ((testRecoveryState == null) ? 0 : testRecoveryState.hashCode());
		result = prime * result + ((transferBytes == null) ? 0 : transferBytes.hashCode());
		result = prime * result + ((transferSeconds == null) ? 0 : transferSeconds.hashCode());
		result = prime * result + ((transferStartTime == null) ? 0 : transferStartTime.hashCode());
		result = prime * result + ((vrServerInfo == null) ? 0 : vrServerInfo.hashCode());
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
		ReplicationGroup other = (ReplicationGroup) obj;
		if (currentRpoViolation == null) {
			if (other.currentRpoViolation != null)
				return false;
		} else if (!currentRpoViolation.equals(other.currentRpoViolation))
			return false;
		if (eventPartitionId == null) {
			if (other.eventPartitionId != null)
				return false;
		} else if (!eventPartitionId.equals(other.eventPartitionId))
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
		if (networkCompressionEnabled == null) {
			if (other.networkCompressionEnabled != null)
				return false;
		} else if (!networkCompressionEnabled.equals(other.networkCompressionEnabled))
			return false;
		if (paused == null) {
			if (other.paused != null)
				return false;
		} else if (!paused.equals(other.paused))
			return false;
		if (placeholderVappId == null) {
			if (other.placeholderVappId != null)
				return false;
		} else if (!placeholderVappId.equals(other.placeholderVappId))
			return false;
		if (quiesceGuestEnabled == null) {
			if (other.quiesceGuestEnabled != null)
				return false;
		} else if (!quiesceGuestEnabled.equals(other.quiesceGuestEnabled))
			return false;
		if (recoveryCompletionTime == null) {
			if (other.recoveryCompletionTime != null)
				return false;
		} else if (!recoveryCompletionTime.equals(other.recoveryCompletionTime))
			return false;
		if (recoveryState == null) {
			if (other.recoveryState != null)
				return false;
		} else if (!recoveryState.equals(other.recoveryState))
			return false;
		if (replicationState == null) {
			if (other.replicationState != null)
				return false;
		} else if (!replicationState.equals(other.replicationState))
			return false;
		if (rpo == null) {
			if (other.rpo != null)
				return false;
		} else if (!rpo.equals(other.rpo))
			return false;
		if (testRecoveryState == null) {
			if (other.testRecoveryState != null)
				return false;
		} else if (!testRecoveryState.equals(other.testRecoveryState))
			return false;
		if (transferBytes == null) {
			if (other.transferBytes != null)
				return false;
		} else if (!transferBytes.equals(other.transferBytes))
			return false;
		if (transferSeconds == null) {
			if (other.transferSeconds != null)
				return false;
		} else if (!transferSeconds.equals(other.transferSeconds))
			return false;
		if (transferStartTime == null) {
			if (other.transferStartTime != null)
				return false;
		} else if (!transferStartTime.equals(other.transferStartTime))
			return false;
		if (vrServerInfo == null) {
			if (other.vrServerInfo != null)
				return false;
		} else if (!vrServerInfo.equals(other.vrServerInfo))
			return false;
		return true;
	}
	public ReplicationGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "ReplicationGroup [name=" + name + ", href=" + href + ", rpo=" + rpo + ", quiesceGuestEnabled="
				+ quiesceGuestEnabled + ", networkCompressionEnabled=" + networkCompressionEnabled
				+ ", placeholderVappId=" + placeholderVappId + ", eventPartitionId=" + eventPartitionId
				+ ", replicationState=" + replicationState + ", vrServerInfo=" + vrServerInfo + ", paused=" + paused
				+ ", currentRpoViolation=" + currentRpoViolation + ", testRecoveryState=" + testRecoveryState
				+ ", recoveryState=" + recoveryState + ", recoveryCompletionTime=" + recoveryCompletionTime
				+ ", transferStartTime=" + transferStartTime + ", transferSeconds=" + transferSeconds
				+ ", transferBytes=" + transferBytes + "]";
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
	public String getRpo() {
		return rpo;
	}
	public void setRpo(String rpo) {
		this.rpo = rpo;
	}
	public String getQuiesceGuestEnabled() {
		return quiesceGuestEnabled;
	}
	public void setQuiesceGuestEnabled(String quiesceGuestEnabled) {
		this.quiesceGuestEnabled = quiesceGuestEnabled;
	}
	public String getNetworkCompressionEnabled() {
		return networkCompressionEnabled;
	}
	public void setNetworkCompressionEnabled(String networkCompressionEnabled) {
		this.networkCompressionEnabled = networkCompressionEnabled;
	}
	public String getPlaceholderVappId() {
		return placeholderVappId;
	}
	public void setPlaceholderVappId(String placeholderVappId) {
		this.placeholderVappId = placeholderVappId;
	}
	public String getEventPartitionId() {
		return eventPartitionId;
	}
	public void setEventPartitionId(String eventPartitionId) {
		this.eventPartitionId = eventPartitionId;
	}
	public String getReplicationState() {
		return replicationState;
	}
	public void setReplicationState(String replicationState) {
		this.replicationState = replicationState;
	}
	public String getVrServerInfo() {
		return vrServerInfo;
	}
	public void setVrServerInfo(String vrServerInfo) {
		this.vrServerInfo = vrServerInfo;
	}
	public String getPaused() {
		return paused;
	}
	public void setPaused(String paused) {
		this.paused = paused;
	}
	public String getCurrentRpoViolation() {
		return currentRpoViolation;
	}
	public void setCurrentRpoViolation(String currentRpoViolation) {
		this.currentRpoViolation = currentRpoViolation;
	}
	public String getTestRecoveryState() {
		return testRecoveryState;
	}
	public void setTestRecoveryState(String testRecoveryState) {
		this.testRecoveryState = testRecoveryState;
	}
	public String getRecoveryState() {
		return recoveryState;
	}
	public void setRecoveryState(String recoveryState) {
		this.recoveryState = recoveryState;
	}
	public String getRecoveryCompletionTime() {
		return recoveryCompletionTime;
	}
	public void setRecoveryCompletionTime(String recoveryCompletionTime) {
		this.recoveryCompletionTime = recoveryCompletionTime;
	}
	public String getTransferStartTime() {
		return transferStartTime;
	}
	public void setTransferStartTime(String transferStartTime) {
		this.transferStartTime = transferStartTime;
	}
	public String getTransferSeconds() {
		return transferSeconds;
	}
	public void setTransferSeconds(String transferSeconds) {
		this.transferSeconds = transferSeconds;
	}
	public String getTransferBytes() {
		return transferBytes;
	}
	public void setTransferBytes(String transferBytes) {
		this.transferBytes = transferBytes;
	}
	

}
