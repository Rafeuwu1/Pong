package io.github.PVZ.efargames;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.PVZ.efargames.components.*;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Mappers;

public class BoundBox { // rebounds the ball on collision
    private Entity box;

    public BoundBox(Sprite sprite) {
        box = new Entity();
        addComponents();

        Mappers.pm.get(box).x = 200;
        Mappers.pm.get(box).y = 500;

        Mappers.im.get(box).sprite = sprite;
        Entities.setImageEntity(box);
    }
    public BoundBox(Sprite sprite, int x, int y, int width, int height) {
        box = new Entity();
        addComponents();

        Mappers.pm.get(box).x = x;
        Mappers.pm.get(box).y = y;

        Mappers.sm.get(box).height = height;
        Mappers.sm.get(box).width = width;

        Mappers.im.get(box).sprite = sprite;
        Entities.setImageEntity(box);
    }

    public Entity getBox() {
        return box;
    }

    private void addComponents() {
        box.add(new PositionComponent());
        box.add(new ImageComponent());
        box.add(new SizeComponent());
    }

}
