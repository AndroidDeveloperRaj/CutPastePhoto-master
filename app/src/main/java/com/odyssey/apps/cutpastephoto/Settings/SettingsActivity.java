package com.odyssey.apps.cutpastephoto.Settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.odyssey.apps.cutpastephoto.IAP.IAPActivity;
import com.odyssey.apps.cutpastephoto.MainActivity;
import com.odyssey.apps.cutpastephoto.More.MoreAppsActivity;
import com.odyssey.apps.cutpastephoto.R;


public class SettingsActivity extends AppCompatActivity {




    // Data part . . .
    private int colors [];
    private String fonts[];
    private void prepareData(){

        colors = com.odyssey.apps.cutpastephoto.AddText.Data.getSharedInstance().getColors();
        fonts = com.odyssey.apps.cutpastephoto.AddText.Data.getSharedInstance().getFonts();
    }

    ListView table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);



        //Collection View Setup . .
        table = (ListView) findViewById(R.id.ASListView);
        final SettingsAdapter adapter = new SettingsAdapter(SettingsActivity.this);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(SettingsActivity.this,"Clicked on Item" + i , Toast.LENGTH_SHORT ).show();

                //Pre-process
                Intent browserIntent;

                switch (i)
                {
                    case 0 : //Header
                        break;
                    case 1 :
                        Intent store = new Intent(SettingsActivity.this,IAPActivity.class);
                        startActivity(store);
                        break;
                    case 2 :
                        Intent help = new Intent(SettingsActivity.this,MainActivity.class);
                        //erase.putExtra("BitmapImage2", byteArray);
                        //bool = true;
                        setResult(Activity.RESULT_OK,
                                help);
                        finish();
                        break;
                    case 3 :
                        SettingModel.getSharedInstance().tellAFriend(SettingsActivity.this,"https://play.google.com/store/apps/details?id=com.odyssey.apps.cutpastephoto");
                        break;
                    case 4 :
                        SettingModel.getSharedInstance().contactUS(SettingsActivity.this);
                        break;
                    case 5 :
                        SettingModel.getSharedInstance().shareURL(SettingsActivity.this,"https://play.google.com/store/apps/details?id=com.odyssey.apps.cutpastephoto");
                        break;
                    case 6 :
                        SettingModel.getSharedInstance().pushURL(SettingsActivity.this,"https://play.google.com/store/apps/details?id=com.odyssey.apps.cutpastephoto");
                        break;
                    case 7 :
                        Intent myIntent = new Intent(SettingsActivity.this, MoreAppsActivity.class);
                        myIntent.putExtra("key", "Kumbaya . . !"); //Optional parameters
                        SettingsActivity.this.startActivity(myIntent);
                        //SettingModel.getSharedInstance().pushURL(SettingsActivity.this,"https://play.google.com/store/apps/details?id=com.odyssey.apps.cutpastephoto");
                        break;

                    case 8 : //Header
                        break;

                    case 9 :
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Odyssey-Apps-Ltd-1430982826963290/"));
                        startActivity(browserIntent);
                        break;

                    case 10 :
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/odysseyappsltd"));
                        startActivity(browserIntent);
                        break;
                    case 11 :
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/odysseyapps/"));
                        startActivity(browserIntent);
                        break;
                    case 12 :
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/odyssey-apps-ltd/"));
                        startActivity(browserIntent);
                        break;
                    case 13 : //Footer
                        break ;


                }
            }
        });
    }
    public void cancelAct(View view){
        finish();
    }
}
