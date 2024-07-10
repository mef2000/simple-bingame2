package ru.mefccplusstudios.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class MyGroup extends Group {
	private MyActor gact;
	public MyGroup(Texture texi) {
		super();
		gact = new MyActor();
		gact.init(texi);
		this.addActor(gact);
	}
	public MyGroup() {
		super();
	}
	public void setAScale(float scl) { gact.setScaler(scl);}
	public MyActor getActor() {return gact;}
	public void setTexture(Texture texi) { gact.init(texi);}
	public void setEnabled(boolean on) { gact.setEnabled(on); }
}
class MyActor extends Actor {
	private Sprite sp;
	private boolean disable=false;
	public void init(Texture texi) {
		if(sp!=null) {
			sp = null;
		}
		if(texi!=null) {
			sp = new Sprite(texi);
			sp.setBounds(0, 0, sp.getWidth(), sp.getHeight());
		}
	}
	public float getOHeight() { return sp.getHeight();}
	public float getOWidth() { return sp.getWidth();}
	public void setScaler(float scl) { sp.scale(scl);sp.setBounds(0, 0, sp.getWidth(), sp.getHeight());}
	public void setEnabled(boolean on) { disable = on;}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if((sp!=null)&&!disable) sp.draw(batch, parentAlpha);
	}
}