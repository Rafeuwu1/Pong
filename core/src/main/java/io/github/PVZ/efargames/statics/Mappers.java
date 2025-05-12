package io.github.PVZ.efargames.statics;

import com.badlogic.ashley.core.ComponentMapper;
import io.github.PVZ.efargames.components.*;

public class Mappers { // used to simplify access of different entity components
    public static final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);
    public static final ComponentMapper<ImageComponent> im = ComponentMapper.getFor(ImageComponent.class);
    public static final ComponentMapper<PlayerComponent> plm = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<WinComponent> wm = ComponentMapper.getFor(WinComponent.class);
    public static final ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);

}
