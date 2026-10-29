/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/moduleInstall.java to edit this template
 */
package seovan.es.generalmods;

import beleris.es.finaldbmanager.FDBMan;
import static beleris.es.finaldbmanager.FDBMan.DB_MYSQL_Selected;
import static beleris.es.finaldbmanager.FDBMan.DB_PostgreSQL_Selected;
import static beleris.es.finaldbmanager.FDBMan.DB_SQLite_Selected;
import static beleris.es.finaldbmanager.FDBMan.SQLLiteDBExists;
import beleris.es.finalutils.FileManagerGenerator;
import java.awt.Frame;
import java.io.File;
import java.util.Set;
import org.openide.modules.ModuleInstall;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import static org.openide.windows.WindowManager.getDefault;
import static seovan.es.programconfig.PConfigManager.PConfig;
import seovan.es.programconfig.ProgramOptions;

/**
 *
 * @author edavid
 */
final public class Instalador extends ModuleInstall {

    private static final long serialVersionUID = 197856352L;

    String ProgramHomePath = System.getProperty("user.home");
    final String ConfigPath = ProgramHomePath;
    FileManagerGenerator FMG = null;

    FDBMan DBMSEP = null;
    FDBMan DBMSAP = null;

    @Override
    public void restored() {

        /**
         *
         */
        if (FMG == null) {
            FMG = new FileManagerGenerator();
        }
        CheckConfiguration();
        if (FMG.FileSize(ConfigPath + File.separator + "seovan.cfg") > 0) {
            ReadConfiguration();
        } else {

        }
        PConfig.setDatabaseManagerinUse(FDBMan.DB_SQLite_Selected);
        InstallProdedures();
    }

    @Override
    public void close() {
        WriteConfiguration();
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
        if (!FMG.isError()) {
            PConfig.setDatabaseManagerinUse(itmpDBConf);
        }
        String dbUser = FMG.ReadString();
        String dbPass = FMG.ReadString();
        if (dbUser != null) {
            PConfig.setConfUserDB(dbUser);
        }
        if (dbPass != null) {
            PConfig.setConfPassDB(dbPass);
        }
        /*/
        DBMSEP = new FDBMan(PConfig.DataBaseEvalUrlConn, PConfig.getConfPassDB(), "sourcesevalprotocol", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());
        DBMSAP = new FDBMan(PConfig.DataBaseAnalysisUrlConn, PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());*/
        //FMG.WriteString(Integer.toString(PConfig.getDatabaseManagerinUse()));
        FMG.CloseAll();
    }

    void InstallProdedures() {
        ConfigureDB();
        CreateDBs();
        ExitandClose();
    }

