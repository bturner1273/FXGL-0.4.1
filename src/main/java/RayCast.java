import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.RaycastResult;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Shows how to use raycast.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */

/**
* I just kept this in my Project for reference this is not my code
* and in no way am I trying to take credit for it
*
*/
public class RayCast extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("RaycastSample");



    }

    private Line laser = new Line();

    @Override
    protected void initGame() {
        spawnWall(500, 100, 50, 50);
        spawnWall(550, 150, 50, 50);
        spawnWall(600, 200, 50, 50);
        spawnWall(600, 400, 50, 50);
        spawnWall(300, 450, 50, 50);
        spawnWall(500, 550, 50, 50);
        spawnWall(300, 300, 50, 50);

        laser.setStroke(Color.RED);
        laser.setStrokeWidth(2);
        laser.setStartY(300);

        getGameScene().addGameView(new EntityView(laser));
    }

    private double endY = -300;

    @Override
    protected void onUpdate(double tpf) {
        laser.setEndX(getWidth());
        laser.setEndY(endY);

        // 1. raycast is essentially a ray from start point to end point in physics world
        RaycastResult result = getPhysicsWorld().raycast(new Point2D(0, 300), new Point2D(getWidth(), endY));

        // 2. if the ray hits something in physics world the point will the closest hit
        result.getPoint().ifPresent(p -> {
            laser.setEndX(p.getX());
            laser.setEndY(p.getY());
        });

        endY+=5;
        if (endY >= getHeight() + 300)
            endY = -300;
    }

    private void spawnWall(double x, double y, double w, double h) {
        Entities.builder()
                .at(x, y)
                .viewFromNodeWithBBox(new Rectangle(w, h))
                .with(new PhysicsComponent())
                .buildAndAttach(getGameWorld());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
