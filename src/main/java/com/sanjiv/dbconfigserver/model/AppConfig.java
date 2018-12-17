package com.sanjiv.dbconfigserver.model;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@Entity
@Table
@Audited
public class AppConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "`APPLICATION`")
	private String application;
	
	@Column(name = "`PROFILE`")
	private String profile;
	
	@Column(name = "`LABEL`")
	private String label;
	
	@Column(name = "`KEY`")
	private String key;
	
	@Column(name = "`VALUE`")
	private String value;

}
