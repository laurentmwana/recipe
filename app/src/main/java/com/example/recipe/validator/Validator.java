package com.example.recipe.validator;

import android.widget.EditText;
import android.widget.TextView;

public class Validator {

    private boolean stateError = false;

    public Validator regex(EditText editText, String regex) {

        RuleValidator rule = new RuleValidator(getData(editText));
        if (rule.regex(regex)) {
            addError(editText, "regex");
        }

        return this;
    }

    public Validator isTime(TextView textView) {

        RuleValidator rule = new RuleValidator(textView.getText().toString());
        if (rule.regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            addError(textView, "time");
        }

        return this;
    }

    public Validator required(EditText... editTextList) {

        for (EditText ed : editTextList) {
            RuleValidator rule = new RuleValidator(getData(ed));
            if (rule.required()) {
                addError(ed, "required");
            }
        }

        return this;
    }

    public Validator min(EditText editText, int min) {
        RuleValidator rule = new RuleValidator(getData(editText));

        if (rule.max(min)) {
            addError(editText, "min", min);
        }

        return this;
    }

    public Validator max(EditText editText, int max) {
        RuleValidator rule = new RuleValidator(getData(editText));

        if (rule.max(max)) {
            addError(editText, "max", max);
        }

        return this;
    }

    public Validator between(EditText editText, int min, int max) {
        RuleValidator rule = new RuleValidator(getData(editText));

        if (rule.between(min, max)) {
            addError(editText, "between", min, max);
        }

        return this;
    }

    public Validator positive(EditText ... editTexts) {

        for (EditText ed : editTexts) {
            RuleValidator rule = new RuleValidator(getData(ed));

            if (rule.positive()) {
                addError(ed, "positive");
            }
        }

        return this;
    }

    public boolean hasError() {
        return !stateError;
    }

    private String getData(EditText editText) {
        return editText.getText().toString();
    }

    private void addError(EditText editText, String key,  Object... objects) {
        String m = ErrorMessageValidator.getMessage(key);
        String format = String.format(m, objects);
        editText.setError(format);
        stateError = true;
    }

    private void addError(TextView editText, String key,  Object... objects) {
        String m = ErrorMessageValidator.getMessage(key);
        String format = String.format(m, objects);
        editText.setError(format);
        stateError = true;
    }
}
