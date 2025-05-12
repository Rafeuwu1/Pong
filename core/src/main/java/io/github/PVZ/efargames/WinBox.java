package io.github.PVZ.efargames;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.PVZ.efargames.components.*;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Mappers;

public class WinBox { // allows for point gains
    private Entity box;

    public WinBox(Sprite sprite) {
        box = new Entity();
        addComponents();

        Mappers.pm.get(box).x = 200;
        Mappers.pm.get(box).y = 500;

        Mappers.im.get(box).sprite = sprite;
        Entities.setImageEntity(box);
    }
    public WinBox(Sprite sprite, int x, int ID) {
        box = new Entity();
        addComponents();

        Mappers.pm.get(box).x = x;
        Mappers.pm.get(box).y = 0;

        Mappers.sm.get(box).height = 1300;
        Mappers.sm.get(box).width = 40;

        Mappers.wm.get(box).ID = ID;

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
        box.add(new WinComponent());
    }

}
