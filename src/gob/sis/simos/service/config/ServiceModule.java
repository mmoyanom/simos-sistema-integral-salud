package gob.sis.simos.service.config;

import gob.sis.simos.service.EESSService;
import gob.sis.simos.service.LoginService;
import gob.sis.simos.service.impl.EESSServiceImpl;
import gob.sis.simos.service.impl.LoginServiceImpl;
import roboguice.config.AbstractAndroidModule;

import com.google.inject.Singleton;

public class ServiceModule extends AbstractAndroidModule {

	@Override
	protected void configure() {

		bind(LoginService.class).to(LoginServiceImpl.class).in(Singleton.class);
		bind(EESSService.class).to(EESSServiceImpl.class).in(Singleton.class);
	}

}
