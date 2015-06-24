package com.caturday.app.capsules.common.view.views;

import android.content.Context;
import android.content.Intent;

import com.caturday.app.R;
import com.caturday.app.capsules.login.view.LoginActivity;

public class LoggedOutEmptyView extends EmptyView{

    public LoggedOutEmptyView(Context context) {
        super(context);
    }

    @Override
    protected void setContent() {
        titleTV.setText(R.string.logged_out_title);
        subTitleTV.setText(R.string.logged_out_info);
        imageIV.setImageResource(R.drawable.logged_out);
    }

    @Override
    protected void setAction() {
            container.setOnClickListener(v -> {
                        Intent intent = new Intent(context, LoginActivity.class);
                        int y = v.getHeight() / 2 + v.getTop();
                        int x = v.getWidth() / 2 + v.getLeft();
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_X, x);
                        intent.putExtra(LoginActivity.RIPPLE_ORIGIN_Y, y);
                        context.startActivity(intent);
                    }
            );
    }
}
