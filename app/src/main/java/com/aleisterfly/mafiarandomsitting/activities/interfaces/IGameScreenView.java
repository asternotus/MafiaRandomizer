package com.aleisterfly.mafiarandomsitting.activities.interfaces;

import com.aleisterfly.mafiarandomsitting.models.Player;

import java.util.List;

public interface IGameScreenView extends IScreenView {
    void updateAdapter(List<Player> players);
}
