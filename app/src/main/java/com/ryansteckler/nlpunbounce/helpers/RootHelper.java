package com.ryansteckler.nlpunbounce.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RootHelper {
    /** @author Kevin Kowalewski */

    private RootHelper(){}
        public static boolean isDeviceRooted() {
            return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();
        }

        public static boolean checkRootMethod1() {
            String buildTags = android.os.Build.TAGS;
            return buildTags != null && buildTags.contains("test-keys");
        }

    public static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {return false;}
    }

    public static boolean checkRootMethod4() {
        try {
            File file = new File("/system/xbin/su");
            return file.exists();
        } catch (Exception e) {return false;}
    }

    public static boolean checkRootMethod3() {
            return new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary)!=null;
        }
    }

    /** @author Kevin Kowalewski */
    class ExecShell {

        private static String LOG_TAG = ExecShell.class.getName();

        public static enum SHELL_CMD {
            check_su_binary(new String[] { "/system/xbin/which", "su" });

            String[] command;

            SHELL_CMD(String[] command) {
                this.command = command;
            }
        }

        public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
            String line = null;
            ArrayList<String> fullResponse = new ArrayList<String>();
            Process localProcess = null;
            try {
                localProcess = Runtime.getRuntime().exec(shellCmd.command);
            } catch (Exception e) {
                return null;
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    localProcess.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    localProcess.getInputStream()));
            try {
                while ((line = in.readLine()) != null) {
                    fullResponse.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fullResponse;
        }

}
