package com.example.andrey.newtmpclient.fragments.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.dialogs.directions.DirectionsFragment;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.fragments.map.di.MapNewComponent;
import com.example.andrey.newtmpclient.fragments.map.di.MapNewModule;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private UsersManager usersManager = UsersManager.INSTANCE;
    private ProgressDialog dialog;
    private Date calDate = new Date();

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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Карта");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getUsersCoordes();
        if(UsersManager.INSTANCE.getUser().getLogin().equals("АВ")){
            setHasOptionsMenu(true);
        }
    }



    void setDialogTitleAndText(String title, String message) {
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

    private void updateUI(List<UserCoords> userCoordsList) {
        if (map == null) {
            return;
        }
        map.clear();
        for (int i = 0; i < userCoordsList.size(); i++) {
            UserCoords userCoords = userCoordsList.get(i);
            LatLng point = new LatLng(userCoords.getLat(), userCoords.getLog());
            String login = usersManager.getUserById(userCoords.getUserId()).getLogin();
            MarkerOptions mark = new MarkerOptions().position(point).title(login);
            map.addMarker(mark);
        }
        LatLng myPoint = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint)
                .title(usersManager.getUser().getLogin() + " это я");
        map.addMarker(myMarker);

        //draw myself on map
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(myPoint)
//                .build();

//        int margin = 30;
//        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
//        map.setOnMapLoadedCallback(() -> map.moveCamera(update));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void drawDirections(List<UserCoords> userCoordes) {
        LatLng latLng;
        map.clear();
        for (UserCoords userCoords : userCoordes) {
//            leg.getStartLocation();
            latLng = new LatLng(userCoords.getLat(),
                    userCoords.getLog());
            MarkerOptions startMarkerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_marker))
                    .title(userCoords.getTs());
            map.addMarker(startMarkerOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
        }

//        drawPrimaryLinePath(PolyUtil.decode(
//                routeResponse.getRoutes().get(0).getOverviewPolyline().getPoints()), map);
    }

    private void drawPrimaryLinePath(List<LatLng> mPoints, GoogleMap mGoogleMap) {

        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(R.color.colorAccent);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        LatLng whereUserMarker = null;
        for (int i = 0; i < mPoints.size(); i++) {
//            MarkerOptions startMarkerOptions = new MarkerOptions()
//                    .position(mPoints.get(i))
//                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_marker))
//                    .title("was here");
//            mGoogleMap.addMarker(startMarkerOptions);


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
//                whereUserMarker = startMarkerOptions.getPosition();
//            }
            line.add(mPoints.get(i));
            latLngBuilder.include(mPoints.get(i));
        }
        mGoogleMap.addPolyline(line);

//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(whereUserMarker, 10f));

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

    @Override
    public void notSuccessAuth() {
        if(getContext()!=null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_admin_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_directions_settings:
                DirectionsFragment directionsFragment = new DirectionsFragment();
                directionsFragment.setCalDate(calDate);
                directionsFragment.setUserDateGetter((user, date) -> {
                    Log.i(TAG, "onOptionsItemSelected: " + user + " date " + date);
                    calDate = date;
                    String dateStr = new SimpleDateFormat("yyyy.MM.dd").format(calDate)+" 00:00 AM";
                    presenter.getDirections(user, dateStr);
                });
                directionsFragment.show(getFragmentManager(), "directions");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
