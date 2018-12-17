package com.sanjiv.dbconfigserver.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;
import com.sanjiv.dbconfigserver.exception.ResourceNotFoundException;
import com.sanjiv.dbconfigserver.model.AppConfig;
import com.sanjiv.dbconfigserver.repository.AppConfigRepository;


@RestController
@RequestMapping("/api")
public class DbConfigServerController {

    @Autowired
    AppConfigRepository appConfigRepository;

    @GetMapping("/")
    public List<AppConfig> getAllAppConfigs() {
        return appConfigRepository.findAll();
    }
    
    
    @GetMapping("/filter")
    public String getByJsonPath(@RequestParam("jsonPath") String jsonPath) {
    	List<AppConfig> appConfigs = appConfigRepository.findAll();
    	
    	return JsonPath.parse(appConfigs).read(jsonPath);
    	
    }

    @GetMapping("/{id}")
    public AppConfig getAppConfigById(@PathVariable(value = "id") Long id) {
        return appConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "id", id));
    }
    
    @GetMapping("/{id}/filter")
    public String getByJsonPath(@PathVariable(value = "id") Long id,
    		@RequestParam("jsonPath") String jsonPath) {
    	AppConfig appConfig = appConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "id", id));
    	
    	return JsonPath.parse(appConfig.getValue()).read(jsonPath);
    	
    }

    

    @GetMapping("/app/{application}/profile/{profileName}/label/{label}/key/{key}")
    public AppConfig getAppConfigByAppNameAndModuleAndConfigTypeAndkey(@PathVariable(value = "application") String application,
    		@PathVariable(value = "profileName") String profile,
    		@PathVariable(value = "label") String label,
    		@PathVariable(value = "key") String key) {
        return appConfigRepository.findByApplicationAndProfileAndLabelAndKey(application, profile, label, key)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "application, profile, label, key", application + profile+ label + key));
    }

    @GetMapping("/app/{application}/profile/{profileName}/label/{label}")
    public List<AppConfig> getAppConfigByApplicationAndModuleAndConfigType(@PathVariable(value = "application") String application,
    		@PathVariable(value = "profileName") String profile,
    		@PathVariable(value = "label") String label) {
        return appConfigRepository.findByApplicationAndProfileAndLabel(application, profile, label)
        		.orElseThrow(() -> new ResourceNotFoundException("AppConfig", "application, profile, label", application + profile+ label));
    }

    @GetMapping("/app/{application}/profile/{profileName}")
    public List<AppConfig> getAppConfigByApplicationAndModule(@PathVariable(value = "application") String application,
    		@PathVariable(value = "profileName") String profile) {
        return appConfigRepository.findByApplicationAndProfile(application, profile)
        		.orElseThrow(() -> new ResourceNotFoundException("AppConfig", "application, profile", application + profile));

    }

    @GetMapping("/application/{application}")
    public List<AppConfig> getAppConfigByApplication(@PathVariable(value = "application") String application) {
        return appConfigRepository.findByApplication(application)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "application", application));
    }
    
    
    @PostMapping(path ="/application/{application}/profile/{profile}/label/{label}/key/{key}", consumes = "application/json", produces = "application/json")
    public AppConfig createAppConfig(@PathVariable(value = "application") String application,
    		@PathVariable(value = "profile") String profile,
    		@PathVariable(value = "label") String label,
    		@PathVariable(value = "key") String key,
    		@Valid @RequestBody String value,
    		@RequestParam("userName") String userName) {
    	AppConfig appConfig = new AppConfig();
    	appConfig.setApplication(application);
    	appConfig.setProfile(profile);
    	appConfig.setLabel(label);
    	appConfig.setKey(key);
    	
    	System.out.println("configValue:"+ JsonPath.parse(value).jsonString());
    	
    	appConfig.setValue(JsonPath.parse(value).jsonString());
    	
    	MDC.put("UPDATED_BY", userName);
    	
        return appConfigRepository.save(appConfig);
    }


    @PutMapping("/{id}")
    public AppConfig updateAppConfigById(@PathVariable(value = "id") Long id,
    		@Valid @RequestBody AppConfig appConfigDetails,
    		@RequestParam("userName") String userName) {
        AppConfig appConfig = appConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "id", id));

        appConfig.setValue(appConfigDetails.getValue());

    	MDC.put("UPDATED_BY", userName);
    	
        AppConfig updatedAppConfig = appConfigRepository.save(appConfigDetails);

        return updatedAppConfig;
    }

    
    @PutMapping("/application/{application}/profile/{profile}/label/{label}/key/{key}")
    public AppConfig updateAppConfig(@PathVariable(value = "application") String application,
    		@PathVariable(value = "profile") String profile,
    		@PathVariable(value = "label") String label,
    		@PathVariable(value = "key") String key,
    		@Valid @RequestBody String value,
    		@RequestParam("userName") String userName) {

    	AppConfig appConfig = appConfigRepository.findByApplicationAndProfileAndLabelAndKey(application, profile, label, key)
    			.orElseThrow(() -> new ResourceNotFoundException("AppConfig", "application, profile, label, key", application + profile+ label + key));


        appConfig.setApplication(application);
        appConfig.setProfile(profile);
        appConfig.setLabel(label);
        appConfig.setKey(key);

    	appConfig.setValue(JsonPath.parse(value).jsonString());

    	MDC.put("UPDATED_BY", userName);

        AppConfig updatedAppConfig = appConfigRepository.save(appConfig);
        return updatedAppConfig;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppConfig(@PathVariable(value = "id") Long id,
    		@RequestParam("userName") String userName) {
        AppConfig appConfig = appConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppConfig", "id", id));

        MDC.put("UPDATED_BY", userName);
        
        appConfigRepository.delete(appConfig);

        return ResponseEntity.ok().build();
    }
}
