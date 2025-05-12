package io.github.PVZ.efargames;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.PVZ.efargames.Systems.CollisionSystem;
import io.github.PVZ.efargames.Systems.MovementSystem;
import io.github.PVZ.efargames.Systems.PointSystem;
import io.github.PVZ.efargames.components.GameComponent;
import io.github.PVZ.efargames.statics.Families;
import io.github.PVZ.efargames.statics.Mappers;
import io.github.PVZ.efargames.statics.Utils;

import static com.badlogic.gdx.Gdx.gl;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener{
    // required variables
    private OrthographicCamera camera;
    private BitmapFont font;
    CharSequence scores = "0-0";

    private float VIEWPORT_WIDTH = 1300f;
    private float VIEWPORT_HEIGHT = 1300f;
    private float HEADER_HEIGHT = 50f;

    private SpriteBatch spriteBatch;

    private static TextureAtlas atlas;
    private Stage stage;

    private Button button;

    private Engine engine = new Engine();
    private Entity entity = new Entity();

    private Projectile placeholderProjectile;

    private Player leftPlayer;
    private Player rightPlayer;

    private BoundBox topBox;
    private BoundBox bottomBox;

    private WinBox p1Box;
    private WinBox p2Box;

    private Entity game;

    @Override
    public void create() {
        // Prepare your application here.
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        atlas = new TextureAtlas(Utils.getInternalPath("atlas/game_atlas.atlas"));

        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT * height/width);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        spriteBatch = new SpriteBatch();

        Skin skin = new Skin(Utils.getInternalPath("ui/uiskin.json"));

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        button = new Button(skin);
        button.setBounds(700, 500, 300, 300);
        stage.addActor(button);

        font = new BitmapFont();

        placeholderProjectile = new Projectile(atlas.createSprite("Red_Square"), 650, 650);
        engine.addEntity(placeholderProjectile.getProjectile());

        leftPlayer = new Player(atlas.createSprite("Red_Square"), 0);
        engine.addEntity(leftPlayer.getPlayer());

        rightPlayer = new Player(atlas.createSprite("Red_Square"), 1);
        engine.addEntity(rightPlayer.getPlayer());

        topBox = new BoundBox(atlas.createSprite("Red_Square"), 0, -49, 1500, 10);
        engine.addEntity(topBox.getBox());

        bottomBox = new BoundBox(atlas.createSprite("Red_Square"), 0, 1259, 1500, 10);
        engine.addEntity(bottomBox.getBox());

        p1Box = new WinBox(atlas.createSprite("Red_Square"), 1300, 0);
        engine.addEntity(p1Box.getBox());
        p2Box = new WinBox(atlas.createSprite("Red_Square"), -40, 1);
        engine.addEntity(p2Box.getBox());

        game = new Entity();
        game.add(new GameComponent());
        engine.addEntity(game);

        MovementSystem movement = new MovementSystem();
        CollisionSystem collision = new CollisionSystem();
        PointSystem points = new PointSystem();
        engine.addSystem(movement);
        engine.addSystem(collision);
        engine.addSystem(points);
//        engine.getSystem(MovementSystem.class).setProcessing(false); // stop movement system
//        ImmutableArray<Component> components = entity.getComponents();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = VIEWPORT_HEIGHT * height/width;
        camera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() { // main loop
        // Draw your application here.
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        inputChecks();
        scores = Mappers.gm.get(engine.getEntitiesFor(Families.game).get(0)).scorep1 + "-" + Mappers.gm.get(engine.getEntitiesFor(Families.game).get(0)).scorep2;
//        System.out.println(button.isPressed());
        // game logic here
        stage.getViewport().apply();
        stage.act();

        engine.update(Gdx.graphics.getDeltaTime());

        stage.draw();
        spriteBatch.begin();

        for(int i = 0; i < engine.getEntities().size() - 1; i++) {
            Mappers.im.get(getEntity(i)).sprite.draw(spriteBatch);
        }
        font.draw(spriteBatch, scores, 650, 1200);

        spriteBatch.end();
    }
    public Entity getEntity(int index) {
        return engine.getEntities().get(index);
    }
    public static TextureAtlas getAtlas() {
        return atlas;
    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        spriteBatch.dispose();
        atlas.dispose();
        stage.dispose();
    }
    public void inputChecks() {
        if(Gdx.input.isKeyPressed(Input.Keys.W) && Mappers.pm.get(engine.getEntitiesFor(Families.player).get(0)).y >= 150) {
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(0)).vertical = -500;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).up = true;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).down = false;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && Mappers.pm.get(engine.getEntitiesFor(Families.player).get(0)).y <= 1250){
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(0)).vertical = 500;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).up = false;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).down = true;
        }
        else{
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(0)).vertical = 0;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).up = false;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(0)).down = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && Mappers.pm.get(engine.getEntitiesFor(Families.player).get(1)).y >= 150){
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(1)).vertical = -500;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).up = true;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).down = false;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Mappers.pm.get(engine.getEntitiesFor(Families.player).get(1)).y <= 1250){
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(1)).vertical = 500;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).up = false;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).down = true;
        }
        else {
            Mappers.vm.get(engine.getEntitiesFor(Families.player).get(1)).vertical = 0;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).up = false;
            Mappers.plm.get(engine.getEntitiesFor(Families.player).get(1)).down = false;
        }
    }

}
