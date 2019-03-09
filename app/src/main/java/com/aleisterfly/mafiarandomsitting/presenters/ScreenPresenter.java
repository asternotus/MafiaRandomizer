package com.aleisterfly.mafiarandomsitting.presenters;

import android.arch.lifecycle.ViewModel;

import com.aleisterfly.mafiarandomsitting.activities.interfaces.IScreenView;

public class ScreenPresenter<T extends IScreenView> extends ViewModel {

    private final T screenView;

    public ScreenPresenter(T screenView) {
        this.screenView = screenView;
    }

    public T getScreenView(){
        return screenView;
    }

}
