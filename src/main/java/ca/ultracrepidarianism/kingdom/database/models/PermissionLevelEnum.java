package ca.ultracrepidarianism.kingdom.database.models;

public enum PermissionLevelEnum {
    MEMBER(1),
    OFFICER(2),
    OWNER(3);

    private final int level;

    PermissionLevelEnum(final int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static PermissionLevelEnum fromLevel(final int level) {
        for (final PermissionLevelEnum permission : PermissionLevelEnum.values()) {
            if (permission.getLevel() == level) {
                return permission;
            }
        }
        throw new IllegalArgumentException("Invalid level value: " + level);
    }

    public boolean hasPermission(final PermissionLevelEnum permissionLevel) {
        return this.getLevel() >= permissionLevel.getLevel();
    }
}
