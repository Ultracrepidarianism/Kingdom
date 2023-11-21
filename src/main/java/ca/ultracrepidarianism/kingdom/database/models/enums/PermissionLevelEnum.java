package ca.ultracrepidarianism.kingdom.database.models.enums;

public enum PermissionLevelEnum {
    MEMBER(1),
    OFFICER(2),
    OWNER(3);

    private final int level;

    PermissionLevelEnum(final int level) {
        this.level = level;
    }

    public PermissionLevelEnum getNextPermissionLevel() {
        return fromLevel(this.level);
    }

    public PermissionLevelEnum getPreviousPermissionLevel() {
        return fromLevel(this.level - 1);
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
        return null;
    }

    public boolean hasPermission(final PermissionLevelEnum permissionLevel) {
        return this.getLevel() >= permissionLevel.getLevel();
    }
}
