package material_design.soussi.com.events_tunisie.map_menu_button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import java.io.*;
/**
 * Created by Soussi on 10/05/2015.
 */
public class ABImageProcess {
    public static final String TAG = ABImageProcess.class.getSimpleName();


    /***************************************BLUR START****************************************/
    /**
     *
     *
     * @param bm
     * @param view
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view) {
        return fastBlur(bm, view, true, 8);
    }

    /**
     * viewbackground?
     *
     * @param context
     * @param bm
     * @param view
     */
    public static void fastBlurSetBg(Context context, Bitmap bm, View view) {
        fastBlurSetBg(context, bm, view, true, 8);
    }

    /**
     *
     *
     * @param bm
     * @param view
     * @param downScale
     * @param scaleF
     * @return
     */
    public static Bitmap fastBlur(Bitmap bm, View view, boolean downScale, float scaleF) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (downScale) {
            scaleFactor = scaleF;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        //
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Logger.d(TAG, "fast blur takes: " + (System.currentTimeMillis() - startMs + "ms"));
        return overlay;

    }

    public static Bitmap fastBlur(Bitmap bm, float scaleFactor, float radius, int width, int height) {
        long startMs = System.currentTimeMillis();
        scaleFactor = scaleFactor > 0 ? scaleFactor : 1;
        radius = radius > 0 ? radius : 20;

        Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        //
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Logger.d(TAG, "fast blur takes: " + (System.currentTimeMillis() - startMs + "ms"));
        return overlay;
    }

    public static Bitmap fastBlur(Bitmap bm, float scaleFactor, float radius) {
        return fastBlur(bm, scaleFactor, radius, bm.getWidth(), bm.getHeight());
    }

    /**
     * background
     *
     * @param context
     * @param bm
     * @param view
     * @param downScale
     * @param scaleFactor
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static void fastBlurSetBg(Context context, Bitmap bm, View view, boolean downScale, float scaleFactor) {
        Bitmap overlay = fastBlur(bm, view, downScale, scaleFactor);
        if (null == overlay) {
            Logger.e(TAG, "fast blur error(result[overlay] is null)");
            return;
        }
        ABViewUtil.setBackgroundDrawable(view, new BitmapDrawable(context.getResources(), overlay));
    }
    /***************************************BLUR END****************************************/


