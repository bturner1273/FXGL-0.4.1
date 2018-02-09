package tiled_platformer;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.component.RotationComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.event.EventBus;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.scene.Viewport;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

//next level I add needs to have two walls close together that you need to jump
//up to get to the exit


public class TiledPlatformerMain extends GameApplication {
	Entity player;
	boolean canJump = false;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(15 * 70);
		settings.setHeight(10*70);
		settings.setCloseConfirmation(false);
		settings.setMenuEnabled(false);
		settings.setProfilingEnabled(false);
		settings.setFullScreenAllowed(true);
		settings.setIntroEnabled(false);
		settings.setTitle("TiledMapPlatformer");
		settings.setVersion("");
		settings.setManualResizeEnabled(true);
	}

	@Override
	protected void initPhysics() {
		PhysicsWorld physicsWorld = getPhysicsWorld();
		physicsWorld.addCollisionHandler(new CollisionHandler(TiledPlatformerTypes.NINJA, TiledPlatformerTypes.COIN) {
			protected void onCollision(Entity ninja, Entity coin) {
				coin.removeFromWorld();
			}
		});	
	}

	@Override
	protected void initInput() {
		final Input input = getInput();
		input.addAction(new UserAction("left") {
			@Override
			public void onAction() {
				//player.getControl(NinjaControl.class).moveLeft();
				player.getComponent(PhysicsComponent.class).setVelocityX(-200);

			}
		}, KeyCode.LEFT);
		input.addAction(new UserAction("right") {
			@Override
			public void onAction() {
				//player.getControl(NinjaControl.class).moveRight();
				player.getComponent(PhysicsComponent.class).setVelocityX(200);
			}
		}, KeyCode.RIGHT);
		input.addAction(new UserAction("up") {
			@Override
			public void onAction() {
				if(!isJumping()) {
				player.getComponent(PhysicsComponent.class).setVelocityY(-400);
				input.mockKeyRelease(KeyCode.UP);
				}
			}
		}, KeyCode.UP);
		

	}
	protected boolean isJumping() {
		return !(player.getComponent(PhysicsComponent.class).getVelocityY() < 20 && player.getComponent(PhysicsComponent.class).getVelocityY() > -20);
	}

	@Override
	protected void initGame() {
		getGameWorld().setLevelFromMap("NinjaPlatformerLevel3.json");
		player = getGameWorld().spawn("player", 80, 100);
		//player.getViewComponent().turnOnDebugBBox(true);
		getGameScene().getViewport().shakeTranslational(5);
		getMasterTimer().runOnceAfter(() ->{
			getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
		}, Duration.seconds(1.4));
	}
	public static void main(String[] args) {
		launch(args);
	}

}
