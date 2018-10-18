package com.boss.p1051_fragmentdynamic;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    final String frag1_str = "frag1";
    final String frag2_str = "frag2";

    //Fragment1 frag1;
    //Fragment2 frag2;
    FragmentTransaction fTrans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //frag1 = new Fragment1();
        //frag2 = new Fragment2();
    }

    public void onClick(View v) {
        fTrans = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.btnAdd:

                Fragment1 frag1 = new Fragment1();
                Fragment2 frag2 = new Fragment2();

                fTrans.add(R.id.frgmCont, frag1, frag1_str);
                fTrans.addToBackStack(null);

                fTrans.add(R.id.frgmCont, frag2, frag2_str);
                fTrans.addToBackStack(null);

                fTrans.commit();
                break;

            case R.id.btnRemove:
                //fTrans.remove(frag1);

                getFragmentManager()
                        .beginTransaction().
                        remove(getFragmentManager().findFragmentByTag(frag1_str)).commit();

//                int cnt  = getFragmentManager().getBackStackEntryCount();
//                for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
//                    getFragmentManager().popBackStack();
//
//                    getFragmentManager()
//                            .beginTransaction().
//                            remove(getFragmentManager().findFragmentByTag("frag1")).commit();
//                }

//                int cnt_remove = 0;
//                android.app.Fragment frag = null;
//                do {
//                    frag = getFragmentManager().findFragmentByTag(frag1_str);
//                    if(frag != null) {
//                        fTrans = getFragmentManager().beginTransaction();
//                        fTrans.remove(frag);
//                        fTrans.commit();
//                        cnt_remove++;
//                    }
//                } while(frag != null);

//                while (getFragmentManager().getBackStackEntryCount() > 0) {
//                    cnt++;
//                    if(cnt > 1000) {
//                        break;
//                    }
//                    getFragmentManager().popBackStack();
//                    android.app.Fragment frag = getFragmentManager().findFragmentByTag(frag1_str);
//                    if(frag != null) {
//                        fTrans = getFragmentManager().beginTransaction();
//                        fTrans.remove(frag);
//                        fTrans.commit();
//                        cnt_remove++;
//                    }
//                }

//                Log.i("States", "cnt_remove " + String.valueOf(cnt_remove));
                break;

            default:
                break;
        }
    }
}
