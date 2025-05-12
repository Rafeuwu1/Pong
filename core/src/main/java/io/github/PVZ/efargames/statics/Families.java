package io.github.PVZ.efargames.statics;

import com.badlogic.ashley.core.Family;
import io.github.PVZ.efargames.components.*;

public class Families {
    public static Family movinBox = Family.all(PositionComponent.class, VelocityComponent.class).get();
    public static Family player = Family.all(PlayerComponent.class).get();
    public static Family rebound = Family.all(PositionComponent.class).exclude(VelocityComponent.class).get();
    public static Family projectile = Family.all(PositionComponent.class, VelocityComponent.class).exclude(PlayerComponent.class).get();
    public static Family winBox = Family.all(WinComponent.class).get();
    public static Family game = Family.all(GameComponent.class).get();
    public static Family image = Family.all(ImageComponent.class).get();
}
