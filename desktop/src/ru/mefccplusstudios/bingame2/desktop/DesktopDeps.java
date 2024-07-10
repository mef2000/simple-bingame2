package ru.mefccplusstudios.bingame2.desktop;

import com.badlogic.gdx.Gdx;

import ru.mefccplusstudios.main.Dependable;

public class DesktopDeps extends Dependable {
	@Override public void openUrl(String surl) {
		Gdx.net.openURI(surl);
	}

}
