package ru.mefccplusstudios.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.mefccplusstudios.bingame2.GenWin;

public class InviteGame extends Game implements ApplicationListener {
	SpriteBatch batch;
	BitmapFont font;
	public Globaliz global = new Globaliz();
	public MyUI myui;
	public GenWin ts;
	public Dependable deproot;
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		global.init();
		myui = new MyUI(this);
		ts = new GenWin(this);
		setScreen(ts);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		//try {
			super.render();
			if (global.debug) {
				batch.begin();
				myui.fonts.get("default").draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 3, 66);
				batch.end();
			}
		//}catch(Exception e) {System.out.println("ERROR OBTAINED: "+e);}
	}
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		myui.killUI();
		super.dispose();
	}
}