    /*************************************** BEGIN****************************************/
    /**
     *
     *
     * @param filePath
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String bitmapToString(String filePath, int w, int h) {

        Bitmap bm = getSmallBitmap(filePath, w, h);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        String str = Base64.encodeToString(b, Base64.DEFAULT);
        ABIOUtil.recycleBitmap(bm);
        return str;

    }

    /**
     *
     *
     * @param o
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options o,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image

//        if (height > reqHeight || width > reqWidth) {
//
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
////            final int heightRatio = height / reqHeight;
////            final int widthRatio = width / reqWidth;
//
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }

//        while((h = h / 2) > reqHeight && (w = w / 2) > reqWidth){
//            inSampleSize *= 2;
//        }

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
//                        if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize){
//                            break;
//                        }
//                        width_tmp /= 2;
//                        height_tmp /= 2;
//                        scale *= 2;
            if (width_tmp <= reqWidth || height_tmp <= reqHeight) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;

        }

        return scale;
    }


    /**
     *
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, w, h);
//        options.inSampleSize = computeSampleSize(options, -1, w * h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

//        Bitmap b = BitmapFactory.decodeFile(filePath, options);

//        OperatePic.zoomBitmap(b, w, h);
        Bitmap resultBm = null;
        try {
            Bitmap proBm = decodeFile(filePath, options);
            if (null == proBm) {
                return null;
            }
            resultBm = formatCameraPictureOriginal(filePath, proBm);
        } catch (Throwable ex) {
            Logger.e(TAG, ex);
        }
        return resultBm;
    }

    public static Bitmap getSmallBitmapZoom(String filePath, int w, int h) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, w, h);
////        options.inSampleSize = computeSampleSize(options, -1, w * h);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//
//        Bitmap b = BitmapFactory.decodeFile(filePath, options);
        Bitmap smallBm = getSmallBitmap(filePath, w, h);
        Bitmap zoomBm = zoomBitmap(smallBm, w, h);
        ABIOUtil.recycleBitmap(smallBm);
        return zoomBm;
    }

    public static Bitmap getSmallBitmapQuality(String filePath, int w, int h, int quality) {
        Bitmap bm = getSmallBitmap(filePath, w, h);

        if (null == bm || quality >= 100 || quality <= 0) {
            return bm;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);//

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
        ABIOUtil.recycleBitmap(bm);
        return bitmap;
    }


    /**
     *
     *
     * @param image
     * @param size
     * @return
     */
    public static ByteArrayInputStream compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) {    //
            baos.reset();//baos
            options -= 10;//10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//ByteArrayInputStream
        return isBm;
    }

    public static void compressImage2SD(File file, String srcPath, float ww, float hh, int size) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //options.inJustDecodeBounds
        newOpts.inJustDecodeBounds = true;
        decodeFile(srcPath, newOpts);//

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //800*480
//        float hh = 800f;//
//        float ww = 480f;//
        //
        int be = 1;//be=1
        if (w > h && w > ww) {//
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//
        //options.inJustDecodeBounds
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = decodeFile(srcPath, newOpts);
        if (null == bitmap) {
            return;
        }

        bitmap = formatCameraPictureOriginal(srcPath, bitmap); //

        ByteArrayInputStream isBm = compressImage(bitmap, size);//
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte[] buffer = new byte[isBm.available()];
            isBm.read(buffer);
            os.write(buffer, 0, buffer.length);
            os.flush();
        } catch (Exception e) {
            Logger.e(TAG, e);
        } finally {
            ABIOUtil.closeIO(isBm, os);
            ABIOUtil.recycleBitmap(bitmap);
        }

    }

    /**
     * Compress image
     *
     * @param filePath
     * @param ww
     * @param hh
     * @param size
     * @return
     */
    public static InputStream compressImage(String filePath, float ww, float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //options.inJustDecodeBoundstrue?
        newOpts.inJustDecodeBounds = true;
        decodeFile(filePath, newOpts);//

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //
        int be = 1;//be=1
        if (w > h && w > ww) {//
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//
        //options.inJustDecodeBounds false?
        newOpts.inJustDecodeBounds = false;

        Bitmap bitmap = decodeFile(filePath, newOpts);
        if (null == bitmap) {
            return null;
        }
        InputStream resultIs = compressImage(bitmap, size);
        ABIOUtil.recycleBitmap(bitmap);
        return resultIs;
    }


    public static Bitmap getCompressedImage(String srcPath, float ww, float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //
        newOpts.inJustDecodeBounds = true;
        decodeFile(srcPath, newOpts);//

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        int be = 1;//
        if (w > h && w > ww) {//
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//

        newOpts.inJustDecodeBounds = false;

        Bitmap bitmap = null;
        ByteArrayInputStream commpressedBm = null;
        Bitmap resultBm = null;
        try {
            bitmap = decodeFile(srcPath, newOpts);
            if (null == bitmap) {
                return null;
            }
            commpressedBm = compressImage(bitmap, size);
            resultBm = BitmapFactory.decodeStream(
                    commpressedBm, //
                    null, null);//
        } finally {
            ABIOUtil.recycleBitmap(bitmap);
            ABIOUtil.closeIO(commpressedBm);
        }
        return resultBm;
    }

    /*******************************************************************************/


    /*************************************** BEGIN****************************************/
    /**
     *
     *
     * @param bitmap
     * @param roundPx
     * @return Bitmap
     * @author com.tiantian
     */
    public static Bitmap roundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     *
     *
     * @param originalBitmap
     * @return
     */
    public static Bitmap shadowBitmap(Bitmap originalBitmap) {
//		Bitmap originalBitmap = drawableToBitmap(drawable);
        BlurMaskFilter blurFilter = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);

		/*
         * int[] offsetXY = new int[2]; Bitmap shadowImage =
		 * originalBitmap.extractAlpha(shadowPaint, offsetXY);
		 *
		 * Bitmap bmp=shadowImage.copy(Config.ARGB_8888, true); Canvas c = new
		 * Canvas(bmp); c.drawBitmap(originalBitmap, -offsetXY[0], -offsetXY[1],
		 * null); return shadowImage;
		 */
        final int w = originalBitmap.getWidth();
        final int h = originalBitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w + 20, h + 20, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawBitmap(originalBitmap, 10, 10, shadowPaint);
        c.drawBitmap(originalBitmap, 10, 10, null);
        return bmp;
    }

    /**
     *
     *
     * @param bitmap
     * @return
     * @author com.tiantian
     */
    public static Bitmap reflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
    /*************************************** END****************************************/

    /***********************************BEGIN*************************************/

    /**
     *
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     * @author com.tiantian
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     *
     *
     * @param bitmap
     * @param widthScale
     * @param heightScale
     * @return
     * @author com.tiantian
     */
    public static Bitmap zoomBitmapScale(Bitmap bitmap, float widthScale, float heightScale) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = widthScale;
        float scaleHeight = heightScale;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Drawable??
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawablebitmap
        Bitmap oldbmp = drawable2Bitmap(drawable);
        // Matrix
        Matrix matrix = new Matrix();
        //
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        //
        matrix.postScale(sx, sy);
        //
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        ABIOUtil.recycleBitmap(oldbmp);
        return new BitmapDrawable(newbmp);
    }

    /**
     * Drawable
     *
     * @param bitmap
     * @return
     * @author com.tiantian
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * DrawableBitmap
     *
     * @param drawable
     * @return
     * @author com.tiantian
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        // drawable
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        //  drawable
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        //  bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //  bitmap
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // drawable
        drawable.draw(canvas);
        return bitmap;
    }

    /***********************************BEGIN*************************************/


    /*********************************** BEGIN*************************************/

    /**
     *
     *
     * @param path
     * @return degree
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static int readPictureDegreeFromExif(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Logger.e(TAG, e);
        }
        return degree;
    }

    /*
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {
        //
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ABIOUtil.recycleBitmap(bitmap);
        return resizedBitmap;
    }

    /**
     *
     *
     * @param path
     * @return bitmap
     */
    public static Bitmap formatCameraPictureOriginal(String path, Bitmap bitmap) {
        /**
         *
         */
        int degree = ABImageProcess.readPictureDegreeFromExif(path);
        if (0 == degree) {
            return bitmap;
        }
        /**
         *
         */
        Bitmap newbitmap = rotaingImage(degree, bitmap);
        ABIOUtil.recycleBitmap(bitmap);
        return newbitmap;
    }

    /**
     *
     *
     * @param path
     * @return
     */
    public static Bitmap formatCameraPicture(String path) {
        /**
         *
         */
        int degree = ABImageProcess.readPictureDegreeFromExif(path);
        BitmapFactory.Options opts = new BitmapFactory.Options();//
        opts.inSampleSize = 2;
        Bitmap cbitmap = decodeFile(path, opts);
        if (null == cbitmap) {
            return null;
        }
        /**
         *
         */
        Bitmap newbitmap = rotaingImage(degree, cbitmap);
        ABIOUtil.recycleBitmap(cbitmap);
        return newbitmap;
    }

    /*********************************** END*************************************/


    /***********************************BEGIN*************************************/
    /**
     * ResourceDrawable?bound
     *
     * @param context
     * @param drawableResId
     * @param bound
     * @return
     */
    public static Drawable getResourceDrawableBounded(Context context, int drawableResId, int bound) {
        Drawable drawable = null;
        try {
            drawable = context.getResources().getDrawable(drawableResId);
            drawable.setBounds(0, 0, bound, bound);
        } catch (Exception ex) {
            Logger.e(TAG, ex);
        }
        return drawable;
    }

    /**
     * ******************************** END************************************
     */

    private static Bitmap decodeFile(String pathName, BitmapFactory.Options options) {
        Bitmap resultBm = null;
        try {
            resultBm = BitmapFactory.decodeFile(pathName, options);
        } catch (Throwable throwable) {
            Logger.e(TAG, throwable);
        }
        return resultBm;
    }

}
