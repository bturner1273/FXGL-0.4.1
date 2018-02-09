package tiled_platformer;


import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SetEntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;

@SetEntityFactory
public class TiledPlatformerFactory implements EntityFactory {
	@Spawns("platform")
	public Entity newPlatform(SpawnData data) {
		return Entities.builder()
				.from(data)
				.type(TiledPlatformerTypes.PLATFORM)
				.renderLayer(RenderLayer.TOP)
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.with(new PhysicsComponent())
				.build();
	}
	@Spawns("")
	public Entity newThing(SpawnData data) {
		return Entities.builder()
				.renderLayer(RenderLayer.BACKGROUND)
				.with(new PhysicsComponent())
				.build();
	}
	@Spawns("player")
	public Entity newPlayer(SpawnData data) {
		PhysicsComponent physics = new PhysicsComponent();
		FixtureDef fd = new FixtureDef();
		BodyDef def = new BodyDef();
		def.setActive(true);
		def.setType(BodyType.DYNAMIC);
		def.setFixedRotation(true);
		fd.setFriction(10f);
		fd.setRestitution(.3f);
		physics.setFixtureDef(fd);
		physics.setBodyDef(def);
		//HitBox box = new HitBox(BoundingShape.box(232, 439));
		HitBox box1 = new HitBox(BoundingShape.box(40, 40));
		return Entities.builder()
				.with(new CollidableComponent(true))
				.from(data)
				.type(TiledPlatformerTypes.NINJA)
				.with(physics)
				.viewFromNode(new Rectangle(40,40,Color.BLUE))
				//.with(new NinjaControl())
				.bbox(box1)
				.build();
	}
	@Spawns("box")
	public Entity newBox(SpawnData data) {
		PhysicsComponent physics = new PhysicsComponent();
		physics.setBodyType(BodyType.KINEMATIC);
		return Entities.builder()
				.from(data)
				.with(new CollidableComponent(true))
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.with(physics)
				.type(TiledPlatformerTypes.BOX)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("door")
	public Entity newDoor(SpawnData data) {
		return Entities.builder()
				.from(data)
				.renderLayer(RenderLayer.TOP)
				.type(TiledPlatformerTypes.DOOR)
				.with(new CollidableComponent(true))
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.build();
	}
	
	@Spawns("coin")
	public Entity newCoin(SpawnData data) {
		return Entities.builder()
				.from(data)
				.renderLayer(RenderLayer.TOP)
				.type(TiledPlatformerTypes.COIN)
				.with(new CollidableComponent(true))
				.bbox(new HitBox(BoundingShape.circle(10)))
				.viewFromNode(new Circle(10, Color.GOLD))
				.build();
	}

}
