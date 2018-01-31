package com.example.andrey.newtmpclient.fragments.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.map.RouteResponse;
import com.example.andrey.newtmpclient.fragments.map.di.MapNewComponent;
import com.example.andrey.newtmpclient.fragments.map.di.MapNewModule;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class MapNewFragment extends SupportMapFragment implements MapNewView {
    private static final String TAG = MapNewFragment.class.getSimpleName();
    @Inject
    MapNewPresenter presenter;
    private GoogleMap map;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;
    private Location currentLocation = userCoordsManager.getLocation();
    UsersManager usersManager = UsersManager.INSTANCE;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MapNewComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new MapNewModule(this))).inject(this);
        getMapAsync(googleMap -> {
            map = googleMap;
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        });
        setDialogTitleAndText("Получение координат", PLEASE_WAIT);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Карта");
        }
        presenter.getUsersCoordes();
//        presenter.getDirections();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    void setDialogTitleAndText(String title, String message){
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setTitle(title);
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserCoordes(List<UserCoords> userCoordes) {
        updateUI(userCoordes);
    }

    private void updateUI(List<UserCoords> userCoordsList){
        if(map==null){
            return;
        }
        map.clear();
        for (int i = 0; i < userCoordsList.size(); i++) {
            UserCoords userCoords = userCoordsList.get(i);
            LatLng point = new LatLng(userCoords.getLat(), userCoords.getLog());
            MarkerOptions mark = new MarkerOptions().position(point).title(usersManager.getUserById(userCoords.getUserId()).getLogin());
            map.addMarker(mark);
        }
        LatLng myPoint = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint)
                .title(usersManager.getUser().getLogin()+(int)myPoint.latitude+(int)myPoint.longitude);
        map.addMarker(myMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPoint)
                .build();

        int margin = 30;
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        map.setOnMapLoadedCallback(() -> map.moveCamera(update));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void drawDirections(RouteResponse routeResponse) {
        drawPrimaryLinePath(PolyUtil.decode(
                routeResponse.getRoutes().get(0).getOverviewPolyline().getPoints()), map);
    }

    private void drawPrimaryLinePath(List<LatLng>mPoints, GoogleMap mGoogleMap) {

        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(R.color.colorAccent);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        LatLng whereUserMarker = null;
        for (int i = 0; i < mPoints.size(); i++) {
            MarkerOptions startMarkerOptions = new MarkerOptions()
                    .position(mPoints.get(i))
                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_marker))
                    .title("was here");
            mGoogleMap.addMarker(startMarkerOptions);
//            if (i == 0) {
//                MarkerOptions startMarkerOptions = new MarkerOptions()
//                        .position(mPoints.get(i))
//                        .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_marker))
//                        .title("Ваш заказ здесь");
//                mGoogleMap.addMarker(startMarkerOptions);
//            } else if (i == mPoints.size() - 1) {
//                MarkerOptions endMarkerOptions = new MarkerOptions()
//                        .position(mPoints.get(i))
//                        .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_marker))
//                        .title("Вы здесь");
//                mGoogleMap.addMarker(endMarkerOptions);
                whereUserMarker = startMarkerOptions.getPosition();
//            }
            line.add(mPoints.get(i));
            latLngBuilder.include(mPoints.get(i));
        }
        mGoogleMap.addPolyline(line);

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(whereUserMarker, 10f));

//        int size = getResources().getDisplayMetrics().widthPixels;
//        LatLngBounds latLngBounds = latLngBuilder.build();
//        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
//        mGoogleMap.moveCamera(track);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
