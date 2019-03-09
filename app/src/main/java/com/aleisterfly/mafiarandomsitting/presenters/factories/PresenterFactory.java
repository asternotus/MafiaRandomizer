package com.aleisterfly.mafiarandomsitting.presenters.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aleisterfly.mafiarandomsitting.activities.interfaces.IGameScreenView;
import com.aleisterfly.mafiarandomsitting.activities.interfaces.IScreenView;
import com.aleisterfly.mafiarandomsitting.presenters.GamePresenter;

public class PresenterFactory implements ViewModelProvider.Factory {

    private final IScreenView screenView;

    public PresenterFactory(IScreenView screenView){
        this.screenView = screenView;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GamePresenter.class)){
            return (T) new GamePresenter((IGameScreenView) screenView);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
