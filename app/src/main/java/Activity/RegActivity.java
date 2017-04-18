package Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.sharencare.R;

public class RegActivity extends AppCompatActivity {
    private Button submit;
    private EditText editText1, editText2, editText3, editText4, editusrname, edtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        submit = (Button) findViewById(R.id.submit);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editusrname = (EditText) findViewById(R.id.editusrname);
        edtPwd = (EditText) findViewById(R.id.editPwd);


        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                      }
                                  }
        );
    }
}
