package com.huotu.fanmore.pinkcatraiders.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;

import butterknife.Bind;

public class SetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edtpsd)
    EditText edtpsd;
    @Bind(R.id.btnshow)
    Button btnshow;
    @Bind(R.id.btn_commit)
    Button btn_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnshow:{

            }
            break;
            case R.id.btn_commit:{

            }
            break;
            default:
                break;
        }

    }
}
