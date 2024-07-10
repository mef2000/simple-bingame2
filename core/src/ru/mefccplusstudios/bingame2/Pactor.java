package ru.mefccplusstudios.bingame2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pactor extends Actor{
	 Sprite sp;
	 public Pactor(String path, String name, float x, float y) {
		 sp = new Sprite(new Texture(Gdx.files.internal(path)));
		 super.setName(name);
		 sp.setPosition(x, y);
		 super.setBounds(x, y, sp.getWidth(), sp.getHeight());
	 }
	 @Override
	 public void act(float delta) {
		 super.act(delta);
	 }
	 @Override
	 public void draw(Batch batch, float parentAlpha) {
		 sp.draw(batch, parentAlpha);
	 }
	 public void rSp(float x, float y) {
		 sp.setSize(x, y);
		 super.setBounds(sp.getX(), sp.getY(), sp.getWidth(), sp.getHeight());
	 }
}
