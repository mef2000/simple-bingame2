package ru.mefccplusstudios.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TestScreen implements Screen{
	public InviteGame root;
	public Stage scene;
	public ImageTextButton tested;
	public ImageTextButton tested2;
	public TestScreen(InviteGame root) {
		this.root = root;
		scene = new Stage(new ExtendViewport(root.global.vw, root.global.vh));
		Gdx.input.setInputProcessor(scene);
		tested = root.myui.obtainBigDefBut("START", false);
		tested2 = root.myui.obtainBigDefBut("Reborn (Ads)", true);
		tested2.setPosition(tested.getWidth()+5, tested.getHeight()+5);
		scene.addActor(tested);
		scene.addActor(tested2);
		scene.addActor(root.myui.makeOneDialog("ТруЪ Инфа о Шурочке", "Он - Шурочка.", false));
	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		scene.act(delta);
		scene.draw();
	}

	@Override
	public void resize(int width, int height) {
		scene.getViewport().update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
