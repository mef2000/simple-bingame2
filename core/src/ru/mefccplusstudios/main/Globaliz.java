package ru.mefccplusstudios.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Globaliz {
	public int vh = 1920;
	public int vw = 1080;
	public boolean debug = false;
	public String deflang = "en";
	public Preferences prefs = null; 
	public String lang = null;
	public Map<String, String> localizer = new HashMap<>();
	public void SwitchLang(String langz) {
		prefs.putString("lang", langz);
		prefs.flush();
	}
	public void init() {
		prefs = Gdx.app.getPreferences("ru.mefccplusstudios.platform.settings.BINGAME2");
		//lang = prefs.getString("lang", "en");
		lang = Locale.getDefault().getLanguage();
		initLocale(lang);
	}
	public void initLocale(String langt) {
		try {
		JSONParser parser = new JSONParser();
		JSONObject root = (JSONObject)parser.parse(Gdx.files.internal("raw/languages.json").reader("UTF-8"));
		if(!root.keySet().contains(langt)) langt = "en";
		JSONArray arri = (JSONArray)root.get(langt);
		for(int q=0; q<arri.size(); q++) {
			JSONObject objz = (JSONObject)arri.get(q);
			Set<String> keyz = objz.keySet();
			for(String keyname: keyz) localizer.put(keyname, (String)objz.get(keyname));
		}
		}catch(IOException e) {
			System.out.println("IOE: "+e);
		}catch(ParseException e) {
			System.out.println("ParseE: "+e);
		}
	}
}
