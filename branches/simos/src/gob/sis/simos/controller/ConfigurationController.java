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

	public void saveConfig(Config cfg) {
		cfg.setId(1);
		if(configService.getConfiguration() == null){
			configService.createConfiguration(cfg);
			return;
		}
		if (configService.getConfiguration().getServer() == null){
			configService.createConfiguration(cfg);
			return;
		}
		if (configService.getConfiguration().getServer().isEmpty()){
			configService.createConfiguration(cfg);
			return;
		}
		configService.updateConfiguration(cfg);
	}
	
}
