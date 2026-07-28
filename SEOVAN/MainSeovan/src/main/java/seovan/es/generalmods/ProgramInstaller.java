package seovan.es.generalmods;

import beleris.es.finaldbmanager.FDBMan;
import static beleris.es.finaldbmanager.FDBMan.DB_MYSQL_Selected;
import static beleris.es.finaldbmanager.FDBMan.DB_SQLite_Selected;
import beleris.es.finalutils.FileManagerGenerator;
import java.awt.Frame;
import java.io.File;
import java.util.Set;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import static org.openide.windows.WindowManager.getDefault;
import static seovan.es.programconfig.PConfigManager.PConfig;
import seovan.es.programconfig.ProgramOptions;

/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
public final class ProgramInstaller {

    private final String ProgramHomePath = System.getProperty("user.home");
    final String ConfigPath = ProgramHomePath;
    private FileManagerGenerator FMG = null;

    FDBMan DBMSEP = null;
    FDBMan DBMSAP = null;

    /**
     *
     */
    static public ProgramInstaller Instalador = new ProgramInstaller();

    /**
     *
     */
    public ProgramInstaller() {
        if (FMG == null) {
            FMG = new FileManagerGenerator();
        }
           CheckConfiguration();
        if (FMG.FileSize(ConfigPath + File.separator + "seovan.cfg") > 0) {
            ReadConfiguration();
        } else {
        }
        PConfig.setDatabaseManagerinUse(FDBMan.DB_MYSQL_Selected);
    }

    /**
     *
     */
    public void CheckConfiguration() {

        FMG = new FileManagerGenerator(ConfigPath + File.separator + "seovan.cfg");
        if (FMG.isError() && FMG.getErrorcode() == FMG.getIerrCodeFileNotExist()) {
            WriteConfiguration();
        }
        //   Configure_DBInfo();
    }

    /**
     *
     */
    public void WriteConfiguration() {

        FMG = new FileManagerGenerator(FMG.getIfmCreateWrite(), ConfigPath + File.separator + "seovan.cfg");
        FMG.WriteInt(PConfig.getDatabaseManagerinUse());
        FMG.WriteString(PConfig.getConfUserDB());
        FMG.WriteString(PConfig.getConfPassDB());
        FMG.CloseAll();

    }

    /**
     *
     */
    public void ReadConfiguration() {
        FMG = new FileManagerGenerator(FMG.getIfmRead(), ConfigPath + File.separator + "seovan.cfg");
        int itmpDBConf = FMG.ReadInt();
        PConfig.setDatabaseManagerinUse(itmpDBConf);
        String dbUser = FMG.ReadString();
        String dbPass = FMG.ReadString();
        PConfig.setConfUserDB(dbUser);
        PConfig.setConfPassDB(dbPass);
        DBMSEP = new FDBMan("jdbc:mysql://localhost:3306/sourcesevalprotocol", PConfig.getConfPassDB(), "sourcesevalprotocol", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());
        DBMSAP = new FDBMan("jdbc:mysql://localhost:3306/desidaniespsources", PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());
        //FMG.WriteString(Integer.toString(PConfig.getDatabaseManagerinUse()));
        FMG.CloseAll();
    }

