// Generated by view binder compiler. Do not edit!
package com.example.fibuv2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.fibuv2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCreateAccountBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final EditText CreateAccounUsernameInput;

  @NonNull
  public final EditText CreateAccountEmailInput;

  @NonNull
  public final EditText CreateAccountPasswordInput;

  @NonNull
  public final ProgressBar HesapOlusturProgress;

  @NonNull
  public final Button createAcctButton;

  @NonNull
  public final LinearLayout emailHesapOlusturForm;

  @NonNull
  public final ImageView imageView;

  private ActivityCreateAccountBinding(@NonNull RelativeLayout rootView,
      @NonNull EditText CreateAccounUsernameInput, @NonNull EditText CreateAccountEmailInput,
      @NonNull EditText CreateAccountPasswordInput, @NonNull ProgressBar HesapOlusturProgress,
      @NonNull Button createAcctButton, @NonNull LinearLayout emailHesapOlusturForm,
      @NonNull ImageView imageView) {
    this.rootView = rootView;
    this.CreateAccounUsernameInput = CreateAccounUsernameInput;
    this.CreateAccountEmailInput = CreateAccountEmailInput;
    this.CreateAccountPasswordInput = CreateAccountPasswordInput;
    this.HesapOlusturProgress = HesapOlusturProgress;
    this.createAcctButton = createAcctButton;
    this.emailHesapOlusturForm = emailHesapOlusturForm;
    this.imageView = imageView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCreateAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCreateAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_create_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCreateAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.CreateAccounUsernameInput;
      EditText CreateAccounUsernameInput = rootView.findViewById(id);
      if (CreateAccounUsernameInput == null) {
        break missingId;
      }

      id = R.id.CreateAccountEmailInput;
      EditText CreateAccountEmailInput = rootView.findViewById(id);
      if (CreateAccountEmailInput == null) {
        break missingId;
      }

      id = R.id.CreateAccountPasswordInput;
      EditText CreateAccountPasswordInput = rootView.findViewById(id);
      if (CreateAccountPasswordInput == null) {
        break missingId;
      }

      id = R.id.Hesap_olustur_progress;
      ProgressBar HesapOlusturProgress = rootView.findViewById(id);
      if (HesapOlusturProgress == null) {
        break missingId;
      }

      id = R.id.create_acct_button;
      Button createAcctButton = rootView.findViewById(id);
      if (createAcctButton == null) {
        break missingId;
      }

      id = R.id.email_Hesap_Olustur_form;
      LinearLayout emailHesapOlusturForm = rootView.findViewById(id);
      if (emailHesapOlusturForm == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = rootView.findViewById(id);
      if (imageView == null) {
        break missingId;
      }

      return new ActivityCreateAccountBinding((RelativeLayout) rootView, CreateAccounUsernameInput,
          CreateAccountEmailInput, CreateAccountPasswordInput, HesapOlusturProgress,
          createAcctButton, emailHesapOlusturForm, imageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
