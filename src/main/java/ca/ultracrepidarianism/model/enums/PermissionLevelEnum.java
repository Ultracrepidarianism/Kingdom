package ca.ultracrepidarianism.model.enums;

public enum PermissionLevelEnum {
    MEMBER(1),
    OFFICER(2),
    OWNER(3);

    private final int level;

    PermissionLevelEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public PermissionLevelEnum fromLevel(int level) {
        for (PermissionLevelEnum permission : PermissionLevelEnum.values()) {
            if (permission.getLevel() == level) {
                return permission;
            }
        }
        throw new IllegalArgumentException("Invalid level value: " + level);
    }

    public boolean hasPermission(PermissionLevelEnum permissionLevel) {
        return this.getLevel() >= permissionLevel.getLevel();
    }
}
