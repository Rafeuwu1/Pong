package io.github.PVZ.efargames.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Families;
import io.github.PVZ.efargames.statics.Mappers;

public class PointSystem extends EntitySystem { // resets game if points are gained
    private Entity game;
    private Entity projectile;
    private int scoreP1 = 0;
    private int scoreP2 = 0;

    public PointSystem() {}

    public void addedToEngine(Engine engine) {
        game = engine.getEntitiesFor(Families.game).get(0);
        projectile = engine.getEntitiesFor(Families.projectile).get(0);
    }

    public void update(float deltaTime) {
        if(Mappers.gm.get(game).scorep1 != scoreP1 || Mappers.gm.get(game).scorep2 != scoreP2) {
            Mappers.pm.get(projectile).x = 650;
            Mappers.pm.get(projectile).y = 650;
            Mappers.vm.get(projectile).horizontal = 200.0f * (Math.random() < 0.5 ? -1 : 1);
            Mappers.vm.get(projectile).vertical = (int)(Math.random() * 150);
            scoreP1 = Mappers.gm.get(game).scorep1;
            scoreP2 = Mappers.gm.get(game).scorep2;
        }
    }
}