    void CreateProgramDatabasesSQLIte() {
        //DBMSAP.CreateDB("desidaniespsources");0
        String CreateTablesAnalStat = "DROP TABLE IF EXISTS `authorities`;"
                + "CREATE TABLE authorities ("
                + "  id_authorities INTEGER PRIMARY KEY,"
                + "  pricauthor TEXT NOT NULL,"
                + "  otherauthor TEXT NOT NULL"
                + ");";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        /*   CreateTablesAnalStat = "DROP TABLE IF EXISTS `contents`;\n"
                + "CREATE TABLE `contents` (\n"
                + "  `id_contents` int NOT NULL AUTO_INCREMENT,\n"
                + "  `gendescript` longtext NOT NULL,\n"
                + "  `contents` longtext,\n"
                + "  `mainextract` longtext,\n"
                + "  PRIMARY KEY (`id_contents`)\n"
                + ");\n";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = "DROP TABLE IF EXISTS `evaluation`;\n"
                + "CREATE TABLE `evaluation` (\n"
                + "  `id_evaluation` int NOT NULL AUTO_INCREMENT,\n"
                + "  `scoreeval` int DEFAULT NULL,\n"
                + "  PRIMARY KEY (`id_evaluation`)\n"
                + ");\n";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = "DROP TABLE IF EXISTS `identification`;\n"
                + "CREATE TABLE `identification` (\n"
                + "  `id_identification` int NOT NULL AUTO_INCREMENT,\n"
                + "  `dp_title` varchar(255) NOT NULL,\n"
                + "  `dp_subtitleorothertitlepart` varchar(128) NOT NULL,\n"
                + "  `dp_completenameform` varchar(128) NOT NULL,\n"
                + "  `ad_alternateppaltitle` varchar(128) DEFAULT NULL,\n"
                + "  `ad_parrareloralternatetitle` varchar(128) DEFAULT NULL,\n"
                + "  `ad_otheracceptedname` varchar(128) DEFAULT NULL,\n"
                + "  `url_dnsdomain` varchar(255) DEFAULT NULL,\n"
                + "  `url_completeurl` varchar(255) DEFAULT NULL,\n"
                + "  `url_permalink` varchar(255) DEFAULT NULL,\n"
                + "  `sindchann` varchar(255) DEFAULT NULL,\n"
                + "  `pubdate` varchar(128) NOT NULL DEFAULT '01-01-2023',\n"
                + "  `actdate` varchar(128) NOT NULL DEFAULT '01-01-2023',\n"
                + "  `rigths` varchar(128) DEFAULT NULL,\n"
                + "  `language` varchar(128) NOT NULL DEFAULT 'español',\n"
                + "  PRIMARY KEY (`id_identification`)\n"
                + ");";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = "DROP TABLE IF EXISTS `list_access_table`;\n"
                + "CREATE TABLE `list_access_table` (\n"
                + "  `idlist_access_table` int NOT NULL AUTO_INCREMENT,\n"
                + "  `access_item` varchar(45) NOT NULL,\n"
                + "  PRIMARY KEY (`idlist_access_table`)\n"
                + ") ;\n"
                + "\n"
                + "LOCK TABLES `list_access_table` WRITE;\n"
                + "INSERT INTO `list_access_table` VALUES (1,'libre'),(2,'suscripción'),(3,'mixto');\n"
                + "UNLOCK TABLES;DROP TABLE IF EXISTS `list_format_medium_table`;\n"
                + "CREATE TABLE `list_format_medium_table` (\n"
                + "  `idlist_format_medium_table` int NOT NULL AUTO_INCREMENT,\n"
                + "  `format_mediumitem` varchar(45) NOT NULL,\n"
                + "  PRIMARY KEY (`idlist_format_medium_table`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
                + "LOCK TABLES `list_format_medium_table` WRITE;\n"
                + "INSERT INTO `list_format_medium_table` VALUES (1,'en línea'),(2,'impresa');\n"
                + "UNLOCK TABLES;\n"
                + "DROP TABLE IF EXISTS `list_geo_cover_table`;\n"
                + "CREATE TABLE `list_geo_cover_table` (\n"
                + "  `idlist_geo_cover_table` int NOT NULL AUTO_INCREMENT,\n"
                + "  `geographicvoveritem` varchar(90) NOT NULL,\n"
                + "  PRIMARY KEY (`idlist_geo_cover_table`)\n"
                + ") ;\n"
                + "LOCK TABLES `list_geo_cover_table` WRITE;\n"
                + "INSERT INTO `list_geo_cover_table` VALUES (1,'local'),(2,'regional'),(3,'nacional'),(4,'internacional');\n"
                + "UNLOCK TABLES;\n"
                + "DROP TABLE IF EXISTS `list_source_origin_table`;\n"
                + "CREATE TABLE `list_source_origin_table` (\n"
                + "  `idlist_source_origin_table` int NOT NULL AUTO_INCREMENT,\n"
                + "  `originitem` varchar(45) DEFAULT NULL,\n"
                + "  PRIMARY KEY (`idlist_source_origin_table`)\n"
                + ") ;\n"
                + "LOCK TABLES `list_source_origin_table` WRITE;\n"
                + "INSERT INTO `list_source_origin_table` VALUES (1,'Institucional'),(2,'Documental'),(3,'Personal');\n"
                + "UNLOCK TABLES;\n"
                + "DROP TABLE IF EXISTS `list_sourcecontents`;\n"
                + "CREATE TABLE `list_sourcecontents` (\n"
                + "  `id_sourcecontents` int NOT NULL AUTO_INCREMENT,\n"
                + "  `sourcecontentsitem` varchar(90) NOT NULL,\n"
                + "  PRIMARY KEY (`id_sourcecontents`),\n"
                + "  KEY `fk_sourcecontents_contents_idx` (`sourcecontentsitem`)\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
                + "LOCK TABLES `list_sourcecontents` WRITE;\n"
                + "INSERT INTO `list_sourcecontents` VALUES (1,'actas de congresos'),(44,'aplicaciones web'),(2,'artículos científicos'),(3,'artículos de divulgación'),(4,'bases de datos de tesis'),(10,'bases de datos referenciales'),(5,'bibliografías'),(6,'bibliografías de bibliografías'),(8,'boletines de resúmenes'),(7,'boletines de sumarios'),(9,'catálogos bibliográficos'),(11,'código fuente'),(31,'diccionarios de referencia'),(12,'directorios científicos'),(13,'directorios de bases de datos'),(16,'directorios de editoriales'),(14,'directorios de revistas'),(15,'directorios de unidades de información y documentación'),(17,'directorios institucionales'),(18,'ensayos científicos'),(19,'eprints'),(21,'guías de obras de referencia'),(20,'guías temáticas'),(22,'índices bibliográficos'),(23,'índices de citas'),(24,'índices de impacto'),(25,'informes técnicos'),(26,'investigaciones académicas'),(33,'modelos de utilidad'),(27,'monografías científicas'),(47,'monografías de ciencias sociales'),(46,'monografías de humanidades'),(28,'nomenclaturas y especificaciones'),(29,'normas y estándares'),(30,'obras enciclopédicas'),(32,'patentes'),(34,'ponencias'),(35,'preprints'),(36,'proyectos de investigación'),(37,'repertorios de catálogos'),(38,'repertorios de índices'),(39,'reviews científicos'),(40,'revistas científicas'),(41,'separatas'),(42,'simposios'),(43,'software'),(45,'tesis doctorales. ');\n"
                + "UNLOCK TABLES;\n"
                + "DROP TABLE IF EXISTS `list_sourcelevel`;\n"
                + "CREATE TABLE `list_sourcelevel` (\n"
                + "  `id_sourcelevel` int NOT NULL AUTO_INCREMENT,\n"
                + "  `sourcelevelitem` varchar(90) NOT NULL,\n"
                + "  PRIMARY KEY (`id_sourcelevel`)\n"
                + ") ;\n"
                + "LOCK TABLES `list_sourcelevel` WRITE;\n"
                + "INSERT INTO `list_sourcelevel` VALUES (1,'primaria'),(2,'secundaria'),(3,'terciaria'),(4,'complementaria');\n"
                + "UNLOCK TABLES;";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = "DROP TABLE IF EXISTS `sourceapplication`;\n"
                + "CREATE TABLE `sourceapplication` (\n"
                + "  `id_sourceapplication` int NOT NULL DEFAULT '1',\n"
                + "  `utilsapplication` varchar(255) NOT NULL,\n"
                + "  PRIMARY KEY (`id_sourceapplication`)\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
                + "DROP TABLE IF EXISTS `temclass`;\n"
                + "CREATE TABLE `temclass` (\n"
                + "  `id_temclass` int NOT NULL AUTO_INCREMENT,\n"
                + "  `genclass` varchar(255) DEFAULT NULL,\n"
                + "  `specialclas` varchar(255) DEFAULT NULL,\n"
                + "  `descriptors` varchar(255) DEFAULT NULL,\n"
                + "  PRIMARY KEY (`id_temclass`)\n"
                + ") ;DROP TABLE IF EXISTS `tipification`;\n"
                + "CREATE TABLE `tipification` (\n"
                + "  `id_tipification` int NOT NULL AUTO_INCREMENT,\n"
                + "  `level` int NOT NULL,\n"
                + "  `content` int NOT NULL,\n"
                + "  `origin` int NOT NULL,\n"
                + "  `access` int NOT NULL,\n"
                + "  `geographycover` int NOT NULL,\n"
                + "  `tempcover` varchar(90) NOT NULL,\n"
                + "  `formatormedium` int NOT NULL,\n"
                + "  PRIMARY KEY (`id_tipification`),\n"
                + "  KEY `fk_tipification_1_idx` (`content`),\n"
                + "  KEY `fk_tipification_2_idx` (`access`),\n"
                + "  KEY `fk_tipification_3_idx` (`formatormedium`),\n"
                + "  KEY `fk_tipification_4_idx` (`geographycover`),\n"
                + "  KEY `fk_tipification_5_idx` (`tempcover`),\n"
                + "  KEY `fk_tipification_6_idx` (`level`),\n"
                + "  KEY `fk_tipification_7_idx` (`origin`),\n"
                + "  CONSTRAINT `fk_tipification_1` FOREIGN KEY (`content`) REFERENCES `list_sourcecontents` (`id_sourcecontents`),\n"
                + "  CONSTRAINT `fk_tipification_2` FOREIGN KEY (`access`) REFERENCES `list_access_table` (`idlist_access_table`),\n"
                + "  CONSTRAINT `fk_tipification_3` FOREIGN KEY (`formatormedium`) REFERENCES `list_format_medium_table` (`idlist_format_medium_table`),\n"
                + "  CONSTRAINT `fk_tipification_4` FOREIGN KEY (`geographycover`) REFERENCES `list_geo_cover_table` (`idlist_geo_cover_table`),\n"
                + "  CONSTRAINT `fk_tipification_6` FOREIGN KEY (`level`) REFERENCES `list_sourcelevel` (`id_sourcelevel`),\n"
                + "  CONSTRAINT `fk_tipification_7` FOREIGN KEY (`origin`) REFERENCES `list_source_origin_table` (`idlist_source_origin_table`)\n"
                + ") ;\n"
                + "CREATE TABLE `elements_record` (\n"
                + "  `idelements_record` int NOT NULL AUTO_INCREMENT,\n"
                + "  `elements_autorithies` int NOT NULL DEFAULT '1',\n"
                + "  `elements_contents` int NOT NULL DEFAULT '1',\n"
                + "  `elements_evaluation` int NOT NULL DEFAULT '1',\n"
                + "  `elements_identification` int NOT NULL DEFAULT '1',\n"
                + "  `elements_sourceapp` int NOT NULL DEFAULT '1',\n"
                + "  `elements_classtem` int NOT NULL DEFAULT '1',\n"
                + "  `elements_tipification` int NOT NULL DEFAULT '1',\n"
                + "  PRIMARY KEY (`idelements_record`),\n"
                + "  KEY `fk_elements_record_1_idx` (`elements_autorithies`),\n"
                + "  KEY `fk_elements_record_2_idx` (`elements_contents`),\n"
                + "  KEY `fk_elements_record_3_idx` (`elements_evaluation`),\n"
                + "  KEY `fk_elements_record_4_idx` (`elements_identification`),\n"
                + "  KEY `fk_elements_record_5_idx` (`elements_sourceapp`),\n"
                + "  KEY `fk_elements_record_6_idx` (`elements_classtem`),\n"
                + "  KEY `fk_elements_record_7_idx` (`elements_tipification`),\n"
                + "  CONSTRAINT `fk_elements_record_1` FOREIGN KEY (`elements_autorithies`) REFERENCES `authorities` (`id_authorities`),\n"
                + "  CONSTRAINT `fk_elements_record_2` FOREIGN KEY (`elements_contents`) REFERENCES `contents` (`id_contents`),\n"
                + "  CONSTRAINT `fk_elements_record_3` FOREIGN KEY (`elements_evaluation`) REFERENCES `evaluation` (`id_evaluation`),\n"
                + "  CONSTRAINT `fk_elements_record_4` FOREIGN KEY (`elements_identification`) REFERENCES `identification` (`id_identification`) ON DELETE CASCADE ON UPDATE CASCADE,\n"
                + "  CONSTRAINT `fk_elements_record_5` FOREIGN KEY (`elements_sourceapp`) REFERENCES `sourceapplication` (`id_sourceapplication`),\n"
                + "  CONSTRAINT `fk_elements_record_6` FOREIGN KEY (`elements_classtem`) REFERENCES `temclass` (`id_temclass`),\n"
                + "  CONSTRAINT `fk_elements_record_7` FOREIGN KEY (`elements_tipification`) REFERENCES `tipification` (`id_tipification`)\n"
                + ") ;\n";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        //DBMSEP.CreateDB("sourcesevalprotocol");
        String CreateTablesEvalStat = "DROP TABLE IF EXISTS `actuality`;\n"
                + "CREATE TABLE `actuality` (\n"
                + "  `id_actuality` int NOT NULL,\n"
                + "  `publicationdate` int NOT NULL DEFAULT '5',\n"
                + "  `updated` int NOT NULL DEFAULT '10',\n"
                + "  PRIMARY KEY (`id_actuality`)\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
                + "CREATE TABLE `authority` (\n"
                + "  `id_authority` int NOT NULL AUTO_INCREMENT,\n"
                + "  `recognisedauthor` int NOT NULL DEFAULT '10',\n"
                + "  `authorsaffiliation` int NOT NULL DEFAULT '5',\n"
                + "  `author` int NOT NULL DEFAULT '5',\n"
                + "  PRIMARY KEY (`id_authority`)\n"
                + ") ;\n"
                + "DROP TABLE IF EXISTS `objetivity`;\n"
                + "CREATE TABLE `objetivity` (\n"
                + "  `id_objetivity` int NOT NULL AUTO_INCREMENT,\n"
                + "  `noncommercial` int NOT NULL DEFAULT '10',\n"
                + "  `clarityadsprecision` int NOT NULL DEFAULT '5',\n"
                + "  `withsesg` int NOT NULL DEFAULT '5',\n"
                + "  `validatedinfo` int NOT NULL DEFAULT '10',\n"
                + "  PRIMARY KEY (`id_objetivity`)\n"
                + ") ;DROP TABLE IF EXISTS `publication`;\n"
                + "CREATE TABLE `publication` (\n"
                + "  `id_publication` int NOT NULL,\n"
                + "  `publisher` int NOT NULL DEFAULT '5',\n"
                + "  `publications_quality` int NOT NULL DEFAULT '5',\n"
                + "  PRIMARY KEY (`id_publication`)\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
                + "CREATE TABLE `quality` (\n"
                + "  `id_quality` int NOT NULL AUTO_INCREMENT,\n"
                + "  `orginfo` int NOT NULL DEFAULT '10',\n"
                + "  `tablesgraphics` int NOT NULL DEFAULT '5',\n"
                + "  `indexandrankings` int NOT NULL DEFAULT '10',\n"
                + "  PRIMARY KEY (`id_quality`)\n"
                + ") ;\n"
                + "CREATE TABLE `relevance` (\n"
                + "  `id_relevance` int NOT NULL AUTO_INCREMENT,\n"
                + "  `academic` int NOT NULL DEFAULT '5',\n"
                + "  `typeaware` int NOT NULL DEFAULT '10',\n"
                + "  `newandaddedvalue` int NOT NULL DEFAULT '5',\n"
                + "  PRIMARY KEY (`id_relevance`)\n"
                + ") ;\n"
                + "CREATE TABLE `theweb` (\n"
                + "  `id_theweb` int NOT NULL AUTO_INCREMENT,\n"
                + "  `institutiionalandcientificresources` int NOT NULL DEFAULT '5',\n"
                + "  `accesibilityandusability` int NOT NULL DEFAULT '5',\n"
                + "  PRIMARY KEY (`id_theweb`)\n"
                + ") ;\n";
        DBMSEP.ExecuteQueryTableDBWithOptions(CreateTablesEvalStat);*/
    }

