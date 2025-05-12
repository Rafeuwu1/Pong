package io.github.PVZ.efargames.statics;

import com.badlogic.ashley.core.Entity;

public class Entities {
    public static void setImageEntity(Entity entity) {
        Mappers.im.get(entity).sprite.setBounds(Mappers.pm.get(entity).x, 1300 - 50 - Mappers.pm.get(entity).y,
            Mappers.sm.get(entity).width, Mappers.sm.get(entity).height);
    }
}
