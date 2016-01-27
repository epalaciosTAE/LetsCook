package com.tae.letscook.Utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.client.Response;

/**
 * Helper class that contains methods to store and get Video 
 * from Android Local Storage.
 */
public class PhotoStorageUtils {
    /** 
     * Create a file Uri for saving a recorded video
     */ 
    @SuppressLint("SimpleDateFormat")
    public static Uri getPictureUri(Context context) {

        // Check to see if external SDCard is mounted or not.
        if (isExternalStorageWritable()) {
            // Create a path where we will place our recorded video in
            // the user's public DCIM directory. Note that you should
            // be careful about what you place here, since the user
            // often manages these files.
            final File photoStorageDir =
                Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM);

            // Create the storage directory if it does not exist
            if (!photoStorageDir.exists()) {
                if (!photoStorageDir.mkdirs()) {
                    return null;
                }
            }

            // Create a TimeStamp for the video file.
            final String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            // Create a video file name from the TimeStamp.
            final File photoFile =
                new File(photoStorageDir.getPath() + File.separator + "IMG_"
                         + timeStamp + ".jpg");

            // Always notify the MediaScanners after storing
            // the Video, so that it is immediately available to
            // the user.
            notifyMediaScanners(context, photoFile);
            
            //Return Uri from Video file.
            return Uri.fromFile(photoFile);

        } else 
            //Return null if no SDCard is mounted.
            return null;
    } 
    
    /**
     * Stores the Video in External Downloads directory in Android.
     */
    public static File storePictureInExternalDirectory(Context context,
                                                       Response response,
                                                       String photoName) {
        // Try to get the File from the Directory where the Video
        // is to be stored.
        final File file =
            getPhotoStorageDir(photoName);
        if (file != null) {
            try {
                // Get the InputStream from the Response.
                final InputStream inputStream =
                    response.getBody().in();
                
                // Get the OutputStream to the file
                // where Video data is to be written.
                final OutputStream outputStream =
                    new FileOutputStream(file);
                
                // Write the Video data to the File.
                IOUtils.copy(inputStream,
                        outputStream);
                
                // Close the streams to free the Resources used by the
                // stream.
                outputStream.close();
                inputStream.close();

                // Always notify the MediaScanners after Downloading
                // the Video, so that it is immediately available to
                // the user.
                notifyMediaScanners(context,
                                    file);
                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    /**
     * Notifies the MediaScanners after Downloading the Video, so it
     * is immediately available to the user.
     */
    public static void notifyMediaScanners(Context context,
                                            File videoFile) {
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile
            (context,
                    new String[]{videoFile.toString()},
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path,
                                                    Uri uri) {
                        }
                    });
    }

    /**
     * Checks if external storage is available for read and write.
     * 
     * @return True-If the external storage is writable.
     */
    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals
            (Environment.getExternalStorageState());
    }

    /**
     * Get the External Downloads Directory to 
     * store the Videos.
     * 
     * @param photoName
     */
    private static File getPhotoStorageDir(String photoName) {
        //Check to see if external SDCard is mounted or not.
        if (isExternalStorageWritable()) {
            // Create a path where we will place our video in the
            // user's public Downloads directory. Note that you should be
            // careful about what you place here, since the user often 
            // manages these files.
            final File path =
                Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS);
            final File file = new File(path,
                                 photoName + ".jpg");
            // Make sure the Downloads directory exists.
            path.mkdirs();
            return file;
        } else {
            return null;
        }
    }

    /**
     * Make VideoStorageUtils a utility class by preventing instantiation.
     */
    private PhotoStorageUtils() {
        throw new AssertionError();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Bitmap decodeImageFromPath(Uri pathToImageFile) {
        try (InputStream inputStream =
                     new FileInputStream(pathToImageFile.toString())) {
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
