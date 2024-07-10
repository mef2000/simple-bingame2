package ru.mefccplusstudios.bingame2.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.mefccplusstudios.main.InviteGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		InviteGame game = new InviteGame();
		DesktopDeps dd = new DesktopDeps();
		game.deproot = dd;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 576;
		config.height = 1024;
		config.resizable = true;
		config.addIcon("default/ic_launcher_32.png", FileType.Internal);
		new LwjglApplication(game, config);
	}
}
