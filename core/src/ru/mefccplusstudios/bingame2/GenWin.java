package ru.mefccplusstudios.bingame2;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;

import ru.mefccplusstudios.main.Dependable;
import ru.mefccplusstudios.main.InviteGame;

public class GenWin implements Screen {
	final InviteGame root;
	Stage scene;
	Pactor logo, backer, back2m;
	Group backs = new Group(), logoz = new Group();
	ImageTextButton exit, start, about;
	ImageButton musicon;
	TextureRegionDrawable mon = new TextureRegionDrawable(new Texture(Gdx.files.internal("default/music_on.png"))),
			moff = new TextureRegionDrawable(new Texture(Gdx.files.internal("default/music_off.png"))),
			mdown = new TextureRegionDrawable(new Texture(Gdx.files.internal("default/music_clicked.png")));
	Sound sevent, sndup, snddown, ublock;
	Music bgm;
	Group dialog, layer1, layer2;
	int list = 0;
	
	boolean gamestart = false, bmusicon = true;
	Group downpot, copybanner, bup, uppot;
	Pactor star;
	Label lcount, llevel;
	
	int count = 0, lvl=0; float spawn = 5f; float tspawn=0;

	//boolean adsviewed = false;
	Texture b0, u0, u1, w0, w1;
	ArrayList<Liner> linez = new ArrayList<Liner>();
	int[] potarray = {1, 2, 4, 8, 16, 32, 64, 128, 255};
	Random r = new Random();
	public GenWin(final InviteGame root) {
		this.root = root;
		scene = new Stage(new ExtendViewport(root.global.vw, root.global.vh));
		Gdx.input.setInputProcessor(scene);
		sevent = Gdx.audio.newSound(Gdx.files.internal("snd/event.ogg"));
		sndup = Gdx.audio.newSound(Gdx.files.internal("snd/pressup.ogg"));
		snddown = Gdx.audio.newSound(Gdx.files.internal("snd/pressdown.ogg"));
		ublock = Gdx.audio.newSound(Gdx.files.internal("snd/ublockclick.ogg"));
		bgm = Gdx.audio.newMusic(Gdx.files.internal("mus/airtone.reCreation.mp3"));
		bgm.setLooping(true);
		layer1 = new Group();
		layer2 = new Group();
		layer2.addAction(Actions.alpha(0));
		backer = new Pactor("gfx/backs.jpg", "back", 0, 0);
		backs.addActor(backer);
		backs.scaleBy(root.global.vw/720f-1f);
		backs.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(10f), Actions.fadeIn(10f))));
		logo = new Pactor("gfx/logo.png", "myc_logo", 0, 0);
		logoz.addActor(logo);
		logoz.scaleBy(root.global.vw/720f-1f);
		logoz.setPosition(25, 1280);
		start = root.myui.obtainBigDefBut(root.global.localizer.get("btnstart"), false);
		start.setPosition(284, 1024);
		about = root.myui.obtainBigDefBut(root.global.localizer.get("btnabout"), false);
		about.setPosition(284, 800);
		exit = root.myui.obtainBigDefBut(root.global.localizer.get("btnexit"), false);
		exit.setPosition(284, 576);
		
		musicon = new ImageButton(mon, mdown, moff);//new CheckBox(null, new CheckBox.CheckBoxStyle(mon, moff, root.myui.fonts.get("Default"), Color.WHITE));
		musicon.setPosition(5, 240);
		musicon.addListener(new InputListener(){
			  @Override
			  public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				  sndup.play();
				  if(bmusicon) {
					  bgm.pause();
					  //musicon.getStyle().up = moff;
					  //musicon.setStyle(musicon.getStyle());
					  bmusicon = false;
				  } else {
					  bgm.play();
					 // musicon.getStyle().up = mon;
					 // musicon.setStyle(musicon.getStyle());
					  bmusicon = true;
				  }
				  musicon.setChecked(!bmusicon);
			  }
			  @Override
			  public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			    return true;
			  }
			});
		root.myui.stdbutt.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				sndup.play();
				switch(list) {
				case 101:
					dialog.addAction(Actions.sequence(Actions.fadeOut(0.5f, Interpolation.fade), Actions.run(new Runnable() {
					@Override public void run() {
					dialog.remove();
					}}))); break;
				case 202:
					dialog.addAction(Actions.sequence(Actions.fadeOut(0.5f, Interpolation.fade), Actions.run(new Runnable() {
						@Override public void run() {
						clearStage();
						dialog.remove();
						}}))); break;
				}
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//snddown.play();
				return true;
			}
		});
		root.myui.negbutt.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				sndup.play();
				switch(list) {
				case 202:
					dialog.addAction(Actions.sequence(Actions.fadeOut(0.5f, Interpolation.fade), Actions.run(new Runnable() {
					@Override
					public void run() {
					dialog.remove();
					if(Gdx.app.getType()==ApplicationType.Desktop) {
						Dependable.ADS_VIEWED = false;
						Dependable.ADS_CLOSETTA = true;
						root.deproot.openUrl("https://www.patreon.com/mefccplusstudios");
					}else if(Gdx.app.getType()==ApplicationType.Android){
						root.deproot.showAd(Dependable.ADS_REWARDERED);
					}
					/*root.mact.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if(root.mact.reward != null) {
								root.mact.reward.setVideoListener(new VideoListener() {
									@Override
									public void onVideoCompleted() {
										adsviewed = true;
										root.mact.closetta = true;
										root.mact.prepRewardedVideo();
									}
								});
								root.mact.reward.showAd();
							} else if(root.mact.rewprep) {
								root.mact.recreward.showAd(new AdDisplayListener() {
									@Override
									public void adHidden(Ad ad) {
										adsviewed = true;
										root.mact.closetta = true;
										System.out.println("ADS CLOSED");
									}
									@Override
									public void adDisplayed(Ad ad) {
										//System.out.println("ADS SHOWED");
										//adsviewed = true;
										//root.mact.closetta = true;
									}
									@Override
									public void adClicked(Ad ad) {
										//adsviewed = true;
										//root.mact.closetta = true;
										//System.out.println("ADS CLICKED");
									}
									@Override
									public void adNotDisplayed(Ad ad) {
										clearStage();
										root.mact.prepRewardedVideo();
										//System.out.println("ADS ERROR");
										Toast.makeText(root.mact, root.global.localizer.get("adsproblem"), Toast.LENGTH_LONG).show();
									}
								});
							}else{
								clearStage();
								root.mact.prepRewardedVideo();
								Toast.makeText(root.mact, root.global.localizer.get("adsproblem"), Toast.LENGTH_LONG).show();
							}
					}});*/
						}})));
				}
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//snddown.play();
				return true;
			}
		});
		start.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				sndup.play();
				layer1.clearActions();
				layer1.addAction(Actions.sequence(Actions.fadeOut(1f, Interpolation.fade), Actions.run(new Runnable() {
					@Override public void run() {
						layer1.remove();
						layer2.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f, Interpolation.fade), Actions.run(new Runnable() {
							@Override public void run() {
								gamestart = true;
							}})
								));
			
					}})));
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//snddown.play();
				return true;
			}
		});
		about.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				sndup.play();
				//sevent.play();
				dialog = root.myui.makeOneDialog(root.global.localizer.get("gatitle"), root.global.localizer.get("aboutext"), true);
				dialog.clearActions();
				dialog.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f, Interpolation.fade)));
				list = 101;
				layer1.addActor(dialog);
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//snddown.play();
				return true;
			}
		});
		exit.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				sndup.play();
				Gdx.app.exit();
				//if(Gdx.app.getType()==ApplicationType.Android){
				//	root.deproot.showAd(Dependable.ADS_REWARDERED);
				//}
				/*root.mact.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(root.mact.adsprep) {
							root.mact.exitable.showAd(new AdDisplayListener() {
								@Override
								public void adHidden(Ad ad) {
									root.mact.finish();
								}
								@Override
								public void adDisplayed(Ad ad) {
								}
								@Override
								public void adClicked(Ad ad) {
									root.mact.finish();
								}
								@Override
								public void adNotDisplayed(Ad ad) {
									root.mact.finish();
								}
							});
						}else{ root.mact.finish();}
					}
				});*/
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//snddown.play();
				return true;
			}
		});
		layer1.addActor(backs);
		layer1.addActor(logoz);
		layer1.addActor(exit);
		layer1.addActor(about);
		layer1.addActor(start);
		scene.addActor(layer2);
		scene.addActor(layer1);
		
		back2m = new Pactor("gfx/bg2m.jpg", "back2m", 0, 0);
		layer2.addActor(back2m);
		downpot = new Group();
		downpot.addActor(new Pactor("gfx/down_pot.png", "downpot", 0, 0));
		downpot.scaleBy(root.global.vw/720f-1f);
		downpot.setPosition(0, 240);
		layer2.addActor(downpot);
		copybanner = new Group();
		copybanner.addActor(new Pactor("gfx/copybanner.png", "copypot", 0, 0));
		//copybanner.scaleBy(root.global.vw/720f-1f);
		copybanner.setPosition(0, 0);
		copybanner.setScale(1.5f, 6f);
		layer2.addActor(copybanner);
		uppot = new Group();
		uppot.addActor(new Pactor("gfx/up_pot.png", "uppot", 0, 0));
		uppot.scaleBy(root.global.vw/720f-1f);
		uppot.setPosition(0, 1757);
		layer2.addActor(uppot);
		bup = new Group();
		bup.addActor(new Pactor("gfx/bup.png", "buppot", 0, 0));
		bup.scaleBy(root.global.vw/720f-1f);
		bup.setPosition(0, 1840);
		layer2.addActor(bup);
		star = new Pactor("gfx/star.png", "star", 7, 1843);
		layer2.addActor(star);
		lcount = new Label(root.global.localizer.get("count"), root.myui.labelst32w);
		lcount.setPosition(90, 1843);
		lcount.setText(root.global.localizer.get("count")+count);
		layer2.addActor(lcount);
		llevel = new Label(root.global.localizer.get("count"), root.myui.labelst32w);
		llevel.setText(root.global.localizer.get("level")+lvl);
		llevel.setPosition(1060-llevel.getPrefWidth(), 1843);
		layer2.addActor(llevel);
		layer2.addActor(musicon);
		u0 = new Texture(Gdx.files.internal("gfx/u0.png"));
		b0 = new Texture(Gdx.files.internal("gfx/b0.png"));
		w0 = new Texture(Gdx.files.internal("gfx/w0.png"));
		w1 = new Texture(Gdx.files.internal("gfx/w1.png"));
		u1 = new Texture(Gdx.files.internal("gfx/u1.png"));
		}
