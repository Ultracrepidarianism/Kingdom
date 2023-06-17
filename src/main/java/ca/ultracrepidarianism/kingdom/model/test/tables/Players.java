/*
 * This file is generated by jOOQ.
 */
package ca.ultracrepidarianism.kingdom.model.test.tables;


import ca.ultracrepidarianism.kingdom.model.test.Keys;
import ca.ultracrepidarianism.kingdom.model.test.Minecraft;
import ca.ultracrepidarianism.kingdom.model.test.enums.PlayersPermissionlevel;
import ca.ultracrepidarianism.kingdom.model.test.tables.records.PlayersRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Players extends TableImpl<PlayersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>minecraft.players</code>
     */
    public static final Players PLAYERS = new Players();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayersRecord> getRecordType() {
        return PlayersRecord.class;
    }

    /**
     * The column <code>minecraft.players.UUID</code>.
     */
    public final TableField<PlayersRecord, String> UUID = createField(DSL.name("UUID"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>minecraft.players.permissionLevel</code>.
     */
    public final TableField<PlayersRecord, PlayersPermissionlevel> PERMISSIONLEVEL = createField(DSL.name("permissionLevel"), SQLDataType.VARCHAR(7).asEnumDataType(ca.ultracrepidarianism.kingdom.model.test.enums.PlayersPermissionlevel.class), this, "");

    /**
     * The column <code>minecraft.players.kingdomId</code>.
     */
    public final TableField<PlayersRecord, Long> KINGDOMID = createField(DSL.name("kingdomId"), SQLDataType.BIGINT, this, "");

    private Players(Name alias, Table<PlayersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Players(Name alias, Table<PlayersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>minecraft.players</code> table reference
     */
    public Players(String alias) {
        this(DSL.name(alias), PLAYERS);
    }

    /**
     * Create an aliased <code>minecraft.players</code> table reference
     */
    public Players(Name alias) {
        this(alias, PLAYERS);
    }

    /**
     * Create a <code>minecraft.players</code> table reference
     */
    public Players() {
        this(DSL.name("players"), null);
    }

    public <O extends Record> Players(Table<O> child, ForeignKey<O, PlayersRecord> key) {
        super(child, key, PLAYERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Minecraft.MINECRAFT;
    }

    @Override
    public UniqueKey<PlayersRecord> getPrimaryKey() {
        return Keys.KEY_PLAYERS_PRIMARY;
    }

    @Override
    public List<ForeignKey<PlayersRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_PLAYER_KINGDOM);
    }

    private transient Kingdoms _kingdoms;

    /**
     * Get the implicit join path to the <code>minecraft.kingdoms</code> table.
     */
    public Kingdoms kingdoms() {
        if (_kingdoms == null)
            _kingdoms = new Kingdoms(this, Keys.FK_PLAYER_KINGDOM);

        return _kingdoms;
    }

    @Override
    public Players as(String alias) {
        return new Players(DSL.name(alias), this);
    }

    @Override
    public Players as(Name alias) {
        return new Players(alias, this);
    }

    @Override
    public Players as(Table<?> alias) {
        return new Players(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(String name) {
        return new Players(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(Name name) {
        return new Players(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Players rename(Table<?> name) {
        return new Players(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, PlayersPermissionlevel, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super String, ? super PlayersPermissionlevel, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super String, ? super PlayersPermissionlevel, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
