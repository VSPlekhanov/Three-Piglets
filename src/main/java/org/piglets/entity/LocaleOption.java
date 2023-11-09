package org.piglets.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Document(collection = "Locale")
public class LocaleOption implements Serializable {

    public String localeOption;
    public Boolean isDefault;
    public Map<String, String> localeData;

}
