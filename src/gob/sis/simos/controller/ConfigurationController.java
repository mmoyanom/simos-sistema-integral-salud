package gob.sis.simos.controller;

import com.google.inject.Inject;

import gob.sis.simos.entity.Config;
import gob.sis.simos.service.impl.ConfigurationServiceImpl;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ConfigurationController {
	
	@Inject
	private ConfigurationServiceImpl configService;
	
	public Config getConfig(){
		return configService.getConfiguration();
	}

	public void updateConfig(Config cfg) {
		configService.updateConfiguration(cfg);
	}
	
}
