package fr.esgi.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);

        this.initView();

        return this.rootView;
    }

    public static ViewPagerFragment newInstance(User user) {
        ViewPagerFragment myFragment = new ViewPagerFragment();

        Bundle args = new Bundle();
        args.putParcelable("USER", user);
        myFragment.setArguments(args);

        return myFragment;
    }

    private void initView(){
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        if (viewPager != null) {
            User user = getArguments().getParcelable("USER");
            viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),
                    getActivity(), user));
        }
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(viewPager);
    }

}
