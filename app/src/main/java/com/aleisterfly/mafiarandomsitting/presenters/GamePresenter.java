package com.aleisterfly.mafiarandomsitting.presenters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import android.util.Log;

import com.aleisterfly.mafiarandomsitting.MafiaApp;
import com.aleisterfly.mafiarandomsitting.activities.interfaces.IGameScreenView;
import com.aleisterfly.mafiarandomsitting.db.PlayerDB;

import com.aleisterfly.mafiarandomsitting.models.Player;
import com.aleisterfly.mafiarandomsitting.presenters.interfaces.IGamePresenter;

import org.reactivestreams.Subscription;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GamePresenter extends ScreenPresenter<IGameScreenView> implements IGamePresenter {

    private final String LOG_TAG = GamePresenter.class.getSimpleName();

    private PlayerDB db;

    private final int PLAYERS_NUMBER = 10;

    private List<Player> players;

    private IGameScreenView iGameScreenView;

    public GamePresenter(IGameScreenView screenView) {
        super(screenView);
        iGameScreenView = getScreenView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Log.d(LOG_TAG, "paused observing lifecycle.");
    }

    @Override
    public List<Player> getPlayersFromDatabase() {

        db = MafiaApp.getInstance().getPlayerDB();

        if(db.playerDao().getAll().isEmpty()) {
            for (int i = 0; i < PLAYERS_NUMBER; i++) {
                addPlayer(i);
            }
        }

        players = db.playerDao().getAll();

        return players;
    }

    @Override
    public void shufflePlayers() {
        Collections.shuffle(players);

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i);
            db.playerDao().update(players.get(i));
        }
    }

    @Override
    public void updatePlayerData(int position) {
        updatePlayer(position);
    }

    public void clearDB(){
        List<Player> players = db.playerDao().getAll();

        for(Player p : players) {
            db.playerDao().delete(p);
        }
    }

    public void addPlayer(int position){
        Player player = new Player(position);
        Completable.fromAction(() -> db.playerDao().insert(player))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("GameActivity", "onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("GameActivity", "onComplete");
                        players.add(player);
                        iGameScreenView.updateAdapter(players);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("GameActivity", "onError");
                    }
                });
    }

    public void updatePlayer(int position){
        Completable.fromAction(() -> db.playerDao().update(players.get(position)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("GameActivity", "onSubscribeUpdate");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("GameActivity", "onCompleteUpdate");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("GameActivity", "onErrorUpdate");
                    }
                });
    }
}