    void CreateProgramDatabasesMySQL() {
        //DBMSAP.CreateDB("desidaniespsources");
        String CreateTablesAnalStat = """
                                      DROP TABLE IF EXISTS `authorities`;
                                      /*!40101 SET @saved_cs_client     = @@character_set_client */;
                                      /*!50503 SET character_set_client = utf8mb4 */;
                                      CREATE TABLE `authorities` (
                                        `id_authorities` int NOT NULL AUTO_INCREMENT,
                                        `pricauthor` varchar(128) NOT NULL,
                                        `otherauthor` varchar(128) NOT NULL,
                                        PRIMARY KEY (`id_authorities`),
                                        KEY `fk_authorities_1_idx` (`pricauthor`)
                                      ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                                      /*!40101 SET character_set_client = @saved_cs_client */;
                                      
                                      --
                                      -- Dumping data for table `authorities`
                                      --
                                      
                                      LOCK TABLES `authorities` WRITE;
                                      /*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
                                      INSERT INTO `authorities` VALUES (1,'Mois\u00e9s Barrio Andr\u00e9s','Editorial Tirant lo Blanch');
                                      /*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
                                      UNLOCK TABLES;""";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `contents`;
                               CREATE TABLE `contents` (
                                 `id_contents` int NOT NULL AUTO_INCREMENT,
                                 `gendescript` longtext NOT NULL,
                                 `contents` longtext,
                                 `mainextract` longtext,
                                 PRIMARY KEY (`id_contents`)
                               );
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `evaluation`;
                               CREATE TABLE `evaluation` (
                                 `id_evaluation` int NOT NULL AUTO_INCREMENT,
                                 `scoreeval` int DEFAULT NULL,
                                 PRIMARY KEY (`id_evaluation`)
                               );
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `identification`;
                               CREATE TABLE `identification` (
                                 `id_identification` int NOT NULL AUTO_INCREMENT,
                                 `dp_title` varchar(255) NOT NULL,
                                 `dp_subtitleorothertitlepart` varchar(128) NOT NULL,
                                 `dp_completenameform` varchar(128) NOT NULL,
                                 `ad_alternateppaltitle` varchar(128) DEFAULT NULL,
                                 `ad_parrareloralternatetitle` varchar(128) DEFAULT NULL,
                                 `ad_otheracceptedname` varchar(128) DEFAULT NULL,
                                 `url_dnsdomain` varchar(255) DEFAULT NULL,
                                 `url_completeurl` varchar(255) DEFAULT NULL,
                                 `url_permalink` varchar(255) DEFAULT NULL,
                                 `sindchann` varchar(255) DEFAULT NULL,
                                 `pubdate` varchar(128) NOT NULL DEFAULT '01-01-2023',
                                 `actdate` varchar(128) NOT NULL DEFAULT '01-01-2023',
                                 `rigths` varchar(128) DEFAULT NULL,
                                 `language` varchar(128) NOT NULL DEFAULT 'espa\u00f1ol',
                                 PRIMARY KEY (`id_identification`)
                               );""";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `list_access_table`;
                               CREATE TABLE `list_access_table` (
                                 `idlist_access_table` int NOT NULL AUTO_INCREMENT,
                                 `access_item` varchar(45) NOT NULL,
                                 PRIMARY KEY (`idlist_access_table`)
                               ) ;
                               
                               LOCK TABLES `list_access_table` WRITE;
                               INSERT INTO `list_access_table` VALUES (1,'libre'),(2,'suscripci\u00f3n'),(3,'mixto');
                               UNLOCK TABLES;DROP TABLE IF EXISTS `list_format_medium_table`;
                               CREATE TABLE `list_format_medium_table` (
                                 `idlist_format_medium_table` int NOT NULL AUTO_INCREMENT,
                                 `format_mediumitem` varchar(45) NOT NULL,
                                 PRIMARY KEY (`idlist_format_medium_table`)
                               ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                               LOCK TABLES `list_format_medium_table` WRITE;
                               INSERT INTO `list_format_medium_table` VALUES (1,'en l\u00ednea'),(2,'impresa');
                               UNLOCK TABLES;
                               DROP TABLE IF EXISTS `list_geo_cover_table`;
                               CREATE TABLE `list_geo_cover_table` (
                                 `idlist_geo_cover_table` int NOT NULL AUTO_INCREMENT,
                                 `geographicvoveritem` varchar(90) NOT NULL,
                                 PRIMARY KEY (`idlist_geo_cover_table`)
                               ) ;
                               LOCK TABLES `list_geo_cover_table` WRITE;
                               INSERT INTO `list_geo_cover_table` VALUES (1,'local'),(2,'regional'),(3,'nacional'),(4,'internacional');
                               UNLOCK TABLES;
                               DROP TABLE IF EXISTS `list_source_origin_table`;
                               CREATE TABLE `list_source_origin_table` (
                                 `idlist_source_origin_table` int NOT NULL AUTO_INCREMENT,
                                 `originitem` varchar(45) DEFAULT NULL,
                                 PRIMARY KEY (`idlist_source_origin_table`)
                               ) ;
                               LOCK TABLES `list_source_origin_table` WRITE;
                               INSERT INTO `list_source_origin_table` VALUES (1,'Institucional'),(2,'Documental'),(3,'Personal');
                               UNLOCK TABLES;
                               DROP TABLE IF EXISTS `list_sourcecontents`;
                               CREATE TABLE `list_sourcecontents` (
                                 `id_sourcecontents` int NOT NULL AUTO_INCREMENT,
                                 `sourcecontentsitem` varchar(90) NOT NULL,
                                 PRIMARY KEY (`id_sourcecontents`),
                                 KEY `fk_sourcecontents_contents_idx` (`sourcecontentsitem`)
                               ) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                               LOCK TABLES `list_sourcecontents` WRITE;
                               INSERT INTO `list_sourcecontents` VALUES (1,'actas de congresos'),(44,'aplicaciones web'),(2,'art\u00edculos cient\u00edficos'),(3,'art\u00edculos de divulgaci\u00f3n'),(4,'bases de datos de tesis'),(10,'bases de datos referenciales'),(5,'bibliograf\u00edas'),(6,'bibliograf\u00edas de bibliograf\u00edas'),(8,'boletines de res\u00famenes'),(7,'boletines de sumarios'),(9,'cat\u00e1logos bibliogr\u00e1ficos'),(11,'c\u00f3digo fuente'),(31,'diccionarios de referencia'),(12,'directorios cient\u00edficos'),(13,'directorios de bases de datos'),(16,'directorios de editoriales'),(14,'directorios de revistas'),(15,'directorios de unidades de informaci\u00f3n y documentaci\u00f3n'),(17,'directorios institucionales'),(18,'ensayos cient\u00edficos'),(19,'eprints'),(21,'gu\u00edas de obras de referencia'),(20,'gu\u00edas tem\u00e1ticas'),(22,'\u00edndices bibliogr\u00e1ficos'),(23,'\u00edndices de citas'),(24,'\u00edndices de impacto'),(25,'informes t\u00e9cnicos'),(26,'investigaciones acad\u00e9micas'),(33,'modelos de utilidad'),(27,'monograf\u00edas cient\u00edficas'),(47,'monograf\u00edas de ciencias sociales'),(46,'monograf\u00edas de humanidades'),(28,'nomenclaturas y especificaciones'),(29,'normas y est\u00e1ndares'),(30,'obras enciclop\u00e9dicas'),(32,'patentes'),(34,'ponencias'),(35,'preprints'),(36,'proyectos de investigaci\u00f3n'),(37,'repertorios de cat\u00e1logos'),(38,'repertorios de \u00edndices'),(39,'reviews cient\u00edficos'),(40,'revistas cient\u00edficas'),(41,'separatas'),(42,'simposios'),(43,'software'),(45,'tesis doctorales. ');
                               UNLOCK TABLES;
                               DROP TABLE IF EXISTS `list_sourcelevel`;
                               CREATE TABLE `list_sourcelevel` (
                                 `id_sourcelevel` int NOT NULL AUTO_INCREMENT,
                                 `sourcelevelitem` varchar(90) NOT NULL,
                                 PRIMARY KEY (`id_sourcelevel`)
                               ) ;
                               LOCK TABLES `list_sourcelevel` WRITE;
                               INSERT INTO `list_sourcelevel` VALUES (1,'primaria'),(2,'secundaria'),(3,'terciaria'),(4,'complementaria');
                               UNLOCK TABLES;""";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `sourceapplication`;
                               CREATE TABLE `sourceapplication` (
                                 `id_sourceapplication` int NOT NULL DEFAULT '1',
                                 `utilsapplication` varchar(255) NOT NULL,
                                 PRIMARY KEY (`id_sourceapplication`)
                               ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                               DROP TABLE IF EXISTS `temclass`;
                               CREATE TABLE `temclass` (
                                 `id_temclass` int NOT NULL AUTO_INCREMENT,
                                 `genclass` varchar(255) DEFAULT NULL,
                                 `specialclas` varchar(255) DEFAULT NULL,
                                 `descriptors` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id_temclass`)
                               ) ;DROP TABLE IF EXISTS `tipification`;
                               CREATE TABLE `tipification` (
                                 `id_tipification` int NOT NULL AUTO_INCREMENT,
                                 `level` int NOT NULL,
                                 `content` int NOT NULL,
                                 `origin` int NOT NULL,
                                 `access` int NOT NULL,
                                 `geographycover` int NOT NULL,
                                 `tempcover` varchar(90) NOT NULL,
                                 `formatormedium` int NOT NULL,
                                 PRIMARY KEY (`id_tipification`),
                                 KEY `fk_tipification_1_idx` (`content`),
                                 KEY `fk_tipification_2_idx` (`access`),
                                 KEY `fk_tipification_3_idx` (`formatormedium`),
                                 KEY `fk_tipification_4_idx` (`geographycover`),
                                 KEY `fk_tipification_5_idx` (`tempcover`),
                                 KEY `fk_tipification_6_idx` (`level`),
                                 KEY `fk_tipification_7_idx` (`origin`),
                                 CONSTRAINT `fk_tipification_1` FOREIGN KEY (`content`) REFERENCES `list_sourcecontents` (`id_sourcecontents`),
                                 CONSTRAINT `fk_tipification_2` FOREIGN KEY (`access`) REFERENCES `list_access_table` (`idlist_access_table`),
                                 CONSTRAINT `fk_tipification_3` FOREIGN KEY (`formatormedium`) REFERENCES `list_format_medium_table` (`idlist_format_medium_table`),
                                 CONSTRAINT `fk_tipification_4` FOREIGN KEY (`geographycover`) REFERENCES `list_geo_cover_table` (`idlist_geo_cover_table`),
                                 CONSTRAINT `fk_tipification_6` FOREIGN KEY (`level`) REFERENCES `list_sourcelevel` (`id_sourcelevel`),
                                 CONSTRAINT `fk_tipification_7` FOREIGN KEY (`origin`) REFERENCES `list_source_origin_table` (`idlist_source_origin_table`)
                               ) ;
                               CREATE TABLE `elements_record` (
                                 `idelements_record` int NOT NULL AUTO_INCREMENT,
                                 `elements_autorithies` int NOT NULL DEFAULT '1',
                                 `elements_contents` int NOT NULL DEFAULT '1',
                                 `elements_evaluation` int NOT NULL DEFAULT '1',
                                 `elements_identification` int NOT NULL DEFAULT '1',
                                 `elements_sourceapp` int NOT NULL DEFAULT '1',
                                 `elements_classtem` int NOT NULL DEFAULT '1',
                                 `elements_tipification` int NOT NULL DEFAULT '1',
                                 PRIMARY KEY (`idelements_record`),
                                 KEY `fk_elements_record_1_idx` (`elements_autorithies`),
                                 KEY `fk_elements_record_2_idx` (`elements_contents`),
                                 KEY `fk_elements_record_3_idx` (`elements_evaluation`),
                                 KEY `fk_elements_record_4_idx` (`elements_identification`),
                                 KEY `fk_elements_record_5_idx` (`elements_sourceapp`),
                                 KEY `fk_elements_record_6_idx` (`elements_classtem`),
                                 KEY `fk_elements_record_7_idx` (`elements_tipification`),
                                 CONSTRAINT `fk_elements_record_1` FOREIGN KEY (`elements_autorithies`) REFERENCES `authorities` (`id_authorities`),
                                 CONSTRAINT `fk_elements_record_2` FOREIGN KEY (`elements_contents`) REFERENCES `contents` (`id_contents`),
                                 CONSTRAINT `fk_elements_record_3` FOREIGN KEY (`elements_evaluation`) REFERENCES `evaluation` (`id_evaluation`),
                                 CONSTRAINT `fk_elements_record_4` FOREIGN KEY (`elements_identification`) REFERENCES `identification` (`id_identification`) ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT `fk_elements_record_5` FOREIGN KEY (`elements_sourceapp`) REFERENCES `sourceapplication` (`id_sourceapplication`),
                                 CONSTRAINT `fk_elements_record_6` FOREIGN KEY (`elements_classtem`) REFERENCES `temclass` (`id_temclass`),
                                 CONSTRAINT `fk_elements_record_7` FOREIGN KEY (`elements_tipification`) REFERENCES `tipification` (`id_tipification`)
                               ) ;
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        //DBMSEP.CreateDB("sourcesevalprotocol");
        String CreateTablesEvalStat = """
                                      DROP TABLE IF EXISTS `actuality`;
                                      CREATE TABLE `actuality` (
                                        `id_actuality` int NOT NULL,
                                        `publicationdate` int NOT NULL DEFAULT '5',
                                        `updated` int NOT NULL DEFAULT '10',
                                        PRIMARY KEY (`id_actuality`)
                                      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                                      CREATE TABLE `authority` (
                                        `id_authority` int NOT NULL AUTO_INCREMENT,
                                        `recognisedauthor` int NOT NULL DEFAULT '10',
                                        `authorsaffiliation` int NOT NULL DEFAULT '5',
                                        `author` int NOT NULL DEFAULT '5',
                                        PRIMARY KEY (`id_authority`)
                                      ) ;
                                      DROP TABLE IF EXISTS `objetivity`;
                                      CREATE TABLE `objetivity` (
                                        `id_objetivity` int NOT NULL AUTO_INCREMENT,
                                        `noncommercial` int NOT NULL DEFAULT '10',
                                        `clarityadsprecision` int NOT NULL DEFAULT '5',
                                        `withsesg` int NOT NULL DEFAULT '5',
                                        `validatedinfo` int NOT NULL DEFAULT '10',
                                        PRIMARY KEY (`id_objetivity`)
                                      ) ;DROP TABLE IF EXISTS `publication`;
                                      CREATE TABLE `publication` (
                                        `id_publication` int NOT NULL,
                                        `publisher` int NOT NULL DEFAULT '5',
                                        `publications_quality` int NOT NULL DEFAULT '5',
                                        PRIMARY KEY (`id_publication`)
                                      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                                      CREATE TABLE `quality` (
                                        `id_quality` int NOT NULL AUTO_INCREMENT,
                                        `orginfo` int NOT NULL DEFAULT '10',
                                        `tablesgraphics` int NOT NULL DEFAULT '5',
                                        `indexandrankings` int NOT NULL DEFAULT '10',
                                        PRIMARY KEY (`id_quality`)
                                      ) ;
                                      CREATE TABLE `relevance` (
                                        `id_relevance` int NOT NULL AUTO_INCREMENT,
                                        `academic` int NOT NULL DEFAULT '5',
                                        `typeaware` int NOT NULL DEFAULT '10',
                                        `newandaddedvalue` int NOT NULL DEFAULT '5',
                                        PRIMARY KEY (`id_relevance`)
                                      ) ;
                                      CREATE TABLE `theweb` (
                                        `id_theweb` int NOT NULL AUTO_INCREMENT,
                                        `institutiionalandcientificresources` int NOT NULL DEFAULT '5',
                                        `accesibilityandusability` int NOT NULL DEFAULT '5',
                                        PRIMARY KEY (`id_theweb`)
                                      ) ;
                                      """;
        DBMSEP.ExecuteQueryTableDBWithOptions(CreateTablesEvalStat);
    }

    /**
     *
     */
    public void CreateProgramDatabases() {

        switch (PConfig.getDatabaseManagerinUse()) {
            case DB_MYSQL_Selected -> {
                CreateProgramDatabasesMySQL();
            }
            case DB_SQLite_Selected -> {
                CreateProgramDatabasesSQLIte();
            }
            default -> {
                CreateProgramDatabasesMySQL();
            }

        }

    }

    private void Configure_DBInfo() {
        Frame f = WindowManager.getDefault().getMainWindow();
        //FHelpPresenter DHelp = new FHelpPresenter("Registro de control de Seguridad", "help/NewGeneralDataIntroPanels-PSRC.html");
        ProgramOptions OptionsDLG = new ProgramOptions(f, true);
        OptionsDLG.setLocationRelativeTo(f);
        OptionsDLG.setVisible(true);
        Set<TopComponent> openTopComponents = getDefault().getRegistry().getOpened();
        PrincipalWindowTopComponent ptc = null;
        for (TopComponent stc : openTopComponents) {
            if ("Ventana Principal".equals(stc.getName())) {
                ptc = (PrincipalWindowTopComponent) stc;
            }
            if (ptc != null) {
                ptc.setDBToUse(OptionsDLG.getDatabasemanager());
            }
        }
    }

}
