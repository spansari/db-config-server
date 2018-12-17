package com.sanjiv.dbconfigserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.sanjiv.dbconfigserver.model.AppConfig;

@Repository
public interface AppConfigRepository extends  RevisionRepository<AppConfig, Long, Integer>, JpaRepository<AppConfig, Long> {

	Optional<AppConfig> findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label, String key);

	Optional<List<AppConfig>> findByApplicationAndProfileAndLabel(String application, String profile, String label);

	Optional<List<AppConfig>> findByApplicationAndProfile(String application, String profile);
	
	Optional<List<AppConfig>> findByApplication(String application);
}