    void CreateAnalDBTablesSQLIte() {
        String SQLOptions = "PRAGMA foreign_keys = ON";
        DBMSAP.ExecuteQueryTableDBWithOptions(SQLOptions);
        String CreateTablesAnalStat = "DROP TABLE IF EXISTS `authorities`;"
                + "CREATE TABLE authorities ("
                + "  id_authorities INTEGER PRIMARY KEY,"
                + "  pricauthor TEXT NOT NULL,"
                + "  otherauthor TEXT NOT NULL"
                + ");";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);

        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `contents`;
                               CREATE TABLE `contents` (
                                 `id_contents` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `gendescript` TEXT NOT NULL,
                                 `contents` TEXT,
                                 `mainextract` TEXT
                               );
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `evaluation`;
                               CREATE TABLE `evaluation` (
                                 `id_evaluation` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `scoreeval` int DEFAULT NULL
                               );
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `identification`;
                               CREATE TABLE `identification` (
                                 `id_identification` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `dp_title` TEXT NOT NULL,
                                 `dp_subtitleorothertitlepart` TEXT NOT NULL,
                                 `dp_completenameform` TEXT NOT NULL,
                                 `ad_alternateppaltitle` TEXT DEFAULT NULL,
                                 `ad_parrareloralternatetitle` TEXT DEFAULT NULL,
                                 `ad_otheracceptedname` TEXT DEFAULT NULL,
                                 `url_dnsdomain` TEXT DEFAULT NULL,
                                 `url_completeurl` TEXT DEFAULT NULL,
                                 `url_permalink` TEXT DEFAULT NULL,
                                 `sindchann` TEXT DEFAULT NULL,
                                 `pubdate` TEXT NOT NULL DEFAULT '01-01-2023',
                                 `actdate` TEXT NOT NULL DEFAULT '01-01-2023',
                                 `rigths` TEXT DEFAULT NULL,
                                 `language` TEXT NOT NULL DEFAULT 'espa\u00f1ol'
                               );""";
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);
        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `list_access_table`;
                               CREATE TABLE `list_access_table` (
                                 `idlist_access_table` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `access_item` TEXT NOT NULL
                               ) ;
                               
                               INSERT INTO `list_access_table` VALUES (1,'libre'),(2,'suscripci\u00f3n'),(3,'mixto');
                               DROP TABLE IF EXISTS `list_format_medium_table`;
                               CREATE TABLE `list_format_medium_table` (
                                 `idlist_format_medium_table` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `format_mediumitem` TEXT NOT NULL
                               );
                               INSERT INTO `list_format_medium_table` VALUES (1,'en l\u00ednea'),(2,'impresa');
                               DROP TABLE IF EXISTS `list_geo_cover_table`;
                               CREATE TABLE `list_geo_cover_table` (
                                 `idlist_geo_cover_table` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `geographicvoveritem` TEXT NOT NULL
                               ) ;
                               INSERT INTO `list_geo_cover_table` VALUES (1,'local'),(2,'regional'),(3,'nacional'),(4,'internacional');
                               DROP TABLE IF EXISTS `list_source_origin_table`;
                               CREATE TABLE `list_source_origin_table` (
                                 `idlist_source_origin_table` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `originitem` TEXT DEFAULT NULL
                               ) ;
                               INSERT INTO `list_source_origin_table` VALUES (1,'Institucional'),(2,'Documental'),(3,'Personal');
                               DROP TABLE IF EXISTS `list_sourcecontents`;
                               CREATE TABLE `list_sourcecontents` (
                                 `id_sourcecontents` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `sourcecontentsitem` TEXT NOT NULL
                               );
                               INSERT INTO `list_sourcecontents` VALUES (1,'actas de congresos'),(44,'aplicaciones web'),(2,'art\u00edculos cient\u00edficos'),(3,'art\u00edculos de divulgaci\u00f3n'),(4,'bases de datos de tesis'),(10,'bases de datos referenciales'),(5,'bibliograf\u00edas'),(6,'bibliograf\u00edas de bibliograf\u00edas'),(8,'boletines de res\u00famenes'),(7,'boletines de sumarios'),(9,'cat\u00e1logos bibliogr\u00e1ficos'),(11,'c\u00f3digo fuente'),(31,'diccionarios de referencia'),(12,'directorios cient\u00edficos'),(13,'directorios de bases de datos'),(16,'directorios de editoriales'),(14,'directorios de revistas'),(15,'directorios de unidades de informaci\u00f3n y documentaci\u00f3n'),(17,'directorios institucionales'),(18,'ensayos cient\u00edficos'),(19,'eprints'),(21,'gu\u00edas de obras de referencia'),(20,'gu\u00edas tem\u00e1ticas'),(22,'\u00edndices bibliogr\u00e1ficos'),(23,'\u00edndices de citas'),(24,'\u00edndices de impacto'),(25,'informes t\u00e9cnicos'),(26,'investigaciones acad\u00e9micas'),(33,'modelos de utilidad'),(27,'monograf\u00edas cient\u00edficas'),(47,'monograf\u00edas de ciencias sociales'),(46,'monograf\u00edas de humanidades'),(28,'nomenclaturas y especificaciones'),(29,'normas y est\u00e1ndares'),(30,'obras enciclop\u00e9dicas'),(32,'patentes'),(34,'ponencias'),(35,'preprints'),(36,'proyectos de investigaci\u00f3n'),(37,'repertorios de cat\u00e1logos'),(38,'repertorios de \u00edndices'),(39,'reviews cient\u00edficos'),(40,'revistas cient\u00edficas'),(41,'separatas'),(42,'simposios'),(43,'software'),(45,'tesis doctorales. ');
                               DROP TABLE IF EXISTS `list_sourcelevel`;
                               CREATE TABLE `list_sourcelevel` (
                                 `id_sourcelevel` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `sourcelevelitem` TEXT NOT NULL
                               ) ;
                               INSERT INTO `list_sourcelevel` VALUES (1,'primaria'),(2,'secundaria'),(3,'terciaria'),(4,'complementaria');
                               """;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);

        CreateTablesAnalStat = """
                               DROP TABLE IF EXISTS `sourceapplication`;
                               CREATE TABLE `sourceapplication` (
                                 `id_sourceapplication` INTEGER PRIMARY KEY,
                                 `utilsapplication` TEXT NOT NULL
                               );
                               DROP TABLE IF EXISTS `temclass`;
                               CREATE TABLE `temclass` (
                                 `id_temclass` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `genclass` TEXT DEFAULT NULL,
                                 `specialclas` TEXT DEFAULT NULL,
                                 `descriptors` TEXT DEFAULT NULL
                               ) ;DROP TABLE IF EXISTS `tipification`;
                               CREATE TABLE `tipification` (
                                 `id_tipification` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `level` int NOT NULL,
                                 `content` int NOT NULL,
                                 `origin` int NOT NULL,
                                 `access` int NOT NULL,
                                 `geographycover` int NOT NULL,
                                 `tempcover` TEXT NOT NULL,
                                 `formatormedium` int NOT NULL,
                                 CONSTRAINT `fk_tipification_1` FOREIGN KEY (`content`) REFERENCES `list_sourcecontents` (`id_sourcecontents`),
                                 CONSTRAINT `fk_tipification_2` FOREIGN KEY (`access`) REFERENCES `list_access_table` (`idlist_access_table`),
                                 CONSTRAINT `fk_tipification_3` FOREIGN KEY (`formatormedium`) REFERENCES `list_format_medium_table` (`idlist_format_medium_table`),
                                 CONSTRAINT `fk_tipification_4` FOREIGN KEY (`geographycover`) REFERENCES `list_geo_cover_table` (`idlist_geo_cover_table`),
                                 CONSTRAINT `fk_tipification_6` FOREIGN KEY (`level`) REFERENCES `list_sourcelevel` (`id_sourcelevel`),
                                 CONSTRAINT `fk_tipification_7` FOREIGN KEY (`origin`) REFERENCES `list_source_origin_table` (`idlist_source_origin_table`)
                               ) ;
                               DROP TABLE IF EXISTS `elements_record`;
                               CREATE TABLE `elements_record` (
                                 `idelements_record` INTEGER PRIMARY KEY AUTOINCREMENT,
                                 `elements_autorithies` int NOT NULL DEFAULT '1',
                                 `elements_contents` int NOT NULL DEFAULT '1',
                                 `elements_evaluation` int NOT NULL DEFAULT '1',
                                 `elements_identification` int NOT NULL DEFAULT '1',
                                 `elements_sourceapp` int NOT NULL DEFAULT '1',
                                 `elements_classtem` int NOT NULL DEFAULT '1',
                                 `elements_tipification` int NOT NULL DEFAULT '1',
                                 CONSTRAINT `fk_elements_record_1` FOREIGN KEY (`elements_autorithies`) REFERENCES `authorities` (`id_authorities`),
                                 CONSTRAINT `fk_elements_record_2` FOREIGN KEY (`elements_contents`) REFERENCES `contents` (`id_contents`),
                                 CONSTRAINT `fk_elements_record_3` FOREIGN KEY (`elements_evaluation`) REFERENCES `evaluation` (`id_evaluation`),
                                 CONSTRAINT `fk_elements_record_4` FOREIGN KEY (`elements_identification`) REFERENCES `identification` (`id_identification`) ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT `fk_elements_record_5` FOREIGN KEY (`elements_sourceapp`) REFERENCES `sourceapplication` (`id_sourceapplication`),
                                 CONSTRAINT `fk_elements_record_6` FOREIGN KEY (`elements_classtem`) REFERENCES `temclass` (`id_temclass`),
                                 CONSTRAINT `fk_elements_record_7` FOREIGN KEY (`elements_tipification`) REFERENCES `tipification` (`id_tipification`)
                               ) ;
                               """ // Agregado DROP para consistencia antes de su CREATE
                ;
        DBMSAP.ExecuteQueryTableDBWithOptions(CreateTablesAnalStat);

    }

    void CreateEvalDBTablesSQLIte() {
        String SQLOptions = "PRAGMA foreign_keys = ON";
        DBMSAP.ExecuteQueryTableDBWithOptions(SQLOptions);
        String CreateTablesEvalStat = """
                                      DROP TABLE IF EXISTS `actuality`;
                                      CREATE TABLE `actuality` (
                                        `id_actuality` INTEGER PRIMARY KEY,
                                        `publicationdate` int NOT NULL DEFAULT '5',
                                        `updated` int NOT NULL DEFAULT '10'
                                      );
                                      DROP TABLE IF EXISTS `authority`;
                                      CREATE TABLE `authority` (
                                        `id_authority` INTEGER PRIMARY KEY AUTOINCREMENT,
                                        `recognisedauthor` int NOT NULL DEFAULT '10',
                                        `authorsaffiliation` int NOT NULL DEFAULT '5',
                                        `author` int NOT NULL DEFAULT '5'
                                      ) ;
                                      DROP TABLE IF EXISTS `objetivity`;
                                      CREATE TABLE `objetivity` (
                                        `id_objetivity` INTEGER PRIMARY KEY AUTOINCREMENT,
                                        `noncommercial` int NOT NULL DEFAULT '10',
                                        `clarityadsprecision` int NOT NULL DEFAULT '5',
                                        `withsesg` int NOT NULL DEFAULT '5',
                                        `validatedinfo` int NOT NULL DEFAULT '10'
                                      ) ;DROP TABLE IF EXISTS `publication`;
                                      CREATE TABLE `publication` (
                                        `id_publication` INTEGER PRIMARY KEY,
                                        `publisher` int NOT NULL DEFAULT '5',
                                        `publications_quality` int NOT NULL DEFAULT '5'
                                      );
                                      DROP TABLE IF EXISTS `quality`;
                                      CREATE TABLE `quality` (
                                        `id_quality` INTEGER PRIMARY KEY AUTOINCREMENT,
                                        `orginfo` int NOT NULL DEFAULT '10',
                                        `tablesgraphics` int NOT NULL DEFAULT '5',
                                        `indexandrankings` int NOT NULL DEFAULT '10'
                                      ) ;
                                      DROP TABLE IF EXISTS `relevance`;
                                      CREATE TABLE `relevance` (
                                        `id_relevance` INTEGER PRIMARY KEY AUTOINCREMENT,
                                        `academic` int NOT NULL DEFAULT '5',
                                        `typeaware` int NOT NULL DEFAULT '10',
                                        `newandaddedvalue` int NOT NULL DEFAULT '5'
                                      ) ;
                                      DROP TABLE IF EXISTS `theweb`;
                                      CREATE TABLE `theweb` (
                                        `id_theweb` INTEGER PRIMARY KEY AUTOINCREMENT,
                                        `institutiionalandcientificresources` int NOT NULL DEFAULT '5',
                                        `accesibilityandusability` int NOT NULL DEFAULT '5'
                                      ) ;
                                      """ // Añadido para consistencia
                // Añadido para consistencia
                // Añadido para consistencia
                // Añadido para consistencia
                ;
        DBMSEP.ExecuteQueryTableDBWithOptions(CreateTablesEvalStat);
    }

    void CreateProgramDataDBTablesSQLIte() {
        CreateAnalDBTablesSQLIte();
        CreateEvalDBTablesSQLIte();

    }

    void CreateProgramDataDBTablesMySQL() {
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

    void CreateProgramDataDBTablesPostgreSQL() {
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
    public void CreateProgramDataDBTables() {
        boolean dbexists = false;
        String DBAnal = "desidaniespsources";
        String DBEval = "sourcesevalprotocol";
        switch (PConfig.getDatabaseManagerinUse()) {
            case DB_MYSQL_Selected -> {
                CreateProgramDataDBTablesMySQL();
            }
            case DB_PostgreSQL_Selected -> {
                CreateProgramDataDBTablesPostgreSQL();
            }
            case DB_SQLite_Selected -> {
                dbexists = SQLLiteDBExists(DBAnal + ".db");
                if (!dbexists) {
                    DBMSAP = new FDBMan(DBAnal, PConfig.getConfUserDB(), PConfig.getConfPassDB(), PConfig.getDatabaseManagerinUse());
                    CreateAnalDBTablesSQLIte();
                } else {
                    // CreateAnalDBTablesSQLIte();
                }

                dbexists = SQLLiteDBExists(DBEval + ".db");
                if (!dbexists) {
                    DBMSEP = new FDBMan(DBAnal, PConfig.getConfUserDB(), PConfig.getConfPassDB(), PConfig.getDatabaseManagerinUse());
                    CreateEvalDBTablesSQLIte();
                } else {
                    //  CreateEvalDBTablesSQLIte();
                }

            }
            default -> {
                CreateProgramDataDBTablesMySQL();
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

    private void ExitandClose() {
        DBMSAP.Close();
        DBMSEP.Close();
    }

    private void CreateDBs() {
        /*
        switch (PConfig.getDatabaseManagerinUse()) {
            case DB_MYSQL_Selected -> {
                CreateProgramDataDBTablesMySQL();
            }
            case DB_SQLite_Selected -> {
                CreateProgramDataDBTablesSQLIte();
            }
            case DB_PostgreSQL_Selected -> {
                CreateProgramDataDBTablesPostgreSQL();
            }
            default -> {
                CreateProgramDataDBTablesMySQL();
            }

        }*/
        CreateProgramDataDBTables();
    }

    private void ConfigureDB() {
        Configure_DBInfo();
    }

}
