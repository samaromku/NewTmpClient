package com.example.andrey.newtmpclient.dialogs.directions;

public class DirectionsPresenter {
    private static final String TAG = DirectionsPresenter.class.getSimpleName();
    private DirectionsView view;
    private DirectionsInterActor interActor;

    public DirectionsPresenter(DirectionsView view, DirectionsInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getListFroAdapter() {
        interActor.getListFroAdapter()
                .subscribe(list -> view.setListToAdapter(list));
    }

    void onUserClick(int position) {
        interActor.onUserClick(position)
                .subscribe(() -> view.updateAdapterPosition(position));
    }

    void getSelected() {
        interActor.getSelected()
                .subscribe(user -> {
                            view.hideDialog();
                            view.setUserDateToActivity(user);
                        }, Throwable::printStackTrace,
                        () -> {
                        });
    }
}
