package io.github.PVZ.efargames.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component { // gives id and updates what direction the player is going
    public int ID = 0;
    public boolean up;
    public boolean down;
}
