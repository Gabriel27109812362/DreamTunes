package validators;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public abstract class InputValidator implements TextWatcher {

    private final EditText textInput;


    public InputValidator(EditText textInput) {
        this.textInput = textInput;
    }

    public abstract void validate(EditText textInput, String text);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = textInput.getText().toString();
        validate(textInput, text);
    }
}
