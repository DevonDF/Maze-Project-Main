package com.gdxtemplate.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.devonf.mazeproject.MazeSolver;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = MazeSolver.HEIGHT;
		config.width = MazeSolver.WIDTH;
		new LwjglApplication(new MazeSolver(), config);
	}
}
