/*
 * This file is generated by jOOQ.
 */
package com.alex.simple.process.jooq;


import com.alex.simple.process.jooq.tables.JProcesses;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.1"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class JPublic extends SchemaImpl {

    /**
     * The reference instance of <code>public</code>
     */
    public static final JPublic PUBLIC = new JPublic();
    private static final long serialVersionUID = -217426499;
    /**
     * Таблица хранит данные процессов
     */
    public final JProcesses PROCESSES = com.alex.simple.process.jooq.tables.JProcesses.PROCESSES;

    /**
     * No further instances allowed
     */
    private JPublic() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
                JProcesses.PROCESSES);
    }
}
