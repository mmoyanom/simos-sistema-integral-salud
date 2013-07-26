package gob.sis.simos.service.config;

import java.util.List;
import roboguice.application.RoboApplication;
import com.google.inject.Module;

public class SimosApplication extends RoboApplication {

	private Module module = new ServiceModule();
	
	
	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(module);
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
	
}
