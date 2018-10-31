package com.amapv2.apis;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amapv2.apis.basic.BasicMapActivity;
import com.amapv2.apis.basic.CameraActivity;
import com.amapv2.apis.basic.EventsActivity;
import com.amapv2.apis.basic.LayersActivity;
import com.amapv2.apis.basic.MapOptionActivity;
import com.amapv2.apis.basic.OpenglActivity;
import com.amapv2.apis.basic.ScreenShotActivity;
import com.amapv2.apis.basic.UiSettingsActivity;
import com.amapv2.apis.busline.BuslineActivity;
import com.amapv2.apis.geocoder.GeocoderActivity;
import com.amapv2.apis.location.LocationGPSActivity;
import com.amapv2.apis.location.LocationModeSourceActivity;
import com.amapv2.apis.location.LocationNetworkActivity;
import com.amapv2.apis.location.LocationSourceActivity;
import com.amapv2.apis.offlinemap.OfflineMapActivity;
import com.amapv2.apis.overlay.ArcActivity;
import com.amapv2.apis.overlay.CircleActivity;
import com.amapv2.apis.overlay.GroundOverlayActivity;
import com.amapv2.apis.overlay.MarkerActivity;
import com.amapv2.apis.overlay.PolygonActivity;
import com.amapv2.apis.overlay.PolylineActivity;
import com.amapv2.apis.overlay.TileOverlayActivity;
import com.amapv2.apis.poisearch.PoiAroundSearchActivity;
import com.amapv2.apis.poisearch.PoiKeywordSearchActivity;
import com.amapv2.apis.route.RouteActivity;
import com.amapv2.apis.view.FeatureView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * AMapV2地图demo总汇
 */
public final class MainActivity extends ListActivity {
	private static class DemoDetails {
		private final int titleId;
		private final int descriptionId;
		private final Class<? extends android.app.Activity> activityClass;

		public DemoDetails(int titleId, int descriptionId,
				Class<? extends android.app.Activity> activityClass) {
			super();
			this.titleId = titleId;
			this.descriptionId = descriptionId;
			this.activityClass = activityClass;
		}
	}

	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
			super(context, R.layout.feature, R.id.title, demos);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FeatureView featureView;
			if (convertView instanceof FeatureView) {
				featureView = (FeatureView) convertView;
			} else {
				featureView = new FeatureView(getContext());
			}
			DemoDetails demo = getItem(position);
			featureView.setTitleId(demo.titleId);
			featureView.setDescriptionId(demo.descriptionId);
			return featureView;
		}
	}

	private static final DemoDetails[] demos = {
			new DemoDetails(R.string.basic_map, R.string.basic_description,
					BasicMapActivity.class),
			new DemoDetails(R.string.camera_demo, R.string.camera_description,
					CameraActivity.class),
			new DemoDetails(R.string.events_demo, R.string.events_description,
					EventsActivity.class),
			new DemoDetails(R.string.layers_demo, R.string.layers_description,
					LayersActivity.class),
			new DemoDetails(R.string.mapOption_demo,
					R.string.mapOption_description, MapOptionActivity.class),
			new DemoDetails(R.string.screenshot_demo,
					R.string.screenshot_description, ScreenShotActivity.class),
			new DemoDetails(R.string.opengl_demo, R.string.opengl_description,
					OpenglActivity.class),
			new DemoDetails(R.string.uisettings_demo,
					R.string.uisettings_description, UiSettingsActivity.class),
			new DemoDetails(R.string.polyline_demo,
					R.string.polyline_description, PolylineActivity.class),
			new DemoDetails(R.string.polygon_demo,
					R.string.polygon_description, PolygonActivity.class),
			new DemoDetails(R.string.circle_demo, R.string.circle_description,
					CircleActivity.class),
			new DemoDetails(R.string.marker_demo, R.string.marker_description,
					MarkerActivity.class),
			new DemoDetails(R.string.arc_demo, R.string.arc_description,
					ArcActivity.class),
			new DemoDetails(R.string.groundoverlay_demo,
					R.string.groundoverlay_description,
					GroundOverlayActivity.class),
			new DemoDetails(R.string.tileoverlay_demo,
					R.string.tileoverlay_description, TileOverlayActivity.class),
			new DemoDetails(R.string.geocoder_demo,
					R.string.geocoder_description, GeocoderActivity.class),
			new DemoDetails(R.string.locationsource_demo,
					R.string.locationsource_description,
					LocationSourceActivity.class),
			new DemoDetails(R.string.locationmodesource_demo,
					R.string.locationmodesource_description,
					LocationModeSourceActivity.class),
			new DemoDetails(R.string.locationGPS_demo,
					R.string.locationGPS_description, LocationGPSActivity.class),
			new DemoDetails(R.string.locationNetwork_demo,
					R.string.locationNetwork_description,
					LocationNetworkActivity.class),
			new DemoDetails(R.string.poikeywordsearch_demo,
					R.string.poikeywordsearch_description,
					PoiKeywordSearchActivity.class),
			new DemoDetails(R.string.poiaroundsearch_demo,
					R.string.poiaroundsearch_description,
					PoiAroundSearchActivity.class),
			new DemoDetails(R.string.busline_demo,
					R.string.busline_description, BuslineActivity.class),
			new DemoDetails(R.string.route_demo, R.string.route_description,
					RouteActivity.class),
			new DemoDetails(R.string.offlinemap_demo,
					R.string.offlinemap_description, OfflineMapActivity.class) };


	/**
	 * 需要进行检测的权限数组
	 */
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE
	};

	private static final int PERMISSON_REQUESTCODE = 0;

	/**
	 * 判断是否需要检测，防止不停的弹框
	 */
	private boolean isNeedCheck = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		ListAdapter adapter = new CustomArrayAdapter(
				this.getApplicationContext(), demos);
		setListAdapter(adapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= 23
				&& getApplicationInfo().targetSdkVersion >= 23) {
			if (isNeedCheck) {
				checkPermissions(needPermissions);
			}
		}
	}

	/**
	 *
	 * @param permissions
	 * @since 2.5.0
	 *
	 */
	private void checkPermissions(String... permissions) {
		try {
			if (Build.VERSION.SDK_INT >= 23
					&& getApplicationInfo().targetSdkVersion >= 23) {
				List<String> needRequestPermissonList = findDeniedPermissions(permissions);
				if (null != needRequestPermissonList
						&& needRequestPermissonList.size() > 0) {
					String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
					Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
							int.class});

					method.invoke(this, array, PERMISSON_REQUESTCODE);
				}
			}
		} catch (Throwable e) {
		}
	}


	/**
	 * 获取权限集中需要申请权限的列表
	 *
	 * @param permissions
	 * @return
	 * @since 2.5.0
	 *
	 */
	private List<String> findDeniedPermissions(String[] permissions) {
		List<String> needRequestPermissonList = new ArrayList<String>();
		if (Build.VERSION.SDK_INT >= 23
				&& getApplicationInfo().targetSdkVersion >= 23){
			try {
				for (String perm : permissions) {
					Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
					Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
							String.class);
					if ((Integer)checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
							|| (Boolean)shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
						needRequestPermissonList.add(perm);
					}
				}
			} catch (Throwable e) {

			}
		}
		return needRequestPermissonList;
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
		startActivity(new Intent(this.getApplicationContext(),
				demo.activityClass));
	}



}
