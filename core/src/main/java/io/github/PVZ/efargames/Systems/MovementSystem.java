package io.github.PVZ.efargames.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Families;
import io.github.PVZ.efargames.components.PositionComponent;
import io.github.PVZ.efargames.components.VelocityComponent;
import io.github.PVZ.efargames.statics.Mappers;

public class MovementSystem extends EntitySystem { // allows for entities to update with velocities
    private ImmutableArray<Entity> entities;


    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Families.movinBox);
    }

    public void update(float deltaTime) {
        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            PositionComponent position = Mappers.pm.get(entity);
            VelocityComponent velocity = Mappers.vm.get(entity);

            position.x += velocity.horizontal * deltaTime;
            position.y += velocity.vertical * deltaTime;
//            System.out.println("x: " + position.x + " y: " + position.y); // just in case things stop working
            Entities.setImageEntity(entity);
        }
    }
}
