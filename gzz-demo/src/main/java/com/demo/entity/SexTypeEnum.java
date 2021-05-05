package com.demo.entity;

public enum SexTypeEnum {
    NA(0, ""),		//无效值
    MAN(1, "男"),		//男
    WOMAN(2, "女");   //女

    private int value;
    private String label;

    private SexTypeEnum(int value,String label) {
        this.value = value;
        this.label = label;
    }
    public int getValue() {
        return value;
    }
    public String getLabel() {
        return label;
    }
    /**
     * 返回名称
     * @param value
     * @return
     */
    public static SexTypeEnum valueOf(int value) {
        SexTypeEnum[] types = SexTypeEnum.values();
        for (SexTypeEnum type : types) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return SexTypeEnum.NA;
    }


    /**
     * 返回ID值
     * @param name
     * @return
     */
    public static int nameOf(String name) {
        for (SexTypeEnum act : SexTypeEnum.values()) {
            if (name.equalsIgnoreCase(act.toString())) {
                return act.getValue();
            }
        }
        return 0;
    }

}
