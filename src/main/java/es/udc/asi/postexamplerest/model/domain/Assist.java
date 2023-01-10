package es.udc.asi.postexamplerest.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class Assist implements Serializable{
	
	@Id
	@Column(name="id_event")
	protected Long id_event;
	  
	@Id
	@Column(name="id_assistant")
	private Long id_assistant;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "id_event" , referencedColumnName="id")
	private Event event;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "id_assistant" , referencedColumnName="id")
	private User assistant;
	
	private Boolean assist;

	private LocalDateTime timestamp = LocalDateTime.now();

	public Assist() {
	}
	
	
	public Long getId_event() {
		return id_event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Long getId_assistant() {
		return id_assistant;
	}

	public void setAssistant(User user) {
		this.assistant = user;
	}

	public Boolean getAssist() {
		return assist;
	}

	public void setAssist(Boolean assist) {
		this.assist = assist;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}