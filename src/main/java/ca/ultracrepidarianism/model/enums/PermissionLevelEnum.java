package ca.ultracrepidarianism.model.enums;

public enum PermissionLevelEnum {
    MEMBER(0),
    OFFICER(50),
    OWNER(100);

    private final int value;

    PermissionLevelEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public PermissionLevelEnum fromValue(int value){
        for(PermissionLevelEnum e : PermissionLevelEnum.values()){
            if(e.getValue() == value)
                return e;
        }
        return PermissionLevelEnum.MEMBER;
    }
}
