package com.basketproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static String Id="";
    static String Name="";
    static String Birth="";
    static String Sex="";
    static String Position = "";
    static String Team="";
    static String realTime="";
    static String check_status="";
    static String check_time="";
    static String check_court="";
   // SharedPreferences prefs;
 //   SharedPreferences.Editor editor = prefs.edit();
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences prefs_user = getSharedPreferences("basketball_user", MODE_PRIVATE);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("Id");
        Name = intent1.getStringExtra("Name");
        Birth = intent1.getStringExtra("Birth");
        Sex = intent1.getStringExtra("Sex");
        Position = intent1.getStringExtra("Position");
        Team = prefs_user.getString("Team", "");
        realTime = new SimpleDateFormat("HHmm").format(new java.sql.Date(System.currentTimeMillis()));
        SharedPreferences prefs;
        prefs = getSharedPreferences("Mybasket_CheckIn", MODE_PRIVATE);
        if(prefs.getString("status", "").equals("checking")) {
            check_status = "checking";
            check_time = prefs.getString("time","");
            check_court = prefs.getString("court","");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences prefs1 = getSharedPreferences("basketball_user", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Nav_TeamMake) {
            Intent intent_TeamMake = new Intent(MainActivity.this, Navigation_TeamManager_TeamMake1.class);
            intent_TeamMake.putExtra("Id",Id);
            startActivity(intent_TeamMake);
        }
        else if (id == R.id.Nav_UserInfo) {
            Intent intent_UserInfo = new Intent(MainActivity.this, Navigation_TeamManager.class);
            intent_UserInfo.putExtra("Id",Id);
            startActivity(intent_UserInfo);
        }
        else if (id == R.id.Nav_TeamManager) {
            Intent intent_TeamManager = new Intent(MainActivity.this, Navigation_TeamManager.class);
            intent_TeamManager.putExtra("Id",Id);
            intent_TeamManager.putExtra("Team",Team);
            startActivity(intent_TeamManager);
        } else if (id == R.id.Nav_TeamInfo) {
            Intent intent_TeamInfo = new Intent(MainActivity.this, Navigation_TeamInfo.class);
            intent_TeamInfo.putExtra("Id", Id);
            startActivity(intent_TeamInfo);
        } else if (id == R.id.Nav_LogOut) {
            editor1.putString("phone", ".");
            editor1.putString("pw", ".");
            editor1.putString("login1", "logout");
            editor1.commit();
            Intent intent_Login = new Intent(MainActivity.this, Login.class);
            startActivity(intent_Login);
            finish();

        } else if (id == R.id.Nav_TeamIntro) {
            Intent intent_TeamIntro = new Intent(MainActivity.this, Navigation_TeamIntro.class);
            intent_TeamIntro.putExtra("Id", Id);
            startActivity(intent_TeamIntro);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new SectionsFragment1();
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new SectionsFragment2();
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new SectionsFragment3();
                    args = new Bundle();
                    break;
                case 3:
                    fragment = new SectionsFragment4();
                    args = new Bundle();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "매칭";
                case 1:
                    return "대회";
                case 2:
                    return "리그";
                case 3:
                    return "설정";
            }
            return null;
        }
    }
    public static class SectionsFragment1 extends Fragment {
        Button Match_Button_Out, Match_Button_In, Match_Layout_Out_Button_CheckIn, Match_In_Button_Register;
        ListView Match_Out_CustomList, Match_In_CustomList;
        LinearLayout Match_Layout_Out, Match_Layout_In;
        Match_Out_CustomList_MyAdapter match_Out_CustomList_MyAdapter;
        ArrayList<Match_Out_CustomList_MyData> match_Out_CustomList_MyData;
        Match_In_CustomList_MyAdapter match_In_CustomList_MyAdapter;
        ArrayList<Match_In_CustomList_MyData> match_In_CustomList_MyData;
        Spinner Match_In_Spinner_Address_do, Match_In_Spinner_Address_se;
        ArrayAdapter<CharSequence> adspin1, adspin2;
        String[][] parsedData;
        public SectionsFragment1() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.layout_match, container, false);
            Match_Out_CustomList = (ListView)rootView.findViewById(R.id.Match_Out_CustomList);
            Match_In_CustomList = (ListView)rootView.findViewById(R.id.Match_In_CustomList);
            Match_Button_Out = (Button)rootView.findViewById(R.id.Match_Button_Out);
            Match_Layout_Out_Button_CheckIn = (Button)rootView.findViewById(R.id.Match_Layout_Out_Button_CheckIn);
            Match_Layout_Out = (LinearLayout)rootView.findViewById(R.id.Match_Layout_Out);
            Match_Layout_In = (LinearLayout)rootView.findViewById(R.id.Match_Layout_In);
            Match_In_Spinner_Address_do = (Spinner)rootView.findViewById(R.id.Match_In_Spinner_Address_do);
            Match_In_Spinner_Address_se = (Spinner)rootView.findViewById(R.id.Match_In_Spinner_Address_se);
            Match_In_Button_Register = (Button)rootView.findViewById(R.id.Match_In_Button_Register);


            if(check_status.equals("checking")){
                if(Integer.parseInt(realTime) < (Integer.parseInt(check_time)+300))
                {
                    Match_Layout_Out_Button_CheckIn.setText(check_court+ " Check - OUT");
                }
            }
            Match_In_Button_Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            Match_Layout_Out_Button_CheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Match_Layout_Out_Button_CheckIn.getText().toString().equals("Check-In"))
                    {
                        new IntentIntegrator(getActivity()).initiateScan();
                    }
                    else
                    {
                        LayoutInflater inflater = (LayoutInflater)rootView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View layout = inflater.inflate(R.layout.layout_customdialog_checkout, (ViewGroup) rootView.findViewById(R.id.Layout_CustomDialog_CheckOut_Root));
                        final Button Layout_CustomDialog_CheckOut_buttonOK = (Button)layout.findViewById(R.id.Layout_CustomDialog_CheckOut_buttonOK);
                        final Button Layout_CustomDialog_CheckOut_buttonCancel = (Button)layout.findViewById(R.id.Layout_CustomDialog_CheckOut_buttonCancel);
                        final AlertDialog.Builder aDialog = new AlertDialog.Builder(rootView.getContext());
                        aDialog.setTitle("Check - IN");
                        aDialog.setView(layout);
                        final AlertDialog ad = aDialog.create();
                        ad.show();
                        Layout_CustomDialog_CheckOut_buttonOK.setOnClickListener(new View.OnClickListener() {
                            String result = "";

                            @Override
                            public void onClick(View view) {
                                try {
                                    HttpClient client = new DefaultHttpClient();
                                    String postURL = "http://210.122.7.195:8080/Web_basket/CheckOut.jsp";
                                    HttpPost post = new HttpPost(postURL);
                                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("Id", Id));
                                    HttpResponse response = client.execute(post);
                                    BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                                    String line = null;

                                    while ((line = bufreader.readLine()) != null) {
                                        result += line;
                                    }
                                    Log.i("result", result);
                                    ad.dismiss();
                                    Match_Layout_Out_Button_CheckIn.setText("Check - IN");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        });

                        Layout_CustomDialog_CheckOut_buttonCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ad.dismiss();
                            }
                        });
                    }
                }
            });
            Match_Button_Out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Match_Layout_Out.setVisibility(View.VISIBLE);
                    Match_Layout_In.setVisibility(View.GONE);
                    if(check_status.equals("checking")){
                        if(Integer.parseInt(realTime) < (Integer.parseInt(check_time)+300))
                        {
                            Match_Layout_Out_Button_CheckIn.setText(check_court+ " Check - OUT");
                        }
                    }
                    String result="";
                    try {
                        HttpClient client = new DefaultHttpClient();
                        String postURL = "http://210.122.7.195:8080/Web_basket/OutList.jsp";
                        HttpPost post = new HttpPost(postURL);

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        HttpResponse response = client.execute(post);
                        BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                        String line = null;

                        while ((line = bufreader.readLine()) != null) {
                            result += line;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    parsedData = outList_jsonParserList(result);
                    ////////////////////////////////리스트 뷰 구현////////////////////////////////////////////////
                    outList_setData();
                    match_Out_CustomList_MyAdapter = new Match_Out_CustomList_MyAdapter(rootView.getContext(), match_Out_CustomList_MyData);
                    //리스트뷰에 어댑터 연결

                    Match_Out_CustomList.setAdapter(match_Out_CustomList_MyAdapter);
                }
            });

           String result="";
            try {
                HttpClient client = new DefaultHttpClient();
                String postURL = "http://210.122.7.195:8080/Web_basket/OutList.jsp";
                HttpPost post = new HttpPost(postURL);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                HttpResponse response = client.execute(post);
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                String line = null;

                while ((line = bufreader.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            parsedData = outList_jsonParserList(result);
            ////////////////////////////////리스트 뷰 구현////////////////////////////////////////////////
          outList_setData();
            match_Out_CustomList_MyAdapter = new Match_Out_CustomList_MyAdapter(rootView.getContext(), match_Out_CustomList_MyData);
            //리스트뷰에 어댑터 연결

            Match_Out_CustomList.setAdapter(match_Out_CustomList_MyAdapter);

////////////////////////////////            /////매칭 -In 구현/////////////////////////////////////////////////////////////////////////////////
            Match_Button_In = (Button)rootView.findViewById(R.id.Match_Button_In);
            Match_Button_In.setOnClickListener(new View.OnClickListener() {
                String choice_do, choice_se;

                @Override
                public void onClick(View view) {
                    Match_Layout_Out.setVisibility(View.GONE);
                    Match_Layout_In.setVisibility(View.VISIBLE);

                    adspin1 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do, R.layout.zfile_spinner_test);
                    adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Match_In_Spinner_Address_do.setAdapter(adspin1);
                    Match_In_Spinner_Address_do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (adspin1.getItem(i).equals("서울")) {
                                choice_do = "서울";
                                //두번째 스피너 이벤트
                                adspin2 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do_seoul, R.layout.zfile_spinner_test);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                Match_In_Spinner_Address_se.setAdapter(adspin2);
                                Match_In_Spinner_Address_se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        choice_se = adspin2.getItem(i).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            } else if (adspin1.getItem(i).equals("경기도")) {
                                choice_do = "경기도";
                                adspin2 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do_Gyeonggido, R.layout.zfile_spinner_test);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                Match_In_Spinner_Address_se.setAdapter(adspin2);
                                Match_In_Spinner_Address_se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        choice_se = adspin2.getItem(i).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    try {
                        HttpClient client = new DefaultHttpClient();
                        String postURL = "http://210.122.7.195:8080/Web_basket/Match_InList.jsp";
                        HttpPost post = new HttpPost(postURL);

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("do", "서울"));
                        params.add(new BasicNameValuePair("se", "전 체"));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                        post.setEntity(ent);

                        HttpResponse response = client.execute(post);
                        BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                        String line = null;
                        String result = "";
                        while ((line = bufreader.readLine()) != null) {
                            result += line;
                        }
                        parsedData = inList_jsonParserList(result);
                        inList_setData();
                        match_In_CustomList_MyAdapter = new Match_In_CustomList_MyAdapter(rootView.getContext(), match_In_CustomList_MyData);
                        Match_In_CustomList.setAdapter(match_In_CustomList_MyAdapter);
                    }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

            }
                });
            /////////////////어댑터에 값 넣음./////////////////////////


            return rootView;
        }
        private void outList_setData()
        {
            match_Out_CustomList_MyData = new ArrayList<Match_Out_CustomList_MyData>();
            for(int i =0; i<parsedData.length; i++) {
                match_Out_CustomList_MyData.add(new Match_Out_CustomList_MyData(parsedData[i][0],parsedData[i][1],parsedData[i][2]));
            }
        }
        private void inList_setData()
        {
            match_In_CustomList_MyData = new ArrayList<Match_In_CustomList_MyData>();
            for(int i =0; i<parsedData.length; i++) {
                match_In_CustomList_MyData.add(new Match_In_CustomList_MyData(parsedData[i][0],parsedData[i][1],parsedData[i][2],parsedData[i][3]));
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        /////매칭 탭 - out : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
        public String[][] outList_jsonParserList(String pRecvServerPage){
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try{
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");

                String[] jsonName = {"msg1","msg2","msg3"};
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
        /////매칭 탭 - in : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
        public String[][] inList_jsonParserList(String pRecvServerPage){
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try{
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");

                String[] jsonName = {"msg1","msg2","msg3","msg4"};
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }





    public static class SectionsFragment2 extends Fragment {
        Spinner Contest_Spinner_Address_do, Contest_Spinner_Address_se, Contest_Spinner_AgeGroup, Contest_Spinner_Kind;
        ArrayAdapter<CharSequence> adspin1, adspin2, adspin3, adspin4;
        Button Contest_Button_search;
        String choice_do,choice_se,choice_agegroup="일반부",choice_kind="5:5";
        String[][] parsedData;
        Contest_CustomList_MyAdapter Contest_CustomList_MyAdapter;
        ArrayList<Contest_CustomList_MyData> Contest_CustomList_MyData;
        ListView Contest_CustomList;
        public SectionsFragment2() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.layout_contest, container, false);
            Contest_Spinner_Address_do = (Spinner)rootView.findViewById(R.id.Contest_Spinner_Address_do);
            Contest_Spinner_Address_se = (Spinner)rootView.findViewById(R.id.Contest_Spinner_Address_se);
            Contest_Spinner_AgeGroup = (Spinner)rootView.findViewById(R.id.Contest_Spinner_AgeGroup);
            Contest_Spinner_Kind = (Spinner)rootView.findViewById(R.id.Contest_Spinner_Kind);
            Contest_Button_search = (Button)rootView.findViewById(R.id.Contest_Button_Search);
            Contest_CustomList = (ListView)rootView.findViewById(R.id.Contest_CustomList);

            adspin3 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_agegroup, R.layout.zfile_spinner_test);
            adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Contest_Spinner_AgeGroup.setAdapter(adspin3);

            adspin4 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_kind, R.layout.zfile_spinner_test);
            adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Contest_Spinner_Kind.setAdapter(adspin4);

            adspin1 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do, R.layout.zfile_spinner_test);
            adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Contest_Spinner_Address_do.setAdapter(adspin1);
            Contest_Spinner_Address_do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (adspin1.getItem(i).equals("서울")) {
                        choice_do = "서울";
                        //두번째 스피너 이벤트
                        adspin2 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do_seoul, R.layout.zfile_spinner_test);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Contest_Spinner_Address_se.setAdapter(adspin2);
                        Contest_Spinner_Address_se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                choice_se = adspin2.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    } else if (adspin1.getItem(i).equals("경기도")) {
                        choice_do = "경기도";
                        adspin2 = ArrayAdapter.createFromResource(rootView.getContext(), R.array.spinner_do_Gyeonggido, R.layout.zfile_spinner_test);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Contest_Spinner_Address_se.setAdapter(adspin2);
                        Contest_Spinner_Address_se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                choice_se = adspin2.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            Contest_Spinner_Kind.setAdapter(adspin4);
            Contest_Spinner_Kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(adspin4.getItem(i).equals("5:5")){
                        choice_kind = "5:5";
                    }
                    else if(adspin4.getItem(i).equals("3:3")){
                        choice_kind = "3:3";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            Contest_Spinner_AgeGroup.setAdapter(adspin3);
            Contest_Spinner_AgeGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(adspin3.getItem(i).equals("일반부"))
                    {
                        choice_agegroup = "일반부";
                    }
                    else if(adspin3.getItem(i).equals("고등부"))
                    {
                        choice_agegroup = "고등부";
                    }
                    else if(adspin3.getItem(i).equals("중등부"))
                    {
                        choice_agegroup = "중등부";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            try {
                HttpClient client = new DefaultHttpClient();
                String postURL = "http://210.122.7.195:8080/Web_basket/Contest.jsp";
                HttpPost post = new HttpPost(postURL);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("do", "서울"));
                params.add(new BasicNameValuePair("se", "전 체"));
                params.add(new BasicNameValuePair("agegroup", "전 체"));
                params.add(new BasicNameValuePair("kind", "전 체"));

                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(ent);

                HttpResponse response = client.execute(post);
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                String line = null;
                String result = "";
                while ((line = bufreader.readLine()) != null) {
                    result += line;
                }
                parsedData = contest_jsonParserList(result);
                contest_setData();
                Contest_CustomList_MyAdapter = new Contest_CustomList_MyAdapter(rootView.getContext(), Contest_CustomList_MyData);
                Contest_CustomList.setAdapter(Contest_CustomList_MyAdapter);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Contest_Button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        HttpClient client = new DefaultHttpClient();
                        String postURL = "http://210.122.7.195:8080/Web_basket/Contest.jsp";
                        HttpPost post = new HttpPost(postURL);

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("do", choice_do));
                        params.add(new BasicNameValuePair("se", choice_se));
                        params.add(new BasicNameValuePair("agegroup", choice_agegroup));
                        params.add(new BasicNameValuePair("kind", choice_kind));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                        post.setEntity(ent);

                        HttpResponse response = client.execute(post);
                        BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                        String line = null;
                        String result = "";
                        while ((line = bufreader.readLine()) != null) {
                            result += line;
                        }
                        parsedData = contest_jsonParserList(result);
                        contest_setData();
                        Contest_CustomList_MyAdapter = new Contest_CustomList_MyAdapter(rootView.getContext(), Contest_CustomList_MyData);
                        Contest_CustomList.setAdapter(Contest_CustomList_MyAdapter);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            return rootView;
        }
        private void contest_setData()
        {
            Contest_CustomList_MyData = new ArrayList<Contest_CustomList_MyData>();
            for(int i =0; i<parsedData.length; i++) {
                Contest_CustomList_MyData.add(new Contest_CustomList_MyData(parsedData[i][0],parsedData[i][1]));
            }
        }
        /////대회 탭  받아온 json 파싱합니다.//////////////////////////////////////////////////////////
        public String[][] contest_jsonParserList(String pRecvServerPage){
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try{
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");

                String[] jsonName = {"msg1","msg2"};
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }
    public static class SectionsFragment3 extends Fragment {


        public SectionsFragment3() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_league, container, false);
            return rootView;
        }
    }
    public static class SectionsFragment4 extends Fragment {


        public SectionsFragment4() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_setup, container, false);
            return rootView;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Button Match_Layout_Out_Button_CheckIn = (Button)findViewById(R.id.Match_Layout_Out_Button_CheckIn);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, data);
        String[][] parsedData;

        String str = result.getContents();
        String[] result1 = str.split("=");
        Log.i("msg",result1[1]);
        String postResult="";
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/CheckIn.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Id", Id));
            params.add(new BasicNameValuePair("CourtName", result1[1]));
            params.add(new BasicNameValuePair("Time", realTime));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;

            while ((line = bufreader.readLine()) != null) {
                postResult += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parsedData = jsonParserList(postResult);
        if(parsedData[0][0].equals("succed")){
            Toast.makeText(this,result1[1]+"\nCheck - In",Toast.LENGTH_SHORT).show();
            Match_Layout_Out_Button_CheckIn.setText(result1[1] + " Check - OUT");
            SharedPreferences prefs2 = getSharedPreferences("Mybasket_CheckIn", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs2.edit();
            editor.putString("status", "checking");
            editor.putString("time", realTime);
            editor.putString("court", result1[1]);
            editor.commit();
        }
        SharedPreferences prefs = getSharedPreferences("Mybasket_CheckIn", MODE_PRIVATE);
        Log.i("test1", prefs.getString("status",""));
        ////////////////////////////////리스트 뷰 구현////////////////////////////////////////////////
    }
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
