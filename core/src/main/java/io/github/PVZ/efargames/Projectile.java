package io.github.PVZ.efargames;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.PVZ.efargames.components.*;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Mappers;

public class Projectile {
    private Entity projectile;

    public Projectile(Sprite sprite) {
        projectile = new Entity();
        addComponents();

        Mappers.pm.get(projectile).x = 0;
        Mappers.pm.get(projectile).y = 500;

        Mappers.sm.get(projectile).width = 10;
        Mappers.sm.get(projectile).height = 10;

        Mappers.vm.get(projectile).horizontal = 200.0f;
        Mappers.vm.get(projectile).vertical = 500.0f;

        Mappers.im.get(projectile).sprite = sprite;
        Entities.setImageEntity(projectile);
    }
    public Projectile(Sprite sprite, float x, float y) {
        projectile = new Entity();
        addComponents();

        Mappers.pm.get(projectile).x = x;
        Mappers.pm.get(projectile).y = y;

        Mappers.sm.get(projectile).width = 10;
        Mappers.sm.get(projectile).height = 10;

        Mappers.vm.get(projectile).horizontal = 200.0f * (Math.random() < 0.5 ? -1 : 1);
        Mappers.vm.get(projectile).vertical = (int)(Math.random() * 150);

        Mappers.im.get(projectile).sprite = sprite;
        Entities.setImageEntity(projectile);

    }


    private void addComponents() {
        projectile.add(new PositionComponent());
        projectile.add(new VelocityComponent());
        projectile.add(new ImageComponent());
        projectile.add(new SizeComponent());
    }

    public Entity getProjectile() {
        return  projectile;
    }
}
