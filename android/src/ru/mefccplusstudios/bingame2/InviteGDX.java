package ru.mefccplusstudios.bingame2;

import android.view.*;
import com.badlogic.gdx.backends.android.*;
import android.os.*;
import android.content.*;

import ru.mefccplusstudios.main.InviteGame;

public class InviteGDX extends AndroidFragmentApplication
{
    MainActivity mact;
    public InviteGame game;
    AndroidDeps deproot = new AndroidDeps();
    public void attach(MainActivity mact) { this.mact=mact; deproot.attach(mact);}
  // @Override
 //public void startActivity(Intent p1)
   // {
        // TODO: Implement this method
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        game = new InviteGame();
        game.deproot = deproot;
        //game.attach(mact);
        //scrman=new ScrMan();
        //scrman.attach(mact);
        return initializeForView(game, cfg);
    }

}
