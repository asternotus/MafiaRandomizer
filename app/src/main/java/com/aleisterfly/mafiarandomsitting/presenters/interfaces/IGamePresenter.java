package com.aleisterfly.mafiarandomsitting.presenters.interfaces;

import com.aleisterfly.mafiarandomsitting.models.Player;

import java.util.List;

public interface IGamePresenter extends IPresenter {
    List<Player> getPlayersFromDatabase();
    void shufflePlayers();
    void updatePlayerData(int position);
}
