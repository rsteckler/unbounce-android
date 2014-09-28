# README

For the most up-to-date conversation, check the [Xda-Developers thread](http://forum.xda-developers.com/xposed/modules/mod-nlpunbounce-reduce-nlp-wakelocks-t2853874).

Please target all pull requests to the "develop" branch.  Thanks! 

## FAQ
### I have a bug or feature request.
Cool. Thanks for helping out! Please go file the issue [here, on BitBucket](https://bitbucket.org/cryptyk/nlpunbounce/issues?status=new&status=open). You can also upvote bugs and feature requests there.

### Why is Play Services such a battery killer:
1) If Google Location Services are turned on, it sets an alarm to wakeup every 60 seconds, check the network location, and broadcast ALARM_WAKEUP_LOCATOR.

2) Everytime ALARM_WAKEUP_LOCATOR fires, Google Search receives the broadcast and grabs an NlpWakeLock and NlpCollectorWakeLock.

3) Those wakelocks hold the keep the device on for 5-15 seconds. This means that the device is actually awake for about 15 seconds per minute, or 25% of the time!


### Why was this such a pain to fix?
First, Google Play Services is obfuscated, making it really hard to tell what's happening in the code. 
Second, the value of 60 seconds for the wakelock is hardcoded in classes2.dex, which isn't accessible by the Xposed framework. I had to follow the usage of that into a place where it's accessible by Xposed and change it there.

### What are the consequences of reducing that alarm frequency?
There aren't likely any side effects. You can completely disable the alarm altogether by disabling Google Location Services. Simply reducing the alarm lets you keep Google Location Services on, but balances the benefit with your battery life.

### Cool, but I have a different battery killer. Can you fix mine?
Totally. I'd like this to become a one-stop shop for battery life, especially as impacted by Google Now (Google Search) and Google Play Services. Give me a dump of your Better Battery Stats and I'll see what I can do.

### I still get a ton of NlpWakeLocks and NlpCollectorWakeLocks
After looking at the code, there looks to be a google bug where any broadcast that includes the word "collector" triggers a Play Services NlpCollectorWakeLock. NlpUnbounce also has settings to temper these wakelocks. Try using that if the alarms by themselves don't solve the problem.

### Why is it called Unbounce?
Debouncing is the process of taking a signal that's "bouncing" and interpreting it as a single "hit". This is a similar concept to what termpering the wakelocks does. It let's one of them come through, then turns them off for a certain amount of time. After I fixed the wakelocks, I realized that the alarms were causing them and I could actually stop the device from even waking up by killing the alarm. If DEbounce is a good term for denying the wakelock, UNbounce seemed like a good term for stopping them at the source.

### Where's the source code?
It's here: [https://bitbucket.org/cryptyk/nlpunbounce](https://bitbucket.org/cryptyk/nlpunbounce)

A couple of things:
 
1) Don't judge. I need to do some cleanup, but wanted to post the repo early so people can take a look at what it does.
  
2) Pull requests are welcome.


### Where's the change log?
It's here: [https://bitbucket.org/cryptyk/nlpunbounce/commits/branch/master](https://bitbucket.org/cryptyk/nlpunbounce/commits/branch/master)

### How can I tell it's working?
First, your battery life should be better when Location Services is turned on.  If you really want to evaluate the change, here's how you can see it in action:

1) Before you install the module, check your alarm frequency by running: adb logcat -v time | grep "ALARM_WAKEUP_LOCATOR\|ALARM_WAKEUP_ACTIVITY_DETEC TION\|NlpWakeLock\|NlpCollectorWakeLock"

2) Note the frequency of those alarms (I was getting them every minute)

3) Install the mod and reboot.

4) To watch the fix in action, run: adb logcat -v time | grep "NlpUnbounce"

5) Watch your ADB for messages showing that Unbounce is patching the intervals.


### What are the best settings?
There is no "best". What you're trying to do is make everything work like it's supposed to without allowing these alarms and wakelocks to kill your battery. It makes sense to start with the least intrusive methods and work your way up. If you really want to tune, do this:

1) Turn the module off and use BBS and record the number of ALARM_WAKEUP_LOCATOR and ALARM_WAKEUP_DETECTION alarms you get per hour. Also record how much time is spent in NlpWakeLock and NlpCollectorWakeLock.
 
2) Turn the module on and set the settings to:
 
    a) ALARM_WAKEUP_LOCATOR = 240

    b) ALARM_WAKEUP_DETECTION = 240

    c) NlpWakeLock = 0 (to disable the modification and use stock settings)

    d) NlpCollectorWakeLock = 0 (to disable the modification and use stock settings)

3) Now go back to step 1. If you're happy with the results, you're all done! If you still have a lot of time spent in NlpWakeLock and NlpCollectorWakeLock, change those settings to 240, also.

### What's an alarm vs. a wakelock, and how are they related with regards to Nlp (Network Location Provider)?
An alarm wakes your device from deep sleep, scheduled using the RTC (Real Time Clock). For example, Google Play Services schedules an alarm every 60 seconds to wake up the device with the ALARM_WAKEUP_LOCATOR alarm. That's why I didn't try to stop the wakelocks at first, but instead tried to stop the alarms. I don't even want the device to wakeup in the first place!
Once the alarm fires, the Nlp service wants to get your location and send it back to google. This requires keeping the device awake for a few (5-15) seconds. To keep the device awake, Google Play Services grabs the NlpWakeLock. This is the second point we can modify the system, but it only puts the device back to sleep once it's already awake.
A couple of other things: This is why I recommend starting with just the alarms. It's less intrusive, and stops the device from waking up at all. Most people will probably be fine with the settings 240, 240, 0, 0. There are other things that can cause an NlpWakeLock, though, including third party apps. If the alarms don't work by themselves for you, you can also slow down the frequency of the wakelocks directly in the settings.