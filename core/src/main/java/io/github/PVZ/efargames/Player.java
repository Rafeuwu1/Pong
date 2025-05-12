package io.github.PVZ.efargames;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.PVZ.efargames.components.*;
import io.github.PVZ.efargames.statics.Entities;
import io.github.PVZ.efargames.statics.Mappers;

import java.util.ArrayList;

public class Player {
    private Entity player;

    public Player(Sprite sprite, int newID) {
        player = new Entity();
        addComponents();
        Mappers.plm.get(player).ID = newID;

        if(Mappers.plm.get(player).ID == 0) {
            Mappers.pm.get(player).x = 40;
        }
        else{
            Mappers.pm.get(player).x = 1250;
        }

        Mappers.pm.get(player).y = 750;

        Mappers.vm.get(player).vertical = 0;
        Mappers.vm.get(player).horizontal = 0;

        Mappers.sm.get(player).width = 20;
        Mappers.sm.get(player).height = 200;

        Mappers.im.get(player).sprite = sprite;
        Entities.setImageEntity(player);
    }

    public Entity getPlayer() {
        return player;
    }


    private void addComponents() {
        player.add(new PositionComponent());
        player.add(new ImageComponent());
        player.add(new SizeComponent());
        player.add(new PlayerComponent());
        player.add(new VelocityComponent());
    }

}
