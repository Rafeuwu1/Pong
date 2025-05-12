package io.github.PVZ.efargames.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.PVZ.efargames.statics.Families;
import io.github.PVZ.efargames.statics.Mappers;

public class CollisionSystem extends EntitySystem {
    private ImmutableArray<Entity> paddles;
    private ImmutableArray<Entity> projectiles;
    private Entity projectile;
    private ImmutableArray<Entity> bounds;
    private ImmutableArray<Entity> winBox;
    private Entity game;
    private int addedVel = 80;

    public CollisionSystem(){}

    public void addedToEngine(Engine engine) {
        paddles = engine.getEntitiesFor(Families.player);
        projectiles = engine.getEntitiesFor(Families.projectile);
        projectile = projectiles.get(0);
        bounds = engine.getEntitiesFor(Families.rebound);
        winBox = engine.getEntitiesFor(Families.winBox);
        game = engine.getEntitiesFor(Families.game).get(0);
    }

    public void update(float deltaTime) {
        for (int i = 0; i < paddles.size(); i++) { // projectile damage
            float paddleX = Mappers.pm.get(paddles.get(i)).x;
            float paddleY = Mappers.pm.get(paddles.get(i)).y;
            float paddleBoundsWidth = Mappers.sm.get(paddles.get(i)).width;
            float paddleBoundsHeight = Mappers.sm.get(paddles.get(i)).height;
            float projectileX = Mappers.pm.get(projectile).x;
            float projectileY = Mappers.pm.get(projectile).y;
            if (projectileX >= paddleX && projectileX <= paddleX + paddleBoundsWidth &&
                projectileY <= paddleY && projectileY >= paddleY - paddleBoundsHeight) {
                Mappers.vm.get(projectile).horizontal = -Mappers.vm.get(projectile).horizontal;
                if (Math.abs(Mappers.vm.get(projectile).horizontal) <= 800) {
                    Mappers.vm.get(projectile).horizontal += addedVel * Math.signum(Mappers.vm.get(projectile).horizontal);
                    if (Mappers.plm.get(paddles.get(i)).up) {
                        Mappers.vm.get(projectile).vertical += -((int) (Math.random() * 100) + 100);
                    } else if (Mappers.plm.get(paddles.get(i)).down) {
                        Mappers.vm.get(projectile).vertical += ((int)(Math.random() * 100) + 100);
                    }
                }
                System.out.println(Mappers.vm.get(projectile).horizontal);
            }
        }
        for (int i = 0; i < bounds.size(); i++) {
            float boundX = Mappers.pm.get(bounds.get(i)).x;
            float boundY = Mappers.pm.get(bounds.get(i)).y;
            float boundWidth = Mappers.sm.get(bounds.get(i)).width;
            float boundHeight = Mappers.sm.get(bounds.get(i)).height;
            float projectileX = Mappers.pm.get(projectile).x;
            float projectileY = Mappers.pm.get(projectile).y;
            if (projectileX >= boundX && projectileX <= boundX + boundWidth &&
                projectileY >= boundY && projectileY <= boundY + boundHeight) {
                Mappers.vm.get(projectile).vertical = -Mappers.vm.get(projectile).vertical;
                Mappers.vm.get(projectile).horizontal += addedVel * Math.signum(Mappers.vm.get(projectile).horizontal);
            }
        }
        for(int i = 0; i < winBox.size(); i++) {
            if (Mappers.pm.get(projectile).x >= Mappers.pm.get(winBox.get(i)).x && Mappers.wm.get(winBox.get(i)).ID == 0) {
                    Mappers.gm.get(game).scorep1++;
                }
            else if(Mappers.pm.get(projectile).x <= Mappers.pm.get(winBox.get(i)).x && Mappers.wm.get(winBox.get(i)).ID == 1) {
                Mappers.gm.get(game).scorep2++;
            }
        }
    }

}
