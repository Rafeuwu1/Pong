package io.github.PVZ.efargames.statics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Utils { // generally useful statics for making games
    public static FileHandle getClasspath(String filepath) {
        return Gdx.files.classpath(filepath); // return any file with the name in resource folders to get assets
    }

    public static FileHandle getInternalPath(String filepath) {
        return Gdx.files.internal(filepath); // only has access to folder with application, error if outside folder
    }

    public static FileHandle getLocalPath(String filepath) {
        return Gdx.files.local(filepath); // same as internal but can read and write while being private
    }

    public static FileHandle getExternalPath(String filepath) {
        return Gdx.files.external(filepath); // store large files with writing/reading, when storing external user can edit files
    }
}
