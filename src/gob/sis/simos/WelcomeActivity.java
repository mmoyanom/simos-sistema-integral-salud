package gob.sis.simos;

import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.opengl.texture.source.AssetTextureSource;
import org.anddev.andengine.opengl.texture.source.ITextureSource;
import org.anddev.andengine.ui.activity.BaseSplashActivity;

import android.app.Activity;

public class WelcomeActivity extends BaseSplashActivity {
	
	        private static final int SPLASH_DURATION = 5;
	        private static final float SPLASH_SCALE_FROM = 0.8f;
	        
	        protected ScreenOrientation getScreenOrientation()
	        {
	                return ScreenOrientation.PORTRAIT;
	        }
	        protected ITextureSource onGetSplashTextureSource()
	        {
	                return new AssetTextureSource(this, "splash/marca_peru.jpg");
	        }
	        protected float getSplashDuration()
	        {
	                return SPLASH_DURATION;
	        }
	        protected float getSplashScaleFrom()
	        {
	                return SPLASH_SCALE_FROM;
	        }
	        protected Class<? extends Activity> getFollowUpActivity()
	        {
	                return LoginActivity.class;
	        }
	

}
