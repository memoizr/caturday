package com.lovecats.catlover.util.helper;

import android.content.Context;
import android.content.Intent;

public class ShareHelper {

    public static void shareLinkAction(String subject, String extraText, Context context) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);

        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, extraText);

        context.startActivity(Intent.createChooser(share, "Share link!"));
    }
}
