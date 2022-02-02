// Generated by view binder compiler. Do not edit!
package com.lay.fibuv2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lay.fibuv2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityResetPasswordBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final EditText passwordreset;

  @NonNull
  public final ProgressBar resetprogressBar;

  @NonNull
  public final TextView restetaftertext;

  @NonNull
  public final Button restetresetbtn;

  @NonNull
  public final TextView restettextView;

  @NonNull
  public final TextView sifrenimiunuttun;

  private ActivityResetPasswordBinding(@NonNull RelativeLayout rootView,
      @NonNull EditText passwordreset, @NonNull ProgressBar resetprogressBar,
      @NonNull TextView restetaftertext, @NonNull Button restetresetbtn,
      @NonNull TextView restettextView, @NonNull TextView sifrenimiunuttun) {
    this.rootView = rootView;
    this.passwordreset = passwordreset;
    this.resetprogressBar = resetprogressBar;
    this.restetaftertext = restetaftertext;
    this.restetresetbtn = restetresetbtn;
    this.restettextView = restettextView;
    this.sifrenimiunuttun = sifrenimiunuttun;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityResetPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityResetPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_reset_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityResetPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.passwordreset;
      EditText passwordreset = ViewBindings.findChildViewById(rootView, id);
      if (passwordreset == null) {
        break missingId;
      }

      id = R.id.resetprogressBar;
      ProgressBar resetprogressBar = ViewBindings.findChildViewById(rootView, id);
      if (resetprogressBar == null) {
        break missingId;
      }

      id = R.id.restetaftertext;
      TextView restetaftertext = ViewBindings.findChildViewById(rootView, id);
      if (restetaftertext == null) {
        break missingId;
      }

      id = R.id.restetresetbtn;
      Button restetresetbtn = ViewBindings.findChildViewById(rootView, id);
      if (restetresetbtn == null) {
        break missingId;
      }

      id = R.id.restettextView;
      TextView restettextView = ViewBindings.findChildViewById(rootView, id);
      if (restettextView == null) {
        break missingId;
      }

      id = R.id.sifrenimiunuttun;
      TextView sifrenimiunuttun = ViewBindings.findChildViewById(rootView, id);
      if (sifrenimiunuttun == null) {
        break missingId;
      }

      return new ActivityResetPasswordBinding((RelativeLayout) rootView, passwordreset,
          resetprogressBar, restetaftertext, restetresetbtn, restettextView, sifrenimiunuttun);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
