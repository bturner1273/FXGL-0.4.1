package tiled_platformer;

import java.awt.geom.Point2D;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Control;
import javafx.util.Duration;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

public class NinjaControl extends Control {
	private int speed = 0;
	private AnimatedTexture texture;
	private AnimationChannel attack, jumpAttack, run, idle, jump, die;
	
	public NinjaControl() {
		idle = new AnimationChannel("ninjaIdleAnimation.png",10,232,439,Duration.millis(400), 0, 9);
		run = new AnimationChannel("ninjaRunningAnimation.png",10,363,458, Duration.millis(400), 0, 9);
		
		texture = new AnimatedTexture(idle);
	}
	@Override
	public void onAdded(Entity entity) {
		//texture.setScaleX(.5);
		//texture.setScaleY(.5);
	    entity.setView(texture);
	}
	
	@Override
	public void onUpdate(Entity entity, double tpf) {
		entity.getComponent(PhysicsComponent.class).setVelocityX(speed*tpf);
	    if (speed == 0) {
	        texture.setAnimationChannel(idle);
	    } else {
	        texture.setAnimationChannel(run);

	        speed = (int) (speed * 0.9);

	        if (FXGLMath.abs(speed) < 1) {
	            speed = 0;
	        }
	    }
	}
	public void moveRight() {
	    speed = 1500;

	    getEntity().setScaleX(1);
	}

	public void moveLeft() {
	    speed = -1500;

	    getEntity().setScaleX(-1);
	}

}
