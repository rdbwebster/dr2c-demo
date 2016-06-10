package demoapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demoapp.App;
import demoapp.VcdClient;
import demoapp.DemoException;
import demoapp.model.Organization;
import demoapp.model.Vdc;

/* NavBean tracks the state of users selections of Org and Vdc in the left navigation window.
 * It also contains overall lists of Org and Vdc references for the user
 * It is stored in the user http session.
 * As a model object it should not contain references to the vcdClient, its state is set by App
 * 
 */

public class NavBean {
	
	public static final String ORG_TITLE = "Organizations";
	public static final String VDC_TITLE = "  Virtual Data Centers";
	public static final String ALL_VDC_TITLE = "All_Replications";

	
//	VcdClient vcdClient;
    


	List<ReferenceType> orgList;         // set by app.dologin
	List<ReferenceType> orgVdcList;      // set by app.selectOrg
	

	Organization selectedOrg = null;
	Vdc selectedVdc = null;
	
	String tabPage = "";
	String navTitle = "";
	String allVdcTitle = "";
	
	boolean vdcEntrySelected = false;
	



	final static Logger logger = LoggerFactory.getLogger(NavBean.class);
	
	public static NavBean create() throws DemoException{
		
		NavBean bean = new NavBean();
	
		bean.navTitle = ORG_TITLE;
		bean.allVdcTitle = ALL_VDC_TITLE;
		return bean;
		
	}
	
	public void setOrgList(List<ReferenceType> orgList) {
		this.orgList = orgList;
	}

	public void setOrgVdcList(List<ReferenceType> orgVdcList) {
		this.orgVdcList = orgVdcList;
	}

	public String getTabPage() {
		return tabPage;
	}

	public void setTabpage(String tabpage) {
		this.tabPage = tabpage;
	}

	/* 
	 * Search the stored OrgList and return a reference to the named Org
	 */
	public ReferenceType getOrgRefByName(String name) {
		ReferenceType rt = new ReferenceType();
		
		for(ReferenceType ref : orgList) {
			if(ref.getName().equals(name)) {
				rt.setHref(ref.getHref());
				rt.setId(ref.getId());
				rt.setName(ref.getName());
				rt.setType(ref.getType());
			}
		}
		
		if(rt.getName().length() == 0) {
			logger.warn("Unable to retrieve ReferenceType for Org " + name + " from NavBean");
		}
		
		return rt;
		
	}
	
	/* 
	 * Search the stored VdcList and return a reference to the named Vdc
	 */
	public ReferenceType getVdcRefByName(String name) {
		ReferenceType rt = new ReferenceType();
		
		for(ReferenceType ref : orgVdcList) {
			if(ref.getName().equals(name)) {
				rt.setHref(ref.getHref());
				rt.setId(ref.getId());
				rt.setName(ref.getName());
				rt.setType(ref.getType());
			}
		}
		
		if(rt.getName().length() == 0) {
			logger.warn("Unable to retrieve ReferenceType for Vdc " + name + " from NavBean");
		}
		
		return rt;
		
	}
    
	public String getNavTitle() {
		return navTitle;
	}


	private  NavBean() {
		super();
		
	}
	

	public Organization getSelectedOrg() {
		
		return selectedOrg;
	}
	
	public Vdc getSelectedVdc() {
		
		return selectedVdc;
	}
 
	// helper method for leftnav.ftl to deal with null vdc name for all-replications
    public String getSelectedVdcName() {
    	if (selectedVdc != null) {
    	   return selectedVdc.getName();
    	}
    	else return "";
    }
	
	public void deSelectVdc() {
		
			// user navigated back to org list after selecting org and vdc
			navTitle = ORG_TITLE;
			selectedOrg = null;
			selectedVdc = null;
			vdcEntrySelected = false;
		
	}

	public void setSelectedOrg(Organization selectedOrg) throws DemoException {
		
		this.selectedOrg = selectedOrg;
        
		// change Nav Title
		
		navTitle = VDC_TITLE;
	   
	}


	public void setSelectedVdc(Vdc selectedVdc) throws DemoException {
		
		this.selectedVdc = selectedVdc;

	}
	


	public List<ReferenceType> getOrgList() {
		return orgList;
	}

	public List<ReferenceType> getOrgVdcList() {
		return orgVdcList;
	}
	
	public boolean isOrgSelected() {
		if (selectedOrg != null)
	    	return true;
		else return false;
	}
	
	public boolean isVdcEntrySelected() {  // indicate whether an entry is made could be a specific vdc or 'all-replications'
		 return vdcEntrySelected;
	}
	
	public boolean getVdcEntrySelected() {  // helper for home.ftl which does not understand isVdcEntrySelected
		return vdcEntrySelected;
	}
	
    public void setVdcEntrySelected(boolean vdcEntrySelected) {
		this.vdcEntrySelected = vdcEntrySelected;
	}
	    

	public String getAllVdcTitle() {
		return allVdcTitle;
	}

	public void setAllVdcTitle(String allVdcTitle) {
		this.allVdcTitle = allVdcTitle;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((orgList == null) ? 0 : orgList.hashCode());
		result = prime * result + ((selectedOrg == null) ? 0 : selectedOrg.hashCode());
		result = prime * result + ((orgVdcList == null) ? 0 : orgVdcList.hashCode());
		result = prime * result + ((tabPage == null) ? 0 : tabPage.hashCode());
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
		NavBean other = (NavBean) obj;
		
		if (orgList == null) {
			if (other.orgList != null)
				return false;
		} else if (!orgList.equals(other.orgList))
			return false;
		if (selectedOrg == null) {
			if (other.selectedOrg != null)
				return false;
		} else if (!selectedOrg.equals(other.selectedOrg))
			return false;
		
		if (orgVdcList == null) {
			if (other.orgVdcList != null)
				return false;
		} else if (!orgVdcList.equals(other.orgVdcList))
			return false;
		if (tabPage == null) {
			if (other.tabPage != null)
				return false;
		} else if (!tabPage.equals(other.tabPage))
			return false;
		return true;
	}



	

}
