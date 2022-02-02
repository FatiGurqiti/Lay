// Generated by view binder compiler. Do not edit!
package com.lay.fibuv2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lay.fibuv2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityThumbnailFullScreenBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageView fullThumbnail;

  @NonNull
  public final LinearLayout fullscreenContentControls;

  private ActivityThumbnailFullScreenBinding(@NonNull FrameLayout rootView,
      @NonNull ImageView fullThumbnail, @NonNull LinearLayout fullscreenContentControls) {
    this.rootView = rootView;
    this.fullThumbnail = fullThumbnail;
    this.fullscreenContentControls = fullscreenContentControls;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityThumbnailFullScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityThumbnailFullScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_thumbnail_full_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityThumbnailFullScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fullThumbnail;
      ImageView fullThumbnail = ViewBindings.findChildViewById(rootView, id);
      if (fullThumbnail == null) {
        break missingId;
      }

      id = R.id.fullscreen_content_controls;
      LinearLayout fullscreenContentControls = ViewBindings.findChildViewById(rootView, id);
      if (fullscreenContentControls == null) {
        break missingId;
      }

      return new ActivityThumbnailFullScreenBinding((FrameLayout) rootView, fullThumbnail,
          fullscreenContentControls);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
