package luck.materialdesign.tabsnavigator.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import luck.materialdesign.tabsnavigator.R;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment {
    View content;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1,container,false);
        content = v;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String text = bundle.getString("text");
            TextView view = (TextView)content.findViewById(R.id.textView_1);
            if(view != null) {
                view.setText("Tab1 [" + text + "]");
            }
        }

        return v;
    }

    public void append_text(String text) {
        TextView view = (TextView)content.findViewById(R.id.textView_1);
        if(view != null) {
            view.setText("Tab1 [" + text + "]");
        }
    }
}
