package com.diragi.falloutcd;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity {

    Boolean notifications = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Set todays date and release date
        Calendar release = new GregorianCalendar(2015, 11, 10);
        Calendar today = Calendar.getInstance();

        //Get the amount of days between today and release
        int days = Days.daysBetween(new DateTime(today), new DateTime(release)).getDays();

        //Subtract 30 because something's fucky
        days = days - 30;

        if (notifications) {

                if (days == 1) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //Notification
                        Notification oneDay = new NotificationCompat.Builder(getApplicationContext())
                                .setCategory(Notification.CATEGORY_MESSAGE)
                                .setContentTitle("Fallout 4 Countdown")
                                .setContentText("Fallout 4 comes out tomorrow!")
                                .setSmallIcon(R.mipmap.ic_noti)
                                .setAutoCancel(true)
                                .setVisibility(Notification.VISIBILITY_PUBLIC)
                                .build();

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, oneDay);
                    }

                }

                if (days == 30) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //Notification
                        Notification oneDay = new NotificationCompat.Builder(getApplicationContext())
                                .setCategory(Notification.CATEGORY_MESSAGE)
                                .setContentTitle("Fallout 4 Countdown")
                                .setContentText("Fallout 4 comes out in 30 days!")
                                .setSmallIcon(R.mipmap.ic_noti)
                                .setAutoCancel(true)
                                .setVisibility(Notification.VISIBILITY_PUBLIC)
                                .build();

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, oneDay);
                    }

                }

                if (days == 7) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //Notification
                        Notification oneDay = new NotificationCompat.Builder(getApplicationContext())
                                .setCategory(Notification.CATEGORY_MESSAGE)
                                .setContentTitle("Fallout 4 Countdown")
                                .setContentText("Fallout 4 comes out in 1 week!")
                                .setSmallIcon(R.mipmap.ic_noti)
                                .setAutoCancel(true)
                                .setVisibility(Notification.VISIBILITY_PUBLIC)
                                .build();

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, oneDay);
                    }

                }
            }

        //Get the amount of hours between today and release
        int hours = Hours.hoursBetween(new DateTime(today), new DateTime(release)).getHours();
        //Subtract 30 days (in hours) because something is fucky
        hours = hours - (30*24);
        //Subtract the amount of days that have already passed
        hours = hours - (days*24);

        //Do the same but for minutes and seconds
        int min = Minutes.minutesBetween(new DateTime(today), new DateTime(release)).getMinutes();
        min = min - (30 * 1440);
        min = min - (days *1440) - (hours * 60);

        int sec = Seconds.secondsBetween(new DateTime(today), new DateTime(release)).getSeconds();
        sec = sec - (30 * 86400);
        sec = sec % 60;

        //Get the textview
        final TextView countDown = (TextView)findViewById(R.id.days);
        //Create the string to pass to the textView
        String textView = String.format("Days: %s Hours: %s Minutes: %s Seconds: %s", days, hours, min, sec);
        //Set the string to the textView
        countDown.setText(textView);


        int millisToGo = (sec*1000)+(min*1000*60)+(hours*1000*60*60)+(days*1000*60*60*24);
        Log.d("millistogo:", String.valueOf(millisToGo));

        //TODO: Update every second
        new CountDownTimer(millisToGo, 1000) {

            @Override
            public void onTick(long millis) {

                int sec = (int) (millis / 1000) % 60;
                int min = (int) (millis / (1000 * 60)) % 60;
                int hours = (int) (millis / (1000 * 60 * 60)) % 24;
                int days = (int) (millis / 86400000) + 100;

                //Let's do some string formatting woohoo
                String dayString = "day";
                if (days == 1){
                    dayString = "day";
                } else if (days > 1){
                    dayString = "days";
                }

                String hoursString = "hours";
                if (hours == 1){
                    hoursString = "hour";
                } else if (hours > 1){
                    hoursString = "hours";
                }

                String minString = "minutes";
                if (min == 1){
                    minString = "minute";
                } else if (min > 1){
                    minString = "minutes";
                }

                String secString = "seconds";
                if (sec == 1){
                    secString = "second";
                } else if (sec > 1){
                    secString = "seconds";
                }

                //Create the string to pass to the textView
                String textView = String.format("%s " +dayString +", %s " +hoursString +", %s " +minString +", %s " +secString +"", String.valueOf(days), String.valueOf(hours), String.valueOf(min), String.valueOf(sec));
                //Log.d("String: ", textView);
                //Set the string to the textView
                countDown.setText(textView);

            }

            @Override
            public void onFinish() {

                countDown.setText("TODAY");

            }

        }.start();
        //TODO: Change font size and make the layout less shit
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.noti) {
            if (item.isChecked()){
                //Don't show notifications
                item.setChecked(false);
                this.notifications = false;
            } else {
                item.setChecked(true);
                this.notifications = true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
