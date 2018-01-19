package com.example.andrey.newtmpclient.activities.address;

public class AddressMvpPresenter {
    private static final String TAG = AddressMvpPresenter.class.getSimpleName();
    private AddressMvpView view;
    private AddressMvpInterActor interActor;

    public AddressMvpPresenter(AddressMvpView view, AddressMvpInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getListFroAdapter() {
        interActor.getListFroAdapter()
                .subscribe(list -> view.setListToAdapter(list));
    }
}
