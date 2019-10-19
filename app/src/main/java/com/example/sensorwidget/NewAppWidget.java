package com.example.sensorwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider implements SensorEventListener {

    // Name of shared preferences file & key
    private static final String SHARED_PREF_FILE =
            "com.example.android.appwidgetsample";
    private static final String COUNT_KEY = "count";
    private static SensorManager sensorManager;
    private static Sensor proximitySensor;
    private static Sensor lightSensor;
    private static SensorEventListener proximitySensorListener;
    private static SensorEvent sensorEvent;
    private static CharSequence widgetText;
    private static CharSequence widgetTextLight;
    // private String widgetText;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.appwidget_id, widgetText);
        views.setTextViewText(R.id.id_light, widgetTextLight);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        sensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (proximitySensor == null) {
            Toast.makeText(context, "Proximity sensor is not available !", Toast.LENGTH_LONG).show();
            finish();
        }

        if (lightSensor == null) {
            Toast.makeText(context, "Light sensor is not available !", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void finish() {
    }

    @Override
    public void onEnabled(Context context) {


        // Enter relevant functionality for when the first widget is created
        // Get an instance of the sensor manager.
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //widgetText="Proximity : "+sensorEvent.values[0];
        //widgetTextLight="Light : "+sensorEvent.values[0];
        //Log log = null;
        //log.i("Pesan",sensorEvent+ "%");

        int sensorType = sensorEvent.sensor.getType();

        // The new data value of the sensor.  Both the light and proximity
        // sensors report one value at a time, which is always the first
        // element in the values array.
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                // Set the light sensor text view to the light sensor string
                // from the resources, with the placeholder filled in.
                widgetText="Proximity : "+currentValue;
                break;
            case Sensor.TYPE_PROXIMITY:
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                widgetTextLight="Light : "+currentValue;
                break;
            default:
                // do nothing
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

