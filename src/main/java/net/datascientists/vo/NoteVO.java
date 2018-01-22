package net.datascientists.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteVO {

	private long id;
	private NodeVO nodeVO;
	private Date lastUpdated;
	private Integer deleted;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public NodeVO getNodeVO() {
		return nodeVO;
	}
	public void setNodeVO(NodeVO nodeVO) {
		this.nodeVO = nodeVO;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Integer getDeleted() {		
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
