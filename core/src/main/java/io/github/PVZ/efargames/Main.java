package io.github.PVZ.efargames;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private OrthographicCamera camera; // makes it so that the camera will resize with the screen resizing
    private BitmapFont font; // default font for text
    CharSequence scores = "0-0"; // in case text is not updated before display

    private float VIEWPORT_WIDTH = 1300f; // variable for width of screen
    private float VIEWPORT_HEIGHT = 1300f; // variable for height of screen

    private SpriteBatch spriteBatch; // holds all of the sprites so that they can be drawn

    private static TextureAtlas atlas; // holds the textures used in the game
    private Stage stage; // handles inputs and screen functions

//    private Button button; // ignore this is for later projects

    private Engine engine = new Engine(); // handles all of the entities as well as systems

    private Projectile ball; // the entity for the ball

    private Player leftPlayer; // player objects
    private Player rightPlayer;

    private BoundBox topBox; // boxes for rebounding ball at top and bottom of screen
    private BoundBox bottomBox;

    private WinBox p1Box; // boxes for adding points to sides
    private WinBox p2Box;

    private Entity game; // holds variables such as score

    @Override
    public void create() {
        // Prepare your application here.
        float width = Gdx.graphics.getWidth(); // gdx auto width and heights
        float height = Gdx.graphics.getHeight();
        atlas = new TextureAtlas(Utils.getInternalPath("atlas/game_atlas.atlas")); // starts the atlas

        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT * height/width); // sets up camera
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update(); // starts camera

        spriteBatch = new SpriteBatch(); // sets up sprite batch

//        Skin skin = new Skin(Utils.getInternalPath("ui/uiskin.json")); // ignore used for button

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage); // makes inputs availible through stage

//        button = new Button(skin); // ignore this is for later projects
//        button.setBounds(700, 500, 300, 300);
//        stage.addActor(button);

        font = new BitmapFont(); // sets up font

        ball = new Projectile(atlas.createSprite("Red_Square"), 650, 650); // gives start location and sprite to ball
        engine.addEntity(ball.getProjectile()); // adds entity from class

        leftPlayer = new Player(atlas.createSprite("Red_Square"), 0); // sets id to distinguish players
        engine.addEntity(leftPlayer.getPlayer()); // adds entity from call

        rightPlayer = new Player(atlas.createSprite("Red_Square"), 1); // sets id to distinguish players
        engine.addEntity(rightPlayer.getPlayer()); // adds entity from call

        topBox = new BoundBox(atlas.createSprite("Red_Square"), 0, -49, 1500, 10); // sets up bounds
        engine.addEntity(topBox.getBox());  // adds entity to engine

        bottomBox = new BoundBox(atlas.createSprite("Red_Square"), 0, 1259, 1500, 10); // sets up bounds
        engine.addEntity(bottomBox.getBox()); // adds entity from call to engine

        p1Box = new WinBox(atlas.createSprite("Red_Square"), 1300, 0); // sets id to show who get points
        engine.addEntity(p1Box.getBox()); // adds entity to engine
        p2Box = new WinBox(atlas.createSprite("Red_Square"), -40, 1); // sets id to show who get points
        engine.addEntity(p2Box.getBox()); // adds entity to engine

        game = new Entity();
        game.add(new GameComponent()); // initializes game
        engine.addEntity(game); // adds game to engine

        MovementSystem movement = new MovementSystem(); // adds systems that are called every frame, allows for velocity to effect entities
        CollisionSystem collision = new CollisionSystem(); // checks collision of top and bottom
        PointSystem points = new PointSystem(); // sets up points
        engine.addSystem(movement);
        engine.addSystem(collision);
        engine.addSystem(points); // adds systems to engines
//        engine.getSystem(MovementSystem.class).setProcessing(false); // stop movement system
//        ImmutableArray<Component> components = entity.getComponents();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        // resizes and sets up camera on game startup
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = VIEWPORT_HEIGHT * height/width;
        camera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() { // main loop
        // Draw your application here.
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clears each frame so next frame can be drawn
        spriteBatch.setProjectionMatrix(camera.combined); // makes it so that sprites will scale to screen size
        inputChecks(); // checks all possible inputs to set velocity values
        scores = Mappers.gm.get(engine.getEntitiesFor(Families.game).get(0)).scorep1 + "-" +
            Mappers.gm.get(engine.getEntitiesFor(Families.game).get(0)).scorep2; // updates players score text
//        System.out.println(button.isPressed()); // ignore used for button
        // game logic here
        stage.getViewport().apply(); // allows for resizing of viewport
        stage.act(); // enacts viewport size change

        engine.update(Gdx.graphics.getDeltaTime()); // updates systems inside of engine using delta time

        stage.draw();// starts drawing
        spriteBatch.begin(); // starts drawing

        for(int i = 0; i < engine.getEntities().size() - 1; i++) {
            Mappers.im.get(getEntity(i)).sprite.draw(spriteBatch); // draws all sprites in engine
        }
        font.draw(spriteBatch, scores, 650, 1200); // draws text

        spriteBatch.end(); // ends drawing phase
    }
    public Entity getEntity(int index) { // allows other places to access engine
        return engine.getEntities().get(index);
    }
    public static TextureAtlas getAtlas() { // allows other places to access the texture atlas
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
    public void dispose() { // clears engine when program ends
        // Destroy application's resources here.
        spriteBatch.dispose();
        atlas.dispose();
        stage.dispose();
    }
    public void inputChecks() { // checks inputs for player movement
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
