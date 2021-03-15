package database;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.primaryKey;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.VARCHAR;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.tinylog.Logger;

public class ConfigurationTable {
    private ConfigurationTable() {
    }

    static final String FIELD_PREFIX = "prefix";
    static final String FIELD_MUSICCHANNEL = "musicchannel";
    public static final String FIELD_GUILDID = "guildid";
    static final String TABLE_NAME = "configuration";

    public static void createTable() {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            try (CreateTableColumnStep table = create.createTable(TABLE_NAME)) {
                table.column(FIELD_GUILDID, BIGINT).column(FIELD_PREFIX, VARCHAR(255).defaultValue("."))
                        .column(FIELD_MUSICCHANNEL, VARCHAR(255).defaultValue("everywhere"))
                        .constraints(primaryKey(FIELD_GUILDID)).execute();
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    public static Configuration getConfigurationByGuildId(long guildId) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Record record = create.select().from(table(TABLE_NAME)).where(field(FIELD_GUILDID).eq(guildId)).fetchOne();
            if (record == null) {
                return null;
            }
            return new Configuration(record.get(FIELD_GUILDID, Long.class), record.get(FIELD_PREFIX, String.class),
                    record.get(FIELD_MUSICCHANNEL, String.class));
        } catch (SQLException e) {
            Logger.error(e);
        }
        return null;
    }

    public static List<Configuration> getConfigurations() {
        List<Configuration> configurations = new ArrayList<>();

        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            Result<Record> result = create.select().from(table(TABLE_NAME)).fetch();

            for (var record : result) {
                Configuration configuration = new Configuration(record.get(FIELD_GUILDID, Long.class),
                        record.get(FIELD_PREFIX, String.class), record.get(FIELD_MUSICCHANNEL, String.class));
                configurations.add(configuration);
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return configurations;
    }

    public static Configuration createConfiguration(Configuration configuration) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.insertInto(table(TABLE_NAME), field(FIELD_GUILDID), field(FIELD_MUSICCHANNEL), field(FIELD_PREFIX))
                    .values(configuration.getGuildId(), configuration.getMusicChannel(), configuration.getPrefix())
                    .execute();
            return getConfigurationByGuildId(configuration.getGuildId());
        } catch (SQLException e) {
            Logger.error(e);
        }
        return null;
    }

    public static Configuration updateConfiguration(Configuration configuration) {
        try (Connection con = DBManager.getConnection()) {
            DSLContext create = DSL.using(con, DBManager.DEFAULT_DIALECT);
            create.update(table(TABLE_NAME)).set(field(FIELD_MUSICCHANNEL), configuration.getMusicChannel())
                    .set(field(FIELD_PREFIX), configuration.getPrefix())
                    .where(field(FIELD_GUILDID).eq(configuration.getGuildId())).execute();
            return getConfigurationByGuildId(configuration.getGuildId());
        } catch (SQLException e) {
            Logger.error(e);
        }
        return null;
    }
}
