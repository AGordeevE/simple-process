/*
 * This file is generated by jOOQ.
 */
package com.alex.simple.process.jooq;


import com.alex.simple.process.jooq.tables.JProcesses;
import com.alex.simple.process.jooq.tables.records.JProcessesRecord;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships and constraints of tables of
 * the <code>public</code> schema.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.1"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JProcessesRecord> PROCESSES_PK = UniqueKeys0.PROCESSES_PK;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<JProcessesRecord> PROCESSES_PK = Internal.createUniqueKey(JProcesses.PROCESSES, "processes_pk", JProcesses.PROCESSES.UUID);
    }
}