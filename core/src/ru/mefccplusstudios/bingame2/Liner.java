package ru.mefccplusstudios.bingame2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Liner extends Group {
	public GenWin base;
	public UBlock[] arri = new UBlock[8];
	public Liner linka = this;
	Label need, yet;
	int nado=0, total = 0;
	public Liner(GenWin base) {
		super();
		this.base = base;
		need = new Label("", base.root.myui.labelst32w);
		yet = new Label("", base.root.myui.labelst32w);
		for(int q=0; q<arri.length; q++) {
			arri[q] = new UBlock();
			arri[q].sp.setPosition(145.5f+q*99, 0);
			arri[q].setBounds(145.5f+q*99, 0, 96, 119);
			addActor(arri[q]);
		}
		yet.setPosition(940f, 60);
		addActor(yet);
		addActor(need);
	}
	public void prepBlocks(int nnado) {
		nado = nnado;
		total = 0;
		need.setText(nado);
		need.setPosition(135f-need.getPrefWidth(), 60);
		yet.setText(total);
		for(int i=0; i<8; i++) {
			arri[i].state = 0;
			arri[i].sp.setTexture(base.b0);;
			arri[i].winned = false;
		}
		
	}
	public void reCalc() {
		for(int i=0; i<8; i++) {
			if(arri[i].state==1) {
				for(int q=7; q>i; q--) if(arri[q].state==0) arri[q].sp.setTexture(base.u0);
				else arri[q].sp.setTexture(base.u1);
				break;
			} else arri[i].sp.setTexture(base.b0);
		}
		total = 0;
		for(int q=7; q>-1; q--) {
			total = total+arri[q].state*(int)Math.pow(2, 7-q);
		}
		yet.setText(total);
		if(total==nado) {
			for(int i=0; i<8; i++) {
				if(arri[i].state==1) {
					arri[i].sp.setTexture(base.w1);
					//for(int q=7; q>i; q--) if(arri[q].state==0) arri[q].sp.setTexture(base.w0);
					//else arri[q].sp.setTexture(base.w1);
					//break;
				} else arri[i].sp.setTexture(base.w0);
				arri[i].winned = true;
			}
			base.sevent.play();
			base.count = base.count+100;
			base.linez.remove(this);
			base.tspawn = 0;
			addAction(Actions.sequence(Actions.parallel(Actions.fadeOut(2f), Actions.moveBy(1080, 196, 1.5f)), Actions.run(new Runnable() {
				@Override public void run() {
					linka.remove();
				}
			})));
		}
	}
	public void downGrade() {
		if(!(base.count-50<0)) base.count=base.count-50;
	}
	class UBlock extends Actor {
		public int state = 0;
		public Sprite sp;
		public boolean winned = false;
		public UBlock() {
			super();
			sp  = new Sprite(base.b0);
			addListener(new ClickListener() {
		        public void clicked(InputEvent ev, float x, float y) {
		        	if(!winned) {
		        	base.ublock.play();
		        	switch(state) {
		        	case 0:
		        		sp.setTexture(base.u1);
		        		state=1;
		        		reCalc();
		        		break;
		        	case 1:
		        		sp.setTexture(base.b0);
		        		state = 0;
		        		downGrade();
		        		reCalc();
		        		break;
		        	}
		        	}
		        }
			});
		}
		@Override
		public void act(float delta) {
			super.act(delta);
		}
		@Override
		public void draw(Batch b, float parentAlpha) {
			sp.draw(b, parentAlpha);
		}
	}
}