//This is method of constructor - use as general create() function
	@Override
	public void hide() { bgm.pause();}
	@Override
	public void resize(int width, int height) { scene.getViewport().update(width, height);}
	@Override
	public void pause() {
		bgm.pause();
	}
	@Override
	public void resume() { bgm.play();}
	@Override
	public void render(float delta) {
		try {
			scene.act(delta);
			scene.draw();
			if (gamestart) {
				lvl = Math.round(count / 1000f);
				tspawn = tspawn + delta;
				lcount.setText(root.global.localizer.get("count") + count);
				llevel.setText(root.global.localizer.get("level") + lvl);
				llevel.setPosition(1060 - llevel.getPrefWidth(), 1843);
				if (linez.size() == 0) {
					Liner tline = new Liner(this);
					tline.prepBlocks(spawnInt());
					linez.add(tline);
					layer2.addActor(tline);
					tspawn = 0;
				} else if (tspawn > spawn) {
					if (linez.size() + 1 > 11) {
						gamestart = false;
						tspawn = 0;
						sevent.play();
						if(Gdx.app.getType()==ApplicationType.Desktop) {
							dialog = root.myui.makeDualDialog(root.global.localizer.get("gotitle"), root.global.localizer.get("yourcount") + count + "\n" + root.global.localizer.get("yourlvl") + lvl + "\n" +
									root.global.localizer.get("maxcount") + root.global.prefs.getInteger("maxcount", 0) + "\n" + root.global.localizer.get("maxlvl") + root.global.prefs.getInteger("maxlvl", 0), false, root.global.localizer.get("btnexitz"), root.global.localizer.get("btnsupport"));
						}else if(Gdx.app.getType()==ApplicationType.Android){
							dialog = root.myui.makeDualDialog(root.global.localizer.get("gotitle"), root.global.localizer.get("yourcount") + count + "\n" + root.global.localizer.get("yourlvl") + lvl + "\n" +
									root.global.localizer.get("maxcount") + root.global.prefs.getInteger("maxcount", 0) + "\n" + root.global.localizer.get("maxlvl") + root.global.prefs.getInteger("maxlvl", 0), false, root.global.localizer.get("btnexitz"), root.global.localizer.get("btnads"));
						}
						dialog.clearActions();
						dialog.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f, Interpolation.fade)));
						list = 202;
						layer2.addActor(dialog);
					} else {
						Liner tline = new Liner(this);
						tline.prepBlocks(spawnInt());
						linez.add(tline);
						tspawn = 0;
						layer2.addActor(tline);
					}
				}
				for (int i = 0; i < linez.size(); i++) {
					linez.get(i).setPosition(0, 380 + i * 125);
				}
				if (lvl > 8) spawn = 1.0f - 4 / (5 - lvl);
				else spawn = 5 - 0.0415f * lvl * lvl;
			} else if ((Dependable.ADS_CLOSETTA) && (!Dependable.ADS_VIEWED)) {
				clearStage();
				Dependable.ADS_CLOSETTA = false;
			} else if ((Dependable.ADS_CLOSETTA) && (Dependable.ADS_VIEWED)) {
				Dependable.ADS_CLOSETTA = false;
				Dependable.ADS_VIEWED = false;
				for (int k = 1; k < 6; k++) {
					int index = r.nextInt(k);
					linez.get(index).remove();
					linez.remove(index);
				}
				gamestart = true;
				tspawn = 0;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Override
	public void show() {
		Gdx.graphics.setTitle("BinGAME 2 S2021.08.25");
		Gdx.input.setInputProcessor(scene);
		bgm.play();
	}
	@Override
	public void dispose() {
	}
	
	public void initLayer1() {
		
	}
	public void clearStage() {
		if(root.global.prefs.getInteger("maxlvl", 0)<lvl) root.global.prefs.putInteger("maxlvl", lvl);
		if(root.global.prefs.getInteger("maxcount", 0)<count) root.global.prefs.putInteger("maxcount", count);
		count = 0; lvl=0; spawn = 5f; tspawn=0; for(Liner tl: linez) tl.remove(); linez.clear(); gamestart = true;
		root.global.prefs.flush();
	}
	public int spawnInt() {
		if(lvl>=12) {
			return 1+(int)(Math.random()*255);
		} else if((lvl<=11)&(lvl>=9))	{
			int g = potarray[r.nextInt(8)]+r.nextInt(2)-1;
			if(g==256) g=255; else if(g==0) g=1;
			return g;
		}else if((lvl>=6)&(lvl<=8)) {
			int g = potarray[r.nextInt(8)]+r.nextInt(2);
			if(g==256) g=255; else if(g==0) g=1;
			return g;
		} else return potarray[r.nextInt(8)];
	}
}
