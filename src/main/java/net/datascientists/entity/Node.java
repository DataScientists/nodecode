package net.datascientists.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Node implements Cloneable {	
	@Id 
	@GeneratedValue
	private long id;
	private long parentId;
		
	private String name;
	private String description;	
	private int sequence;
		
	private Integer deleted = 0;
	private Date lastUpdated;
	
	@OneToMany(mappedBy = "parentId", targetEntity = Node.class)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.PERSIST})
    @OrderBy("sequence ASC")
    protected List<Node> childNodes;
	
	@OneToMany(mappedBy="node", fetch = FetchType.LAZY)
	@Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.PERSIST})
    @JsonInclude(Include.NON_EMPTY)
	private List<Note> notes;	

	public Node() {
		super();
	}
	public void addNote(Note note) {
		note.setNode(this);
		this.setNotes(this.getNotes() == null?new ArrayList<Note>():this.getNotes());
		this.getNotes().add(note);
	}	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
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
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}