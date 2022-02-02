// Generated by view binder compiler. Do not edit!
package com.example.fibuv2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.fibuv2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSecondBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView firstball;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final ImageView secondball;

  @NonNull
  public final TextView sliderbottomtext;

  @NonNull
  public final TextView slidertitle;

  @NonNull
  public final ImageView third;

  private FragmentSecondBinding(@NonNull RelativeLayout rootView, @NonNull ImageView firstball,
      @NonNull ImageView imageView6, @NonNull ImageView imageView8, @NonNull ImageView imageView9,
      @NonNull ImageView secondball, @NonNull TextView sliderbottomtext,
      @NonNull TextView slidertitle, @NonNull ImageView third) {
    this.rootView = rootView;
    this.firstball = firstball;
    this.imageView6 = imageView6;
    this.imageView8 = imageView8;
    this.imageView9 = imageView9;
    this.secondball = secondball;
    this.sliderbottomtext = sliderbottomtext;
    this.slidertitle = slidertitle;
    this.third = third;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSecondBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSecondBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_second, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSecondBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.firstball;
      ImageView firstball = rootView.findViewById(id);
      if (firstball == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = rootView.findViewById(id);
      if (imageView6 == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = rootView.findViewById(id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.imageView9;
      ImageView imageView9 = rootView.findViewById(id);
      if (imageView9 == null) {
        break missingId;
      }

      id = R.id.secondball;
      ImageView secondball = rootView.findViewById(id);
      if (secondball == null) {
        break missingId;
      }

      id = R.id.sliderbottomtext;
      TextView sliderbottomtext = rootView.findViewById(id);
      if (sliderbottomtext == null) {
        break missingId;
      }

      id = R.id.slidertitle;
      TextView slidertitle = rootView.findViewById(id);
      if (slidertitle == null) {
        break missingId;
      }

      id = R.id.third;
      ImageView third = rootView.findViewById(id);
      if (third == null) {
        break missingId;
      }

      return new FragmentSecondBinding((RelativeLayout) rootView, firstball, imageView6, imageView8,
          imageView9, secondball, sliderbottomtext, slidertitle, third);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
