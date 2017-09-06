package com.github.chengheaven.heaven.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.github.chengheaven.heaven.customer.statusbar.StatusBarView;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import static com.github.chengheaven.heaven.customer.statusbar.StatusBarUtil.getStatusBarHeight;

/**
 * @author heaven_Cheng Created on 16/12/6.
 */

public class Utils {

    private static Toast toast;
    private static Boolean mFlag = false;
    private static long mTimeout = -1;
    public static String NUMBERS = "0123456789";
    public static String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static String CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String NUMBER_LOWERCASE = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static String ENG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String NUMBER_ENG = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static boolean debug = true;
    public static String TAG = "MainActivity";

    private Utils() {
        throw new UnsupportedOperationException("this class cannot be invalidate");
    }

    /**
     * 弹出Toast
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showSnackBar(final View rootView, String text) {
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showSnackBar(final View rootView, String text, String action, View.OnClickListener listener) {
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.show();
    }

    public static void showSnackBar(final View rootView, String text, String action, View.OnClickListener listener, Snackbar.Callback callback) {
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.setCallback(callback);
        snackbar.show();
    }

    public static void showSnackBar(final Activity activity, String text) {
        View rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showSnackBar(final Activity activity, String text, String action, View.OnClickListener listener) {
        View rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.show();
    }

    public static void showSnackBar(final Activity activity, String text, String action, View.OnClickListener listener, Snackbar.Callback callback) {
        View rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        final Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.setCallback(callback);
        snackbar.show();
    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (debug) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(tag, msg);
        }
    }


    /**
     * 获取屏幕宽度
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    /**
     * 获取屏幕高度
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 双击退出应用
     */
    public static boolean checkBackAction(Context context) {
        boolean flag = mFlag;
        mFlag = true;
        long time = 3000;
        boolean timeout = (mTimeout == -1 || (System.currentTimeMillis() - mTimeout) > time);
        if (mFlag && (mFlag != flag || timeout)) {
            mTimeout = System.currentTimeMillis();
            //这里写给用户的提示，比如“再点击一次退出应用”
            showToast(context, "再点击一次退出应用");
            return true;
        }
        return !mFlag;
    }

