package com.codergeezer.contenthub.dto.response;

/**
 * @author haidv
 * @version 1.0
 */
public class DictionaryResponse {

    private Long dictionaryId;

    private String dictionaryCode;

    private String dictionaryName;

    private String dictionaryGroupCode;

    private Long dictionaryGroupId;

    private int sortOrder;

    private String value;

    private Boolean isDefault;

    private String description;

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryGroupCode() {
        return dictionaryGroupCode;
    }

    public void setDictionaryGroupCode(String dictionaryGroupCode) {
        this.dictionaryGroupCode = dictionaryGroupCode;
    }

    public Long getDictionaryGroupId() {
        return dictionaryGroupId;
    }

    public void setDictionaryGroupId(Long dictionaryGroupId) {
        this.dictionaryGroupId = dictionaryGroupId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
