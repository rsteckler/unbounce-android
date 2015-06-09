[1mdiff --git a/.idea/inspectionProfiles/Project_Default.xml b/.idea/inspectionProfiles/Project_Default.xml[m
[1mindex fda496f..84e1a45 100644[m
[1m--- a/.idea/inspectionProfiles/Project_Default.xml[m
[1m+++ b/.idea/inspectionProfiles/Project_Default.xml[m
[36m@@ -7,5 +7,6 @@[m
       <option name="loggerClassName" value="org.apache.log4j.Logger,org.slf4j.LoggerFactory,org.apache.commons.logging.LogFactory,java.util.logging.Logger" />[m
       <option name="loggerFactoryMethodName" value="getLogger,getLogger,getLog,getLogger" />[m
     </inspection_tool>[m
[32m+[m[32m    <inspection_tool class="UnusedParameters" enabled="false" level="WARNING" enabled_by_default="false" />[m
   </profile>[m
 </component>[m
\ No newline at end of file[m
[1mdiff --git a/.idea/vcs.xml b/.idea/vcs.xml[m
[1mindex c8f67ff..94a25f7 100644[m
[1m--- a/.idea/vcs.xml[m
[1m+++ b/.idea/vcs.xml[m
[36m@@ -1,2 +1,6 @@[m
 <?xml version="1.0" encoding="UTF-8"?>[m
[31m-<project version="4" />[m
\ No newline at end of file[m
[32m+[m[32m<project version="4">[m
[32m+[m[32m  <component name="VcsDirectoryMappings">[m
[32m+[m[32m    <mapping directory="$PROJECT_DIR$" vcs="Git" />[m
[32m+[m[32m  </component>[m
[32m+[m[32m</project>[m
\ No newline at end of file[m
[1mdiff --git a/app/manifest-merger-release-report.txt b/app/manifest-merger-release-report.txt[m
[1mindex 4f189aa..db1c63b 100644[m
[1m--- a/app/manifest-merger-release-report.txt[m
[1m+++ b/app/manifest-merger-release-report.txt[m
[36m@@ -1,6 +1,8 @@[m
 -- Merging decision tree log ---[m
 manifest[m
 ADDED from AndroidManifest.xml:2:1[m
[32m+[m	[32mxmlns:android[m
[32m+[m		[32mADDED from AndroidManifest.xml:2:11[m
 	package[m
 		ADDED from AndroidManifest.xml:3:5[m
 		INJECTED from AndroidManifest.xml:0:0[m
[36m@@ -8,8 +10,6 @@[m [mADDED from AndroidManifest.xml:2:1[m
 	android:versionName[m
 		INJECTED from AndroidManifest.xml:0:0[m
 		INJECTED from AndroidManifest.xml:0:0[m
[31m-	xmlns:android[m
[31m-		ADDED from AndroidManifest.xml:2:11[m
 	android:versionCode[m
 		INJECTED from AndroidManifest.xml:0:0[m
 		INJECTED from AndroidManifest.xml:0:0[m
[36m@@ -21,151 +21,449 @@[m [mADDED from AndroidManifest.xml:6:5[m
 		ADDED from AndroidManifest.xml:6:22[m
 uses-permission#android.permission.INTERNET[m
 ADDED from AndroidManifest.xml:7:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-ads:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appinvite:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-wallet:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:21:5[m
 	android:name[m
 		ADDED from AndroidManifest.xml:7:22[m
 uses-permission#android.permission.ACCESS_NETWORK_STATE[m
 ADDED from AndroidManifest.xml:8:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-ads:7.5.0:21:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-nearby:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:20:5[m
 	android:name[m
 		ADDED from AndroidManifest.xml:8:22[m
[32m+[m[32muses-permission#android.permission.RECEIVE_BOOT_COMPLETED[m
[32m+[m[32mADDED from AndroidManifest.xml:9:5[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:9:22[m
 application[m
[31m-ADDED from AndroidManifest.xml:10:5[m
[31m-MERGED from com.google.android.gms:play-services:6.5.87:20:5[m
[31m-MERGED from com.android.support:support-v4:21.0.0:16:5[m
[32m+[m[32mADDED from AndroidManifest.xml:11:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-ads:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appindexing:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appinvite:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appstate:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-cast:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.android.support:mediarouter-v7:22.0.0:22:5[m
[32m+[m[32mMERGED from com.android.support:appcompat-v7:22.0.0:22:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-drive:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-fitness:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-location:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:29:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-games:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-drive:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-gcm:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-identity:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-location:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:29:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:29:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-nearby:7.5.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-panorama:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-plus:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-safetynet:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-wallet:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-identity:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:29:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-wearable:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:22:5[m
 	android:label[m
[31m-		ADDED from AndroidManifest.xml:13:9[m
[32m+[m		[32mADDED from AndroidManifest.xml:14:9[m
 	android:allowBackup[m
[31m-		ADDED from AndroidManifest.xml:11:9[m
[31m-	android:icon[m
 		ADDED from AndroidManifest.xml:12:9[m
[32m+[m	[32mandroid:icon[m
[32m+[m		[32mADDED from AndroidManifest.xml:13:9[m
 	android:theme[m
[31m-		ADDED from AndroidManifest.xml:14:9[m
[32m+[m		[32mADDED from AndroidManifest.xml:15:9[m
 meta-data#xposedmodule[m
[31m-ADDED from AndroidManifest.xml:15:9[m
[31m-	android:value[m
[31m-		ADDED from AndroidManifest.xml:17:13[m
[32m+[m[32mADDED from AndroidManifest.xml:16:9[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:16:13[m
[31m-meta-data#xposedminversion[m
[31m-ADDED from AndroidManifest.xml:18:9[m
[32m+[m		[32mADDED from AndroidManifest.xml:17:13[m
 	android:value[m
[31m-		ADDED from AndroidManifest.xml:20:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:18:13[m
[32m+[m[32mmeta-data#xposedminversion[m
[32m+[m[32mADDED from AndroidManifest.xml:19:9[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:19:13[m
[31m-meta-data#xposeddescription[m
[31m-ADDED from AndroidManifest.xml:21:9[m
[32m+[m		[32mADDED from AndroidManifest.xml:20:13[m
 	android:value[m
[31m-		ADDED from AndroidManifest.xml:23:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:21:13[m
[32m+[m[32mmeta-data#xposeddescription[m
[32m+[m[32mADDED from AndroidManifest.xml:22:9[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:22:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:23:13[m
[32m+[m	[32mandroid:value[m
[32m+[m		[32mADDED from AndroidManifest.xml:24:13[m
 activity#com.ryansteckler.nlpunbounce.SettingsActivity[m
[31m-ADDED from AndroidManifest.xml:25:9[m
[32m+[m[32mADDED from AndroidManifest.xml:26:9[m
 	android:label[m
[31m-		ADDED from AndroidManifest.xml:27:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:28:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:26:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:27:13[m
 receiver#com.ryansteckler.nlpunbounce.ActivityReceiver[m
[31m-ADDED from AndroidManifest.xml:30:9[m
[31m-	android:enabled[m
[31m-		ADDED from AndroidManifest.xml:32:13[m
[32m+[m[32mADDED from AndroidManifest.xml:31:9[m
 	android:exported[m
[32m+[m		[32mADDED from AndroidManifest.xml:34:13[m
[32m+[m	[32mandroid:enabled[m
 		ADDED from AndroidManifest.xml:33:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:31:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:32:13[m
 intent-filter#com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION+com.ryansteckler.nlpunbounce.PUSH_NETWORK_STATS+com.ryansteckler.nlpunbounce.RESET_FILES_ACTION+com.ryansteckler.nlpunbounce.STATS_REFRESHED_ACTION[m
[31m-ADDED from AndroidManifest.xml:34:13[m
[32m+[m[32mADDED from AndroidManifest.xml:35:13[m
 action#com.ryansteckler.nlpunbounce.STATS_REFRESHED_ACTION[m
[31m-ADDED from AndroidManifest.xml:35:17[m
[31m-	android:name[m
[31m-		ADDED from AndroidManifest.xml:35:25[m
[31m-action#com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION[m
 ADDED from AndroidManifest.xml:36:17[m
 	android:name[m
 		ADDED from AndroidManifest.xml:36:25[m
[31m-action#com.ryansteckler.nlpunbounce.RESET_FILES_ACTION[m
[32m+[m[32maction#com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION[m
 ADDED from AndroidManifest.xml:37:17[m
 	android:name[m
 		ADDED from AndroidManifest.xml:37:25[m
[31m-action#com.ryansteckler.nlpunbounce.PUSH_NETWORK_STATS[m
[32m+[m[32maction#com.ryansteckler.nlpunbounce.RESET_FILES_ACTION[m
 ADDED from AndroidManifest.xml:38:17[m
 	android:name[m
 		ADDED from AndroidManifest.xml:38:25[m
[32m+[m[32maction#com.ryansteckler.nlpunbounce.PUSH_NETWORK_STATS[m
[32m+[m[32mADDED from AndroidManifest.xml:39:17[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:39:25[m
 activity#com.ryansteckler.nlpunbounce.MaterialSettingsActivity[m
[31m-ADDED from AndroidManifest.xml:43:9[m
[32m+[m[32mADDED from AndroidManifest.xml:44:9[m
 	android:label[m
[31m-		ADDED from AndroidManifest.xml:45:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:46:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:44:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:45:13[m
 intent-filter#android.intent.action.MAIN+de.robv.android.xposed.category.MODULE_SETTINGS[m
[31m-ADDED from AndroidManifest.xml:46:13[m
[32m+[m[32mADDED from AndroidManifest.xml:47:13[m
 action#android.intent.action.MAIN[m
[31m-ADDED from AndroidManifest.xml:47:17[m
[32m+[m[32mADDED from AndroidManifest.xml:48:17[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:47:25[m
[32m+[m		[32mADDED from AndroidManifest.xml:48:25[m
 category#de.robv.android.xposed.category.MODULE_SETTINGS[m
[31m-ADDED from AndroidManifest.xml:49:17[m
[32m+[m[32mADDED from AndroidManifest.xml:50:17[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:49:27[m
[32m+[m		[32mADDED from AndroidManifest.xml:50:27[m
 activity-alias#com.ryansteckler.nlpunbounce.Settings-Alias[m
[31m-ADDED from AndroidManifest.xml:53:9[m
[31m-	android:enabled[m
[31m-		ADDED from AndroidManifest.xml:55:13[m
[32m+[m[32mADDED from AndroidManifest.xml:54:9[m
 	android:label[m
[31m-		ADDED from AndroidManifest.xml:57:13[m
[31m-	android:targetActivity[m
 		ADDED from AndroidManifest.xml:58:13[m
 	android:icon[m
[32m+[m		[32mADDED from AndroidManifest.xml:57:13[m
[32m+[m	[32mandroid:enabled[m
 		ADDED from AndroidManifest.xml:56:13[m
[32m+[m	[32mandroid:targetActivity[m
[32m+[m		[32mADDED from AndroidManifest.xml:59:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:54:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:55:13[m
 intent-filter#android.intent.action.MAIN+android.intent.category.LAUNCHER[m
[31m-ADDED from AndroidManifest.xml:59:13[m
[32m+[m[32mADDED from AndroidManifest.xml:60:13[m
 category#android.intent.category.LAUNCHER[m
[31m-ADDED from AndroidManifest.xml:62:17[m
[32m+[m[32mADDED from AndroidManifest.xml:63:17[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:62:27[m
[32m+[m		[32mADDED from AndroidManifest.xml:63:27[m
 activity#com.ryansteckler.nlpunbounce.tasker.TaskerActivity[m
[31m-ADDED from AndroidManifest.xml:66:9[m
[32m+[m[32mADDED from AndroidManifest.xml:67:9[m
 	android:label[m
[32m+[m		[32mADDED from AndroidManifest.xml:71:13[m
[32m+[m	[32mandroid:icon[m
 		ADDED from AndroidManifest.xml:70:13[m
 	android:exported[m
[31m-		ADDED from AndroidManifest.xml:68:13[m
[31m-	android:icon[m
 		ADDED from AndroidManifest.xml:69:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:67:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:68:13[m
 intent-filter#com.twofortyfouram.locale.intent.action.EDIT_SETTING[m
[31m-ADDED from AndroidManifest.xml:73:13[m
[32m+[m[32mADDED from AndroidManifest.xml:74:13[m
 action#com.twofortyfouram.locale.intent.action.EDIT_SETTING[m
[31m-ADDED from AndroidManifest.xml:74:17[m
[32m+[m[32mADDED from AndroidManifest.xml:75:17[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:74:25[m
[32m+[m		[32mADDED from AndroidManifest.xml:75:25[m
 receiver#com.ryansteckler.nlpunbounce.tasker.TaskerReceiver[m
[31m-ADDED from AndroidManifest.xml:78:9[m
[31m-	android:enabled[m
[31m-		ADDED from AndroidManifest.xml:80:13[m
[32m+[m[32mADDED from AndroidManifest.xml:79:9[m
 	android:exported[m
[32m+[m		[32mADDED from AndroidManifest.xml:82:13[m
[32m+[m	[32mandroid:enabled[m
 		ADDED from AndroidManifest.xml:81:13[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:79:13[m
[32m+[m		[32mADDED from AndroidManifest.xml:80:13[m
 intent-filter#com.twofortyfouram.locale.intent.action.FIRE_SETTING[m
[31m-ADDED from AndroidManifest.xml:84:13[m
[32m+[m[32mADDED from AndroidManifest.xml:85:13[m
 action#com.twofortyfouram.locale.intent.action.FIRE_SETTING[m
[31m-ADDED from AndroidManifest.xml:85:17[m
[32m+[m[32mADDED from AndroidManifest.xml:86:17[m
 	android:name[m
[31m-		ADDED from AndroidManifest.xml:85:25[m
[32m+[m		[32mADDED from AndroidManifest.xml:86:25[m
[32m+[m[32mreceiver#com.ryansteckler.nlpunbounce.BootReceiver[m
[32m+[m[32mADDED from AndroidManifest.xml:90:9[m
[32m+[m	[32mandroid:enabled[m
[32m+[m		[32mADDED from AndroidManifest.xml:92:13[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:91:13[m
[32m+[m[32mintent-filter#android.intent.action.BOOT_COMPLETED+android.intent.action.QUICKBOOT_POWERON[m
[32m+[m[32mADDED from AndroidManifest.xml:93:13[m
[32m+[m[32maction#android.intent.action.BOOT_COMPLETED[m
[32m+[m[32mADDED from AndroidManifest.xml:94:17[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:94:25[m
[32m+[m[32maction#android.intent.action.QUICKBOOT_POWERON[m
[32m+[m[32mADDED from AndroidManifest.xml:95:17[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:95:25[m
[32m+[m[32mservice#com.ryansteckler.nlpunbounce.SELinuxService[m
[32m+[m[32mADDED from AndroidManifest.xml:99:9[m
[32m+[m	[32mandroid:exported[m
[32m+[m		[32mADDED from AndroidManifest.xml:101:13[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from AndroidManifest.xml:100:13[m
 uses-sdk[m
 INJECTED from AndroidManifest.xml:0:0 reason: use-sdk injection requested[m
[31m-MERGED from com.google.android.gms:play-services:6.5.87:18:5[m
[31m-MERGED from com.android.support:support-v4:21.0.0:15:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-ads:7.5.0:23:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-analytics:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appindexing:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appinvite:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-appstate:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-cast:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.android.support:mediarouter-v7:22.0.0:20:5[m
[32m+[m[32mMERGED from com.android.support:appcompat-v7:22.0.0:20:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-drive:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-fitness:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-location:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:28:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-games:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-drive:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-gcm:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-identity:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-location:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:28:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:28:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-nearby:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-panorama:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-plus:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-safetynet:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-wallet:7.5.0:19:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-identity:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:28:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-wearable:7.5.0:18:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:18:5[m
[32m+[m[32mMERGED from com.android.support:support-v4:22.0.0:20:5[m
 	android:targetSdkVersion[m
 		INJECTED from AndroidManifest.xml:0:0[m
 		INJECTED from AndroidManifest.xml:0:0[m
 	android:minSdkVersion[m
 		INJECTED from AndroidManifest.xml:0:0[m
 		INJECTED from AndroidManifest.xml:0:0[m
[32m+[m[32mactivity#com.google.android.gms.ads.AdActivity[m
[32m+[m[32mADDED from com.google.android.gms:play-services-ads:7.5.0:26:9[m
[32m+[m	[32mandroid:configChanges[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-ads:7.5.0:28:13[m
[32m+[m	[32mandroid:theme[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-ads:7.5.0:29:13[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-ads:7.5.0:27:13[m
[32m+[m[32mactivity#com.google.android.gms.ads.purchase.InAppPurchaseActivity[m
[32m+[m[32mADDED from com.google.android.gms:play-services-ads:7.5.0:30:9[m
[32m+[m	[32mandroid:theme[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-ads:7.5.0:31:13[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-ads:7.5.0:30:19[m
 meta-data#com.google.android.gms.version[m
[31m-ADDED from com.google.android.gms:play-services:6.5.87:21:9[m
[32m+[m[32mADDED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-base:7.5.0:21:9[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-base:7.5.0:22:13[m
 	android:value[m
[31m-		ADDED from com.google.android.gms:play-services:6.5.87:23:13[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-base:7.5.0:23:13[m
[32m+[m[32muses-permission#android.permission.WRITE_EXTERNAL_STORAGE[m
[32m+[m[32mADDED from com.google.android.gms:play-services-maps:7.5.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:22:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:22:5[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-maps:7.5.0:22:22[m
[32m+[m[32muses-permission#android.permission.ACCESS_COARSE_LOCATION[m
[32m+[m[32mADDED from com.google.android.gms:play-services-maps:7.5.0:23:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:23:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:23:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:23:5[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-maps:7.5.0:23:22[m
[32m+[m[32muses-feature#0x00020000[m
[32m+[m[32mADDED from com.google.android.gms:play-services-maps:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:24:5[m
[32m+[m[32mMERGED from com.google.android.gms:play-services-maps:7.5.0:24:5[m
[32m+[m	[32mandroid:required[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-maps:7.5.0:26:8[m
[32m+[m	[32mandroid:glEsVersion[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-maps:7.5.0:25:8[m
[32m+[m[32mandroid:uses-permission#android.permission.READ_EXTERNAL_STORAGE[m
[32m+[m[32mIMPLIED from AndroidManifest.xml:2:1 reason: com.google.android.gms.maps requested WRITE_EXTERNAL_STORAGE[m
[32m+[m[32muses-permission#android.permission.GET_ACCOUNTS[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:21:5[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:21:22[m
[32m+[m[32muses-permission#android.permission.USE_CREDENTIALS[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:22:5[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:22:22[m
[32m+[m[32mmeta-data#com.google.android.gms.wallet.api.enabled[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:25:9[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:26:13[m
[32m+[m	[32mandroid:value[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:27:13[m
[32m+[m[32mreceiver#com.google.android.gms.wallet.EnableWalletOptimizationReceiver[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:28:9[m
[32m+[m	[32mandroid:exported[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:30:13[m
[32m+[m	[32mandroid:name[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:29:13[m
[32m+[m[32mintent-filter#com.google.android.gms.wallet.ENABLE_WALLET_OPTIMIZATION[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:31:13[m
[32m+[m[32maction#com.google.android.gms.wallet.ENABLE_WALLET_OPTIMIZATION[m
[32m+[m[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:32:17[m
 	android:name[m
[31m-		ADDED from com.google.android.gms:play-services:6.5.87:22:13[m
[32m+[m		[32mADDED from com.google.android.gms:play-services-wallet:7.5.0:32:25[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/Base64.java b/app/src/main/java/com/ryansteckler/inappbilling/Base64.java[m
[1mindex 9e32157..bca2f51 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/Base64.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/Base64.java[m
[36m@@ -38,7 +38,7 @@[m [mpackage com.ryansteckler.inappbilling;[m
  * <p>Note {@link CharBase64} is a GWT-compatible implementation of this[m
  * class.[m
  */[m
[31m-public class Base64 {[m
[32m+[m[32mclass Base64 {[m
     /** Specify encoding (value is {@code true}). */[m
     public final static boolean ENCODE = true;[m
 [m
[36m@@ -193,8 +193,8 @@[m [mpublic class Base64 {[m
      * @return the <var>destination</var> array[m
      * @since 1.3[m
      */[m
[31m-    private static byte[] encode3to4(byte[] source, int srcOffset,[m
[31m-            int numSigBytes, byte[] destination, int destOffset, byte[] alphabet) {[m
[32m+[m[32m    private static void encode3to4(byte[] source, int srcOffset,[m
[32m+[m[32m                                   int numSigBytes, byte[] destination, int destOffset, byte[] alphabet) {[m
         //           1         2         3[m
         // 01234567890123456789012345678901 Bit position[m
         // --------000000001111111122222222 Array position from threeBytes[m
[36m@@ -217,21 +217,21 @@[m [mpublic class Base64 {[m
                 destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];[m
                 destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];[m
                 destination[destOffset + 3] = alphabet[(inBuff) & 0x3f];[m
[31m-                return destination;[m
[32m+[m
             case 2:[m
                 destination[destOffset] = alphabet[(inBuff >>> 18)];[m
                 destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];[m
                 destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];[m
                 destination[destOffset + 3] = EQUALS_SIGN;[m
[31m-                return destination;[m
[32m+[m
             case 1:[m
                 destination[destOffset] = alphabet[(inBuff >>> 18)];[m
                 destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];[m
                 destination[destOffset + 2] = EQUALS_SIGN;[m
                 destination[destOffset + 3] = EQUALS_SIGN;[m
[31m-                return destination;[m
[32m+[m
             default:[m
[31m-                return destination;[m
[32m+[m
         } // end switch[m
     } // end encode3to4[m
 [m
[36m@@ -269,14 +269,14 @@[m [mpublic class Base64 {[m
      * if it does not fall on 3 byte boundaries[m
      * @since 1.4[m
      */[m
[31m-    public static String encode(byte[] source, int off, int len, byte[] alphabet,[m
[31m-            boolean doPadding) {[m
[32m+[m[32m    private static String encode(byte[] source, int off, int len, byte[] alphabet,[m
[32m+[m[32m                                 boolean doPadding) {[m
         byte[] outBuff = encode(source, off, len, alphabet, Integer.MAX_VALUE);[m
         int outLen = outBuff.length;[m
 [m
         // If doPadding is false, set length to truncate '='[m
         // padding characters[m
[31m-        while (doPadding == false && outLen > 0) {[m
[32m+[m[32m        while (!doPadding && outLen > 0) {[m
             if (outBuff[outLen - 1] != '=') {[m
                 break;[m
             }[m
[36m@@ -296,8 +296,8 @@[m [mpublic class Base64 {[m
      * @param maxLineLength maximum length of one line.[m
      * @return the BASE64-encoded byte array[m
      */[m
[31m-    public static byte[] encode(byte[] source, int off, int len, byte[] alphabet,[m
[31m-            int maxLineLength) {[m
[32m+[m[32m    private static byte[] encode(byte[] source, int off, int len, byte[] alphabet,[m
[32m+[m[32m                                 int maxLineLength) {[m
         int lenDiv3 = (len + 2) / 3; // ceil(len / 3)[m
         int len43 = lenDiv3 * 4;[m
         byte[] outBuff = new byte[len43 // Main 4:3[m
[36m@@ -469,7 +469,7 @@[m [mpublic class Base64 {[m
      * @since 1.3[m
      * @throws Base64DecoderException[m
      */[m
[31m-    public static byte[] decode(byte[] source, int off, int len)[m
[32m+[m[32m    private static byte[] decode(byte[] source, int off, int len)[m
             throws Base64DecoderException {[m
         return decode(source, off, len, DECODABET);[m
     }[m
[36m@@ -484,7 +484,7 @@[m [mpublic class Base64 {[m
      * @param len    the length of characters to decode[m
      * @return decoded data[m
      */[m
[31m-    public static byte[] decodeWebSafe(byte[] source, int off, int len)[m
[32m+[m[32m    private static byte[] decodeWebSafe(byte[] source, int off, int len)[m
             throws Base64DecoderException {[m
         return decode(source, off, len, WEBSAFE_DECODABET);[m
     }[m
[36m@@ -499,7 +499,7 @@[m [mpublic class Base64 {[m
      * @param decodabet the decodabet for decoding Base64 content[m
      * @return decoded data[m
      */[m
[31m-    public static byte[] decode(byte[] source, int off, int len, byte[] decodabet)[m
[32m+[m[32m    private static byte[] decode(byte[] source, int off, int len, byte[] decodabet)[m
             throws Base64DecoderException {[m
         int len34 = len * 3 / 4;[m
         byte[] outBuff = new byte[2 + len34]; // Upper limit on size of output[m
[36m@@ -507,9 +507,9 @@[m [mpublic class Base64 {[m
 [m
         byte[] b4 = new byte[4];[m
         int b4Posn = 0;[m
[31m-        int i = 0;[m
[31m-        byte sbiCrop = 0;[m
[31m-        byte sbiDecode = 0;[m
[32m+[m[32m        int i;[m
[32m+[m[32m        byte sbiCrop;[m
[32m+[m[32m        byte sbiDecode;[m
         for (i = 0; i < len; i++) {[m
             sbiCrop = (byte) (source[i + off] & 0x7f); // Only the low seven bits[m
             sbiDecode = decodabet[sbiCrop];[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/Base64DecoderException.java b/app/src/main/java/com/ryansteckler/inappbilling/Base64DecoderException.java[m
[1mindex 114535a..c9dd19f 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/Base64DecoderException.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/Base64DecoderException.java[m
[36m@@ -19,7 +19,7 @@[m [mpackage com.ryansteckler.inappbilling;[m
  *[m
  * @author nelson[m
  */[m
[31m-public class Base64DecoderException extends Exception {[m
[32m+[m[32mclass Base64DecoderException extends Exception {[m
     public Base64DecoderException() {[m
         super();[m
     }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/IabException.java b/app/src/main/java/com/ryansteckler/inappbilling/IabException.java[m
[1mindex e20f704..c498896 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/IabException.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/IabException.java[m
[36m@@ -21,16 +21,16 @@[m [mpackage com.ryansteckler.inappbilling;[m
  * To get the IAB result that caused this exception to be thrown,[m
  * call {@link #getResult()}.[m
  */[m
[31m-public class IabException extends Exception {[m
[31m-    IabResult mResult;[m
[32m+[m[32mclass IabException extends Exception {[m
[32m+[m[32m    private final IabResult mResult;[m
 [m
[31m-    public IabException(IabResult r) {[m
[32m+[m[32m    private IabException(IabResult r) {[m
         this(r, null);[m
     }[m
     public IabException(int response, String message) {[m
         this(new IabResult(response, message));[m
     }[m
[31m-    public IabException(IabResult r, Exception cause) {[m
[32m+[m[32m    private IabException(IabResult r, Exception cause) {[m
         super(r.getMessage(), cause);[m
         mResult = r;[m
     }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/IabHelper.java b/app/src/main/java/com/ryansteckler/inappbilling/IabHelper.java[m
[1mindex e16ac5d..371b134 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/IabHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/IabHelper.java[m
[36m@@ -71,41 +71,41 @@[m [mimport java.util.List;[m
  */[m
 public class IabHelper {[m
     // Is debug logging enabled?[m
[31m-    boolean mDebugLog = false;[m
[31m-    String mDebugTag = "IabHelper";[m
[32m+[m[32m    private boolean mDebugLog = false;[m
[32m+[m[32m    private String mDebugTag = "IabHelper";[m
 [m
     // Is setup done?[m
[31m-    boolean mSetupDone = false;[m
[32m+[m[32m    private boolean mSetupDone = false;[m
 [m
     // Has this object been disposed of? (If so, we should ignore callbacks, etc)[m
[31m-    boolean mDisposed = false;[m
[32m+[m[32m    private boolean mDisposed = false;[m
 [m
     // Are subscriptions supported?[m
[31m-    boolean mSubscriptionsSupported = false;[m
[32m+[m[32m    private boolean mSubscriptionsSupported = false;[m
 [m
     // Is an asynchronous operation in progress?[m
     // (only one at a time can be in progress)[m
[31m-    boolean mAsyncInProgress = false;[m
[32m+[m[32m    private boolean mAsyncInProgress = false;[m
 [m
     // (for logging/debugging)[m
     // if mAsyncInProgress == true, what asynchronous operation is in progress?[m
[31m-    String mAsyncOperation = "";[m
[32m+[m[32m    private String mAsyncOperation = "";[m
 [m
     // Context we were passed during initialization[m
[31m-    Context mContext;[m
[32m+[m[32m    private Context mContext;[m
 [m
     // Connection to the service[m
[31m-    IInAppBillingService mService;[m
[31m-    ServiceConnection mServiceConn;[m
[32m+[m[32m    private IInAppBillingService mService;[m
[32m+[m[32m    private ServiceConnection mServiceConn;[m
 [m
     // The request code used to launch purchase flow[m
[31m-    int mRequestCode;[m
[32m+[m[32m    private int mRequestCode;[m
 [m
     // The item type of the current purchase flow[m
[31m-    String mPurchasingItemType;[m
[32m+[m[32m    private String mPurchasingItemType;[m
 [m
     // Public key for verifying signature, in base64 encoding[m
[31m-    String mSignatureBase64 = null;[m
[32m+[m[32m    private String mSignatureBase64 = null;[m
 [m
     // Billing response codes[m
     public static final int BILLING_RESPONSE_RESULT_OK = 0;[m
[36m@@ -118,35 +118,35 @@[m [mpublic class IabHelper {[m
     public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;[m
 [m
     // IAB Helper error codes[m
[31m-    public static final int IABHELPER_ERROR_BASE = -1000;[m
[31m-    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;[m
[31m-    public static final int IABHELPER_BAD_RESPONSE = -1002;[m
[31m-    public static final int IABHELPER_VERIFICATION_FAILED = -1003;[m
[31m-    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;[m
[31m-    public static final int IABHELPER_USER_CANCELLED = -1005;[m
[31m-    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;[m
[31m-    public static final int IABHELPER_MISSING_TOKEN = -1007;[m
[31m-    public static final int IABHELPER_UNKNOWN_ERROR = -1008;[m
[31m-    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;[m
[31m-    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;[m
[32m+[m[32m    private static final int IABHELPER_ERROR_BASE = -1000;[m
[32m+[m[32m    private static final int IABHELPER_REMOTE_EXCEPTION = -1001;[m
[32m+[m[32m    private static final int IABHELPER_BAD_RESPONSE = -1002;[m
[32m+[m[32m    private static final int IABHELPER_VERIFICATION_FAILED = -1003;[m
[32m+[m[32m    private static final int IABHELPER_SEND_INTENT_FAILED = -1004;[m
[32m+[m[32m    private static final int IABHELPER_USER_CANCELLED = -1005;[m
[32m+[m[32m    private static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;[m
[32m+[m[32m    private static final int IABHELPER_MISSING_TOKEN = -1007;[m
[32m+[m[32m    private static final int IABHELPER_UNKNOWN_ERROR = -1008;[m
[32m+[m[32m    private static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;[m
[32m+[m[32m    private static final int IABHELPER_INVALID_CONSUMPTION = -1010;[m
 [m
     // Keys for the responses from InAppBillingService[m
[31m-    public static final String RESPONSE_CODE = "RESPONSE_CODE";[m
[31m-    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";[m
[31m-    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";[m
[31m-    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";[m
[31m-    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";[m
[31m-    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";[m
[31m-    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";[m
[31m-    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";[m
[31m-    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";[m
[32m+[m[32m    private static final String RESPONSE_CODE = "RESPONSE_CODE";[m
[32m+[m[32m    private static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";[m
[32m+[m[32m    private static final String RESPONSE_BUY_INTENT = "BUY_INTENT";[m
[32m+[m[32m    private static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";[m
[32m+[m[32m    private static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";[m
[32m+[m[32m    private static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";[m
[32m+[m[32m    private static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";[m
[32m+[m[32m    private static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";[m
[32m+[m[32m    private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";[m
 [m
     // Item types[m
     public static final String ITEM_TYPE_INAPP = "inapp";[m
[31m-    public static final String ITEM_TYPE_SUBS = "subs";[m
[32m+[m[32m    private static final String ITEM_TYPE_SUBS = "subs";[m
 [m
     // some fields on the getSkuDetails response bundle[m
[31m-    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";[m
[32m+[m[32m    private static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";[m
     public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";[m
 [m
     /**[m
[36m@@ -190,7 +190,7 @@[m [mpublic class IabHelper {[m
          *[m
          * @param result The result of the setup process.[m
          */[m
[31m-        public void onIabSetupFinished(IabResult result);[m
[32m+[m[32m        void onIabSetupFinished(IabResult result);[m
     }[m
 [m
     /**[m
[36m@@ -330,12 +330,12 @@[m [mpublic class IabHelper {[m
          * @param result The result of the purchase.[m
          * @param info The purchase information (null if purchase failed)[m
          */[m
[31m-        public void onIabPurchaseFinished(IabResult result, Purchase info);[m
[32m+[m[32m        void onIabPurchaseFinished(IabResult result, Purchase info);[m
     }[m
 [m
     // The listener registered on launchPurchaseFlow, which we have to call back when[m
     // the purchase finishes[m
[31m-    OnIabPurchaseFinishedListener mPurchaseListener;[m
[32m+[m[32m    private OnIabPurchaseFinishedListener mPurchaseListener;[m
 [m
     public void launchPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener) {[m
         launchPurchaseFlow(act, sku, requestCode, listener, "");[m
[36m@@ -351,8 +351,8 @@[m [mpublic class IabHelper {[m
         launchSubscriptionPurchaseFlow(act, sku, requestCode, listener, "");[m
     }[m
 [m
[31m-    public void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode,[m
[31m-            OnIabPurchaseFinishedListener listener, String extraData) {[m
[32m+[m[32m    private void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode,[m
[32m+[m[32m                                                OnIabPurchaseFinishedListener listener, String extraData) {[m
         launchPurchaseFlow(act, sku, ITEM_TYPE_SUBS, requestCode, listener, extraData);[m
     }[m
 [m
[36m@@ -374,8 +374,8 @@[m [mpublic class IabHelper {[m
      *     when the purchase completes. This extra data will be permanently bound to that purchase[m
      *     and will always be returned when the purchase is queried.[m
      */[m
[31m-    public void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode,[m
[31m-                        OnIabPurchaseFinishedListener listener, String extraData) {[m
[32m+[m[32m    private void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode,[m
[32m+[m[32m                                    OnIabPurchaseFinishedListener listener, String extraData) {[m
         checkNotDisposed();[m
         checkSetupDone("launchPurchaseFlow");[m
         flagStartAsync("launchPurchaseFlow");[m
[36m@@ -444,7 +444,7 @@[m [mpublic class IabHelper {[m
      */[m
     public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {[m
         IabResult result;[m
[31m-        if (requestCode != mRequestCode) return false;[m
[32m+[m[32m        if (requestCode != mRequestCode) return true;[m
 [m
         checkNotDisposed();[m
         checkSetupDone("handleActivityResult");[m
[36m@@ -456,7 +456,7 @@[m [mpublic class IabHelper {[m
             logError("Null data in IAB activity result.");[m
             result = new IabResult(IABHELPER_BAD_RESPONSE, "Null data in IAB result");[m
             if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);[m
[31m-            return true;[m
[32m+[m[32m            return false;[m
         }[m
 [m
         int responseCode = getResponseCodeFromIntent(data);[m
[36m@@ -475,10 +475,10 @@[m [mpublic class IabHelper {[m
                 logDebug("Extras: " + data.getExtras().toString());[m
                 result = new IabResult(IABHELPER_UNKNOWN_ERROR, "IAB returned null purchaseData or dataSignature");[m
                 if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);[m
[31m-                return true;[m
[32m+[m[32m                return false;[m
             }[m
 [m
[31m-            Purchase purchase = null;[m
[32m+[m[32m            Purchase purchase;[m
             try {[m
                 purchase = new Purchase(mPurchasingItemType, purchaseData, dataSignature);[m
                 String sku = purchase.getSku();[m
[36m@@ -488,7 +488,7 @@[m [mpublic class IabHelper {[m
                     logError("Purchase signature verification FAILED for sku " + sku);[m
                     result = new IabResult(IABHELPER_VERIFICATION_FAILED, "Signature verification failed for sku " + sku);[m
                     if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, purchase);[m
[31m-                    return true;[m
[32m+[m[32m                    return false;[m
                 }[m
                 logDebug("Purchase signature successfully verified.");[m
             }[m
[36m@@ -497,7 +497,7 @@[m [mpublic class IabHelper {[m
                 e.printStackTrace();[m
                 result = new IabResult(IABHELPER_BAD_RESPONSE, "Failed to parse purchase data.");[m
                 if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);[m
[31m-                return true;[m
[32m+[m[32m                return false;[m
             }[m
 [m
             if (mPurchaseListener != null) {[m
[36m@@ -523,10 +523,10 @@[m [mpublic class IabHelper {[m
             result = new IabResult(IABHELPER_UNKNOWN_PURCHASE_RESPONSE, "Unknown purchase response.");[m
             if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);[m
         }[m
[31m-        return true;[m
[32m+[m[32m        return false;[m
     }[m
 [m
[31m-    public Inventory queryInventory(boolean querySkuDetails, List<String> moreSkus) throws IabException {[m
[32m+[m[32m    private Inventory queryInventory(boolean querySkuDetails, List<String> moreSkus) throws IabException {[m
         return queryInventory(querySkuDetails, moreSkus, null);[m
     }[m
 [m
[36m@@ -543,8 +543,8 @@[m [mpublic class IabHelper {[m
      *     Ignored if null or if querySkuDetails is false.[m
      * @throws IabException if a problem occurs while refreshing the inventory.[m
      */[m
[31m-    public Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus,[m
[31m-                                        List<String> moreSubsSkus) throws IabException {[m
[32m+[m[32m    private Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus,[m
[32m+[m[32m                                     List<String> moreSubsSkus) throws IabException {[m
         checkNotDisposed();[m
         checkSetupDone("queryInventory");[m
         try {[m
[36m@@ -596,7 +596,7 @@[m [mpublic class IabHelper {[m
          * @param result The result of the operation.[m
          * @param inv The inventory.[m
          */[m
[31m-        public void onQueryInventoryFinished(IabResult result, Inventory inv);[m
[32m+[m[32m        void onQueryInventoryFinished(IabResult result, Inventory inv);[m
     }[m
 [m
 [m
[36m@@ -610,9 +610,9 @@[m [mpublic class IabHelper {[m
      * @param moreSkus as in {@link #queryInventory}[m
      * @param listener The listener to notify when the refresh operation completes.[m
      */[m
[31m-    public void queryInventoryAsync(final boolean querySkuDetails,[m
[31m-                               final List<String> moreSkus,[m
[31m-                               final QueryInventoryFinishedListener listener) {[m
[32m+[m[32m    private void queryInventoryAsync(final boolean querySkuDetails,[m
[32m+[m[32m                                     final List<String> moreSkus,[m
[32m+[m[32m                                     final QueryInventoryFinishedListener listener) {[m
         final Handler handler = new Handler();[m
         checkNotDisposed();[m
         checkSetupDone("queryInventory");[m
[36m@@ -661,7 +661,7 @@[m [mpublic class IabHelper {[m
      * @param itemInfo The PurchaseInfo that represents the item to consume.[m
      * @throws IabException if there is a problem during consumption.[m
      */[m
[31m-    void consume(Purchase itemInfo) throws IabException {[m
[32m+[m[32m    private void consume(Purchase itemInfo) throws IabException {[m
         checkNotDisposed();[m
         checkSetupDone("consume");[m
 [m
[36m@@ -704,7 +704,7 @@[m [mpublic class IabHelper {[m
          * @param purchase The purchase that was (or was to be) consumed.[m
          * @param result The result of the consumption operation.[m
          */[m
[31m-        public void onConsumeFinished(Purchase purchase, IabResult result);[m
[32m+[m[32m        void onConsumeFinished(Purchase purchase, IabResult result);[m
     }[m
 [m
     /**[m
[36m@@ -718,7 +718,7 @@[m [mpublic class IabHelper {[m
          * @param results The results of each consumption operation, corresponding to each[m
          *     sku.[m
          */[m
[31m-        public void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results);[m
[32m+[m[32m        void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results);[m
     }[m
 [m
     /**[m
[36m@@ -732,7 +732,7 @@[m [mpublic class IabHelper {[m
     public void consumeAsync(Purchase purchase, OnConsumeFinishedListener listener) {[m
         checkNotDisposed();[m
         checkSetupDone("consume");[m
[31m-        List<Purchase> purchases = new ArrayList<Purchase>();[m
[32m+[m[32m        List<Purchase> purchases = new ArrayList<>();[m
         purchases.add(purchase);[m
         consumeAsyncInternal(purchases, listener, null);[m
     }[m
[36m@@ -784,7 +784,7 @@[m [mpublic class IabHelper {[m
 [m
 [m
     // Checks that setup was done; if not, throws an exception.[m
[31m-    void checkSetupDone(String operation) {[m
[32m+[m[32m    private void checkSetupDone(String operation) {[m
         if (!mSetupDone) {[m
             logError("Illegal state for operation (" + operation + "): IAB helper is not set up.");[m
             throw new IllegalStateException("IAB helper is not set up. Can't perform operation: " + operation);[m
[36m@@ -792,7 +792,7 @@[m [mpublic class IabHelper {[m
     }[m
 [m
     // Workaround to bug where sometimes response codes come as Long instead of Integer[m
[31m-    int getResponseCodeFromBundle(Bundle b) {[m
[32m+[m[32m    private int getResponseCodeFromBundle(Bundle b) {[m
         Object o = b.get(RESPONSE_CODE);[m
         if (o == null) {[m
             logDebug("Bundle with null response code, assuming OK (known issue)");[m
[36m@@ -808,7 +808,7 @@[m [mpublic class IabHelper {[m
     }[m
 [m
     // Workaround to bug where sometimes response codes come as Long instead of Integer[m
[31m-    int getResponseCodeFromIntent(Intent i) {[m
[32m+[m[32m    private int getResponseCodeFromIntent(Intent i) {[m
         Object o = i.getExtras().get(RESPONSE_CODE);[m
         if (o == null) {[m
             logError("Intent with no response code, assuming OK (known issue)");[m
[36m@@ -823,7 +823,7 @@[m [mpublic class IabHelper {[m
         }[m
     }[m
 [m
[31m-    void flagStartAsync(String operation) {[m
[32m+[m[32m    private void flagStartAsync(String operation) {[m
         if (mAsyncInProgress) throw new IllegalStateException("Can't start async operation (" +[m
                 operation + ") because another async operation(" + mAsyncOperation + ") is in progress.");[m
         mAsyncOperation = operation;[m
[36m@@ -831,14 +831,14 @@[m [mpublic class IabHelper {[m
         logDebug("Starting async operation: " + operation);[m
     }[m
 [m
[31m-    void flagEndAsync() {[m
[32m+[m[32m    private void flagEndAsync() {[m
         logDebug("Ending async operation: " + mAsyncOperation);[m
         mAsyncOperation = "";[m
         mAsyncInProgress = false;[m
     }[m
 [m
 [m
[31m-    int queryPurchases(Inventory inv, String itemType) throws JSONException, RemoteException {[m
[32m+[m[32m    private int queryPurchases(Inventory inv, String itemType) throws JSONException, RemoteException {[m
         // Query purchases[m
         logDebug("Querying owned items, item type: " + itemType);[m
         logDebug("Package name: " + mContext.getPackageName());[m
[36m@@ -901,10 +901,10 @@[m [mpublic class IabHelper {[m
         return verificationFailed ? IABHELPER_VERIFICATION_FAILED : BILLING_RESPONSE_RESULT_OK;[m
     }[m
 [m
[31m-    int querySkuDetails(String itemType, Inventory inv, List<String> moreSkus)[m
[32m+[m[32m    private int querySkuDetails(String itemType, Inventory inv, List<String> moreSkus)[m
                                 throws RemoteException, JSONException {[m
         logDebug("Querying SKU details.");[m
[31m-        ArrayList<String> skuList = new ArrayList<String>();[m
[32m+[m[32m        ArrayList<String> skuList = new ArrayList<>();[m
         skuList.addAll(inv.getAllOwnedSkus(itemType));[m
         if (moreSkus != null) {[m
             for (String sku : moreSkus) {[m
[36m@@ -948,14 +948,14 @@[m [mpublic class IabHelper {[m
     }[m
 [m
 [m
[31m-    void consumeAsyncInternal(final List<Purchase> purchases,[m
[31m-                              final OnConsumeFinishedListener singleListener,[m
[31m-                              final OnConsumeMultiFinishedListener multiListener) {[m
[32m+[m[32m    private void consumeAsyncInternal(final List<Purchase> purchases,[m
[32m+[m[32m                                      final OnConsumeFinishedListener singleListener,[m
[32m+[m[32m                                      final OnConsumeMultiFinishedListener multiListener) {[m
         final Handler handler = new Handler();[m
         flagStartAsync("consume");[m
         (new Thread(new Runnable() {[m
             public void run() {[m
[31m-                final List<IabResult> results = new ArrayList<IabResult>();[m
[32m+[m[32m                final List<IabResult> results = new ArrayList<>();[m
                 for (Purchase purchase : purchases) {[m
                     try {[m
                         consume(purchase);[m
[36m@@ -985,15 +985,15 @@[m [mpublic class IabHelper {[m
         })).start();[m
     }[m
 [m
[31m-    void logDebug(String msg) {[m
[32m+[m[32m    private void logDebug(String msg) {[m
         if (mDebugLog) Log.d(mDebugTag, msg);[m
     }[m
 [m
[31m-    void logError(String msg) {[m
[32m+[m[32m    private void logError(String msg) {[m
         Log.e(mDebugTag, "In-app billing error: " + msg);[m
     }[m
 [m
[31m-    void logWarn(String msg) {[m
[32m+[m[32m    private void logWarn(String msg) {[m
         Log.w(mDebugTag, "In-app billing warning: " + msg);[m
     }[m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/IabResult.java b/app/src/main/java/com/ryansteckler/inappbilling/IabResult.java[m
[1mindex 72a02ab..b7d6bc1 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/IabResult.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/IabResult.java[m
[36m@@ -24,8 +24,8 @@[m [mpackage com.ryansteckler.inappbilling;[m
  * calling {@link #isSuccess()} and {@link #isFailure()}.[m
  */[m
 public class IabResult {[m
[31m-    int mResponse;[m
[31m-    String mMessage;[m
[32m+[m[32m    private final int mResponse;[m
[32m+[m[32m    private final String mMessage;[m
 [m
     public IabResult(int response, String message) {[m
         mResponse = response;[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/Inventory.java b/app/src/main/java/com/ryansteckler/inappbilling/Inventory.java[m
[1mindex 511e7f4..96d2c74 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/Inventory.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/Inventory.java[m
[36m@@ -25,8 +25,8 @@[m [mimport java.util.Map;[m
  * An Inventory is returned by such methods as {@link IabHelper#queryInventory}.[m
  */[m
 public class Inventory {[m
[31m-    Map<String,SkuDetails> mSkuMap = new HashMap<String,SkuDetails>();[m
[31m-    Map<String,Purchase> mPurchaseMap = new HashMap<String,Purchase>();[m
[32m+[m[32m    private final Map<String,SkuDetails> mSkuMap = new HashMap<>();[m
[32m+[m[32m    private final Map<String,Purchase> mPurchaseMap = new HashMap<>();[m
 [m
     Inventory() { }[m
 [m
[36m@@ -64,12 +64,12 @@[m [mpublic class Inventory {[m
 [m
     /** Returns a list of all owned product IDs. */[m
     List<String> getAllOwnedSkus() {[m
[31m-        return new ArrayList<String>(mPurchaseMap.keySet());[m
[32m+[m[32m        return new ArrayList<>(mPurchaseMap.keySet());[m
     }[m
 [m
     /** Returns a list of all owned product IDs of a given type */[m
     List<String> getAllOwnedSkus(String itemType) {[m
[31m-        List<String> result = new ArrayList<String>();[m
[32m+[m[32m        List<String> result = new ArrayList<>();[m
         for (Purchase p : mPurchaseMap.values()) {[m
             if (p.getItemType().equals(itemType)) result.add(p.getSku());[m
         }[m
[36m@@ -78,7 +78,7 @@[m [mpublic class Inventory {[m
 [m
     /** Returns a list of all purchases. */[m
     List<Purchase> getAllPurchases() {[m
[31m-        return new ArrayList<Purchase>(mPurchaseMap.values());[m
[32m+[m[32m        return new ArrayList<>(mPurchaseMap.values());[m
     }[m
 [m
     void addSkuDetails(SkuDetails d) {[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/Purchase.java b/app/src/main/java/com/ryansteckler/inappbilling/Purchase.java[m
[1mindex b0623bc..03d638e 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/Purchase.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/Purchase.java[m
[36m@@ -22,16 +22,16 @@[m [mimport org.json.JSONObject;[m
  * Represents an in-app billing purchase.[m
  */[m
 public class Purchase {[m
[31m-    String mItemType;  // ITEM_TYPE_INAPP or ITEM_TYPE_SUBS[m
[31m-    String mOrderId;[m
[31m-    String mPackageName;[m
[31m-    String mSku;[m
[31m-    long mPurchaseTime;[m
[31m-    int mPurchaseState;[m
[31m-    String mDeveloperPayload;[m
[31m-    String mToken;[m
[31m-    String mOriginalJson;[m
[31m-    String mSignature;[m
[32m+[m[32m    final String mItemType;  // ITEM_TYPE_INAPP or ITEM_TYPE_SUBS[m
[32m+[m[32m    private String mOrderId;[m
[32m+[m[32m    private String mPackageName;[m
[32m+[m[32m    private String mSku;[m
[32m+[m[32m    private long mPurchaseTime;[m
[32m+[m[32m    private int mPurchaseState;[m
[32m+[m[32m    private String mDeveloperPayload;[m
[32m+[m[32m    private String mToken;[m
[32m+[m[32m    private final String mOriginalJson;[m
[32m+[m[32m    private String mSignature;[m
 [m
     public Purchase(String itemType, String jsonPurchaseInfo, String signature) throws JSONException {[m
         mItemType = itemType;[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/Security.java b/app/src/main/java/com/ryansteckler/inappbilling/Security.java[m
[1mindex 51744c4..8d13c0a 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/Security.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/Security.java[m
[36m@@ -36,7 +36,7 @@[m [mimport java.security.spec.X509EncodedKeySpec;[m
  * make it harder for an attacker to replace the code with stubs that treat all[m
  * purchases as verified.[m
  */[m
[31m-public class Security {[m
[32m+[m[32mclass Security {[m
     private static final String TAG = "IABUtil/Security";[m
 [m
     private static final String KEY_FACTORY_ALGORITHM = "RSA";[m
[36m@@ -69,7 +69,7 @@[m [mpublic class Security {[m
      * @param encodedPublicKey Base64-encoded public key[m
      * @throws IllegalArgumentException if encodedPublicKey is invalid[m
      */[m
[31m-    public static PublicKey generatePublicKey(String encodedPublicKey) {[m
[32m+[m[32m    private static PublicKey generatePublicKey(String encodedPublicKey) {[m
         try {[m
             byte[] decodedKey = Base64.decode(encodedPublicKey);[m
             KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);[m
[36m@@ -94,7 +94,7 @@[m [mpublic class Security {[m
      * @param signature server signature[m
      * @return true if the data and signature match[m
      */[m
[31m-    public static boolean verify(PublicKey publicKey, String signedData, String signature) {[m
[32m+[m[32m    private static boolean verify(PublicKey publicKey, String signedData, String signature) {[m
         Signature sig;[m
         try {[m
             sig = Signature.getInstance(SIGNATURE_ALGORITHM);[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/inappbilling/SkuDetails.java b/app/src/main/java/com/ryansteckler/inappbilling/SkuDetails.java[m
[1mindex a3299fa..f640536 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/inappbilling/SkuDetails.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/inappbilling/SkuDetails.java[m
[36m@@ -22,20 +22,19 @@[m [mimport org.json.JSONObject;[m
  * Represents an in-app product's listing details.[m
  */[m
 public class SkuDetails {[m
[31m-    String mItemType;[m
[31m-    String mSku;[m
[31m-    String mType;[m
[31m-    String mPrice;[m
[31m-    String mTitle;[m
[31m-    String mDescription;[m
[31m-    String mJson;[m
[32m+[m[32m    private String mSku;[m
[32m+[m[32m    private String mType;[m
[32m+[m[32m    private String mPrice;[m
[32m+[m[32m    private String mTitle;[m
[32m+[m[32m    private String mDescription;[m
[32m+[m[32m    private final String mJson;[m
 [m
     public SkuDetails(String jsonSkuDetails) throws JSONException {[m
         this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);[m
     }[m
 [m
     public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {[m
[31m-        mItemType = itemType;[m
[32m+[m[32m        String mItemType = itemType;[m
         mJson = jsonSkuDetails;[m
         JSONObject o = new JSONObject(mJson);[m
         mSku = o.optString("productId");[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/ActivityReceiver.java b/app/src/main/java/com/ryansteckler/nlpunbounce/ActivityReceiver.java[m
[1mindex 154a4dc..1711696 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/ActivityReceiver.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/ActivityReceiver.java[m
[36m@@ -1,31 +1,16 @@[m
 package com.ryansteckler.nlpunbounce;[m
 [m
[31m-import android.app.Activity;[m
 import android.content.BroadcastReceiver;[m
 import android.content.Context;[m
 import android.content.Intent;[m
 [m
[31m-import com.ryansteckler.nlpunbounce.models.BaseStats;[m
[31m-import com.ryansteckler.nlpunbounce.models.BaseStatsWrapper;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 [m
[31m-import java.io.ByteArrayInputStream;[m
[31m-import java.io.ByteArrayOutputStream;[m
[31m-import java.io.File;[m
[31m-import java.io.FileOutputStream;[m
[31m-import java.io.IOException;[m
[31m-import java.io.ObjectInput;[m
[31m-import java.io.ObjectInputStream;[m
[31m-import java.io.ObjectOutput;[m
[31m-import java.io.ObjectOutputStream;[m
[31m-import java.io.StreamCorruptedException;[m
[31m-import java.util.HashMap;[m
[31m-[m
 public class ActivityReceiver extends BroadcastReceiver {[m
 [m
     public static final String STATS_REFRESHED_ACTION = "com.ryansteckler.nlpunbounce.STATS_REFRESHED_ACTION";[m
     public static final String CREATE_FILES_ACTION = "com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION";[m
[31m-    public static final String RESET_FILES_ACTION = "com.ryansteckler.nlpunbounce.RESET_FILES_ACTION";[m
[32m+[m[32m    private static final String RESET_FILES_ACTION = "com.ryansteckler.nlpunbounce.RESET_FILES_ACTION";[m
     public static final String PUSH_NETWORK_STATS = "com.ryansteckler.nlpunbounce.PUSH_NETWORK_STATS";[m
 [m
     public ActivityReceiver() {[m
[36m@@ -34,12 +19,16 @@[m [mpublic class ActivityReceiver extends BroadcastReceiver {[m
     @Override[m
     public void onReceive(Context context, Intent intent) {[m
         String action = intent.getAction();[m
[31m-        if (action.equals(CREATE_FILES_ACTION)) {[m
[31m-            UnbounceStatsCollection.getInstance().createFiles(context);[m
[31m-        } else if (action.equals(RESET_FILES_ACTION)) {[m
[31m-            UnbounceStatsCollection.getInstance().recreateFiles(context);[m
[31m-        } else if (action.equals(PUSH_NETWORK_STATS)) {[m
[31m-            UnbounceStatsCollection.getInstance().pushStatsToNetworkInternal(context);[m
[32m+[m[32m        switch (action) {[m
[32m+[m[32m            case CREATE_FILES_ACTION:[m
[32m+[m[32m                UnbounceStatsCollection.getInstance().createFiles(context);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case RESET_FILES_ACTION:[m
[32m+[m[32m                UnbounceStatsCollection.getInstance().recreateFiles(context);[m
[32m+[m[32m                break;[m
[32m+[m[32m            case PUSH_NETWORK_STATS:[m
[32m+[m[32m                UnbounceStatsCollection.getInstance().pushStatsToNetworkInternal(context);[m
[32m+[m[32m                break;[m
         }[m
 [m
     }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmDetailFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmDetailFragment.java[m
[1mindex fe1d536..87f853e 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmDetailFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmDetailFragment.java[m
[36m@@ -1,15 +1,13 @@[m
 package com.ryansteckler.nlpunbounce;[m
 [m
 import android.app.AlertDialog;[m
[32m+[m[32mimport android.app.Fragment;[m
 import android.content.Context;[m
 import android.content.DialogInterface;[m
 import android.content.SharedPreferences;[m
[31m-import android.content.pm.ApplicationInfo;[m
[31m-import android.content.pm.PackageManager;[m
 import android.content.res.Resources;[m
 import android.graphics.drawable.Drawable;[m
 import android.os.Bundle;[m
[31m-import android.app.Fragment;[m
 import android.util.TypedValue;[m
 import android.view.KeyEvent;[m
 import android.view.LayoutInflater;[m
[36m@@ -23,8 +21,6 @@[m [mimport android.widget.EditText;[m
 import android.widget.Switch;[m
 import android.widget.TextView;[m
 [m
[31m-import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;[m
[31m-import com.ryansteckler.nlpunbounce.models.EventLookup;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 [m
 [m
[36m@@ -42,8 +38,7 @@[m [mpublic class AlarmDetailFragment extends BaseDetailFragment {[m
     public long getSeconds() {[m
         EditText editSeconds = (EditText)getActivity().findViewById(R.id.editAlarmSeconds);[m
         String text = editSeconds.getText().toString();[m
[31m-        long seconds = Long.parseLong(text);[m
[31m-        return seconds;[m
[32m+[m[32m        return Long.parseLong(text);[m
     }[m
 [m
     @Override[m
[36m@@ -67,10 +62,7 @@[m [mpublic class AlarmDetailFragment extends BaseDetailFragment {[m
         edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {[m
             @Override[m
             public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {[m
[31m-                if (i == EditorInfo.IME_ACTION_DONE) {[m
[31m-                    return handleTextChange(textView, edit);[m
[31m-                }[m
[31m-                return false;[m
[32m+[m[32m                return i == EditorInfo.IME_ACTION_DONE && handleTextChange(textView, edit);[m
             }[m
         });[m
         edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {[m
[36m@@ -89,7 +81,7 @@[m [mpublic class AlarmDetailFragment extends BaseDetailFragment {[m
 [m
         getView().findViewById(R.id.editAlarmSeconds).setEnabled(onOff.isChecked());[m
 [m
[31m-        View panel = (View)getView().findViewById(R.id.settingsPanel);[m
[32m+[m[32m        View panel = getView().findViewById(R.id.settingsPanel);[m
         TypedValue backgroundValue = new TypedValue();[m
         Resources.Theme theme = getActivity().getTheme();[m
         int resId = enabled ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;[m
[36m@@ -161,7 +153,7 @@[m [mpublic class AlarmDetailFragment extends BaseDetailFragment {[m
 [m
         //Enable or disable the seconds setting.[m
         getView().findViewById(R.id.editAlarmSeconds).setEnabled(b);[m
[31m-        View panel = (View)getView().findViewById(R.id.settingsPanel);[m
[32m+[m[32m        View panel = getView().findViewById(R.id.settingsPanel);[m
         TypedValue backgroundValue = new TypedValue();[m
         Resources.Theme theme = getActivity().getTheme();[m
         int resId = b ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;[m
[36m@@ -203,8 +195,7 @@[m [mpublic class AlarmDetailFragment extends BaseDetailFragment {[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
                              Bundle savedInstanceState) {[m
         // Inflate the layout for this fragment[m
[31m-        View view = inflater.inflate(R.layout.fragment_alarm_detail, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_alarm_detail, container, false);[m
     }[m
 [m
     @Override[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmsFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmsFragment.java[m
[1mindex 8fb1611..34333a7 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmsFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/AlarmsFragment.java[m
[36m@@ -64,8 +64,7 @@[m [mpublic class AlarmsFragment extends ListFragment implements AlarmDetailFragment.[m
     @Override[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {[m
         super.onCreateView(inflater, container, savedInstanceState);[m
[31m-        View view = inflater.inflate(R.layout.fragment_stats, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_stats, container, false);[m
     }[m
 [m
     @Override[m
[36m@@ -179,7 +178,6 @@[m [mpublic class AlarmsFragment extends ListFragment implements AlarmDetailFragment.[m
         //there until the animation takes over.  Looks sexier that way.[m
         TypedValue backgroundValue = new TypedValue();[m
         Resources.Theme theme = getActivity().getTheme();[m
[31m-        boolean success = theme.resolveAttribute(R.attr.listItemDownAlarm, backgroundValue, true);[m
         Drawable backgroundColor = getResources().getDrawable(backgroundValue.resourceId);[m
 [m
         v.setBackground(backgroundColor);[m
[36m@@ -271,9 +269,9 @@[m [mpublic class AlarmsFragment extends ListFragment implements AlarmDetailFragment.[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface OnFragmentInteractionListener {[m
[31m-        public void onAlarmsSetTitle(String id);[m
[32m+[m[32m        void onAlarmsSetTitle(String id);[m
 [m
[31m-        public void onAlarmsSetTaskerTitle(String title);[m
[32m+[m[32m        void onAlarmsSetTaskerTitle(String title);[m
     }[m
 [m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/BaseDetailFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/BaseDetailFragment.java[m
[1mindex 47c4d93..06f342d 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/BaseDetailFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/BaseDetailFragment.java[m
[36m@@ -6,8 +6,6 @@[m [mimport android.app.Fragment;[m
 import android.content.Context;[m
 import android.content.DialogInterface;[m
 import android.content.SharedPreferences;[m
[31m-import android.content.pm.ApplicationInfo;[m
[31m-import android.content.pm.PackageManager;[m
 import android.os.Bundle;[m
 import android.view.MotionEvent;[m
 import android.view.View;[m
[36m@@ -26,26 +24,26 @@[m [mimport com.ryansteckler.nlpunbounce.tasker.TaskerActivity;[m
  */[m
 public abstract class BaseDetailFragment extends Fragment {[m
     // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER[m
[31m-    protected static final String ARG_START_TOP = "startTop";[m
[31m-    protected static final String ARG_FINAL_TOP = "finalTop";[m
[31m-    protected static final String ARG_START_BOTTOM = "startBottom";[m
[31m-    protected static final String ARG_FINAL_BOTTOM = "finalBottom";[m
[31m-    protected static final String ARG_CUR_STAT = "curStat";[m
[31m-    protected static final String ARG_TASKER_MODE = "taskerMode";[m
[32m+[m[32m    private static final String ARG_START_TOP = "startTop";[m
[32m+[m[32m    private static final String ARG_FINAL_TOP = "finalTop";[m
[32m+[m[32m    private static final String ARG_START_BOTTOM = "startBottom";[m
[32m+[m[32m    private static final String ARG_FINAL_BOTTOM = "finalBottom";[m
[32m+[m[32m    private static final String ARG_CUR_STAT = "curStat";[m
[32m+[m[32m    private static final String ARG_TASKER_MODE = "taskerMode";[m
 [m
[31m-    protected int mStartTop;[m
[31m-    protected int mFinalTop;[m
[31m-    protected int mStartBottom;[m
[31m-    protected int mFinalBottom;[m
[31m-    protected BaseStats mStat;[m
[31m-    protected boolean mTaskerMode;[m
[32m+[m[32m    private int mStartTop;[m
[32m+[m[32m    private int mFinalTop;[m
[32m+[m[32m    private int mStartBottom;[m
[32m+[m[32m    private int mFinalBottom;[m
[32m+[m[32m    BaseStats mStat;[m
[32m+[m[32m    boolean mTaskerMode;[m
 [m
[31m-    protected FragmentClearListener mClearListener = null;[m
[32m+[m[32m    FragmentClearListener mClearListener = null;[m
 [m
[31m-    protected boolean mKnownSafe = false;[m
[31m-    protected boolean mFree = false;[m
[32m+[m[32m    private boolean mKnownSafe = false;[m
[32m+[m[32m    private boolean mFree = false;[m
 [m
[31m-    protected FragmentInteractionListener mListener;[m
[32m+[m[32m    FragmentInteractionListener mListener;[m
 [m
     protected abstract void loadStatsFromSource(View view);[m
 [m
[36m@@ -181,7 +179,7 @@[m [mpublic abstract class BaseDetailFragment extends Fragment {[m
         setHasOptionsMenu(true);[m
     }[m
 [m
[31m-    protected void warnLicensing(final Switch onOff) {[m
[32m+[m[32m    private void warnLicensing(final Switch onOff) {[m
         new AlertDialog.Builder(getActivity())[m
                 .setTitle(R.string.alert_nopro_title)[m
                 .setMessage(R.string.alert_nopro_content)[m
[36m@@ -225,13 +223,13 @@[m [mpublic abstract class BaseDetailFragment extends Fragment {[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface FragmentInteractionListener {[m
[31m-        public void onDetailSetTitle(String title);[m
[32m+[m[32m        void onDetailSetTitle(String title);[m
 [m
[31m-        public void onDetailSetTaskerTitle(String title);[m
[32m+[m[32m        void onDetailSetTaskerTitle(String title);[m
     }[m
 [m
     public interface FragmentClearListener {[m
[31m-        public void onCleared();[m
[32m+[m[32m        void onCleared();[m
     }[m
 [m
     public void attachClearListener(FragmentClearListener fragment) {[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/BootReceiver.java b/app/src/main/java/com/ryansteckler/nlpunbounce/BootReceiver.java[m
[1mindex e4925a0..9470703 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/BootReceiver.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/BootReceiver.java[m
[36m@@ -5,8 +5,6 @@[m [mimport android.content.Context;[m
 import android.content.Intent;[m
 import android.util.Log;[m
 [m
[31m-import com.ryansteckler.nlpunbounce.helpers.RootHelper;[m
[31m-[m
 /**[m
  * Created by rsteckler on 1/2/15.[m
  */[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/HomeFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/HomeFragment.java[m
[1mindex 28f0a52..11d91b6 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/HomeFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/HomeFragment.java[m
[36m@@ -49,7 +49,6 @@[m [mimport com.ryansteckler.nlpunbounce.helpers.ThemeHelper;[m
 import com.ryansteckler.nlpunbounce.hooks.Wakelocks;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 [m
[31m-[m
 import java.io.File;[m
 [m
 /**[m
[36m@@ -76,8 +75,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
      * number.[m
      */[m
     public static HomeFragment newInstance() {[m
[31m-        HomeFragment fragment = new HomeFragment();[m
[31m-        return fragment;[m
[32m+[m[32m        return new HomeFragment();[m
     }[m
 [m
     public HomeFragment() {[m
[36m@@ -99,7 +97,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
             intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);[m
             try {[m
                 getActivity().sendBroadcast(intent);[m
[31m-            } catch (IllegalStateException ise) {[m
[32m+[m[32m            } catch (IllegalStateException ignored) {[m
 [m
             }[m
 [m
[36m@@ -222,10 +220,10 @@[m [mpublic class HomeFragment extends Fragment  {[m
         @Override[m
         public void onAnimationStart(Animator animator) {}[m
 [m
[31m-        private View mParentView;[m
[31m-        private ValueAnimator mReverseWhenDone;[m
[31m-        private ProgressBar mProgressChecking;[m
[31m-        ValueAnimator mProgressAnimation;[m
[32m+[m[32m        private final View mParentView;[m
[32m+[m[32m        private final ValueAnimator mReverseWhenDone;[m
[32m+[m[32m        private final ProgressBar mProgressChecking;[m
[32m+[m[32m        final ValueAnimator mProgressAnimation;[m
         public WelcomeAnimationListener(View parentView, final ValueAnimator reverseWhenDone, ProgressBar progressChecking, ValueAnimator progressAnimation) {[m
             mParentView = parentView;[m
             mReverseWhenDone = reverseWhenDone;[m
[36m@@ -571,7 +569,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
                                 intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);[m
                                 try {[m
                                     getActivity().sendBroadcast(intent);[m
[31m-                                } catch (IllegalStateException ise) {[m
[32m+[m[32m                                } catch (IllegalStateException ignored) {[m
 [m
                                 }[m
                                 loadStatsFromSource(view);[m
[36m@@ -588,11 +586,6 @@[m [mpublic class HomeFragment extends Fragment  {[m
         });[m
     }[m
 [m
[31m-    @Override[m
[31m-    public void onResume() {[m
[31m-        super.onResume();[m
[31m-    }[m
[31m-[m
 [m
     private boolean isXposedInstalled() {[m
         PackageManager pm = getActivity().getPackageManager();[m
[36m@@ -609,21 +602,15 @@[m [mpublic class HomeFragment extends Fragment  {[m
     private boolean isInstalledFromPlay() {[m
         String installer = getActivity().getPackageManager().getInstallerPackageName("com.ryansteckler.nlpunbounce");[m
 [m
[31m-        if (installer == null) {[m
[31m-            return false;[m
[31m-        }[m
[31m-        else {[m
[31m-            return installer.equals("com.android.vending");[m
[31m-        }[m
[32m+[m[32m        return installer != null && installer.equals("com.android.vending");[m
     }[m
 [m
[31m-    private boolean launchXposedModules() {[m
[32m+[m[32m    private void launchXposedModules() {[m
         Intent LaunchIntent = null;[m
 [m
         try {[m
             LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("de.robv.android.xposed.installer");[m
             if (LaunchIntent == null) {[m
[31m-                return false;[m
             } else {[m
                 Intent intent = new Intent("de.robv.android.xposed.installer.OPEN_SECTION");[m
                 intent.setPackage("de.robv.android.xposed.installer");[m
[36m@@ -636,19 +623,16 @@[m [mpublic class HomeFragment extends Fragment  {[m
                 LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);[m
                 startActivity(LaunchIntent);[m
             } else {[m
[31m-                return false;[m
             }[m
         }[m
[31m-        return true;[m
     }[m
 [m
[31m-    private boolean launchXposedFramework() {[m
[32m+[m[32m    private void launchXposedFramework() {[m
         Intent LaunchIntent = null;[m
 [m
         try {[m
             LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("de.robv.android.xposed.installer");[m
             if (LaunchIntent == null) {[m
[31m-                return false;[m
             } else {[m
                 Intent intent = new Intent("de.robv.android.xposed.installer.OPEN_SECTION");[m
                 intent.setPackage("de.robv.android.xposed.installer");[m
[36m@@ -661,10 +645,8 @@[m [mpublic class HomeFragment extends Fragment  {[m
                 LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);[m
                 startActivity(LaunchIntent);[m
             } else {[m
[31m-                return false;[m
             }[m
         }[m
[31m-        return true;[m
     }[m
 [m
     private BroadcastReceiver refreshReceiver;[m
[36m@@ -681,9 +663,9 @@[m [mpublic class HomeFragment extends Fragment  {[m
 [m
     private void updatePremiumUi() {[m
         if (((MaterialSettingsActivity)getActivity()).isPremium()) {[m
[31m-            View againView = (View) getActivity().findViewById(R.id.layoutDonateAgain);[m
[32m+[m[32m            View againView = getActivity().findViewById(R.id.layoutDonateAgain);[m
             againView.setVisibility(View.VISIBLE);[m
[31m-            View donateView = (View) getActivity().findViewById(R.id.layoutDonate);[m
[32m+[m[32m            View donateView = getActivity().findViewById(R.id.layoutDonate);[m
             donateView.setVisibility(View.GONE);[m
         }[m
     }[m
[36m@@ -691,7 +673,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
     private void loadStatsFromSource(final View view) {[m
         final UnbounceStatsCollection stats = UnbounceStatsCollection.getInstance();[m
         final Context c = getActivity();[m
[31m-        stats.loadStats(c, true);[m
[32m+[m[32m        stats.loadStats(true);[m
         String duration = stats.getWakelockDurationAllowedFormatted(c, UnbounceStatsCollection.STAT_CURRENT);[m
         //Wakelocks[m
         TextView textView = (TextView)view.findViewById(R.id.textLocalWakeTimeAllowed);[m
[36m@@ -779,8 +761,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
                              Bundle savedInstanceState) {[m
 [m
[31m-        View rootView = inflater.inflate(R.layout.fragment_home, container, false);[m
[31m-        return rootView;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_home, container, false);[m
     }[m
 [m
 [m
[36m@@ -822,7 +803,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
         Intent intent = new Intent(XposedReceiver.REFRESH_ACTION);[m
         try {[m
             getActivity().sendBroadcast(intent);[m
[31m-        } catch (IllegalStateException ise) {[m
[32m+[m[32m        } catch (IllegalStateException ignored) {[m
 [m
         }[m
     }[m
[36m@@ -838,7 +819,7 @@[m [mpublic class HomeFragment extends Fragment  {[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface OnFragmentInteractionListener {[m
[31m-        public void onHomeSetTitle(String id);[m
[32m+[m[32m        void onHomeSetTitle(String id);[m
     }[m
 [m
     private void animateButtonContainer(final ViewGroup container) {[m
[36m@@ -893,19 +874,19 @@[m [mpublic class HomeFragment extends Fragment  {[m
     }[m
 [m
 [m
[31m-    public boolean isUnbounceServiceRunning() {[m
[32m+[m[32m    private boolean isUnbounceServiceRunning() {[m
         //The Unbounce hook changes this to true.[m
         return false;[m
     }[m
 [m
[31m-    public String getAmplifyKernelVersion() {[m
[32m+[m[32m    private String getAmplifyKernelVersion() {[m
         //The Unbounce hook changes this to true.[m
         return "0";[m
     }[m
 [m
 [m
 [m
[31m-    public boolean isXposedRunning() {[m
[32m+[m[32m    private boolean isXposedRunning() {[m
         return new File("/data/data/de.robv.android.xposed.installer/bin/XposedBridge.jar").exists();[m
     }[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/MaterialSettingsActivity.java b/app/src/main/java/com/ryansteckler/nlpunbounce/MaterialSettingsActivity.java[m
[1mindex aaaf1ce..4967318 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/MaterialSettingsActivity.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/MaterialSettingsActivity.java[m
[36m@@ -35,15 +35,10 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
         ServicesFragment.OnFragmentInteractionListener[m
     {[m
 [m
[31m-    /**[m
[31m-     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.[m
[31m-     */[m
[31m-    private NavigationDrawerFragment mNavigationDrawerFragment;[m
[32m+[m[32m        IabHelper mHelper;[m
 [m
[31m-    IabHelper mHelper;[m
[31m-[m
[31m-    int mCurTheme = ThemeHelper.THEME_DEFAULT;[m
[31m-    int mCurForceEnglish = -1;[m
[32m+[m[32m    private int mCurTheme = ThemeHelper.THEME_DEFAULT;[m
[32m+[m[32m    private int mCurForceEnglish = -1;[m
 [m
     private boolean mIsPremium = true;[m
 [m
[36m@@ -63,7 +58,10 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
         mCurForceEnglish = LocaleHelper.onActivityCreateSetLocale(this);[m
         setContentView(R.layout.activity_material_settings);[m
 [m
[31m-        mNavigationDrawerFragment = (NavigationDrawerFragment)[m
[32m+[m[32m        /*[m
[32m+[m[32m      Fragment managing the behaviors, interactions and presentation of the navigation drawer.[m
[32m+[m[32m     */[m
[32m+[m[32m        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)[m
                 getFragmentManager().findFragmentById(R.id.navigation_drawer);[m
         mTitle = getTitle();[m
 [m
[36m@@ -153,10 +151,10 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
 [m
     private void updateDonationUi() {[m
         if (isPremium()) {[m
[31m-            View againView = (View) findViewById(R.id.layoutDonateAgain);[m
[32m+[m[32m            View againView = findViewById(R.id.layoutDonateAgain);[m
             if (againView != null)[m
                 againView.setVisibility(View.VISIBLE);[m
[31m-            View donateView = (View) findViewById(R.id.layoutDonate);[m
[32m+[m[32m            View donateView = findViewById(R.id.layoutDonate);[m
             if (donateView != null)[m
                 donateView.setVisibility(View.GONE);[m
         }[m
[36m@@ -170,12 +168,12 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
     @Override[m
     protected void onActivityResult(int requestCode, int resultCode, Intent data)[m
     {[m
[31m-        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {[m
[32m+[m[32m        if (mHelper.handleActivityResult(requestCode, resultCode, data)) {[m
             super.onActivityResult(requestCode, resultCode, data);[m
         }[m
     }[m
 [m
[31m-    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {[m
[32m+[m[32m    final IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {[m
         public void onIabPurchaseFinished(IabResult result, Purchase purchase)[m
         {[m
             if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED ||[m
[36m@@ -205,7 +203,7 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
 [m
         }[m
 [m
[31m-        IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {[m
[32m+[m[32m        final IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {[m
             public void onConsumeFinished(Purchase purchase, IabResult result) {[m
                 //Do nothing[m
             }[m
[36m@@ -253,7 +251,7 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
         }[m
     }[m
 [m
[31m-    public void restoreActionBar() {[m
[32m+[m[32m    private void restoreActionBar() {[m
         ActionBar actionBar = getActionBar();[m
         actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);[m
         actionBar.setDisplayShowTitleEnabled(true);[m
[36m@@ -335,7 +333,7 @@[m [mpublic class MaterialSettingsActivity extends Activity[m
         }[m
 [m
         @Override[m
[31m-        public void onSetTaskerTitle(String title) {[m
[32m+[m[32m        public void onSetTaskerTitle() {[m
             //Do nothing because we're not in Tasker mode.[m
 [m
         }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/NavigationDrawerFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/NavigationDrawerFragment.java[m
[1mindex a76b7c9..4a16ffe 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/NavigationDrawerFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/NavigationDrawerFragment.java[m
[36m@@ -49,7 +49,6 @@[m [mpublic class NavigationDrawerFragment extends Fragment {[m
     private View mFragmentContainerView;[m
 [m
     private int mCurrentSelectedPosition = 0;[m
[31m-    private boolean mFromSavedInstanceState;[m
 [m
     public NavigationDrawerFragment() {[m
     }[m
[36m@@ -61,7 +60,7 @@[m [mpublic class NavigationDrawerFragment extends Fragment {[m
 [m
         if (savedInstanceState != null) {[m
             mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);[m
[31m-            mFromSavedInstanceState = true;[m
[32m+[m[32m            boolean mFromSavedInstanceState = true;[m
         }[m
 [m
         // Select either the default item (0) or the last selected item.[m
[36m@@ -86,7 +85,7 @@[m [mpublic class NavigationDrawerFragment extends Fragment {[m
                 selectItem(position);[m
             }[m
         });[m
[31m-        mDrawerListView.setAdapter(new ArrayAdapter<String>([m
[32m+[m[32m        mDrawerListView.setAdapter(new ArrayAdapter<>([m
                 getActionBar().getThemedContext(),[m
                 android.R.layout.simple_list_item_activated_1,[m
                 android.R.id.text1,[m
[36m@@ -101,7 +100,7 @@[m [mpublic class NavigationDrawerFragment extends Fragment {[m
         return mDrawerListView;[m
     }[m
 [m
[31m-    public boolean isDrawerOpen() {[m
[32m+[m[32m    private boolean isDrawerOpen() {[m
         return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);[m
     }[m
 [m
[36m@@ -251,7 +250,7 @@[m [mpublic class NavigationDrawerFragment extends Fragment {[m
     /**[m
      * Callbacks interface that all activities using this fragment must implement.[m
      */[m
[31m-    public static interface NavigationDrawerCallbacks {[m
[32m+[m[32m    public interface NavigationDrawerCallbacks {[m
         /**[m
          * Called when an item in the navigation drawer is selected.[m
          */[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/ServiceDetailFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/ServiceDetailFragment.java[m
[1mindex 398ad43..ead9beb 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/ServiceDetailFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/ServiceDetailFragment.java[m
[36m@@ -4,8 +4,6 @@[m [mimport android.app.AlertDialog;[m
 import android.content.Context;[m
 import android.content.DialogInterface;[m
 import android.content.SharedPreferences;[m
[31m-import android.content.pm.ApplicationInfo;[m
[31m-import android.content.pm.PackageManager;[m
 import android.os.Bundle;[m
 import android.view.LayoutInflater;[m
 import android.view.View;[m
[36m@@ -13,7 +11,6 @@[m [mimport android.view.ViewGroup;[m
 import android.widget.Switch;[m
 import android.widget.TextView;[m
 [m
[31m-import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 [m
 /**[m
[36m@@ -102,8 +99,7 @@[m [mpublic class ServiceDetailFragment extends BaseDetailFragment {[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
                              Bundle savedInstanceState) {[m
         // Inflate the layout for this fragment[m
[31m-        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_service_detail, container, false);[m
     }[m
 [m
     @Override[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/ServicesFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/ServicesFragment.java[m
[1mindex 2a4d580..7b0bfe9 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/ServicesFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/ServicesFragment.java[m
[36m@@ -47,7 +47,7 @@[m [mpublic class ServicesFragment extends ListFragment implements ServiceDetailFragm[m
         return newInstance(false);[m
     }[m
 [m
[31m-    public static ServicesFragment newInstance(boolean taskerMode) {[m
[32m+[m[32m    private static ServicesFragment newInstance(boolean taskerMode) {[m
         ServicesFragment fragment = new ServicesFragment();[m
         Bundle args = new Bundle();[m
         args.putBoolean(ARG_TASKER_MODE, taskerMode);[m
[36m@@ -58,8 +58,7 @@[m [mpublic class ServicesFragment extends ListFragment implements ServiceDetailFragm[m
     @Override[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {[m
         super.onCreateView(inflater, container, savedInstanceState);[m
[31m-        View view = inflater.inflate(R.layout.fragment_stats, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_stats, container, false);[m
     }[m
 [m
     @Override[m
[36m@@ -238,7 +237,7 @@[m [mpublic class ServicesFragment extends ListFragment implements ServiceDetailFragm[m
         if (!hidden) {[m
             if (mListener != null) {[m
                 mListener.onServicesSetTitle(getResources().getString(R.string.title_services));[m
[31m-                mListener.onSetTaskerTitle(getResources().getString(R.string.tasker_choose_service));[m
[32m+[m[32m                mListener.onSetTaskerTitle();[m
             }[m
             if (mReloadOnShow) {[m
                 mReloadOnShow = false;[m
[36m@@ -268,9 +267,9 @@[m [mpublic class ServicesFragment extends ListFragment implements ServiceDetailFragm[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface OnFragmentInteractionListener {[m
[31m-        public void onServicesSetTitle(String id);[m
[32m+[m[32m        void onServicesSetTitle(String id);[m
 [m
[31m-        public void onSetTaskerTitle(String title);[m
[32m+[m[32m        void onSetTaskerTitle();[m
     }[m
 [m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/SettingsActivity.java b/app/src/main/java/com/ryansteckler/nlpunbounce/SettingsActivity.java[m
[1mindex ddd3aeb..de8a638 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/SettingsActivity.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/SettingsActivity.java[m
[36m@@ -22,11 +22,11 @@[m [mpublic class SettingsActivity extends Activity {[m
 [m
     private static final String TAG = "UnbounceSettings";[m
 [m
[31m-    int mCurTheme = ThemeHelper.THEME_DEFAULT;[m
[31m-    int mCurForceEnglish = -1;[m
[32m+[m[32m    private int mCurTheme = ThemeHelper.THEME_DEFAULT;[m
[32m+[m[32m    private int mCurForceEnglish = -1;[m
 [m
[31m-    static int mClicksOnDebug = 0;[m
[31m-    static Preference mExtendedDebugCategory = null;[m
[32m+[m[32m    private static int mClicksOnDebug = 0;[m
[32m+[m[32m    private static Preference mExtendedDebugCategory = null;[m
 [m
     @Override[m
     public void onCreate(Bundle savedInstanceState) {[m
[36m@@ -70,7 +70,7 @@[m [mpublic class SettingsActivity extends Activity {[m
             onSharedPreferenceChanged(sharedPref, "show_launcher_icon");[m
 [m
             //Hook up the custom clicks[m
[31m-            Preference pref = (Preference) findPreference("reset_defaults");[m
[32m+[m[32m            Preference pref = findPreference("reset_defaults");[m
             pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {[m
                 @Override[m
                 public boolean onPreferenceClick(Preference preference) {[m
[36m@@ -95,7 +95,7 @@[m [mpublic class SettingsActivity extends Activity {[m
                 }[m
             });[m
 [m
[31m-            pref = (Preference) findPreference("about_author");[m
[32m+[m[32m            pref = findPreference("about_author");[m
             pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {[m
                 @Override[m
                 public boolean onPreferenceClick(Preference preference) {[m
[36m@@ -127,56 +127,64 @@[m [mpublic class SettingsActivity extends Activity {[m
 [m
         private void enableDependent(String control, boolean enable)[m
         {[m
[31m-            Preference controlToAffect = (Preference)findPreference(control);[m
[32m+[m[32m            Preference controlToAffect = findPreference(control);[m
             controlToAffect.setEnabled(enable);[m
         }[m
 [m
         @Override[m
         public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {[m
 [m
[31m-            if (key.equals("logging_level"))[m
[31m-            {[m
[31m-                ListPreference pref = (ListPreference) findPreference(key);[m
[31m-                String entry = sharedPreferences.getString(key, "default");[m
[31m-                if (entry.equals("quiet")) {[m
[31m-                    pref.setSummary("Quiet");[m
[31m-                } else if (entry.equals("verbose")) {[m
[31m-                    pref.setSummary("Verbose");[m
[31m-                } else {[m
[31m-                    pref.setSummary("Default");[m
[32m+[m[32m            switch (key) {[m
[32m+[m[32m                case "logging_level": {[m
[32m+[m[32m                    ListPreference pref = (ListPreference) findPreference(key);[m
[32m+[m[32m                    String entry = sharedPreferences.getString(key, "default");[m
[32m+[m[32m                    if (entry.equals("quiet")) {[m
[32m+[m[32m                        pref.setSummary("Quiet");[m
[32m+[m[32m                    } else if (entry.equals("verbose")) {[m
[32m+[m[32m                        pref.setSummary("Verbose");[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        pref.setSummary("Default");[m
[32m+[m[32m                    }[m
[32m+[m[32m                    break;[m
                 }[m
[31m-            }[m
[31m-            else if (key.equals("show_launcher_icon")) {[m
[31m-                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[31m-                boolean value = sharedPreferences.getBoolean(key, false);[m
[31m-                pref.setChecked(value);[m
[31m-[m
[31m-                PackageManager packageManager = getActivity().getPackageManager();[m
[31m-                int state = value ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;[m
[31m-                ComponentName aliasName = new ComponentName(getActivity(), "com.ryansteckler.nlpunbounce.Settings-Alias");[m
[31m-                packageManager.setComponentEnabledSetting(aliasName, state, PackageManager.DONT_KILL_APP);[m
[31m-            }[m
[31m-            else if (key.equals("theme")) {[m
[31m-                ListPreference pref = (ListPreference) findPreference(key);[m
[31m-                if (sharedPreferences.getString(key, "default").equals("dark")) {[m
[31m-                    ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DARK);[m
[31m-                } else {[m
[31m-                    ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DEFAULT);[m
[32m+[m[32m                case "show_launcher_icon": {[m
[32m+[m[32m                    CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[32m+[m[32m                    boolean value = sharedPreferences.getBoolean(key, false);[m
[32m+[m[32m                    pref.setChecked(value);[m
[32m+[m
[32m+[m[32m                    PackageManager packageManager = getActivity().getPackageManager();[m
[32m+[m[32m                    int state = value ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;[m
[32m+[m[32m                    ComponentName aliasName = new ComponentName(getActivity(), "com.ryansteckler.nlpunbounce.Settings-Alias");[m
[32m+[m[32m                    packageManager.setComponentEnabledSetting(aliasName, state, PackageManager.DONT_KILL_APP);[m
[32m+[m[32m                    break;[m
[32m+[m[32m                }[m
[32m+[m[32m                case "theme": {[m
[32m+[m[32m                    ListPreference pref = (ListPreference) findPreference(key);[m
[32m+[m[32m                    if (sharedPreferences.getString(key, "default").equals("dark")) {[m
[32m+[m[32m                        ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DARK);[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DEFAULT);[m
[32m+[m[32m                    }[m
[32m+[m[32m                    break;[m
[32m+[m[32m                }[m
[32m+[m[32m                case "force_english": {[m
[32m+[m[32m                    CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[32m+[m[32m                    boolean value = sharedPreferences.getBoolean(key, false);[m
[32m+[m[32m                    pref.setChecked(value);[m
[32m+[m
[32m+[m[32m                    if (value) {[m
[32m+[m[32m                        LocaleHelper.forceEnglish(this.getActivity());[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        LocaleHelper.revertToSystem(getActivity());[m
[32m+[m[32m                    }[m
[32m+[m[32m                    break;[m
                 }[m
[31m-            } else if (key.equals("force_english")) {[m
[31m-                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[31m-                boolean value = sharedPreferences.getBoolean(key, false);[m
[31m-                pref.setChecked(value);[m
[31m-[m
[31m-                if (value) {[m
[31m-                    LocaleHelper.forceEnglish(this.getActivity());[m
[31m-                } else {[m
[31m-                    LocaleHelper.revertToSystem(getActivity());[m
[32m+[m[32m                case "enable_service_block": {[m
[32m+[m[32m                    CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[32m+[m[32m                    boolean value = sharedPreferences.getBoolean(key, false);[m
[32m+[m[32m                    pref.setChecked(value);[m
[32m+[m[32m                    break;[m
                 }[m
[31m-            }else if (key.equals("enable_service_block")) {[m
[31m-                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);[m
[31m-                boolean value = sharedPreferences.getBoolean(key, false);[m
[31m-                pref.setChecked(value);[m
             }[m
 [m
         }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/WakelockDetailFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/WakelockDetailFragment.java[m
[1mindex 969e9c8..5d68b6c 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/WakelockDetailFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/WakelockDetailFragment.java[m
[36m@@ -21,7 +21,6 @@[m [mimport android.widget.EditText;[m
 import android.widget.Switch;[m
 import android.widget.TextView;[m
 [m
[31m-import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 import com.ryansteckler.nlpunbounce.models.WakelockStats;[m
 [m
[36m@@ -39,8 +38,7 @@[m [mpublic class WakelockDetailFragment extends BaseDetailFragment {[m
     public long getSeconds() {[m
         EditText editSeconds = (EditText) getActivity().findViewById(R.id.editWakelockSeconds);[m
         String text = editSeconds.getText().toString();[m
[31m-        long seconds = Long.parseLong(text);[m
[31m-        return seconds;[m
[32m+[m[32m        return Long.parseLong(text);[m
     }[m
 [m
     @Override[m
[36m@@ -85,7 +83,7 @@[m [mpublic class WakelockDetailFragment extends BaseDetailFragment {[m
 [m
         getView().findViewById(R.id.editWakelockSeconds).setEnabled(onOff.isChecked());[m
 [m
[31m-        View panel = (View) getView().findViewById(R.id.settingsPanel);[m
[32m+[m[32m        View panel = getView().findViewById(R.id.settingsPanel);[m
         TypedValue backgroundValue = new TypedValue();[m
         Resources.Theme theme = getActivity().getTheme();[m
         int resId = enabled ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;[m
[36m@@ -158,7 +156,7 @@[m [mpublic class WakelockDetailFragment extends BaseDetailFragment {[m
 [m
         //Enable or disable the seconds setting.[m
         getView().findViewById(R.id.editWakelockSeconds).setEnabled(b);[m
[31m-        View panel = (View) getView().findViewById(R.id.settingsPanel);[m
[32m+[m[32m        View panel = getView().findViewById(R.id.settingsPanel);[m
         TypedValue backgroundValue = new TypedValue();[m
         Resources.Theme theme = getActivity().getTheme();[m
         int resId = b ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;[m
[36m@@ -203,8 +201,7 @@[m [mpublic class WakelockDetailFragment extends BaseDetailFragment {[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
                              Bundle savedInstanceState) {[m
         // Inflate the layout for this fragment[m
[31m-        View view = inflater.inflate(R.layout.fragment_wakelock_detail, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_wakelock_detail, container, false);[m
     }[m
 [m
     @Override[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/WakelocksFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/WakelocksFragment.java[m
[1mindex 686ab07..fa5624e 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/WakelocksFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/WakelocksFragment.java[m
[36m@@ -64,8 +64,7 @@[m [mpublic class WakelocksFragment extends ListFragment implements BaseDetailFragmen[m
     @Override[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {[m
         super.onCreateView(inflater, container, savedInstanceState);[m
[31m-        View view = inflater.inflate(R.layout.fragment_stats, container, false);[m
[31m-        return view;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_stats, container, false);[m
     }[m
 [m
     @Override[m
[36m@@ -280,9 +279,9 @@[m [mpublic class WakelocksFragment extends ListFragment implements BaseDetailFragmen[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface OnFragmentInteractionListener {[m
[31m-        public void onWakelocksSetTitle(String id);[m
[32m+[m[32m        void onWakelocksSetTitle(String id);[m
 [m
[31m-        public void onWakelocksSetTaskerTitle(String id);[m
[32m+[m[32m        void onWakelocksSetTaskerTitle(String id);[m
     }[m
 [m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/XposedReceiver.java b/app/src/main/java/com/ryansteckler/nlpunbounce/XposedReceiver.java[m
[1mindex 9a2d251..ee97d47 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/XposedReceiver.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/XposedReceiver.java[m
[36m@@ -13,7 +13,7 @@[m [mpublic class XposedReceiver extends BroadcastReceiver {[m
 [m
     public final static String RESET_ACTION = "com.ryansteckler.nlpunbounce.RESET_STATS";[m
     public final static String REFRESH_ACTION = "com.ryansteckler.nlpunbounce.REFRESH_STATS";[m
[31m-    public final static String STAT_NAME = "stat_name";[m
[32m+[m[32m    private final static String STAT_NAME = "stat_name";[m
     public final static String STAT_TYPE = "stat_type";[m
 [m
     @Override[m
[36m@@ -29,12 +29,12 @@[m [mpublic class XposedReceiver extends BroadcastReceiver {[m
                 collection.resetLocalStats(statName);[m
             }[m
         } else if (action.equals(REFRESH_ACTION)) {[m
[31m-            if (!UnbounceStatsCollection.getInstance().saveNow(context)) {[m
[32m+[m[32m            if (UnbounceStatsCollection.getInstance().saveNow(context)) {[m
                 //Request the activity to create the files.[m
                 Intent createIntent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);[m
                 try {[m
                     context.sendBroadcast(createIntent);[m
[31m-                } catch (IllegalStateException ise) {[m
[32m+[m[32m                } catch (IllegalStateException ignored) {[m
                 }[m
             }[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/AlarmsAdapter.java b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/AlarmsAdapter.java[m
[1mindex b8ff643..6b24ac5 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/AlarmsAdapter.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/AlarmsAdapter.java[m
[36m@@ -1,30 +1,20 @@[m
 package com.ryansteckler.nlpunbounce.adapters;[m
 [m
 import android.content.Context;[m
[31m-import android.content.SharedPreferences;[m
 import android.graphics.Color;[m
 import android.view.LayoutInflater;[m
 import android.view.View;[m
 import android.view.ViewGroup;[m
[31m-import android.widget.ArrayAdapter;[m
[31m-import android.widget.Filter;[m
[31m-import android.widget.Filterable;[m
 import android.widget.LinearLayout;[m
 import android.widget.TextView;[m
 [m
 import com.ryansteckler.nlpunbounce.R;[m
 import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;[m
[31m-import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;[m
 import com.ryansteckler.nlpunbounce.models.AlarmStats;[m
 import com.ryansteckler.nlpunbounce.models.BaseStats;[m
[31m-import com.ryansteckler.nlpunbounce.models.EventLookup;[m
[31m-import com.ryansteckler.nlpunbounce.models.WakelockStats;[m
[31m-[m
 [m
 import java.util.ArrayList;[m
 import java.util.Collections;[m
[31m-import java.util.Iterator;[m
[31m-import java.util.List;[m
 [m
 /**[m
  * Created by rsteckler on 9/7/14.[m
[36m@@ -86,7 +76,6 @@[m [mpublic class AlarmsAdapter extends BaseAdapter {[m
 [m
                 //Set the background color along the reg-green spectrum based on the severity of the count.[m
                 float correctedStat = alarm.getAllowedCount() - mLowCount;[m
[31m-                float point = 120 - ((correctedStat / mScale) * 120); //this gives us a 1-120 hue number.[m
 [m
                 float[] hsv = getBackColorFromSpectrum(alarm);[m
                 alarmViewHolder.alarmCount.setBackgroundColor(Color.HSVToColor(hsv));[m
[36m@@ -108,7 +97,7 @@[m [mpublic class AlarmsAdapter extends BaseAdapter {[m
 [m
 [m
 [m
[31m-    public void sort(int sortBy, boolean categorize) {[m
[32m+[m[32m    protected void sort(int sortBy, boolean categorize) {[m
         mSortBy = sortBy;[m
         Collections.sort(mBackingList, SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));[m
         sort(SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/BaseAdapter.java b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/BaseAdapter.java[m
[1mindex 0b81c24..d5d4c26 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/BaseAdapter.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/BaseAdapter.java[m
[36m@@ -12,7 +12,6 @@[m [mimport android.widget.TextView;[m
 import com.ryansteckler.nlpunbounce.R;[m
 import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;[m
 import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;[m
[31m-import com.ryansteckler.nlpunbounce.models.AlarmStats;[m
 import com.ryansteckler.nlpunbounce.models.BaseStats;[m
 import com.ryansteckler.nlpunbounce.models.EventLookup;[m
 [m
[36m@@ -24,24 +23,24 @@[m [mimport java.util.List;[m
  * Created by rsteckler on 10/21/14.[m
  */[m
 public abstract class BaseAdapter extends ArrayAdapter {[m
[31m-    protected long mLowCount = 0;[m
[31m-    protected long mHighCount = 0;[m
[31m-    protected long mScale = 0;[m
[31m-    private String mPrefix;[m
[32m+[m[32m    long mLowCount = 0;[m
[32m+[m[32m    private long mHighCount = 0;[m
[32m+[m[32m    long mScale = 0;[m
[32m+[m[32m    private final String mPrefix;[m
 [m
     private long mCategoryBlockedIndex = 0;[m
     private long mCategorySafeIndex = 0;[m
     private long mCategoryUnknownIndex = 0;[m
     private long mCategoryUnsafeIndex = 0;[m
 [m
[31m-    protected final static int ITEM_TYPE = 0;[m
[31m-    protected final static int CATEGORY_TYPE = 1;[m
[32m+[m[32m    final static int ITEM_TYPE = 0;[m
[32m+[m[32m    final static int CATEGORY_TYPE = 1;[m
 [m
[31m-    protected ArrayList<BaseStats> mBackingList = null;[m
[31m-    protected ArrayList<BaseStats> originalList;[m
[32m+[m[32m    ArrayList<BaseStats> mBackingList = null;[m
[32m+[m[32m    private final ArrayList<BaseStats> originalList;[m
 [m
     //Track whether we're sorting by count or duration.[m
[31m-    protected int mSortBy = SortWakeLocks.SORT_COUNT;[m
[32m+[m[32m    int mSortBy = SortWakeLocks.SORT_COUNT;[m
 [m
     //protected Map<String, List<BaseStats>> mapPackageIndexMap = new HashMap<String, List<BaseStats>>();[m
 [m
[36m@@ -55,7 +54,7 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         return position;[m
     }[m
 [m
[31m-    public BaseAdapter(Context context, int layoutId, ArrayList<BaseStats> baseStatList, String prefix) {[m
[32m+[m[32m    BaseAdapter(Context context, int layoutId, ArrayList<BaseStats> baseStatList, String prefix) {[m
         super(context, layoutId, baseStatList);[m
         mPrefix = prefix;[m
         mBackingList = baseStatList;[m
[36m@@ -65,7 +64,7 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         //addPackgeBasedCategories(mBackingList);[m
     }[m
 [m
[31m-    protected void addCategories(ArrayList<BaseStats> alarmStatList) {[m
[32m+[m[32m    void addCategories(ArrayList<BaseStats> alarmStatList) {[m
         mCategoryBlockedIndex = 0;[m
         mCategorySafeIndex = 1;[m
         mCategoryUnknownIndex = 2;[m
[36m@@ -103,9 +102,7 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);[m
 [m
         //Get the max and min values for the red-green spectrum of counts[m
[31m-        Iterator<BaseStats> iter = baseStatList.iterator();[m
[31m-        while (iter.hasNext()) {[m
[31m-            BaseStats curStat = iter.next();[m
[32m+[m[32m        for (BaseStats curStat : baseStatList) {[m
             if (curStat.getAllowedCount() > mHighCount)[m
                 mHighCount = curStat.getAllowedCount();[m
             if (curStat.getAllowedCount() < mLowCount || mLowCount == 0)[m
[36m@@ -161,9 +158,9 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         TextView name;[m
     }[m
 [m
[31m-    protected View getCategoryView(int position, View convertView, ViewGroup parent) {[m
[32m+[m[32m    View getCategoryView(int position, View convertView, ViewGroup parent) {[m
         //Take care of the category special cases.[m
[31m-        CategoryViewHolder categoryViewHolder = null; // view lookup cache stored in tag[m
[32m+[m[32m        CategoryViewHolder categoryViewHolder; // view lookup cache stored in tag[m
 [m
         // Check if an existing view is being reused, otherwise inflate the view[m
         if (convertView == null) {[m
[36m@@ -192,7 +189,7 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         return convertView;[m
     }[m
 [m
[31m-    protected float[] getBackColorFromSpectrum(BaseStats alarm) {[m
[32m+[m[32m    float[] getBackColorFromSpectrum(BaseStats alarm) {[m
         float correctedStat = alarm.getAllowedCount() - mLowCount;[m
         //Set the background color along the reg-green spectrum based on the severity of the count.[m
         if (ThemeHelper.getTheme() == ThemeHelper.THEME_DEFAULT) {[m
[36m@@ -204,7 +201,7 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         }[m
     }[m
 [m
[31m-    protected float[] getForeColorFromBack(float[] hsvBack) {[m
[32m+[m[32m    float[] getForeColorFromBack(float[] hsvBack) {[m
         if (ThemeHelper.getTheme() == ThemeHelper.THEME_DEFAULT) {[m
             return new float[]{0, 0, 0};[m
         } else {[m
[36m@@ -217,6 +214,8 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
         }[m
     }[m
 [m
[32m+[m
[32m+[m
     protected abstract void sort(int sortBy, boolean categorize);[m
 [m
         @Override[m
[36m@@ -233,9 +232,8 @@[m [mpublic abstract class BaseAdapter extends ArrayAdapter {[m
             @Override[m
             protected FilterResults performFiltering(CharSequence constraint) {[m
                 FilterResults results = new FilterResults();[m
[31m-                List<BaseStats> filteredResults = getFilteredResults(constraint);[m
 [m
[31m-                results.values = filteredResults;[m
[32m+[m[32m                results.values = getFilteredResults(constraint);[m
 [m
                 return results;[m
             }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/ServicesAdapter.java b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/ServicesAdapter.java[m
[1mindex d6a26ea..0089a1c 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/ServicesAdapter.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/ServicesAdapter.java[m
[36m@@ -10,7 +10,6 @@[m [mimport android.widget.TextView;[m
 [m
 import com.ryansteckler.nlpunbounce.R;[m
 import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;[m
[31m-import com.ryansteckler.nlpunbounce.models.AlarmStats;[m
 import com.ryansteckler.nlpunbounce.models.BaseStats;[m
 import com.ryansteckler.nlpunbounce.models.ServiceStats;[m
 [m
[36m@@ -97,7 +96,7 @@[m [mpublic class ServicesAdapter extends BaseAdapter {[m
 [m
 [m
 [m
[31m-    public void sort(int sortBy, boolean categorize) {[m
[32m+[m[32m    protected void sort(int sortBy, boolean categorize) {[m
         mSortBy = sortBy;[m
         Collections.sort(mBackingList, SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));[m
         sort(SortWakeLocks.getBaseListComparator(mSortBy, categorize,this.getContext()));[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/WakelocksAdapter.java b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/WakelocksAdapter.java[m
[1mindex 677299a..3b16421 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/WakelocksAdapter.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/adapters/WakelocksAdapter.java[m
[36m@@ -1,26 +1,20 @@[m
 package com.ryansteckler.nlpunbounce.adapters;[m
 [m
 import android.content.Context;[m
[31m-import android.content.SharedPreferences;[m
 import android.graphics.Color;[m
[31m-import android.graphics.Typeface;[m
 import android.view.LayoutInflater;[m
 import android.view.View;[m
 import android.view.ViewGroup;[m
[31m-import android.widget.ArrayAdapter;[m
 import android.widget.LinearLayout;[m
 import android.widget.TextView;[m
 [m
 import com.ryansteckler.nlpunbounce.R;[m
 import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;[m
[31m-import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;[m
 import com.ryansteckler.nlpunbounce.models.BaseStats;[m
[31m-import com.ryansteckler.nlpunbounce.models.EventLookup;[m
 import com.ryansteckler.nlpunbounce.models.WakelockStats;[m
 [m
 import java.util.ArrayList;[m
 import java.util.Collections;[m
[31m-import java.util.Iterator;[m
 [m
 /**[m
  * Created by rsteckler on 9/7/14.[m
[36m@@ -96,7 +90,7 @@[m [mpublic class WakelocksAdapter extends BaseAdapter {[m
         return convertView;[m
     }[m
 [m
[31m-    public void sort(int sortBy, boolean categorize) {[m
[32m+[m[32m    protected void sort(int sortBy, boolean categorize) {[m
         mSortBy = sortBy;[m
         Collections.sort(mBackingList, SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));[m
         sort(SortWakeLocks.getWakelockListComparator(mSortBy, categorize, this.getContext()));[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/DownloadHelper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/DownloadHelper.java[m
[1mindex 1ffd316..dbd84ac 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/DownloadHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/DownloadHelper.java[m
[36m@@ -71,7 +71,7 @@[m [mpublic class DownloadHelper {[m
     }[m
 [m
     public interface DownloadListener {[m
[31m-        public void onFinished(boolean success, String filename);[m
[32m+[m[32m        void onFinished(boolean success, String filename);[m
     }[m
 [m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LocaleHelper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LocaleHelper.java[m
[1mindex 440e197..881e6d0 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LocaleHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LocaleHelper.java[m
[36m@@ -5,6 +5,7 @@[m [mimport android.content.Context;[m
 import android.content.Intent;[m
 import android.content.SharedPreferences;[m
 import android.content.res.Configuration;[m
[32m+[m[32mimport android.content.res.Resources;[m
 [m
 import java.util.Locale;[m
 [m
[36m@@ -12,7 +13,7 @@[m [mimport java.util.Locale;[m
  * Created by rsteckler on 10/13/14.[m
  */[m
 public class LocaleHelper {[m
[31m-    public static int sForceEnglish = -1;[m
[32m+[m[32m    private static int sForceEnglish = -1;[m
     private static String sLocale = null;[m
 [m
     /**[m
[36m@@ -51,7 +52,7 @@[m [mpublic class LocaleHelper {[m
             config.locale = locale;[m
             activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());[m
         } else {[m
[31m-            Locale defaultLocale = activity.getResources().getSystem().getConfiguration().locale;[m
[32m+[m[32m            Locale defaultLocale = Resources.getSystem().getConfiguration().locale;[m
             Locale.setDefault(defaultLocale);[m
             Configuration config = new Configuration();[m
             config.locale = defaultLocale;[m
[36m@@ -90,7 +91,6 @@[m [mpublic class LocaleHelper {[m
                     sbuf.append(String.format("%02d", paramTimes[i]));[m
                     sbuf.append(":");[m
                 } else {[m
[31m-                    continue;[m
                 }[m
             } else {[m
                 sbuf.append(String.format("%02d", paramTimes[i]));[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LogHelper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LogHelper.java[m
[1mindex 14d575a..ea8a588 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LogHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/LogHelper.java[m
[36m@@ -8,7 +8,7 @@[m [mimport android.util.Log;[m
  * Created by rsteckler on 11/1/14.[m
  */[m
 public class LogHelper {[m
[31m-    private static String TAG = "Amplify";[m
[32m+[m[32m    private static final String TAG = "Amplify";[m
 [m
     public static void debugLog(Context c, String log) {[m
         if (getLogLevel(c).equals("verbose")) {[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/NetworkHelper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/NetworkHelper.java[m
[1mindex bd99a0a..625268d 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/NetworkHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/NetworkHelper.java[m
[36m@@ -46,10 +46,7 @@[m [mpublic class NetworkHelper{[m
                         return;[m
                     }[m
 [m
[31m-                } catch (ClientProtocolException e) {[m
[31m-                } catch (IOException e) {[m
[31m-                } catch (SecurityException e) {[m
[31m-                    //We were denied internet permissions.[m
[32m+[m[32m                } catch (SecurityException | IOException ignored) {[m
                 }[m
                 handler.sendEmptyMessage(0);[m
 [m
[36m@@ -57,7 +54,7 @@[m [mpublic class NetworkHelper{[m
         }.start();[m
     }[m
 [m
[31m-    public static void sendToServer(final String contentId, final String content, final String url, final Handler handler) {[m
[32m+[m[32m    public static void sendToServer(final String content, final String url, final Handler handler) {[m
         //Post the content to the URL[m
 [m
         new Thread()[m
[36m@@ -85,10 +82,7 @@[m [mpublic class NetworkHelper{[m
                         return;[m
                     }[m
 [m
[31m-                } catch (ClientProtocolException e) {[m
[31m-                } catch (IOException e) {[m
[31m-                } catch (SecurityException e) {[m
[31m-                    //We were denied internet permissions.[m
[32m+[m[32m                } catch (SecurityException | IOException ignored) {[m
                 }[m
                 handler.sendEmptyMessage(0);[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/RootHelper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/RootHelper.java[m
[1mindex ceea346..ff63381 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/RootHelper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/RootHelper.java[m
[36m@@ -22,12 +22,12 @@[m [mpublic class RootHelper {[m
         return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();[m
     }[m
 [m
[31m-    public static boolean checkRootMethod1() {[m
[32m+[m[32m    private static boolean checkRootMethod1() {[m
         String buildTags = android.os.Build.TAGS;[m
         return buildTags != null && buildTags.contains("test-keys");[m
     }[m
 [m
[31m-    public static boolean checkRootMethod2() {[m
[32m+[m[32m    private static boolean checkRootMethod2() {[m
         try {[m
             File file = new File("/system/app/Superuser.apk");[m
             return file.exists();[m
[36m@@ -36,7 +36,7 @@[m [mpublic class RootHelper {[m
         }[m
     }[m
 [m
[31m-    public static boolean checkRootMethod4() {[m
[32m+[m[32m    private static boolean checkRootMethod4() {[m
         try {[m
             File file = new File("/system/xbin/su");[m
             return file.exists();[m
[36m@@ -45,7 +45,7 @@[m [mpublic class RootHelper {[m
         }[m
     }[m
 [m
[31m-    public static boolean checkRootMethod3() {[m
[32m+[m[32m    private static boolean checkRootMethod3() {[m
         return new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary) != null;[m
     }[m
 [m
[36m@@ -95,9 +95,9 @@[m [mpublic class RootHelper {[m
 [m
         private static String LOG_TAG = ExecShell.class.getName();[m
 [m
[31m-        public static enum SHELL_CMD {[m
[32m+[m[32m        public enum SHELL_CMD {[m
             check_su_binary(new String[]{"/system/xbin/which", "su"});[m
[31m-            String[] command;[m
[32m+[m[32m            final String[] command;[m
 [m
             SHELL_CMD(String[] command) {[m
                 this.command = command;[m
[36m@@ -105,9 +105,9 @@[m [mpublic class RootHelper {[m
         }[m
 [m
         public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {[m
[31m-            String line = null;[m
[31m-            ArrayList<String> fullResponse = new ArrayList<String>();[m
[31m-            Process localProcess = null;[m
[32m+[m[32m            String line;[m
[32m+[m[32m            ArrayList<String> fullResponse = new ArrayList<>();[m
[32m+[m[32m            Process localProcess;[m
             try {[m
                 localProcess = Runtime.getRuntime().exec(shellCmd.command);[m
             } catch (Exception e) {[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/SortWakeLocks.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/SortWakeLocks.java[m
[1mindex e0f7f59..6ae70e9 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/SortWakeLocks.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/SortWakeLocks.java[m
[36m@@ -68,7 +68,7 @@[m [mpublic class SortWakeLocks {[m
                     } else if (sortType == SORT_PACKAGE) {[m
                         return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));[m
                     } else {[m
[31m-                        return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                        return o1.getName().compareTo(o2.getName());[m
                     }[m
                 } else {[m
                     if (sortType == SORT_COUNT) {[m
[36m@@ -78,7 +78,7 @@[m [mpublic class SortWakeLocks {[m
                     } else if (sortType == SORT_PACKAGE) {[m
                         return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));[m
                     } else {[m
[31m-                        return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                        return o1.getName().compareTo(o2.getName());[m
                     }[m
                 }[m
 [m
[36m@@ -113,12 +113,12 @@[m [mpublic class SortWakeLocks {[m
                             WakelockStats o1prime = (WakelockStats)o1;[m
                             return ((Long) o2prime.getAllowedDuration()).compareTo(o1prime.getAllowedDuration());[m
                         } else {[m
[31m-                            return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                            return o1.getName().compareTo(o2.getName());[m
                         }[m
                     } else if (sortType == SORT_PACKAGE) {[m
                         return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));[m
                     } else {[m
[31m-                        return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                        return o1.getName().compareTo(o2.getName());[m
                     }[m
                 } else {[m
                     if (sortType == SORT_COUNT) {[m
[36m@@ -129,12 +129,12 @@[m [mpublic class SortWakeLocks {[m
                             WakelockStats o1prime = (WakelockStats)o1;[m
                             return ((Long) o2prime.getAllowedDuration()).compareTo(o1prime.getAllowedDuration());[m
                         } else {[m
[31m-                            return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                            return o1.getName().compareTo(o2.getName());[m
                         }[m
                     } else if (sortType == SORT_PACKAGE) {[m
                         return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));[m
                     } else {[m
[31m-                        return ((String) o1.getName()).compareTo(o2.getName());[m
[32m+[m[32m                        return o1.getName().compareTo(o2.getName());[m
                     }[m
                 }[m
             }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/UidNameResolver.java b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/UidNameResolver.java[m
[1mindex e5c9740..fae5214 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/UidNameResolver.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/helpers/UidNameResolver.java[m
[36m@@ -28,8 +28,6 @@[m [mimport android.graphics.drawable.Drawable;[m
  */[m
 public class UidNameResolver {[m
 [m
[31m-    protected String[] m_packages;[m
[31m-    protected String[] m_packageNames;[m
     private static Context m_context;[m
     private static UidNameResolver m_instance;[m
 [m
[36m@@ -48,11 +46,10 @@[m [mpublic class UidNameResolver {[m
     public Drawable getIcon(String packageName) {[m
         Drawable icon = null;[m
         // retrieve and store the icon for that package[m
[31m-        String myPackage = packageName;[m
[31m-        if (!myPackage.equals("")) {[m
[32m+[m[32m        if (!packageName.equals("")) {[m
             PackageManager manager = m_context.getPackageManager();[m
             try {[m
[31m-                icon = manager.getApplicationIcon(myPackage);[m
[32m+[m[32m                icon = manager.getApplicationIcon(packageName);[m
             } catch (Exception e) {[m
                 // nop: no icon found[m
                 icon = null;[m
[36m@@ -110,19 +107,19 @@[m [mpublic class UidNameResolver {[m
     // Side effects: sets mName and mUniqueName[m
     // Sets mNamePackage, mName and mUniqueName[m
     public String getNameForUid(int uid) {[m
[31m-        String uidName = "";[m
[32m+[m[32m        String uidName;[m
         String uidNamePackage = "";[m
         boolean uidUniqueName = false;[m
 [m
         PackageManager pm = m_context.getPackageManager();[m
[31m-        m_packages = pm.getPackagesForUid(uid);[m
[32m+[m[32m        String[] m_packages = pm.getPackagesForUid(uid);[m
 [m
         if (m_packages == null) {[m
             uidName = Integer.toString(uid);[m
             return uidName;[m
         }[m
 [m
[31m-        m_packageNames = new String[m_packages.length];[m
[32m+[m[32m        String[] m_packageNames = new String[m_packages.length];[m
         System.arraycopy(m_packages, 0, m_packageNames, 0, m_packages.length);[m
 [m
         // Convert package names to user-facing labels where possible[m
[36m@@ -131,9 +128,7 @@[m [mpublic class UidNameResolver {[m
         }[m
 [m
         if (m_packageNames.length == 1) {[m
[31m-            uidNamePackage = m_packages[0];[m
             uidName = m_packageNames[0];[m
[31m-            uidUniqueName = true;[m
         } else {[m
             uidName = "UID"; // Default name[m
             // Look for an official name for this UID.[m
[36m@@ -148,7 +143,7 @@[m [mpublic class UidNameResolver {[m
                             break;[m
                         }[m
                     }[m
[31m-                } catch (NameNotFoundException e) {[m
[32m+[m[32m                } catch (NameNotFoundException ignored) {[m
                 }[m
             }[m
         }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/hooks/Wakelocks.java b/app/src/main/java/com/ryansteckler/nlpunbounce/hooks/Wakelocks.java[m
[1mindex e2084c7..e656a3e 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/hooks/Wakelocks.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/hooks/Wakelocks.java[m
[36m@@ -18,20 +18,15 @@[m [mimport com.ryansteckler.nlpunbounce.XposedReceiver;[m
 import com.ryansteckler.nlpunbounce.models.InterimEvent;[m
 import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;[m
 [m
[31m-import java.io.BufferedReader;[m
[31m-import java.io.BufferedWriter;[m
[31m-import java.io.DataOutputStream;[m
[31m-import java.io.InputStreamReader;[m
[31m-import java.io.OutputStreamWriter;[m
 import java.util.ArrayList;[m
 import java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
 [m
 import de.robv.android.xposed.IXposedHookLoadPackage;[m
 import de.robv.android.xposed.XC_MethodHook;[m
 import de.robv.android.xposed.XSharedPreferences;[m
 import de.robv.android.xposed.XposedBridge;[m
 import de.robv.android.xposed.XposedHelpers;[m
[31m-import de.robv.android.xposed.callbacks.XC_LoadPackage;[m
 import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;[m
 [m
 import static de.robv.android.xposed.XposedHelpers.callMethod;[m
[36m@@ -42,19 +37,16 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
     private static final String TAG = "Amplify: ";[m
     public static final String VERSION = "3.0.9"; //This needs to be pulled from the manifest or gradle build.[m
     public static final String FILE_VERSION = "3"; //This needs to be pulled from the manifest or gradle build.[m
[31m-    private HashMap<String, Long> mLastWakelockAttempts = null; //The last time each wakelock was allowed.[m
[31m-    private HashMap<String, Long> mLastAlarmAttempts = null; //The last time each alarm was allowed.[m
[32m+[m[32m    private Map<String, Long> mLastWakelockAttempts = null; //The last time each wakelock was allowed.[m
[32m+[m[32m    private Map<String, Long> mLastAlarmAttempts = null; //The last time each alarm was allowed.[m
 [m
     private long mLastUpdateStats = 0;[m
[31m-    private long mUpdateStatsFrequency = 300000; //Save every five minutes[m
[31m-    private long mLastReloadPrefs = 0;[m
[31m-    private long mReloadPrefsFrequency = 60000; //Reload prefs every minute[m
 [m
     private final BroadcastReceiver mBroadcastReceiver = new XposedReceiver();[m
     private boolean mRegisteredRecevier = false;[m
 [m
[31m-    XSharedPreferences m_prefs;[m
[31m-    public static HashMap<IBinder, InterimEvent> mCurrentWakeLocks;[m
[32m+[m[32m    private XSharedPreferences m_prefs;[m
[32m+[m[32m    private static Map<IBinder, InterimEvent> mCurrentWakeLocks;[m
 [m
     @Override[m
     public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {[m
[36m@@ -66,9 +58,9 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
 [m
             defaultLog("Version " + VERSION);[m
 [m
[31m-            mCurrentWakeLocks = new HashMap<IBinder, InterimEvent>();[m
[31m-            mLastWakelockAttempts = new HashMap<String, Long>();[m
[31m-            mLastAlarmAttempts = new HashMap<String, Long>();[m
[32m+[m[32m            mCurrentWakeLocks = new HashMap<>();[m
[32m+[m[32m            mLastWakelockAttempts = new HashMap<>();[m
[32m+[m[32m            mLastAlarmAttempts = new HashMap<>();[m
 [m
             hookAlarms(lpparam);[m
             hookWakeLocks(lpparam);[m
[36m@@ -91,7 +83,7 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
                 Intent intent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);[m
                 try {[m
                     context.sendBroadcast(intent);[m
[31m-                } catch (IllegalStateException ise) {[m
[32m+[m[32m                } catch (IllegalStateException ignored) {[m
                 }[m
             }[m
         }[m
[36m@@ -437,16 +429,16 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
 //            curStats.setTimeStarted(SystemClock.elapsedRealtime());[m
 //            mCurrentServices.put(serviceName, curStats);[m
 //        }[m
[31m-        Context context = null;[m
[32m+[m[32m        Context context;[m
         if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {[m
             try {[m
                 context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");[m
             } catch (NoSuchFieldError nsfe) {[m
[31m-                Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");[m
[32m+[m[32m                Object am = XposedHelpers.getObjectField(param.thisObject, "mAm");[m
                 context = (Context) XposedHelpers.getObjectField(am, "mContext");[m
             }[m
         } else {[m
[31m-            Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");[m
[32m+[m[32m            Object am = XposedHelpers.getObjectField(param.thisObject, "mAm");[m
             context = (Context) XposedHelpers.getObjectField(am, "mContext");[m
         }[m
         if (context != null) {[m
[36m@@ -510,15 +502,16 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
 [m
             long timeSinceLastUpdateStats = now - mLastUpdateStats;[m
 [m
[32m+[m[32m            long mUpdateStatsFrequency = 300000;[m
             if (timeSinceLastUpdateStats > mUpdateStatsFrequency) {[m
                 resetFilesIfNeeded(context);[m
 [m
[31m-                if (!UnbounceStatsCollection.getInstance().saveNow(context)) {[m
[32m+[m[32m                if (UnbounceStatsCollection.getInstance().saveNow(context)) {[m
                     //Request the activity to create the files.[m
                     Intent intent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);[m
                     try {[m
                         context.sendBroadcast(intent);[m
[31m-                    } catch (IllegalStateException ise) {[m
[32m+[m[32m                    } catch (IllegalStateException ignored) {[m
                     }[m
                 }[m
                 mLastUpdateStats = now;[m
[36m@@ -529,7 +522,9 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
     private void handleAlarm(XC_MethodHook.MethodHookParam param, ArrayList<Object> triggers) {[m
         final long now = SystemClock.elapsedRealtime();[m
         Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");[m
[32m+[m[32m        long mLastReloadPrefs = 0;[m
         long sinceReload = now - mLastReloadPrefs;[m
[32m+[m[32m        long mReloadPrefsFrequency = 60000;[m
         if (sinceReload > mReloadPrefsFrequency) {[m
             setupReceiver(param);[m
             m_prefs.reload();[m
[36m@@ -623,16 +618,16 @@[m [mpublic class Wakelocks implements IXposedHookLoadPackage {[m
 [m
     private void recordServiceBlock(XC_MethodHook.MethodHookParam param, String name, int uId) {[m
 [m
[31m-        Context context = null;[m
[32m+[m[32m        Context context;[m
         if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {[m
             try {[m
                 context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");[m
             } catch (NoSuchFieldError nsfe) {[m
[31m-                Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");[m
[32m+[m[32m                Object am = XposedHelpers.getObjectField(param.thisObject, "mAm");[m
                 context = (Context) XposedHelpers.getObjectField(am, "mContext");[m
             }[m
         } else {[m
[31m-            Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");[m
[32m+[m[32m            Object am = XposedHelpers.getObjectField(param.thisObject, "mAm");[m
             context = (Context) XposedHelpers.getObjectField(am, "mContext");[m
         }[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStats.java b/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStats.java[m
[1mindex d96e3de..a0f77eb 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStats.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStats.java[m
[36m@@ -23,7 +23,7 @@[m [mpublic abstract class BaseStats implements Serializable {[m
 [m
     private String mDerivedPackageName;[m
 [m
[31m-    public String getPackage() {[m
[32m+[m[32m    String getPackage() {[m
         return mPackage;[m
     }[m
 [m
[36m@@ -35,7 +35,7 @@[m [mpublic abstract class BaseStats implements Serializable {[m
         return mType;[m
     }[m
 [m
[31m-    protected void setType(String type) {[m
[32m+[m[32m    void setType(String type) {[m
         mType = type;[m
     }[m
 [m
[36m@@ -69,11 +69,11 @@[m [mpublic abstract class BaseStats implements Serializable {[m
         }[m
     }[m
 [m
[31m-    public long incrementAllowedCount() {[m
[32m+[m[32m    public void incrementAllowedCount() {[m
         synchronized (this) {[m
             mAllowedCount++;[m
         }[m
[31m-        return mAllowedCount;[m
[32m+[m
     }[m
 [m
     public long getBlockCount() {[m
[36m@@ -86,14 +86,14 @@[m [mpublic abstract class BaseStats implements Serializable {[m
         }[m
     }[m
 [m
[31m-    public long incrementBlockCount() {[m
[32m+[m[32m    public void incrementBlockCount() {[m
         synchronized (this) {[m
             mBlockCount++;[m
         }[m
[31m-        return mBlockCount;[m
[32m+[m
     }[m
 [m
[31m-    public int getUid() {[m
[32m+[m[32m    int getUid() {[m
         return uid;[m
     }[m
 [m
[36m@@ -103,10 +103,10 @@[m [mpublic abstract class BaseStats implements Serializable {[m
 [m
     public abstract String getDerivedPackageName(Context ctx);[m
 [m
[31m-    protected void setDerivedPackageName(String mDerivedPackageName) {[m
[32m+[m[32m    void setDerivedPackageName(String mDerivedPackageName) {[m
         this.mDerivedPackageName = mDerivedPackageName;[m
     }[m
[31m-    protected String getDerivedPackageName() {[m
[32m+[m[32m    String getDerivedPackageName() {[m
         return mDerivedPackageName;[m
     }[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStatsWrapper.java b/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStatsWrapper.java[m
[1mindex b63d079..51cac98 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStatsWrapper.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/models/BaseStatsWrapper.java[m
[36m@@ -1,12 +1,12 @@[m
 package com.ryansteckler.nlpunbounce.models;[m
 [m
 import java.io.Serializable;[m
[31m-import java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
 [m
 /**[m
  * Created by rsteckler on 10/3/14.[m
  */[m
[31m-public class BaseStatsWrapper implements Serializable{[m
[32m+[m[32mclass BaseStatsWrapper implements Serializable{[m
     public long mRunningSince = -1;[m
[31m-    public HashMap<String, BaseStats> mStats = null;[m
[32m+[m[32m    public Map<String, BaseStats> mStats = null;[m
 }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/models/EventLookup.java b/app/src/main/java/com/ryansteckler/nlpunbounce/models/EventLookup.java[m
[1mindex 38ef5c0..f36fdab 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/models/EventLookup.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/models/EventLookup.java[m
[36m@@ -112,14 +112,19 @@[m [mpublic class EventLookup {[m
         String lower = eventName.toLowerCase();[m
         boolean toReturn = false;[m
 [m
[31m-        if (lower.equals("nlpwakelock")) {[m
[31m-            toReturn = true;[m
[31m-        } else if (lower.equals("nlpcollectorwakelock")) {[m
[31m-            toReturn = true;[m
[31m-        } else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_locator")) {[m
[31m-            toReturn = true;[m
[31m-        } else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {[m
[31m-            toReturn = true;[m
[32m+[m[32m        switch (lower) {[m
[32m+[m[32m            case "nlpwakelock":[m
[32m+[m[32m                toReturn = true;[m
[32m+[m[32m                break;[m
[32m+[m[32m            case "nlpcollectorwakelock":[m
[32m+[m[32m                toReturn = true;[m
[32m+[m[32m                break;[m
[32m+[m[32m            case "com.google.android.gms.nlp.alarm_wakeup_locator":[m
[32m+[m[32m                toReturn = true;[m
[32m+[m[32m                break;[m
[32m+[m[32m            case "com.google.android.gms.nlp.alarm_wakeup_activity_detection":[m
[32m+[m[32m                toReturn = true;[m
[32m+[m[32m                break;[m
         }[m
         return toReturn;[m
     }[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/models/UnbounceStatsCollection.java b/app/src/main/java/com/ryansteckler/nlpunbounce/models/UnbounceStatsCollection.java[m
[1mindex 0ab6c6a..1fe03bd 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/models/UnbounceStatsCollection.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/models/UnbounceStatsCollection.java[m
[36m@@ -31,6 +31,7 @@[m [mimport java.lang.reflect.Type;[m
 import java.util.ArrayList;[m
 import java.util.HashMap;[m
 import java.util.Iterator;[m
[32m+[m[32mimport java.util.Map;[m
 import java.util.concurrent.TimeUnit;[m
 [m
 import de.robv.android.xposed.XSharedPreferences;[m
[36m@@ -49,18 +50,17 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     final private static String STATS_FILENAME_CURRENT = "nlpunbounce.stats";[m
     final private static String STATS_FILENAME_GLOBAL = "nlpunbounce.global.stats";[m
     final private static String STATS_FILENAME_PUSH = "nlpunbounce.push.stats";[m
[31m-    HashMap<String, BaseStats> mCurrentStats = null;[m
[31m-    HashMap<String, Long> mGlobalStats = null;[m
[31m-    HashMap<String, BaseStats> mSincePushStats = null;[m
[32m+[m[32m    private Map<String, BaseStats> mCurrentStats = null;[m
[32m+[m[32m    private Map<String, Long> mGlobalStats = null;[m
[32m+[m[32m    private Map<String, BaseStats> mSincePushStats = null;[m
     private long mRunningSince = 0;[m
     private boolean mGlobalParticipation = true;[m
 [m
     public static final int STAT_CURRENT = 0;[m
     public static final int STAT_GLOBAL = 1;[m
[31m-    public static final int STAT_PUSH = 2;[m
[32m+[m[32m    private static final int STAT_PUSH = 2;[m
 [m
[31m-    long mLastPush = 0;[m
[31m-    long mPushTimeFrequency = 86400000; //Push every 24 hours[m
[32m+[m[32m    private long mLastPush = 0;[m
 [m
     private static UnbounceStatsCollection mInstance = null;[m
 [m
[36m@@ -100,9 +100,9 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     }[m
 [m
     public ArrayList<BaseStats> toAlarmArrayList(Context context) {[m
[31m-        loadStats(context, false);[m
[31m-        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());[m
[31m-        ArrayList<BaseStats> alarms = new ArrayList<BaseStats>();[m
[32m+[m[32m        loadStats(false);[m
[32m+[m[32m        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());[m
[32m+[m[32m        ArrayList<BaseStats> alarms = new ArrayList<>();[m
 [m
         //TODO:  There are WAY better ways to do this, other than copying arrays.[m
         for (BaseStats curStat : bases) {[m
[36m@@ -116,9 +116,9 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     }[m
 [m
     public ArrayList<BaseStats> toWakelockArrayList(Context context) {[m
[31m-        loadStats(context, false); [m
[31m-        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());[m
[31m-        ArrayList<BaseStats> wakelocks = new ArrayList<BaseStats>();[m
[32m+[m[32m        loadStats(false);[m
[32m+[m[32m        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());[m
[32m+[m[32m        ArrayList<BaseStats> wakelocks = new ArrayList<>();[m
         Iterator<BaseStats> iter = bases.iterator();[m
 [m
         //TODO:  There are WAY better ways to do this, other than copying arrays.[m
[36m@@ -134,15 +134,12 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public ArrayList<BaseStats> toServiceArrayList(Context context)[m
     {[m
[31m-        loadStats(context, false); [m
[31m-        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());[m
[31m-        ArrayList<BaseStats> services = new ArrayList<BaseStats>();[m
[31m-        Iterator<BaseStats> iter = bases.iterator();[m
[32m+[m[32m        loadStats(false);[m
[32m+[m[32m        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());[m
[32m+[m[32m        ArrayList<BaseStats> services = new ArrayList<>();[m
 [m
         //TODO:  There are WAY better ways to do this, other than copying arrays.[m
[31m-        while (iter.hasNext())[m
[31m-        {[m
[31m-            BaseStats curStat = iter.next();[m
[32m+[m[32m        for (BaseStats curStat : bases) {[m
             if (curStat instanceof ServiceStats) {[m
                 services.add(curStat);[m
             }[m
[36m@@ -162,16 +159,13 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            HashMap<String, BaseStats> statChoice = null;[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            Map<String, BaseStats> statChoice = null;[m
[32m+[m[32m            loadStats(false);[m
 [m
             //Iterate over all wakelocks and return the duration[m
[31m-            Iterator<BaseStats> iter = mCurrentStats.values().iterator();[m
[31m-            while (iter.hasNext())[m
[31m-            {[m
[31m-                BaseStats curStat = iter.next();[m
[32m+[m[32m            for (BaseStats curStat : mCurrentStats.values()) {[m
                 if (curStat instanceof WakelockStats) {[m
[31m-                    totalDuration += ((WakelockStats)(curStat)).getAllowedDuration();[m
[32m+[m[32m                    totalDuration += ((WakelockStats) (curStat)).getAllowedDuration();[m
                 }[m
             }[m
         }[m
[36m@@ -193,7 +187,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public WakelockStats getWakelockStats(Context context, String wakelockName)[m
     {[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
         if (mCurrentStats.containsKey(wakelockName)) {[m
             BaseStats base = mCurrentStats.get(wakelockName);[m
             if (base instanceof WakelockStats)[m
[36m@@ -214,7 +208,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public ServiceStats getServiceStats(Context context, String serviceName)[m
     {[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
         if (mCurrentStats.containsKey(serviceName)) {[m
             BaseStats base = mCurrentStats.get(serviceName);[m
             if (base instanceof ServiceStats)[m
[36m@@ -234,8 +228,8 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public AlarmStats getAlarmStats(Context context, String alarmName)[m
     {[m
[31m-        HashMap<String, BaseStats> statChoice = null;[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        Map<String, BaseStats> statChoice = null;[m
[32m+[m[32m        loadStats(false);[m
 [m
         if (mCurrentStats.containsKey(alarmName)) {[m
             BaseStats base = mCurrentStats.get(alarmName);[m
[36m@@ -262,12 +256,9 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            loadStats(false);[m
             //Iterate over all stats and return the duration[m
[31m-            Iterator<BaseStats> iter = mCurrentStats.values().iterator();[m
[31m-            while (iter.hasNext())[m
[31m-            {[m
[31m-                BaseStats curStat = iter.next();[m
[32m+[m[32m            for (BaseStats curStat : mCurrentStats.values()) {[m
                 if (curStat instanceof WakelockStats) {[m
                     totalDuration += ((WakelockStats) curStat).getBlockedDuration();[m
                 }[m
[36m@@ -300,7 +291,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            loadStats(false);[m
 [m
             for (BaseStats curStat : mCurrentStats.values()) {[m
 [m
[36m@@ -322,11 +313,8 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[31m-            Iterator<BaseStats> iter = mCurrentStats.values().iterator();[m
[31m-            while (iter.hasNext())[m
[31m-            {[m
[31m-                BaseStats curStat = iter.next();[m
[32m+[m[32m            loadStats(false);[m
[32m+[m[32m            for (BaseStats curStat : mCurrentStats.values()) {[m
                 if (curStat instanceof ServiceStats)[m
                     totalCount += curStat.getAllowedCount();[m
             }[m
[36m@@ -345,7 +333,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            loadStats(false);[m
 [m
             for (BaseStats curStat : mCurrentStats.values()) {[m
 [m
[36m@@ -369,11 +357,8 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[31m-            Iterator<BaseStats> iter = mCurrentStats.values().iterator();[m
[31m-            while (iter.hasNext())[m
[31m-            {[m
[31m-                BaseStats curStat = iter.next();[m
[32m+[m[32m            loadStats(false);[m
[32m+[m[32m            for (BaseStats curStat : mCurrentStats.values()) {[m
                 if (curStat instanceof ServiceStats)[m
                     totalCount += curStat.getBlockCount();[m
             }[m
[36m@@ -393,7 +378,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            loadStats(false);[m
 [m
             for (BaseStats curStat : mCurrentStats.values()) {[m
 [m
[36m@@ -416,7 +401,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
             }[m
         } else if (statType == STAT_CURRENT) {[m
[31m-            loadStats(context, false); [m
[32m+[m[32m            loadStats(false);[m
 [m
             for (BaseStats curStat : mCurrentStats.values()) {[m
 [m
[36m@@ -430,7 +415,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     public void addInterimWakelock(Context context, InterimEvent toAdd)[m
     {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
 [m
         addInterimWakelock(toAdd, mCurrentStats);[m
         if (mGlobalParticipation) {[m
[36m@@ -440,7 +425,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     }[m
 [m
[31m-    private void addInterimWakelock(InterimEvent toAdd, HashMap<String, BaseStats> statChoice) {[m
[32m+[m[32m    private void addInterimWakelock(InterimEvent toAdd, Map<String, BaseStats> statChoice) {[m
         BaseStats combined = statChoice.get(toAdd.getName());[m
         if (combined == null)[m
         {[m
[36m@@ -448,7 +433,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
         }[m
         if (combined instanceof WakelockStats) {[m
             ((WakelockStats)combined).addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());[m
[31m-            ((WakelockStats)combined).setUid(toAdd.getUId());[m
[32m+[m[32m            combined.setUid(toAdd.getUId());[m
             combined.incrementAllowedCount();[m
             statChoice.put(toAdd.getName(), combined);[m
         }[m
[36m@@ -457,7 +442,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     public void incrementWakelockBlock(Context context, String statName, int uId)[m
     {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
 [m
         incrementWakelockBlock(statName, mCurrentStats, uId);[m
         if (mGlobalParticipation) {[m
[36m@@ -466,7 +451,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     }[m
 [m
[31m-    private void incrementWakelockBlock(String statName, HashMap<String, BaseStats> statChoice, int uId) {[m
[32m+[m[32m    private void incrementWakelockBlock(String statName, Map<String, BaseStats> statChoice, int uId) {[m
         BaseStats combined = statChoice.get(statName);[m
         if (combined == null)[m
         {[m
[36m@@ -481,7 +466,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     public void incrementServiceBlock(Context context, String statName,  int uId)[m
     {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
 [m
         incrementServiceBlock(statName, mCurrentStats, uId);[m
         if (mGlobalParticipation) {[m
[36m@@ -490,7 +475,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     }[m
 [m
[31m-    private void incrementServiceBlock(String statName, HashMap<String, BaseStats> statChoice, int uId) {[m
[32m+[m[32m    private void incrementServiceBlock(String statName, Map<String, BaseStats> statChoice, int uId) {[m
         BaseStats combined = statChoice.get(statName);[m
         if (combined == null)[m
         {[m
[36m@@ -504,7 +489,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
    [m
     public void incrementAlarmBlock(Context context, String statName,String packageName) {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
 [m
         incrementAlarmBlock(statName, mCurrentStats,packageName);[m
         if (mGlobalParticipation) {[m
[36m@@ -513,7 +498,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     }[m
 [m
[31m-    private void incrementAlarmBlock(String statName, HashMap<String, BaseStats> statChoice,String packageName) {[m
[32m+[m[32m    private void incrementAlarmBlock(String statName, Map<String, BaseStats> statChoice,String packageName) {[m
         BaseStats combined = statChoice.get(statName);[m
         if (combined == null) {[m
             combined = new AlarmStats(statName,packageName);[m
[36m@@ -525,7 +510,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public void incrementAlarmAllowed(Context context, String statName,String packageName) {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
         incrementAlarmAllowed(statName, mCurrentStats,packageName);[m
         if (mGlobalParticipation) {[m
             incrementAlarmAllowed(statName, mSincePushStats,packageName);[m
[36m@@ -533,7 +518,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     }[m
 [m
[31m-    private void incrementAlarmAllowed(String statName, HashMap<String, BaseStats> statChoice,String packageName) {[m
[32m+[m[32m    private void incrementAlarmAllowed(String statName, Map<String, BaseStats> statChoice,String packageName) {[m
         BaseStats combined = statChoice.get(statName);[m
         if (combined == null) {[m
             combined = new AlarmStats(statName,packageName);[m
[36m@@ -545,7 +530,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
     public void incrementServiceAllowed(Context context, String statName, int uId)[m
     {[m
         //Load from disk and populate our stats[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
 [m
         incrementServiceAllowed(statName, mCurrentStats, uId);[m
         if (mGlobalParticipation) {[m
[36m@@ -553,7 +538,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
         }[m
 [m
     }[m
[31m-    private void incrementServiceAllowed(String statName, HashMap<String, BaseStats> statChoice, int uId) {[m
[32m+[m[32m    private void incrementServiceAllowed(String statName, Map<String, BaseStats> statChoice, int uId) {[m
         BaseStats combined = statChoice.get(statName);[m
         if (combined == null)[m
         {[m
[36m@@ -566,7 +551,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
 [m
     public void resetStats(Context context, String statName)[m
     {[m
[31m-        loadStats(context, false); [m
[32m+[m[32m        loadStats(false);[m
         mCurrentStats.remove(statName);[m
     }[m
 [m
[36m@@ -603,25 +588,27 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
         }[m
     }[m
 [m
[31m-    private boolean saveStatsToFile(String statFilename, HashMap statChoice) {[m
[32m+[m[32m    private boolean saveStatsToFile(String statFilename, Map statChoice) {[m
[32m+[m[32m        String statFilename1 = statFilename;[m
[32m+[m[32m        Map statChoice1 = statChoice;[m
         if (statChoice != null) {[m
             String filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;[m
 [m
[31m-            Object toWrite = statChoice.clone();[m
[32m+[m[32m            Object toWrite = statChoice;[m
             if (statChoice == mSincePushStats || statChoice == mCurrentStats) {[m
                 //Wrap statchoice to contain metadata[m
                 BaseStatsWrapper wrap = new BaseStatsWrapper();[m
                 if (statChoice == mCurrentStats) {[m
                     wrap.mRunningSince = mRunningSince;[m
                 }[m
[31m-                wrap.mStats = (HashMap)statChoice.clone();[m
[32m+[m[32m                wrap.mStats = (Map)statChoice;[m
                 toWrite = wrap;[m
             }[m
 [m
             try {[m
                 File  outFile = new File(filename);[m
                 if (!outFile.exists()) {[m
[31m-                    return false;[m
[32m+[m[32m                    return true;[m
                 }[m
 [m
                 FileOutputStream out = new FileOutputStream(outFile);[m
[36m@@ -629,13 +616,11 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 objOut.writeObject(toWrite);[m
                 objOut.close();[m
                 out.close();[m
[31m-            } catch (FileNotFoundException e) {[m
[31m-                e.printStackTrace();[m
             } catch (IOException e) {[m
                 e.printStackTrace();[m
             }[m
         }[m
[31m-        return true;[m
[32m+[m[32m        return false;[m
     }[m
 [m
     private void clearStatFile(Context context, String filename) {[m
[36m@@ -643,72 +628,99 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
             FileOutputStream out = context.openFileOutput(filename, Activity.MODE_WORLD_WRITEABLE | Activity.MODE_WORLD_READABLE);[m
             out.write(0);[m
             out.close();[m
[31m-        } catch (IOException ioe) {[m
[32m+[m[32m        } catch (IOException ignored) {[m
 [m
         }[m
     }[m
 [m
     public boolean saveNow(Context context) {[m
[32m+[m[32m        Context context1 = context;[m
 [m
[31m-        if (!saveStatsToFile(STATS_FILENAME_CURRENT, mCurrentStats)) {[m
[31m-            return false;[m
[32m+[m[32m        if (saveStatsToFile(STATS_FILENAME_CURRENT, mCurrentStats)) {[m
[32m+[m[32m            return true;[m
         }[m
[31m-        if (!saveStatsToFile(STATS_FILENAME_PUSH, mSincePushStats)) {[m
[31m-            return false;[m
[32m+[m[32m        if (saveStatsToFile(STATS_FILENAME_PUSH, mSincePushStats)) {[m
[32m+[m[32m            return true;[m
         }[m
[31m-        if (!saveStatsToFile(STATS_FILENAME_GLOBAL, mGlobalStats)) {[m
[31m-            return false;[m
[32m+[m[32m        if (saveStatsToFile(STATS_FILENAME_GLOBAL, mGlobalStats)) {[m
[32m+[m[32m            return true;[m
         }[m
 [m
         long now = SystemClock.elapsedRealtime();[m
         long timeSinceLastPush = now - mLastPush;[m
[32m+[m[32m        long mPushTimeFrequency = 86400000;[m
         if (timeSinceLastPush > mPushTimeFrequency) {[m
             //Push now[m
             mLastPush = now;[m
             pushStatsToNetwork(context);[m
         }[m
[31m-        return true;[m
[32m+[m[32m        return false;[m
     }[m
 [m
[31m-    public void loadStats(Context context, boolean forceReload) {[m
[32m+[m[32m    public void loadStats(boolean forceReload) {[m
         if (mCurrentStats == null || forceReload) {[m
             mCurrentStats = loadStatsFromFile(STATS_FILENAME_CURRENT);[m
             if (mCurrentStats == null)[m
[31m-                mCurrentStats = new HashMap<String, BaseStats>();[m
[32m+[m[32m                mCurrentStats = new HashMap<>();[m
         }[m
         if (mSincePushStats == null || forceReload) {[m
             mSincePushStats = loadStatsFromFile(STATS_FILENAME_PUSH);[m
             if (mSincePushStats == null)[m
[31m-                mSincePushStats = new HashMap<String, BaseStats>();[m
[32m+[m[32m                mSincePushStats = new HashMap<>();[m
         }[m
         if (mGlobalStats == null || forceReload) {[m
             mGlobalStats = loadStatsFromFile(STATS_FILENAME_GLOBAL);[m
             if (mGlobalStats == null)[m
[31m-                mGlobalStats = new HashMap<String, Long>();[m
[32m+[m[32m                mGlobalStats = new HashMap<>();[m
         }[m
     }[m
 [m
[31m-    private HashMap loadStatsFromFile(String statFilename) {[m
[31m-        HashMap statChoice = null;[m
[31m-        String filename = "";[m
[32m+[m[32m    private Map loadStatsFromFile(String statFilename) {[m
[32m+[m[32m        Map statChoice = null;[m
[32m+[m[32m        String filename;[m
         try {[m
             filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;[m
 [m
             File inFile = new File(filename);[m
[31m-            if (inFile.exists() && !inFile.canRead()) {[m
[31m-            }[m
[32m+[m[32m            if (inFile.exists())[m
[32m+[m[32m                if (!inFile.canRead()) {[m
[32m+[m[32m                } else {[m
[32m+[m[32m                    if (inFile.exists() && inFile.canRead()) {[m
[32m+[m[32m                        FileInputStream in = new FileInputStream(inFile);[m
[32m+[m[32m                        ObjectInputStream objIn = new ObjectInputStream(in);[m
[32m+[m[32m                        if (statFilename.equals(STATS_FILENAME_GLOBAL)) {[m
[32m+[m[32m                            statChoice = (Map) objIn.readObject();[m
[32m+[m[32m                        } else {[m
[32m+[m[32m                            BaseStatsWrapper wrap = (BaseStatsWrapper) objIn.readObject();[m
[32m+[m[32m                            //Validate all stats are of the correct basetype[m
[32m+[m[32m                            for (BaseStats curStat : wrap.mStats.values()) {[m
[32m+[m[32m                                //Check if this is castable.[m
[32m+[m[32m                            }[m
[32m+[m
[32m+[m[32m                            //Looks good.[m
[32m+[m[32m                            statChoice = wrap.mStats;[m
[32m+[m
[32m+[m[32m                            if (wrap.mRunningSince != -1) {[m
[32m+[m[32m                                mRunningSince = wrap.mRunningSince;[m
[32m+[m[32m                            }[m
[32m+[m
[32m+[m[32m                        }[m
[32m+[m[32m                        objIn.close();[m
[32m+[m[32m                        in.close();[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        statChoice = new HashMap<>();[m
[32m+[m[32m                    }[m
[32m+[m[32m                }[m
             else if (inFile.exists() && inFile.canRead()) {[m
                 FileInputStream in = new FileInputStream(inFile);[m
                 ObjectInputStream objIn = new ObjectInputStream(in);[m
                 if (statFilename.equals(STATS_FILENAME_GLOBAL)) {[m
[31m-                    statChoice = (HashMap)objIn.readObject();[m
[32m+[m[32m                    statChoice = (Map) objIn.readObject();[m
                 } else {[m
                     BaseStatsWrapper wrap = (BaseStatsWrapper) objIn.readObject();[m
                     //Validate all stats are of the correct basetype[m
[31m-                    Iterator<BaseStats> iter = wrap.mStats.values().iterator();[m
[31m-                    while (iter.hasNext()) {[m
[32m+[m[32m                    for (BaseStats curStat : wrap.mStats.values()) {[m
                         //Check if this is castable.[m
[31m-                        BaseStats curStat = iter.next();[m
                     }[m
 [m
                     //Looks good.[m
[36m@@ -721,37 +733,25 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                 }[m
                 objIn.close();[m
                 in.close();[m
[31m-            }[m
[31m-            else {[m
[31m-                statChoice = new HashMap<String, BaseStats>();[m
[32m+[m[32m            } else {[m
[32m+[m[32m                statChoice = new HashMap<>();[m
             }[m
 [m
         } catch (FileNotFoundException e) {[m
             new Exception().printStackTrace();[m
[31m-            statChoice = new HashMap<String, BaseStats>();[m
[31m-        } catch (StreamCorruptedException e) {[m
[31m-            statChoice = new HashMap<String, BaseStats>();[m
[31m-        } catch (IOException e) {[m
[31m-            statChoice = new HashMap<String, BaseStats>();[m
[31m-        } catch (ClassNotFoundException e) {[m
[31m-            statChoice = new HashMap<String, BaseStats>();[m
[31m-        } catch (ClassCastException e) {[m
[31m-            statChoice = new HashMap<String, BaseStats>();[m
[32m+[m[32m            statChoice = new HashMap<>();[m
[32m+[m[32m        } catch (ClassCastException | ClassNotFoundException | IOException e) {[m
[32m+[m[32m            statChoice = new HashMap<>();[m
         }[m
         return statChoice;[m
     }[m
 [m
[31m-    public void loadGlobalStats() {[m
[31m-        //Download the file from the server[m
[31m-        //Save it to the local system[m
[31m-    }[m
[31m-[m
[31m-    public void pushStatsToNetwork(final Context context) {[m
[32m+[m[32m    private void pushStatsToNetwork(final Context context) {[m
         //Send a broadcast to the activity asking for this.[m
         Intent intent = new Intent(ActivityReceiver.PUSH_NETWORK_STATS);[m
         try {[m
             context.sendBroadcast(intent);[m
[31m-        } catch (IllegalStateException ise) {[m
[32m+[m[32m        } catch (IllegalStateException ignored) {[m
         }[m
 [m
     }[m
[36m@@ -762,14 +762,14 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
         mGlobalParticipation = prefs.getBoolean("global_participation", true);[m
         if (mGlobalParticipation) {[m
             //Serialize the collection to JSON[m
[31m-            loadStats(context, true);[m
[32m+[m[32m            loadStats(true);[m
             if (mSincePushStats != null) {[m
                 if (mSincePushStats.size() > 0) {[m
                     final Gson gson = new GsonBuilder().create();[m
                     String json = gson.toJson(mSincePushStats.values().toArray());[m
 [m
                     //Push the JSON to the server[m
[31m-                    NetworkHelper.sendToServer("DeviceStats", json, URL_STATS, new Handler() {[m
[32m+[m[32m                    NetworkHelper.sendToServer(json, URL_STATS, new Handler() {[m
                         @Override[m
                         public void handleMessage(Message msg) {[m
                             if (msg.what == 1) {[m
[36m@@ -777,7 +777,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                                 //Update global stats[m
                                 String globalStats = msg.getData().getString("global_stats");[m
                                 if (globalStats != null) {[m
[31m-                                    Type globalType = new TypeToken<HashMap<String, Long>>() {[m
[32m+[m[32m                                    Type globalType = new TypeToken<Map<String, Long>>() {[m
                                     }.getType();[m
                                     mGlobalStats = gson.fromJson(globalStats, globalType);[m
                                 }[m
[36m@@ -787,7 +787,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                                 intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_PUSH);[m
                                 try {[m
                                     context.sendBroadcast(intent);[m
[31m-                                } catch (IllegalStateException ise) {[m
[32m+[m[32m                                } catch (IllegalStateException ignored) {[m
 [m
                                 }[m
                                 resetStats(context, STAT_PUSH);[m
[36m@@ -812,7 +812,7 @@[m [mpublic class UnbounceStatsCollection implements Serializable {[m
                         //Update global stats[m
                         String globalStats = msg.getData().getString("global_stats");[m
                         if (globalStats != null) {[m
[31m-                            Type globalType = new TypeToken<HashMap<String, Long>>() {[m
[32m+[m[32m                            Type globalType = new TypeToken<Map<String, Long>>() {[m
                             }.getType();[m
                             final Gson gson = new GsonBuilder().create();[m
                             mGlobalStats = gson.fromJson(globalStats, globalType);[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/models/WakelockStats.java b/app/src/main/java/com/ryansteckler/nlpunbounce/models/WakelockStats.java[m
[1mindex fb1da0e..55a3f39 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/models/WakelockStats.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/models/WakelockStats.java[m
[36m@@ -38,11 +38,11 @@[m [mpublic class WakelockStats extends BaseStats implements Serializable {[m
         }[m
     }[m
 [m
[31m-    public long addDurationAllowed(long duration) {[m
[32m+[m[32m    public void addDurationAllowed(long duration) {[m
         synchronized (this) {[m
             mAllowedDuration += duration;[m
         }[m
[31m-        return mAllowedDuration;[m
[32m+[m
     }[m
 [m
     public String getDurationAllowedFormatted() {[m
[36m@@ -80,9 +80,8 @@[m [mpublic class WakelockStats extends BaseStats implements Serializable {[m
             return 0;[m
         long averageTime = mAllowedDuration / getAllowedCount();[m
         //Now multiply that by the number we've blocked.[m
[31m-        long blockedTime = averageTime * getBlockCount();[m
 [m
[31m-        return blockedTime;[m
[32m+[m[32m        return averageTime * getBlockCount();[m
     }[m
 [m
     public String getDurationBlockedFormatted() {[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerActivity.java b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerActivity.java[m
[1mindex 7e05ead..7bc5758 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerActivity.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerActivity.java[m
[36m@@ -34,17 +34,15 @@[m [mpublic class TaskerActivity extends Activity[m
         TaskerWhichFragment.OnFragmentInteractionListener {[m
 [m
     public static final String EXTRA_BUNDLE = "com.twofortyfouram.locale.intent.extra.BUNDLE";[m
[31m-    public static final String EXTRA_BLURB = "com.twofortyfouram.locale.intent.extra.BLURB";[m
[32m+[m[32m    private static final String EXTRA_BLURB = "com.twofortyfouram.locale.intent.extra.BLURB";[m
     public static final String BUNDLE_TYPE = "type";[m
     public static final String BUNDLE_NAME = "name";[m
     public static final String BUNDLE_SECONDS = "seconds";[m
     public static final String BUNDLE_ENABLED = "enabled";[m
[31m-    IabHelper mHelper;[m
[32m+[m[32m    private IabHelper mHelper;[m
 [m
     private boolean mIsPremium = true;[m
 [m
[31m-    Fragment mCurrentFragment = null;[m
[31m-[m
     private static final String TAG = "NlpUnbounceTasker: ";[m
 [m
     @Override[m
[36m@@ -112,7 +110,7 @@[m [mpublic class TaskerActivity extends Activity[m
             @Override[m
             public void onClick(View view) {[m
                 FragmentManager fragmentManager = getFragmentManager();[m
[31m-                Bundle taskerBundle = null;[m
[32m+[m[32m                Bundle taskerBundle;[m
 [m
                 //Set the default tasker values[m
                 taskerBundle = new Bundle();[m
[36m@@ -177,7 +175,7 @@[m [mpublic class TaskerActivity extends Activity[m
     @Override[m
     protected void onActivityResult(int requestCode, int resultCode, Intent data)[m
     {[m
[31m-        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {[m
[32m+[m[32m        if (mHelper.handleActivityResult(requestCode, resultCode, data)) {[m
             super.onActivityResult(requestCode, resultCode, data);[m
         }[m
     }[m
[36m@@ -185,7 +183,7 @@[m [mpublic class TaskerActivity extends Activity[m
     @Override[m
     protected void onStart() {[m
         super.onStart();[m
[31m-        mCurrentFragment = TaskerWhichFragment.newInstance();[m
[32m+[m[32m        Fragment mCurrentFragment = TaskerWhichFragment.newInstance();[m
         FragmentManager fragmentManager = getFragmentManager();[m
         fragmentManager.beginTransaction()[m
                 .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)[m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerReceiver.java b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerReceiver.java[m
[1mindex 9594781..1f2067d 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerReceiver.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerReceiver.java[m
[36m@@ -25,7 +25,7 @@[m [mpublic class TaskerReceiver extends BroadcastReceiver {[m
                     resetIntent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);[m
                     try {[m
                         context.sendBroadcast(resetIntent);[m
[31m-                    } catch (IllegalStateException ise) {[m
[32m+[m[32m                    } catch (IllegalStateException ignored) {[m
 [m
                     }[m
 [m
[1mdiff --git a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerWhichFragment.java b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerWhichFragment.java[m
[1mindex eb702ea..71f8639 100644[m
[1m--- a/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerWhichFragment.java[m
[1m+++ b/app/src/main/java/com/ryansteckler/nlpunbounce/tasker/TaskerWhichFragment.java[m
[36m@@ -24,8 +24,7 @@[m [mpublic class TaskerWhichFragment extends Fragment {[m
      * number.[m
      */[m
     public static TaskerWhichFragment newInstance() {[m
[31m-        TaskerWhichFragment fragment = new TaskerWhichFragment();[m
[31m-        return fragment;[m
[32m+[m[32m        return new TaskerWhichFragment();[m
     }[m
 [m
     public TaskerWhichFragment() {[m
[36m@@ -87,8 +86,7 @@[m [mpublic class TaskerWhichFragment extends Fragment {[m
     public View onCreateView(LayoutInflater inflater, ViewGroup container,[m
                              Bundle savedInstanceState) {[m
 [m
[31m-        View rootView = inflater.inflate(R.layout.fragment_tasker_which, container, false);[m
[31m-        return rootView;[m
[32m+[m[32m        return inflater.inflate(R.layout.fragment_tasker_which, container, false);[m
     }[m
 [m
 [m
[36m@@ -131,8 +129,8 @@[m [mpublic class TaskerWhichFragment extends Fragment {[m
      * >Communicating with Other Fragments</a> for more information.[m
      */[m
     public interface OnFragmentInteractionListener {[m
[31m-        public void onTaskerResetSelected();[m
[31m-        public void onTaskerWhichSetTitle(String title);[m
[32m+[m[32m        void onTaskerResetSelected();[m
[32m+[m[32m        void onTaskerWhichSetTitle(String title);[m
     }[m
 [m
 [m
[1mdiff --git a/app/src/main/res/menu/alarm_detail.xml b/app/src/main/res/menu/alarm_detail.xml[m
[1mindex 4a419bd..75b9aa1 100644[m
[1m--- a/app/src/main/res/menu/alarm_detail.xml[m
[1m+++ b/app/src/main/res/menu/alarm_detail.xml[m
[36m@@ -1,5 +1,4 @@[m
[31m-<menu xmlns:android="http://schemas.android.com/apk/res/android"[m
[31m-    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m<menu xmlns:tools="http://schemas.android.com/tools"[m
     tools:context="com.ryansteckler.nlpunbounce.MaterialSettingsActivity">[m
 [m
     <!--<item android:id="@+id/wakelock_switch"-->[m
[1mdiff --git a/app/src/main/res/menu/global.xml b/app/src/main/res/menu/global.xml[m
[1mindex 378c14b..2edb639 100644[m
[1m--- a/app/src/main/res/menu/global.xml[m
[1m+++ b/app/src/main/res/menu/global.xml[m
[36m@@ -1,4 +1,4 @@[m
[31m-<menu xmlns:android="http://schemas.android.com/apk/res/android">[m
[32m+[m[32m<menu>[m
     <!--<item android:id="@+id/action_reset_stats"-->[m
         <!--android:title="@string/action_reset_stats"-->[m
         <!--android:orderInCategory="100"-->[m
[1mdiff --git a/app/src/main/res/menu/list.xml b/app/src/main/res/menu/list.xml[m
[1mindex e9340f4..f5cda29 100644[m
[1m--- a/app/src/main/res/menu/list.xml[m
[1m+++ b/app/src/main/res/menu/list.xml[m
[36m@@ -1,7 +1,6 @@[m
 <menu xmlns:android="http://schemas.android.com/apk/res/android"[m
     xmlns:tools="http://schemas.android.com/tools"[m
[31m-    tools:context="com.ryansteckler.nlpunbounce.MaterialSettingsActivity"[m
[31m-    xmlns:app="http://schemas.android.com/apk/res-auto" >[m
[32m+[m[32m    tools:context="com.ryansteckler.nlpunbounce.MaterialSettingsActivity">[m
     >[m
 [m
     <item android:id="@+id/action_search"[m
[1mdiff --git a/app/src/main/res/menu/wakelock_detail.xml b/app/src/main/res/menu/wakelock_detail.xml[m
[1mindex 3cd418f..c9d9850 100644[m
[1m--- a/app/src/main/res/menu/wakelock_detail.xml[m
[1m+++ b/app/src/main/res/menu/wakelock_detail.xml[m
[36m@@ -1,5 +1,4 @@[m
[31m-<menu xmlns:android="http://schemas.android.com/apk/res/android"[m
[31m-    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m<menu xmlns:tools="http://schemas.android.com/tools"[m
     tools:context="com.ryansteckler.nlpunbounce.MaterialSettingsActivity">[m
 [m
     <!--<item android:id="@+id/wakelock_switch"-->[m
warning: LF will be replaced by CRLF in app/manifest-merger-release-report.txt.
The file will have its original line endings in your working directory.