    /**
     * 打开软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 点击空白处，隐藏软键盘
     * 调用: hideSoftInput(context, ev, getCurrentFocus());
     *
     * @Override public boolean dispatchTouchEvent(MotionEvent ev) {
     * hideSoftInput(this, ev, getCurrentFocus());
     * return super.dispatchTouchEvent(ev);
     * }
     */
    public static void hideSoftInput(Context context, MotionEvent ev, View getCurrentFocus) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHide(getCurrentFocus, ev)) {
                doHide(context, getCurrentFocus.getWindowToken());
            }
        }
    }

    private static boolean isShouldHide(View v, MotionEvent event) {
        //这里是用常用的EditText作判断参照的，可根据情况替换成其它View
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            boolean b = event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom;
            return !b;
        }
        return false;
    }

    private static void doHide(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 导航栏透明化
     * 调用： statusBarTransparent(getWindow());
     */
    public static void statusBarTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void statusBarPrimary(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.setNavigationBarColor(Color.argb(255, 33, 150, 243));
            window.setStatusBarColor(Color.argb(255, 33, 150, 243));
        }
    }

    /**
     * 创建一个文件
     */
    public static void createFile(Context context, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                showToast(context, "创建文件失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件转输入流
     */
    public static InputStream file2Is(Context context, File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToast(context, "转换失败");
            return null;
        }
    }

    /**
     * 输入流转文件
     */
    public static File is2File(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        //这里设置缓冲区为8K大小，可根据实际情况变更
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        return file;
    }

    /**
     * bitmap转文件
     *
     * @param quality quality = 100 代表不压缩 quality代表压缩百分比
     */
    public static File bmp2File(Bitmap bmp, File file, Bitmap.CompressFormat format, int quality) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        bmp.compress(format, quality, fos);
        fos.flush();
        fos.close();
        return file;
    }

    /**
     * @param path 传入完整路径字符串, 创建文件
     * @return File
     */
    public static File createFileIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path.substring(0, path.lastIndexOf(File.separator))).mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * @param path 传入路径字符串
     * @return File
     */
    public static File createDirIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * Determine whether the file exist.
     */
    public static boolean isExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 创建新的文件，如果有旧文件，先删除再创建
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        if (isExist(path)) {
            file.delete();
        }
        createFileIfNotExist(path);
        return file;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (isExist(path)) {
            file.delete();
        }
        return true;
    }

    public static boolean deleteFileDir(String path) {
        File file = new File(path);
        if (!isExist(path)) {
            return false;
        }
        if (!file.isDirectory()) {
            file.delete();
            return true;
        }
        String[] fileList = file.list();
        File temp;
        for (String aFileList : fileList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + aFileList);
            } else {
                temp = new File(path + File.separator + aFileList);
            }
            if (temp.isFile()) {

                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFileDir(path + "/" + aFileList);// 先删除文件夹里面的文件
            }
        }
        file.delete();
        return true;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            File myFilePath = new File(folderPath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return flag
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp;
        for (String aTempList : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + aTempList);
            } else {
                temp = new File(path + File.separator + aTempList);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + aTempList);// 先删除文件夹里面的文件
                delFolder(path + "/" + aTempList);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * get file name
     */
    public static String[] getFileName(String path) {
        File root = new File(path);
        File[] filesOrDirs = root.listFiles();
        if (filesOrDirs != null) {
            String[] dir = new String[filesOrDirs.length];
            int num = 0;
            for (int i = 0; i < filesOrDirs.length; i++) {
                if (filesOrDirs[i].isDirectory()) {
                    dir[i] = filesOrDirs[i].getName();

                    num++;
                }
            }
            String[] dir_r = new String[num];
            num = 0;
            for (String aDir : dir) {
                if (aDir != null && !aDir.equals("")) {
                    dir_r[num] = aDir;
                    num++;
                }
            }
            return dir_r;
        } else
            return null;
    }

    /**
     * 获得输出流
     */
    public BufferedWriter getWriter(String path) throws FileNotFoundException,
            UnsupportedEncodingException {
        FileOutputStream fileOut = new FileOutputStream(new File(path));
        OutputStreamWriter writer = new OutputStreamWriter(fileOut, "UTF-8");
        return new BufferedWriter(writer);
    }

    /**
     * 获得输入流
     */
    public InputStream getInputStream(String path) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream in = new FileInputStream(file);
        return new BufferedInputStream(in);
    }

    /**
     * 将InputStream转换成byte数组
     */
    public static byte[] inputStreamToByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[6 * 1024];
        int count;
        while ((count = in.read(data, 0, 4 * 1024)) != -1) {
            outStream.write(data, 0, count);
        }
        return outStream.toByteArray();
    }

    /**
     * 将OutputStream转换成byte数组
     */
    public static byte[] outputStreamToByte(OutputStream out) throws IOException {
        byte[] data = new byte[6 * 1024];
        out.write(data);
        return data;
    }

    /**
     * 将byte数组转换成InputStream
     */
    public static InputStream byteToInputStream(byte[] in) {
        return new ByteArrayInputStream(in);
    }

    /**
     * 将byte数组转换成OutputStream
     */
    public static OutputStream byteToOutputStream(byte[] in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(in);
        return out;
    }

    /**
     * 把数据输入到Path里的文件里
     */
    public static File writeFromInputToSD(String path, InputStream inputStream) {
        File file = null;
        OutputStream output = null;
        try {
            file = createFileIfNotExist(path);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int temp;
            while ((temp = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将byte数组写入路径为path的文件中
     */
    public static File writeFromByteToSD(String path, byte[] b) {
        File file = null;
        OutputStream output = null;
        try {
            file = createFileIfNotExist(path);
            output = new FileOutputStream(file);
            output.write(b);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 方法：把一段文本保存到给定的路径中.
     */
    public static void saveTxtFile(String filePath, String text) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            createFileIfNotExist(filePath);
            String txt = readTextLine(filePath);
            text = text + txt;
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            i("ext", ext);
        }

    }

    public static void clearTxtFile(String filePath) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            String text = "";
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            i("ext", ext);
        }
    }

    // 读取一个给定的文本文件内容,并把内容以一个字符串的形式返回
    public static String readTextLine(String textFile) {
        try {
            // 首先构建一个文件输入流,该流用于从文本文件中读取数据
            FileInputStream input = new FileInputStream(textFile);
            // 为了能够从流中读取文本数据,我们首先要构建一个特定的Reader的实例,
            // 因为我们是从一个输入流中读取数据,所以这里适合使用InputStreamReader.
            InputStreamReader streamReader = new InputStreamReader(input, "gb2312");
            // 为了能够实现一次读取一行文本的功能,我们使用了 LineNumberReader类,
            // 要构建LineNumberReader的实例,必须要传一个Reader实例做参数,
            // 我们传入前面已经构建好的Reder.
            LineNumberReader reader = new LineNumberReader(streamReader);
            // 字符串line用来保存每次读取到的一行文本.
            String line;
            // 这里我们使用一个StringBuilder来存储读取到的每一行文本,
            // 之所以不用String,是因为它每次修改都会产生一个新的实例,
            // 所以浪费空间,效率低.
            StringBuilder allLine = new StringBuilder();
            // 每次读取到一行,直到读取完成
            while ((line = reader.readLine()) != null) {
                allLine.append(line);
                // 这里每一行后面,加上一个换行符,LINUX中换行是”\n”,
                // windows中换行是”\r\n”.
                allLine.append("\n");
            }
            // 把Reader和Stream关闭
            streamReader.close();
            reader.close();
            input.close();
            // 把读取的字符串返回
            return allLine.toString();
        } catch (Exception e) {
            // Toast.makeText(this, e.getLocalizedMessage(),
            // Toast.LENGTH_LONG).show();
            return "";
        }
    }


    /**
     * 格式化字符串
     */
    public static String formatString(String str, String date) {
        return str.replace("#{date}", date);
    }

    public static String formatString(String str, int date) {
        return str.replace("#{date}", String.valueOf(date));
    }

    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    public static List<Integer> inChat(String str) {
        List<Integer> list = new ArrayList<>();
        List<String> sl = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            sl.add(str.substring(i, i + 1));
        }
        for (int i = 0; i < sl.size(); i++) {
            String s = getPinYin(sl.get(i));
            s = s.substring(0, 1);
            list.add(LOWERCASE.indexOf(s) + 1);
        }
        return list;
    }

    public static List<Integer> inEnp(String str) {
        List<Integer> list = new ArrayList<>();
        List<String> sl = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            sl.add(str.substring(i, i + 1));
        }
        for (int i = 0; i < sl.size(); i++) {
            String s = getPinYin(sl.get(i));
            s = s.substring(0, 1);
            list.add(NUMBER_LOWERCASE.indexOf(s) + 1);
        }
        return list;
    }

    public static String encryptPassword(String key, String pass, boolean flag) {
        String password;
        List<Integer> il = inChat(key);
        List<Integer> pl = inEnp(pass);
        List<Integer> pal = new ArrayList<>();
        password = LOWERCASE.substring(il.get(0) - 1, il.get(0));
        while (il.size() < pl.size()) {
            il.addAll(il);
        }
        for (int i = 0; i < pl.size(); i++) {
            pal.add(pl.get(i) + il.get(i));
        }
        for (int i = 0; i < pal.size(); i++) {
            password = password + NUMBER_ENG.substring(pal.get(i) - 1, pal.get(i));
        }
        if (flag) {
            int a = password.length() / 2;
            String b = password.substring(0, a);
            String c = password.substring(a, password.length() - 1);
            return b + "_" + c;
        } else {
            return password;
        }
    }

    /**
     * determine whether it is a number.
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * determine whether it is a Chinese.
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        return pattern.matcher(str).matches();
    }

    /**
     * determine whether it is a String.
     */
    public static boolean isString(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        return pattern.matcher(str).matches();
    }


    /**
     * 获得软件版本，对应manifest中version，失败返回null
     *
     * @param context Application Context
     */
    public static String getApkVersionName(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * install APK
     *
     * @param uri The argument is of type String uri
     */
    public static void installAPK(Context context, String uri) {
        if (TextUtils.isEmpty(uri) || context == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(uri)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * install APK
     *
     * @param filename The argument is of type File filename
     */
    public static void install(Context context, File filename) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(filename), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Determine whether to install the Weixin
     */
    public static boolean isWeixinInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> info = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (info != null) {
            for (PackageInfo packageInfo : info) {
                if (packageInfo.packageName.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether to install the TencentQQ
     */
    public static boolean isQQInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info != null) {
            for (PackageInfo packageInfo : info) {
                if (packageInfo.packageName.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether to install the Weibo
     */
    public static boolean isWeiboInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info != null) {
            for (PackageInfo packageInfo : info) {
                if (packageInfo.packageName.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether to install the AutoMap 高德地图
     */
    public static boolean isAutoMapInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info != null) {
            for (PackageInfo packageInfo : info) {
                if (packageInfo.packageName.equals("com.autonavi.minimap")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether to install the Baidu Map
     */
    public static boolean isBaiduMapInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info != null) {
            for (PackageInfo packageInfo : info) {
                if (packageInfo.packageName.equals("com.baidu.minimap")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether the internet is connected
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine whether the wifi is connected
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Open the Network Settings screen
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * Take the file under the Assets folder
     * 取Asset文件夹下文件
     */
    public static InputStream getAssetsInputStream(Context paramContext, String paramString) throws IOException {
        return paramContext.getResources().getAssets().open(paramString);
    }

    /**
     * To save the way to read the image memory
     * 以省内存的方式读取图片
     */
    public static Bitmap getBitmap(InputStream is) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 4;
        //获取资源图片
        //InputStream is = mContext.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * Gets the current time
     * 获取当前时间
     */
    public static String getTimeByFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取当前日期
     */
    public static String getData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 获取当前时间是否大于12：30
     */
    public static boolean isRightTime() {
        // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int hour = t.hour; // 0-23
        int minute = t.minute;
        return hour > 12 || (hour == 12 && minute >= 30);
    }

    /**
     * 得到上一天的时间
     */
    public static ArrayList<String> getLastTime(String year, String month, String day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));//月份是从0开始的，所以11表示12月

        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int inDay = ca.get(Calendar.DATE);
        ca.set(Calendar.DATE, inDay - 1);

        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(ca.get(Calendar.YEAR)));
        list.add(String.valueOf(ca.get(Calendar.MONTH) + 1));
        list.add(String.valueOf(ca.get(Calendar.DATE)));
        return list;
    }


    public static String getNameByFlag(String source, String flag) {
        String s = source.toLowerCase().replace(flag, "");
        return s.trim();
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 转换dip为px
     */
    public static int convertDipOrPx(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 转换px为dip
     */
    public static int convertPxOrDip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int pxToSp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int spToPx(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 把字加长，使其可以滚动，在音乐界面
     */
    public static String dealString(String st, int size) {
        if (st.length() >= size)
            return "  " + st + "  ";
        else {
            int t = (size - st.length()) / 2;
            for (int i = 0; i < t; i++) {
                st = " " + st + "  ";
            }
            return st;
        }
    }

    public static boolean getBoolean(Context context, String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, b);
    }

    public static void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static String getString(Context context, String key, String s) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, s);
    }

    public static void SetString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();

    }

    public static int getInt(Context context, String key, int i) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, i);
    }

    public static void SetInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();

    }

    public static long getLong(Context context, String key, long l) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getLong(key, l);
    }

    public static void SetLong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();

    }


    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBER_ENG, length);
    }

    /**
     * get a fixed-length random string, its a mixture of numbers
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase and lowercase letters
     */
    public static String getRandomLetters(int length) {
        return getRandom(ENG, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase letters
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL, length);
    }

    /**
     * get a fixed-length random string, its a mixture of lowercase letters
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWERCASE, length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in source
     */
    public static String getRandom(String source, int length) {
        return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in sourceChar
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * get random int between 0 and max
     *
     * @param max
     * @return <ul>
     *         <li>if max <= 0, return 0</li>
     *         <li>else return random int between 0 and max</li>
     *         </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * get random int between min and max
     *
     * @param min
     * @param max
     * @return <ul>
     *         <li>if min > max, return 0</li>
     *         <li>if min == max, return min</li>
     *         <li>else return random int between min and max</li>
     *         </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }


    /**
     * 获取手机及SIM卡相关信息
     */
    public static Map<String, String> getPhoneInfo(Context context) {
        Map<String, String> map = new HashMap<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        String subscriberId = tm.getSubscriberId();
        String phoneMode = Build.MODEL;
        String phoneSDk = Build.VERSION.RELEASE;
        map.put("deviceId", deviceId);
        map.put("subscriberId", subscriberId);
        map.put("phoneMode", phoneMode + "##" + phoneSDk);
        map.put("model", phoneMode);
        map.put("sdk", phoneSDk);
        return map;
    }
}