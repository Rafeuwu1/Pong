package io.github.PVZ.efargames.statics;

import com.badlogic.ashley.core.Family;
import io.github.PVZ.efargames.components.*;

public class Families { // families allow for the access of specific entities with obvious qualities
    public static Family movinBox = Family.all(PositionComponent.class, VelocityComponent.class).get(); // simple motion
    public static Family player = Family.all(PlayerComponent.class).get(); // players
    public static Family rebound = Family.all(PositionComponent.class).exclude(VelocityComponent.class).get(); // bounds walls
    public static Family projectile = Family.all(PositionComponent.class, VelocityComponent.class).exclude(PlayerComponent.class).get(); // projectile motion
    public static Family winBox = Family.all(WinComponent.class).get(); // boxes for player point gain
    public static Family game = Family.all(GameComponent.class).get(); // allows for point storage
}
