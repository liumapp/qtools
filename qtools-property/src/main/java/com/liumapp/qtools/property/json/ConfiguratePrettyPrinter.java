package com.liumapp.qtools.property.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.google.common.base.Strings;

import java.io.IOException;

/**
 * file ConfiguratePrettyPrinter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * An extension of {@link DefaultPrettyPrinter} which can be customised by loader settings.
 */
class ConfiguratePrettyPrinter extends DefaultPrettyPrinter {
    private final FieldValueSeparatorStyle style;

    public ConfiguratePrettyPrinter(int indent, FieldValueSeparatorStyle style) {
        this._objectIndenter = indent == 0 ? NopIndenter.instance : DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.withIndent(Strings.repeat(" ", indent));
        this.style = style;
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(style.getValue());
    }
}
