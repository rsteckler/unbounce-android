package com.ryansteckler.nlpunbounce.models;

import android.content.Context;

import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static com.ryansteckler.nlpunbounce.helpers.LocaleHelper.getFormattedTime;

/**
 * Created by rsteckler on 9/10/14.
 */
public class WakelockStats extends BaseStats implements Serializable {

    private WakelockStats() {
        setType("wakelock");
    }

    public WakelockStats(String wakeLockName, int uId) {

        setType("wakelock");
        setName(wakeLockName);
        setUid(uId);

    }

    private long mAllowedDuration;

    public long getAllowedDuration() {
        return mAllowedDuration;
    }

    public void setAllowedDuration(long duration) {
        synchronized (this) {
            this.mAllowedDuration = duration;
        }
    }

    public long addDurationAllowed(long duration) {
        synchronized (this) {
            mAllowedDuration += duration;
        }
        return mAllowedDuration;
    }

    public String getDurationAllowedFormatted() {
        long allowedTime = mAllowedDuration;

        long days = TimeUnit.MILLISECONDS.toDays(allowedTime);
        allowedTime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(allowedTime);
        allowedTime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(allowedTime);
        allowedTime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(allowedTime);

        /*StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");*/

        // String strLog =days + "d:" +hours +"h:"+minutes +"m:"+seconds +"s" ;

        // return (sb.toString());

        //return strLog;
        return getFormattedTime(days, hours, minutes, seconds);
    }

    public long getBlockedDuration() {
        //Determine the average alive time of a wakelock.
        if (getAllowedCount() == 0)
            return 0;
        long averageTime = mAllowedDuration / getAllowedCount();
        //Now multiply that by the number we've blocked.
        long blockedTime = averageTime * getBlockCount();

        return blockedTime;
    }

    public String getDurationBlockedFormatted() {

        long blockedTime = getBlockedDuration();

        long days = TimeUnit.MILLISECONDS.toDays(blockedTime);
        blockedTime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(blockedTime);
        blockedTime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(blockedTime);
        blockedTime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(blockedTime);

        /*StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");
*/

        return getFormattedTime(days, hours, minutes, seconds);
        // String strLog =days + "d:" +hours +"h:"+minutes +"m:"+seconds +"s" ;

        //return (sb.toString());
        // return strLog;
    }

    @Override
    public String getDerivedPackageName(Context ctx) {
        if (null == getDerivedPackageName()) {
            UidNameResolver resolver = UidNameResolver.getInstance(ctx);
            String packName = resolver.getNameForUid(this.getUid());
            if (null != packName) {
                setDerivedPackageName(packName);
            } else {
                setDerivedPackageName("Unknown");
            }
        }
        return getDerivedPackageName();
    }

}
