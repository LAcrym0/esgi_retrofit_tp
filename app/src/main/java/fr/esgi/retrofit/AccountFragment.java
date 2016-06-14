package fr.esgi.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountFragment extends Fragment {
    @BindView(R.id.username) TextView username;
    @BindView(R.id.userpicture) ImageView userpicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        ButterKnife.bind(this, rootView);
        this.initView();

        return rootView;
    }

    public static AccountFragment newInstance(User user) {
        AccountFragment myFragment = new AccountFragment();

        Bundle args = new Bundle();
        args.putParcelable("USER", user);
        myFragment.setArguments(args);

        return myFragment;
    }

    private void initView() {
        Bundle args = getArguments();
        User user = args.getParcelable("USER");
        if (user != null) {
            System.out.println(user.getName());
            username.setText(user.getName());
            Glide.with(getContext()).load(user.getAvatarUrl()).into(userpicture);
        }
    }
}
