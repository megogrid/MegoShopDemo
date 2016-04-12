package com.megogrid.megoshop;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.megogrid.megoshop.db.ImageDetail;
import com.megogrid.megoshop.db.SavedDbHelper;
import com.megogrid.megoshop.dialog.BoxIDConstant;
import com.megogrid.megoshop.share.GalleryAlbumActivity;
import com.megogrid.megoshop.share.Help;

import com.megogrid.merchandising.callback.FeatureCallback;

import com.megogrid.merchandising.exception.MegogridException;
import com.megogrid.merchandising.utility.IABManager;
import com.megogrid.meuserdemo1.library.Constants;
import com.megogrid.meuserdemo1.library.Toaster;
import com.megogrid.meuserdemo1.library.UriToUrl;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends Activity {

    private Animation animation;
    public RelativeLayout top_holder;
    public RelativeLayout bottom_holder;

    private RelativeLayout step_number;
    private TextView account, Configuration, Help, deleteFeature;
    ImageView logout_line;
    private Uri imageUri;
    private boolean click_status = true;
    com.megogrid.megoshop.CustomDrawerLayout drawer_layout;
    ImageView imageView_back;
    RelativeLayout whatYouWantInLeftDrawer;
    public static final String YOUR_BOX_ID = "KPDFC20UD";
    public static final String YOUR_BOX_TITLE = "Diabetes tracker";


    private ImageView user_image;
    private static String MEVO_GOOGLE_ID = "516811753633-loscod8m7e3gvqdei4queom24rrcrivp.apps.googleusercontent.com";

    private static String DT_GOOGLE_ID = " 720282980579-smn98g7o513htrhf6nnapth4t6bfgklu.apps.googleusercontent.com";

    String LICENCE_kEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgjMjRIZlr+UAC55Z8lq4JqQna85rpp0ehq/urSLGGraLTa5XaKXIpbak3k1x3TNdA6IcAJP/zApn/H29o94UjxgNOCt5bFSD5PrQh71VnBmsUbNSoM3d62Q3H7ez7Tw7rul87icNvQh6sexkq1cgyIJtTDYYSAOfI+l5s3JZwMAIbeSfp0vXN6ecZC4Oy82ecaAxDjw6prsefT0VqO6AKqlVsIM/QYchWxk1NOmQemynjUQxATIqEqXgzik5BPhRAJffFe3NX8HZ4w42gV3uotg0vmhJM45jQiRCipftjBud4cbmO0Z/dTa99hLozYbBxstaoMP3hMl0pgzXyrtsTQIDAQAB";
//    MeUser meUser;
    SavedDbHelper helper;
    ArrayList<ImageDetail> detail;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    IABManager iabManager;

    public MainActivity() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getPackageResourcePath() {
        return super.getPackageResourcePath();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("AppBackup", MODE_PRIVATE);
        editor = preferences.edit();
        helper = new SavedDbHelper(MainActivity.this);
        helper.getReadableDatabase();
        detail = helper.showAllDetail();
        if (getIntent().getBooleanExtra("Exit me", false)) {
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }
        drawer_layout = (com.megogrid.megoshop.CustomDrawerLayout) findViewById(R.id.drawer_layout1);
        top_holder = (RelativeLayout) findViewById(R.id.camera_button);
        bottom_holder = (RelativeLayout) findViewById(R.id.image_button);

        iabManager = new IABManager(MainActivity.this);
        iabManager.setGoogleLicenceKey(LICENCE_kEY);


        imageView_back = (ImageView) findViewById(R.id.imgViewForMenu1);
        whatYouWantInLeftDrawer = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        account = (TextView) findViewById(R.id.account);
//        Configuration = (TextView) findViewById(R.id.Configuration);
        Help = (TextView) findViewById(R.id.help);
        deleteFeature = (TextView)findViewById(R.id.deleteFeature);


        imageView_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout.isDrawerOpen(whatYouWantInLeftDrawer)) {
                    drawer_layout.closeDrawer(whatYouWantInLeftDrawer);
                } else {
                    drawer_layout.openDrawer(whatYouWantInLeftDrawer);
                }
            }
        });

        account.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawer(whatYouWantInLeftDrawer);
                Intent intent = new Intent(MainActivity.this, Help.class);
                startActivity(intent);
            }
        });
       /* Configuration.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
             System.out.println("<<<checking onclickConfiguration");
             drawer_layout.closeDrawer(whatYouWantInLeftDrawer);
             Intent i = new Intent(MainActivity.this, ConnectActivity.class);
             startActivity(i);
          }
         }
        );*/
        Help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawer(whatYouWantInLeftDrawer);
                Intent intent = new Intent(MainActivity.this, GalleryAlbumActivity.class);
                startActivity(intent);
            }
        });

        deleteFeature.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iabManager.deleteFeature(MainActivity.this);
            }
        });


    }


    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        flyIn();
        super.onStart();
    }

    @Override
    protected void onResume() {
        flyIn();
        super.onResume();
    }

    private BroadcastReceiver finishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            LocalBroadcastManager.getInstance(MainActivity.this)
                    .unregisterReceiver(finishReceiver);
        }
    };

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }


    @SuppressWarnings("unused")
    private void displayGallery() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                && !Environment.getExternalStorageState().equals(
                Environment.MEDIA_CHECKING)) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, Constants.REQUEST_GALLERY);
        } else {
            Toaster.make(getApplicationContext(), R.string.no_media);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("<<<checking onActivityResult");
        if (requestCode == Constants.REQUEST_CAMERA) {
            try {
                if (resultCode == RESULT_OK) {
                    displayPhotoActivity(1);
                } else {
                    UriToUrl.deleteUri(getApplicationContext(), imageUri);
                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(),
                        R.string.error_img_not_found);
            }
        } else if (resultCode == RESULT_OK
                && requestCode == Constants.REQUEST_GALLERY) {
            try {
                imageUri = data.getData();
                displayPhotoActivity(2);
            } catch (Exception e) {
                Toaster.make(getApplicationContext(),
                        R.string.error_img_not_found);
            }
        } else if (requestCode == 3) {
            System.out.println("<<<checking setbackup resultcode ="
                    + resultCode);
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    String path = getPath(this, uri);
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    System.out.println("<<<checking mainmenu onactivity result"
                            + e);

                }
            }

        } else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                System.out
                        .println("<<<checking setbackup backupPreference resultcode ="
                                + resultCode);
                long version = 1;
                SharedPreferences preferences = getSharedPreferences("AppBackup", MODE_PRIVATE);

            }
        }
    }

    @SuppressWarnings("unused")
    private void displayCamera() {
        imageUri = getOutputMediaFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    private Uri getOutputMediaFile() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Camera Pro");
        values.put(MediaStore.Images.Media.DESCRIPTION, "www.appsroid.org");
        return getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void displayPhotoActivity(int source_id) {
        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
        intent.setData(imageUri);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void flyOut(final String method_name) {
        if (click_status) {
            click_status = false;

            animation = AnimationUtils.loadAnimation(this, R.anim.holder_top_back);
            top_holder.startAnimation(animation);

            animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom_back);
            bottom_holder.startAnimation(animation);

            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    callMethod(method_name);
                }
            });
        }
    }

    private void callMethod(final String method_name) {
        if (method_name.equals("finish")) {
            overridePendingTransition(0, 0);
            finish();
        } else if(method_name.equals("displayCamera")){
            checkBoxStatus(BoxIDConstant.FEATURE1, method_name);
        }else if(method_name.equals("displayGallery")){
            checkBoxStatus(BoxIDConstant.FEATURE2, method_name);
        }
    }

    @Override
    public void onBackPressed() {
        flyOut("finish");
        super.onBackPressed();
    }

    private String stringBuffer;

    private class fetchString extends AsyncTask<String, Void, String> {

        public fetchString() {
            // TODO Auto-generated constructor stub
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String result = " ";
            try {
                final URL textUrl = new URL(params[0]);
                final BufferedReader bufferReader = new BufferedReader(
                        new InputStreamReader(textUrl.openStream()));
                // String stringBuffer;

                while ((stringBuffer = bufferReader.readLine()) != null) {
                    result += stringBuffer;

                }
                bufferReader.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);


        }
    }


    public void startGallery(View view) {
        flyOut("displayGallery");
    }

    public void startCamera(View view) {
        flyOut("displayCamera");
    }

    private void flyIn() {
        click_status = true;

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_top);
        top_holder.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.holder_bottom);
        bottom_holder.startAnimation(animation);

    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"), 3);
    }

    public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                // String displayName =
                // cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                // System.out.println("aaaaaaaaawqqf"+displayName);
                // editor.putString("filename", displayName);
                // editor.commit();
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void onPrefBackupClick() {
        long version = 1;

        editor.putString("json", detail.toString());
        editor.commit();
        System.out.println("Preference is : " + preferences.getAll());
//        MeUser.getInstance(MainActivity.this).backupPreference(version, preferences);
    }

    private void checkBoxStatus(String boxID, final String method_name){
        try {
//            iabManager.setFinish(true);
            iabManager.getIDStatus(MainActivity.this, boxID, 0, new FeatureCallback() {
                @Override
                public void onFeatureClicked() {
                    callTask(method_name);
                }
            });
        } catch (MegogridException e) {
            e.printStackTrace();
        }
    }

    private void callTask(String mName){
        try {
            Method method = getClass().getDeclaredMethod(mName);
            method.invoke(this, new Object[]{});
        } catch (Exception e) {
        }
    }



}
