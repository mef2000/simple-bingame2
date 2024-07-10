package ru.mefccplusstudios.main;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MyUI {
	public Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
	public InviteGame root;
	public Texture big_std = new Texture(Gdx.files.internal("default/button_standart_big.png"));
	public Texture big_neg = new Texture(Gdx.files.internal("default/button_negative_big.png"));
	public Texture big_down = new Texture(Gdx.files.internal("default/button_down_big.png"));
	public Texture dhead = new Texture(Gdx.files.internal("default/dialog_head.png"));
	public Texture dbodybig = new Texture(Gdx.files.internal("default/dialog_body_big.png"));
	public Texture dbodysmall = new Texture(Gdx.files.internal("default/dialog_body_small.png"));
	public MyGroup dialog = new MyGroup();
	public MyGroup dialoghead = new MyGroup(dhead);
	public MyGroup dialogbodysmall = new MyGroup(dbodysmall);
	public MyGroup dialogbodybig = new MyGroup(dbodybig);
	ImageTextButton.ImageTextButtonStyle itbstylebig = new ImageTextButton.ImageTextButtonStyle();
	ImageTextButton.ImageTextButtonStyle itbstylebigneg = new ImageTextButton.ImageTextButtonStyle();
	public ImageTextButton stdbutt, negbutt;
	public Label.LabelStyle labelst, labelst32, labelst32w;
	public Label ltitle, lbody;
	public MyUI(InviteGame root) {
		this.root = root;
		String FONT_CHARS = "";
		for ( int i = 0x20; i < 0x7B; i++ ) FONT_CHARS += (char)i;
		for ( int i = 0x401; i < 0x452; i++ ) FONT_CHARS += (char)i;
		initFont(FONT_CHARS, "default/20927.ttf", "default", 64);
		initFont(FONT_CHARS, "default/20927.ttf", "default_32", 40);
		itbstylebig.font = fonts.get("default");
		itbstylebig.fontColor = Color.WHITE;
		itbstylebig.up = new TextureRegionDrawable(big_std);
		itbstylebig.down = new TextureRegionDrawable(big_down);
		itbstylebigneg.font = fonts.get("default");
		itbstylebigneg.fontColor = Color.WHITE;
		itbstylebigneg.up = new TextureRegionDrawable(big_neg);
		itbstylebigneg.down = new TextureRegionDrawable(big_down);
		labelst32 = new Label.LabelStyle(fonts.get("default_32"), Color.BLACK);
		labelst32w = new Label.LabelStyle(fonts.get("default"), Color.WHITE);
		labelst = new Label.LabelStyle(fonts.get("default"), Color.BLACK);
		ltitle = new Label("", labelst);
		lbody = new Label("", labelst32);
		lbody.setWrap(true);
		lbody.setWidth(950);
		ltitle.setY(70);
		dialog.setPosition(0, 0);
		dialog.setBounds(0, 0, 1080, 1920);
		lbody.setAlignment(Align.center);
		//lbody.setDebug(true);
		stdbutt = new ImageTextButton("", itbstylebig);
		negbutt = new ImageTextButton("", itbstylebigneg);
		dialoghead.addActor(ltitle);
	}
	public void initFont(String alphabet, String namefile, String namekey, int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(namefile));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.characters = alphabet;
		fonts.put(namekey, generator.generateFont(parameter));
		generator.dispose();
	}
	public void killUI() {
		for(Object f: fonts.values()) { ((BitmapFont)f).dispose(); } 
	}
	public ImageTextButton obtainBigDefBut(String text, boolean negative) {
		if(negative) return new ImageTextButton(text, itbstylebigneg); else return new ImageTextButton(text, itbstylebig);
	}
	public Group makeOneDialog(String title, String bodytext, boolean isBig) {
		dialog.remove();
		dialog.clearChildren();
		dialogbodybig.remove();
		dialogbodysmall.remove();
		ltitle.setText(title);
		ltitle.setX((1024-ltitle.getPrefWidth())/2);
		lbody.setText(bodytext);
		lbody.setX(37);
		stdbutt.setText("Ok!");
		if(isBig) {
			lbody.setY(900-lbody.getPrefHeight()/2);
			dialogbodybig.addActor(lbody);
			dialoghead.setPosition(28, 1442);
			dialogbodybig.setPosition(28, 546);
			stdbutt.setPosition(284, 405);
			dialog.addActor(dialogbodybig);
		} else {
			lbody.setY(256-lbody.getPrefHeight()/2);
			dialogbodysmall.addActor(lbody);
			dialoghead.setPosition(28, 1122);
			dialogbodysmall.setPosition(28, 866);
			stdbutt.setPosition(284, 725);
			dialog.addActor(dialogbodysmall);
		}
		dialog.addActor(dialoghead);
		dialog.addActor(stdbutt);
		//dialog.setVisible(false);
		return dialog;
	}
	public Group makeDualDialog(String title, String bodytext, boolean isBig, String stdbuttz, String negbuttz) {
		dialog.remove();
		dialog.clearChildren();
		dialogbodybig.remove();
		dialogbodysmall.remove();
		ltitle.setText(title);
		ltitle.setX((1024-ltitle.getPrefWidth())/2);
		lbody.setText(bodytext);
		lbody.setX(37);
		stdbutt.setText(stdbuttz);
		negbutt.setText(negbuttz);
		if(isBig) {
			lbody.setY(900-lbody.getPrefHeight()/2);
			dialogbodybig.addActor(lbody);
			dialoghead.setPosition(28, 1442);
			dialogbodybig.setPosition(28, 546);
			stdbutt.setPosition(25, 405);
			negbutt.setPosition(540, 405);
			dialog.addActor(dialogbodybig);
		} else {
			lbody.setY(256-lbody.getPrefHeight()/2);
			dialogbodysmall.addActor(lbody);
			dialoghead.setPosition(28, 1122);
			dialogbodysmall.setPosition(28, 866);
			stdbutt.setPosition(25, 725);
			negbutt.setPosition(540, 725);
			dialog.addActor(dialogbodysmall);
		}
		dialog.addActor(dialoghead);
		dialog.addActor(stdbutt);
		dialog.addActor(negbutt);
		//dialog.setVisible(false);
		return dialog;
	}
}
